package org.wlxy.example.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.wlxy.example.model.Logistics;
import org.wlxy.example.model.Orderhead;

import java.util.List;

public interface LogisticsDao {


    List<Logistics> getAllLogistics(Logistics logistics);

    @Delete("delete from logistics where userName = #{userName}")
    boolean removeLogisticsByUserName(String userName);

    int addLogistics(Logistics logistics);

    int updateLogistics(Logistics logistics);

    @Select("select * from logistics where id =#{id}")
    List<Logistics> getLogisticsById(int id);

    @Select("select * from logistics where userName =#{userName}")
    List<Logistics> getLogisticsByuserName(String userName);





}