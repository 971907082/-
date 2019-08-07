package org.wlxy.example.service;

import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.ProductType;
import org.wlxy.example.model.User;

import java.util.List;


public interface ProductTypeService {

    //查询
    Object getAllProductType(PageParam<ProductType> pageParam);

    //删除
    boolean removeProductTypeById(int id);

    //增加
    Object addProductType(ProductType productType);

    //更新
    boolean updateProductType(ProductType productType);

    //根据id查找
    ProductType getProductTypeById(int id);

    /**
     *  static
     */
    boolean addProductTypeViewNum(int productTypeId);
}