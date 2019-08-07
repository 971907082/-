package org.wlxy.example.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.dao.LogisticsDao;
import org.wlxy.example.dao.UserDao;
import org.wlxy.example.model.*;

import java.util.List;

@Slf4j
@Service
@Transactional //事务处理
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    LogisticsDao logisticsDao;
    @Autowired
    ShoppingCarService shoppingCarService;
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    //查询全部支持多条件查询
    @Override
    public Object getAllLogistics(PageParam<Logistics> pageParam) {//不建议在此方法上加缓存

        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize()); //分页参数注入

        //排序 分页 联合查询
        for(int i=0;i<pageParam.getOrderParams().length;i++){
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }


        List<Logistics> logisticsList=logisticsDao.getAllLogistics(pageParam.getModel());
        PageInfo<Logistics> logisticsInfo = new PageInfo<Logistics>(logisticsList);
        return logisticsInfo;
    }

    //删除
//    @CacheEvict(value = "users",key = "#p0")//@CacheEvict同时删除数据库和缓存中的值
    @Override
    public boolean removeLogisticsByUserName(String userName) {
        return logisticsDao.removeLogisticsByUserName(userName);
    }

    //增加
//    @CachePut(value = "users",key = "#p0.id")//@CachePut无论有没有缓存都将放回的返回结果放到缓存当中
    @Override
    //*boolean
    @Transactional//事务处理
    public Object addLogistics(Logistics logistics) {//把返回值boolean换成object是为了测试h缓存@CachePut注解的功能
        logisticsDao.addLogistics(logistics);
// 为了测试事务       System.out.println(1/0);
        return logisticsDao.getLogisticsById(logistics.getId());
    }

    //修改
    @Override
    public boolean updateLogistics(Logistics logistics) {

        if(StringUtils.isEmpty(logistics.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改物流信息时，id不能为空");
        }

        return logisticsDao.updateLogistics(logistics)==1;
    }


    //#p0代表参数的索引为0就是第一个参数，缓存的一个键
//    @Cacheable(key = "#p0",value = "users") //@Cacheable 缓存注解 在service实现类里添加注解（缓存没有就走数据库查询并返回user，有就直接走缓存）
    @Override
    public List<Logistics> getLogisticsById(int id) {
        return logisticsDao.getLogisticsById(id);
    }

    @Override
    public List<Logistics> getLogisticsByuserName(String userName) {
        return  logisticsDao.getLogisticsByuserName(userName);
    }


    // 实现生成物流信息的方法
    @Override
    public List<Logistics> createLogistics(List<ShoppingCar> shoppingCarList) {
        //判断传过来的shoppingCarList是不是为空
        if(shoppingCarList.size()==0){
            throw new MyException(HttpCode.ERROR).msg("传过来的购物车位空");
        }
        User user = userDao.getUserById(shoppingCarList.get(0).getUserId());
        //循环的把购物车的东西放进物流表
        int i =0;
        for (ShoppingCar shoppingCar :shoppingCarList){

            Logistics logistics = new Logistics();
            BeanUtils.copyProperties(shoppingCar,logistics);

            logistics.setUserName(user.getUserName());
            logistics.setEmail(user.getEmail());
            if(logisticsDao.addLogistics(logistics)==1){
               i+=1; //记录一下添加进去的物流信息数
            }
        }
        if (i!=shoppingCarList.size()){
            throw  new  MyException(HttpCode.ERROR).msg("物流信息录入不完整");
        }

        return  logisticsDao.getLogisticsById(shoppingCarList.get(0).getUserId());
    }


}
