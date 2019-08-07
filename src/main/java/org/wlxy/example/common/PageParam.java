package org.wlxy.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "PageParam", description = "分页条件参数")//@ApiModel用在模型类上，对模型类做注释
@Data
public class PageParam<Model> {


    @ApiModelProperty(value = "条件参数",name = "model")//@ApiModel用在属性上，对属性做注释
    private Model model;

    @ApiModelProperty(value = "排序参数",name = "orderParams")//@ApiModel用在属性上，对属性做注释
    private String[] orderParams;

    @ApiModelProperty(value = "页码",name = "pageNum")//@ApiModel用在属性上，对属性做注释
    private int pageNum;

    @ApiModelProperty(value = "每页记录条数",name = "pageSize")//@ApiModel用在属性上，对属性做注释
    private int pageSize;
}
