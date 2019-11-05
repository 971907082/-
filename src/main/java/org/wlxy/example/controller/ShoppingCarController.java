package org.wlxy.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.MyRsp;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.ShoppingCar;
import org.wlxy.example.service.ShoppingCarService;


import javax.validation.Valid;

@Api(value = "shoppingCar模块接口",description = "这是一个购物车模块的接口文档")
@RequestMapping("shoppingCar")
@RestController
@Slf4j
@CrossOrigin
public class ShoppingCarController {

	@Autowired
    ShoppingCarService shoppingCarService;

	@ApiOperation("查询所有购物车 支持多条件分页排序查询")
    @PostMapping("/getAllShoppingCar")
    public Object getAllShoppingCar(@RequestBody PageParam<ShoppingCar> pageParam){
        return MyRsp.success(shoppingCarService.getAllShoppingCar(pageParam)).msg("查询成功");
    }

    @GetMapping("/removeShoppingCarById/{id}")
    public Object removeShoppingCarById(@PathVariable("id") int id){

        return shoppingCarService.removeShoppingCarById(id)? MyRsp.success(null).msg("删除成功"): MyRsp.error().msg("删除失败");
    }

    @PostMapping("/addShoppingCar")
    public Object addShoppingCar(@RequestBody @Valid ShoppingCar shoppingCarParam){
        ShoppingCar shoppingCar=(ShoppingCar)shoppingCarService.addShoppingCar(shoppingCarParam);

        return shoppingCar!=null? MyRsp.success(shoppingCar).
                msg("添加成功"): MyRsp.error().msg("添加失败");
    }


    @PutMapping("/updateShoppingCar")
    public Object updateShoppingCar(@RequestBody@Valid ShoppingCar shoppingCar){
        return shoppingCarService.updateShoppingCar(shoppingCar)? MyRsp.success(null)
                .msg("修改成功"): MyRsp.error().msg("修改失败");
    }

    @GetMapping("/getShoppingCarById/{id}")
    public Object getShoppingCarById(@PathVariable("id") int id){
        ShoppingCar shoppingCar=shoppingCarService.getShoppingCarById(id);
        return shoppingCar!=null? MyRsp.success(shoppingCar): MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }
	
}