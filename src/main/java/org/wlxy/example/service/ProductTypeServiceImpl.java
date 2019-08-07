package org.wlxy.example.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.dao.ProductTypeDao;
import org.wlxy.example.model.ProductType;


import java.util.List;

@Slf4j
@Service
@Transactional //事务处理
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeDao productTypeDao;

    //查询全部支持多条件查询
    @Override
    public Object getAllProductType(PageParam<ProductType> pageParam) {//不建议在此方法上加缓存

        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize()); //分页参数注入

        //排序 分页 联合查询
        for(int i=0;i<pageParam.getOrderParams().length;i++){
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }


        List<ProductType> productTypeList=productTypeDao.getAllProductType(pageParam.getModel());
        PageInfo<ProductType> productTypePageInfo = new PageInfo<ProductType>(productTypeList);
        return productTypePageInfo;
    }


    //根据id删除
//    @CacheEvict(value = "users",key = "#p0")//@CacheEvict同时删除数据库和缓存中的值
    @Override
    public boolean removeProductTypeById(int id) {
        return productTypeDao.removeProductTypeById(id)==1;
    }

    //增加
//    @CachePut(value = "users",key = "#p0.id")//@CachePut无论有没有缓存都将放回的返回结果放到缓存当中
    @Override
    //*boolean
    @Transactional//事务处理
    public Object addProductType(ProductType productType) {//把返回值boolean换成object是为了测试h缓存@CachePut注解的功能
        productTypeDao.addProductType(productType);
        return productTypeDao.getProductTypeById(productType.getId());
    }


    //修改
    @Override
    @Transactional
    public boolean updateProductType(ProductType productType) {

        if(StringUtils.isEmpty(productType.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改用户时，id不能为空");
        }

        return productTypeDao.updateProductType(productType)==1;
    }

    //根据id查询
    //#p0代表参数的索引为0就是第一个参数，缓存的一个键
//    @Cacheable(key = "#p0",value = "users") //@Cacheable 缓存注解 在service实现类里添加注解（缓存没有就走数据库查询并返回user，有就直接走缓存）
    @Override
    public ProductType getProductTypeById(int id) {
//        log.info("这里走的是数据库查询");
        return productTypeDao.getProductTypeById(id);
    }

    /**
     *  static
     */
    @Override
    public boolean addProductTypeViewNum(int productTypeId) {
        // 原先的点击量
       int viewNumOld = productTypeDao.getProductTypeById(productTypeId).getViewNum();

       // 创建一个新对象
        ProductType productType = new  ProductType();
        productType.setViewNum(++viewNumOld);
        productType.setId(productTypeId);

        return  productTypeDao.updateProductType(productType)==1;
    }


}
