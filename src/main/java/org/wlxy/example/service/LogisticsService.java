package org.wlxy.example.service;

import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.Logistics;
import org.wlxy.example.model.Orderhead;
import org.wlxy.example.model.ShoppingCar;

import java.util.List;


public interface LogisticsService {



	public Object getAllLogistics(PageParam<Logistics> pageParam);

    public boolean removeLogisticsByUserName(String userName);

    public Object addLogistics(Logistics logistics);

    public boolean updateLogistics(Logistics logistics);

    public List<Logistics> getLogisticsById(int id);

    public List<Logistics> getLogisticsByuserName(String userName);
    //写个接口生成物流信息
    List<Logistics> createLogistics(List<ShoppingCar> shoppingCarList);


//    Orderhead createOrder(List<ShoppingCar> shoppingCarList);
}