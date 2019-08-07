package org.wlxy.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "product" ,description = "商品实体类")//@ApiModel：用在模型类上，对模型类做注释
@Data
public class Product implements Serializable {//让我们的User类继承一个可视化序列接口
    // 主键id
    @ApiModelProperty(value = "主键id" ,name = "id")
    private int id;
    // 商品名称
    @ApiModelProperty(value = "商品名称" ,name = "productName")
    private String productName;
    // 正常情况时的价格
    @ApiModelProperty(value = "正常情况时的价格" ,name = "normalPrice")
    private Double normalPrice;
    //折扣价格
    @ApiModelProperty(value = "折扣价格" ,name = "discount")
    private Double discount;
    // 是否参与折扣活动 1参加 0不参加
    @ApiModelProperty(value = "是否参与折扣活动 " ,name = "isInDiscount")
    private int isInDiscount;
    // 商品分类id
    @ApiModelProperty(value = "商品分类id" ,name = "typeId")
    private int typeId;
    // 上架时间
    @ApiModelProperty(value = "上架时间" ,name = "createTime")
    private Date createTime;
    // 是否参与秒杀活动 1参加 0不参加
    @ApiModelProperty(value = "是否参与秒杀活动" ,name = "isInKill")
    private int isInKill;

    @ApiModelProperty(value = "秒杀的折扣" ,name = "killDiscount")
    private Double killDiscount;

    @ApiModelProperty(value = "商品图片" ,name = "productImg")
    private String productImg;

    @ApiModelProperty(value = "浏览量" ,name = "viewNum")
    private int viewNum;

    @ApiModelProperty(value = "库存量" ,name = "deserveNum")
    private int deserveNum;

    @ApiModelProperty(value = "商品的详情" ,name = "details")
    private String details;

    @ApiModelProperty(value = "下单数" ,name = "orderCount")
    private int orderCount;

    @ApiModelProperty(value = "快递价格" ,name = "deliveryPrice")
    private double deliveryPrice;

    @ApiModelProperty(value = "收获地址" ,name = "deliveryPlace")
    private String deliveryPlace;
}
