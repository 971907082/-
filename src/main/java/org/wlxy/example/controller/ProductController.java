package org.wlxy.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.MyRsp;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.Product;
import org.wlxy.example.model.ProductType;
import org.wlxy.example.service.ProductService;
import org.wlxy.example.service.ProductTypeService;

import javax.validation.Valid;

@Api(value = "product模块接口",description = "这是一个商品模块的接口文档")//用在controller上，对controller进行注释
@RequestMapping("product")
@RestController //所有接口的参数都已json格式返回
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("查询所有商品支持多条件")//用在API方法上，对该API做注释，说明API的作用；
    @RequestMapping(value = "/getAllProduct", method = RequestMethod.POST)
    public Object getAllProduct(@RequestBody PageParam<Product> pageParam) {

        return MyRsp.success(productService.getAllProduct(pageParam));
    }

    @ApiOperation("添加商品")
    //添加(新学注解 是requestMapping的升级版)
    //@Valid 校验注解
    @PostMapping("/addProduct" )
    public Object addProduct(@RequestBody @Valid Product product) {

        //以下两句是为了测试h缓存@CachePut注解的功能，不然就只写上面一句
        Product p = (Product) productService.addProduct(product);
        return p!=null?MyRsp.success(p).
                msg("添加成功"):MyRsp.error().msg("添加失败");

    }

    @ApiOperation("删除商品")
    //根据商品类型id进行删除
    // @PathVariable 将路径中的值映射到参数上
    @RequestMapping(value = "/removeProductById/{id}",method = RequestMethod.GET)
    public Object removeProductById(@PathVariable("id") int id){
        return productService.removeProductById(id)?MyRsp.success(null):MyRsp.error().msg("删除失败");
    }

    @ApiOperation("修改商品")
    //修改
    //传数据过来要用@RequestBody接收
    @PutMapping("/updateProduct")
//    @Valid
    public Object updateProduct(@RequestBody Product product) {


//        user.setUserName(null);
//        User user1 = userService.getUserById(user.getId());
//        user.setIsActive(user1.getIsActive()); // 将userName和isActive设置死
        return productService.updateProduct(product)?MyRsp.success(null)
                .msg("修改成功"):MyRsp.error().msg("修改失败");
    }

    @ApiOperation("根据id查询商品类型")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/getProductById/{id}")
    public Object getProductById(@PathVariable("id") int id){

        Product product=productService.getProductById(id);
        return product!=null?MyRsp.success(product):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }

}
