package org.wlxy.example.dao;


import org.wlxy.example.model.ProductType;


import java.util.List;

//@Mapper
public interface ProductTypeDao {
    //查询
    List<ProductType> getAllProductType(ProductType productType);

    //删除
    //    @Delete("delete from user where id = #{id}")
    int removeProductTypeById(int id);

    //增加
    //    @Insert("insert into user (userName,password,roleId)values(#{userName},#{password},#{roleId})")
    int addProductType(ProductType productType);

    //更新
    //    @Update("update user set userName=#{userName},password=#{password} where id=#{id}")
    int updateProductType(ProductType productType);

    //根据id查找
    ProductType getProductTypeById(int id);


}
