package org.wlxy.example.service;

import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.ShoppingCar;

public interface ShoppingCarService {



	public Object getAllShoppingCar(PageParam<ShoppingCar> pageParam);

    public boolean removeShoppingCarById(int id);

    public Object addShoppingCar(ShoppingCar shoppingCar);

    public boolean updateShoppingCar(ShoppingCar shoppingCar);

    public ShoppingCar getShoppingCarById(int id);




}