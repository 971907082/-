package org.wlxy.example.dao;

import org.apache.ibatis.annotations.*;
import org.wlxy.example.model.ShoppingCar;

import java.util.List;

public interface ShoppingCarDao {


    List<ShoppingCar> getAllShoppingCar(ShoppingCar shoppingCar);

    @Delete("delete from shoppingcar where id = #{id}")
    int removeShoppingCarById(int id);

    int addShoppingCar(ShoppingCar shoppingCar);

    int updateShoppingCar(ShoppingCar shoppingCar);

    @Select("select * from shoppingcar where id =#{id}")
    ShoppingCar getShoppingCarById(int id);

//    @Select("select * from shoppingcar where shoppingcarName =#{shoppingcarName}")
//    ShoppingCar getShoppingCarByShoppingCarName(String ShoppingCarName);

}