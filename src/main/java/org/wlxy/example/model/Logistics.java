package org.wlxy.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "Logistics" ,description = "物流表")
@Data  // 自动生成get set 和构造器
public class Logistics implements Serializable {
    // 主键id
    @ApiModelProperty(value = "主键id" ,name = "id")
    private Integer id;
    // 用户的id
    @ApiModelProperty(value = "用户的id" ,name = "userId")
    private Integer userId;
    // 用户姓名
    @ApiModelProperty(value = "用户姓名" ,name = "userName")
    private String userName;
    // 联系邮箱
    @ApiModelProperty(value = "联系邮箱" ,name = "email")
    private String email;
    // 订单号
    @ApiModelProperty(value = "订单号" ,name = "orderId")
    private Integer orderId;
    // 发货时间
    @ApiModelProperty(value = "发货时间" ,name = "deliveryTime")
    private Date deliveryTime;

    @ApiModelProperty(value = "商品名称" ,name = "productName")
    private String productName;
    @ApiModelProperty(value = "商品名称" ,name = "productImg")
    private String productImg;
    // 到货时间
    @ApiModelProperty(value = "到货时间" ,name = "arriveTime")
    private Date arriveTime;
    // 收货时间
    @ApiModelProperty(value = "收货时间" ,name = "receiveTime")
    private Date receiveTime;

}