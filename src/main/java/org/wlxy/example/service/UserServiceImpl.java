package org.wlxy.example.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
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
import org.wlxy.example.dao.UserDao;
import org.wlxy.example.model.User;
import sun.rmi.runtime.Log;

import java.security.Key;
import java.util.List;

@Slf4j
@Service
@Transactional //事务处理
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    //查询全部支持多条件查询
    @Override
    public Object getAllUser(PageParam<User> pageParam) {//不建议在此方法上加缓存

        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize()); //分页参数注入

        //排序 分页 联合查询
        for(int i=0;i<pageParam.getOrderParams().length;i++){
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }


        List<User> userList=userDao.getAllUser(pageParam.getModel());
        PageInfo<User> userPageInfo = new PageInfo<User>(userList);
        return userPageInfo;
    }

    //根据id删除
//    @CacheEvict(value = "users",key = "#p0")//@CacheEvict同时删除数据库和缓存中的值
    @Override
    public boolean removeUserById(int id) {
        return userDao.removeUserById(id)==1;
    }

    //增加
//    @CachePut(value = "users",key = "#p0.id")//@CachePut无论有没有缓存都将放回的返回结果放到缓存当中
    @Override
    //*boolean
    @Transactional//事务处理
    public Object addUser(User user) {//把返回值boolean换成object是为了测试h缓存@CachePut注解的功能
        user.setRoleId("general");
//        *return userDao.addUser(user)==1;
        //以下两句是为了测试h缓存@CachePut注解的功能，不然就只写上面一句
        userDao.addUser(user);
// 为了测试事务       System.out.println(1/0);
        return userDao.getUserById(user.getId());
    }

    //修改user
    @Override
    public boolean updateUser(User user) {

        if(StringUtils.isEmpty(user.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改用户时，id不能为空");
        }

        return userDao.updateUser(user)==1;
    }

    //根据id查询用户
    //#p0代表参数的索引为0就是第一个参数，缓存的一个键
    @Cacheable(key = "#p0",value = "users") //@Cacheable 缓存注解 在service实现类里添加注解（缓存没有就走数据库查询并返回user，有就直接走缓存）
    @Override
    public User getUserById(int id) {
        log.info("这里走的是数据库查询");
        return userDao.getUserById(id);
    }

//    @Override
////    public User getUserById(int id) {
////        return userDao.getUserById(id);
////    }

    //用户登陆
    @Override
    public User login(String userName, String password) {

        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            return null;
        }
        User condition=new User();
        condition.setUserName(userName);
        condition.setPassword(password);

        List<User> userList=userDao.getAllUser(condition);
        User user=null;
        if(userList.size()!=0){
            user=userList.get(0);
        }

        return user;
    }

    @Override
    public User register(User user) {
        user.setIsActive(0);
        user.setRoleId("general");
        userDao.addUser(user);
        return userDao.getUserById(user.getId());
    }

    @Override
    public User userNameIsReged(String userName) {


        return userDao.getUserByUserName(userName);
    }

    @Override
    public User getUserByEmail(String email) {


        return userDao.getUserByEmail(email);
    }


}
