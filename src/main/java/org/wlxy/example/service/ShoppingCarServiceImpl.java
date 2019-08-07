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
import org.wlxy.example.dao.ShoppingCarDao;

import org.wlxy.example.model.ShoppingCar;


import java.util.List;

@Slf4j
@Service
@Transactional
public class ShoppingCarServiceImpl implements ShoppingCarService {


	@Autowired
    ShoppingCarDao shoppingCarDao;

	@Override
    @Transactional(readOnly = true)
	public Object getAllShoppingCar(PageParam<ShoppingCar> pageParam){
    
    	PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
        for(int i=0;i<pageParam.getOrderParams().length;i++){
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }


        List<ShoppingCar> shoppingCarList=shoppingCarDao.getAllShoppingCar(pageParam.getModel());
        PageInfo<ShoppingCar> shoppingCarPageInfo = new PageInfo<ShoppingCar>(shoppingCarList);

        return shoppingCarPageInfo;
    
    }

//	@CacheEvict(value = "shoppingCars",key = "#p0")
    @Override
    public boolean removeShoppingCarById(int id){
    	return shoppingCarDao.removeShoppingCarById(id)==1;
    }

//	@CachePut(value = "shoppingCars",key = "#p0.id")
    @Override
    public Object addShoppingCar(ShoppingCar shoppingCar){
        shoppingCarDao.addShoppingCar(shoppingCar);

        return shoppingCarDao.getShoppingCarById(shoppingCar.getId());
    }

	@Override
    public boolean updateShoppingCar(ShoppingCar shoppingCar){
    	if(StringUtils.isEmpty(shoppingCar.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改shoppingcar时，id不能为空");
        }

        return shoppingCarDao.updateShoppingCar(shoppingCar)==1;
    }

//	@Cacheable(key = "#p0",value="shoppingcars")
    @Override
    @Transactional(readOnly = true)
    public ShoppingCar getShoppingCarById(int id){
    	return shoppingCarDao.getShoppingCarById(id);
    	
    }




}
