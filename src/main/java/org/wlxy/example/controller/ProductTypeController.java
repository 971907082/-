package org.wlxy.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.MyRsp;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.ProductType;
import org.wlxy.example.model.User;
import org.wlxy.example.service.ProductTypeService;
import org.wlxy.example.service.UserService;

import javax.validation.Valid;

@Api(value = "productType模块接口",description = "这是一个商品类型模块的接口文档")//用在controller上，对controller进行注释
@RequestMapping("productType")
@RestController //所有接口的参数都已json格式返回
@CrossOrigin
public class ProductTypeController {
    @Autowired
    ProductTypeService productTypeService;

    @ApiOperation("查询所有商品类型支持多条件")//用在API方法上，对该API做注释，说明API的作用；
    @RequestMapping(value = "/getAllProductType", method = RequestMethod.POST)
    public Object getAllProductType(@RequestBody PageParam<ProductType> pageParam) {

        return MyRsp.success(productTypeService.getAllProductType(pageParam));
    }

    @ApiOperation("添加商品类型")
    //添加(新学注解 是requestMapping的升级版)
    //@Valid 校验注解
    @PostMapping("/addProductType" )
    public Object addProductType(@RequestBody @Valid ProductType productType) {

        //以下两句是为了测试h缓存@CachePut注解的功能，不然就只写上面一句
        ProductType p = (ProductType) productTypeService.addProductType(productType);
        return p!=null?MyRsp.success(p).
                msg("添加成功"):MyRsp.error().msg("添加失败");

    }

    @ApiOperation("删除商品类型")
    //根据商品类型id进行删除
    // @PathVariable 将路径中的值映射到参数上
    @RequestMapping(value = "/removeProductTypeById/{id}",method = RequestMethod.GET)
    public Object removeProductTypeById(@PathVariable("id") int id){
        return productTypeService.removeProductTypeById(id)?MyRsp.success(null):MyRsp.error().msg("删除失败");
    }

    @ApiOperation("修改商品类型")
    //修改
    //传数据过来要用@RequestBody接收
    @PutMapping("/updateProductType")
//    @Valid
    public Object updateProductType(@RequestBody ProductType productType) {


//        user.setUserName(null);
//        User user1 = userService.getUserById(user.getId());
//        user.setIsActive(user1.getIsActive()); // 将userName和isActive设置死
        return productTypeService.updateProductType(productType)?MyRsp.success(null)
                .msg("修改成功"):MyRsp.error().msg("修改失败");
    }

    @ApiOperation("根据id查询商品类型")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/getProductTypeById/{id}")
    public Object getProductTypeById(@PathVariable("id") int id){

        ProductType productType=productTypeService.getProductTypeById(id);
        return productType!=null?MyRsp.success(productType):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }

    /**
     * static controller 一个静态的方法
     */
    @ApiOperation("根据id来给ViewNum加数")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/addProductTypeViewNum/{id}")
    public Object addProductTypeViewNum(@PathVariable("id") int ProductTypeId){

        return productTypeService.addProductTypeViewNum(ProductTypeId)
                ?MyRsp.success(null):MyRsp.error().msg("商品分类模块异常");
    }

}
