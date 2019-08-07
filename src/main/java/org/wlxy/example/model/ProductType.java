package org.wlxy.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "productType" ,description = "商品分类实体类")//@ApiModel：用在模型类上，对模型类做注释
@Data
public class ProductType implements Serializable {//让我们的User类继承一个可视化序列接口


    @ApiModelProperty(value="商品分类主键" ,name = "id")//@ApiModelProperty用在属性上，对属性做注释
    private int id;

    @ApiModelProperty(value="产品分类的名称" ,name = "productTypeName")//@ApiModelProperty用在属性上，对属性做注释
    private String  productTypeName;

    @ApiModelProperty(value="浏览次数" ,name = "viewNum")//@ApiModelProperty用在属性上，对属性做注释
    private int viewNum;

    @ApiModelProperty(value="图片展示" ,name = "typeImg")
    private String typeImg;

    @ApiModelProperty(value="创建时间" ,name = "createTime")
    private Date createTime;

    @ApiModelProperty(value="商品类型详情" ,name = "details")
    private String details;
}
