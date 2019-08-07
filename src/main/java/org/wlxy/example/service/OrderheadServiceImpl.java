package org.wlxy.example.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.dao.OrderheadDao;
import org.wlxy.example.model.Orderdetail;
import org.wlxy.example.model.Orderhead;
import org.wlxy.example.model.Product;
import org.wlxy.example.model.ShoppingCar;


import java.util.List;

@Slf4j
@Service
@Transactional
public class OrderheadServiceImpl implements OrderheadService {


	@Autowired
    OrderheadDao orderheadDao;

	@Autowired
    ProductService productService;

	@Autowired
    OrderdetailService orderdetailService;

    @Autowired
    ShoppingCarService shoppingCarService;

	@Override
    @Transactional(readOnly = true)
	public Object getAllOrderhead(PageParam<Orderhead> pageParam){
    
    	PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
        for(int i=0;i<pageParam.getOrderParams().length;i++){
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }


        List<Orderhead> orderheadList=orderheadDao.getAllOrderhead(pageParam.getModel());
        PageInfo<Orderhead> orderheadPageInfo = new PageInfo<Orderhead>(orderheadList);

        return orderheadPageInfo;
    
    }

//	@CacheEvict(value = "orderheads",key = "#p0")
    @Override
    public boolean removeOrderheadById(int id){
    	return orderheadDao.removeOrderheadById(id)==1;
    }

//	@CachePut(value = "orderheads",key = "#p0.id")
    @Override
    public Object addOrderhead(Orderhead orderhead){
        orderheadDao.addOrderhead(orderhead);

        return orderheadDao.getOrderheadById(orderhead.getId());
    }

//    @CacheEvict(value = "orderheads",key = "#p0.id")
	@Override
    public boolean updateOrderhead(Orderhead orderhead){
    	if(StringUtils.isEmpty(orderhead.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改orderhead时，id不能为空");
        }

        return orderheadDao.updateOrderhead(orderhead)==1;
    }

//	@Cacheable(key = "#p0",value="orderheads")
    @Override
    @Transactional(readOnly = true)
    public Orderhead getOrderheadById(int id){
    	return orderheadDao.getOrderheadById(id);
    	
    }

    /**
     * static
     */
    @Override
    public Orderhead createOrder(List<ShoppingCar> shoppingCarList) {

	    if(shoppingCarList.size()==0){
	        throw new MyException(HttpCode.ERROR).msg("生成订单失败，原因购物车数据长度0");
        }
	    // 给需要的每一个字段赋初始值
	    int totalProductCount=0;
        String firstProductName=shoppingCarList.get(0).getProductName();
        String firstProductImg=shoppingCarList.get(0).getProductImg();
        double totalPrice=0.0;
        int userId=shoppingCarList.get(0).getUserId();


        //订单表头数据  初始化
        Orderhead orderhead = new Orderhead();

        Product product;// 创建一个临时存放的商品数据的对象
        double discount;// 折扣价格
        double killDiscount; // 秒杀的折扣价
        double discountTotal=0;
        double killDiscountTotal=0;
        for(ShoppingCar shoppingCar:shoppingCarList){
             // 订单商品总件数 = shoppingCar里面的产品数量的累加和
            totalProductCount+=shoppingCar.getProductNum();

            // 为了计算价格我们先将产品查询出来
            product=productService.getProductById(shoppingCar.getProductId());

            if(product==null){
                throw new MyException(HttpCode.ERROR).msg("您下单的商品查询不到");
            }
            if(product.getDeserveNum()<shoppingCar.getProductNum()){
                throw new MyException(HttpCode.ERROR).msg("您下单的商品"+shoppingCar.getProductName()+"库存不足！下单失败！");
            }

            // 先判断它到底参不参加折扣活动 参加就给他一个价格 不参加就置0
            discount = product.getIsInDiscount()==2?product.getDiscount():0;

            // 先判断它到底参不参加秒杀活动
            killDiscount=product.getIsInKill()==2?product.getKillDiscount():0;
            // 最后计算价格 正常价格 减去 折扣价 减去 秒杀价 乘以产品数量
            totalPrice+=(product.getNormalPrice()-discount-killDiscount)*shoppingCar.getProductNum();
            //计算总折扣
            discountTotal+=discount*shoppingCar.getProductNum();
            killDiscountTotal+=killDiscount*shoppingCar.getProductNum();
        }

        // 添加订单表头到数据库
        orderhead.setKillDiscount(killDiscountTotal);
        orderhead.setDiscount(discountTotal);
        orderhead.setFirstProductImg(firstProductImg);
        orderhead.setFirstProductName(firstProductName);
        orderhead.setTotalPrice(totalPrice);
        orderhead.setTotalProductCount(totalProductCount);
        orderhead.setUserId(userId);

        // sql  add
        orderheadDao.addOrderhead(orderhead); //将对象传到orderheadDao里面去

        log.info("order head  id:"+orderhead.getId());
        // 如果此时订单表头创建成功  需要及时减去所有库存
        for(ShoppingCar shoppingCar:shoppingCarList){

            product=productService.getProductById(shoppingCar.getProductId());
            if(product==null){
                throw new MyException(HttpCode.ERROR).msg("您下单的商品查询不到");
            }
            //计算新库存
            int newDeserveNum = product.getDeserveNum()-shoppingCar.getProductNum();
            // 减掉库存
            product.setDeserveNum(newDeserveNum);
            product.setCreateTime(null);
            // sql update
            boolean flag = productService.updateProduct(product);
            if(!flag){
                throw new MyException(HttpCode.ERROR).msg("库存扣取操作失败");
            }
        }


        // 添加订单详情到数据库(后期如果一个订单商品数量过多  可以将该方法设为异步操作 如果是分布式架构可采用消息队列提高后台响应速度)
        Orderdetail orderdetail;
        // 创建一个变量  存放插入成功的记录条数
        int successInsert=0;
        for(ShoppingCar shoppingcar:shoppingCarList){
            orderdetail = new Orderdetail();
            BeanUtils.copyProperties(shoppingcar,orderdetail);
            orderdetail.setOrderId(orderhead.getId());
            orderdetail.setId(0);
            // sql  add
            successInsert +=(orderdetailService.addOrderdetail(orderdetail)!=null?1:0);

        }

        if(successInsert!=shoppingCarList.size()){
            throw new MyException(HttpCode.ERROR).msg("订单详情数据录入不完整");
        }

        //订单生成成功  需要清空已被结算的购物车的商品
        for(ShoppingCar shoppingcar:shoppingCarList){
            shoppingCarService.removeShoppingCarById(shoppingcar.getId());
        }

        return orderheadDao.getOrderheadById(orderhead.getId());
    }


}
