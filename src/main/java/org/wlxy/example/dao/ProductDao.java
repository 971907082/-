package org.wlxy.example.dao;


import org.wlxy.example.model.Product;


import java.util.List;

//@Mapper
public interface ProductDao {
    List<Product> getAllProduct(Product product);

    int addProduct(Product product);

    int updateProduct(Product product);

//    @Select("select * from product where id =#{id}")
    Product getProductById(int id);

//    @Select("select * from product where productName =#{productName}")
    Product getProductByProductName(String productName);

//    @Delete("delete from product where id = #{id}")
    int removeProductById(int id);
}
