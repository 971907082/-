package org.wlxy.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.MyRsp;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.Logistics;
import org.wlxy.example.model.ShoppingCar;
import org.wlxy.example.model.User;
import org.wlxy.example.service.LogisticsService;
import org.wlxy.example.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "logistics模块接口",description = "这是一个物流模块的接口文档")//用在controller上，对controller进行注释
@RequestMapping("logistics")
@RestController //所有接口的参数都已json格式返回
@CrossOrigin
public class LogisticsController {
    @Autowired
    LogisticsService logisticsService;

    @ApiOperation("查询所有物流支持多条件")//用在API方法上，对该API做注释，说明API的作用；
    //查询全部信息
   // 因为加了@RestController所以不用写此注解@ResponseBody
    @RequestMapping(value = "/getAllLogistics", method = RequestMethod.POST)
//    @RequiresRoles("admin")//角色注解
//    @RequiresPermissions("general")
//    查询全部 public Object getAllUser(@RequestBody(required = false) User u) {
    public Object getAllLogistics(@RequestBody PageParam<Logistics> pageParam) {
//        1.return UserData.userList;
//        2.return MyRsp.success(UserData.userList);

        return MyRsp.success(logisticsService.getAllLogistics(pageParam));
    }

    @ApiOperation("添加物流")
    //添加(新学注解 是requestMapping的升级版)
    //@Valid 校验注解
    @PostMapping("/addLogistics" )
    public Object addLogistics(@RequestBody List<ShoppingCar> shoppingCarList) {

        for (ShoppingCar shoppingCar : shoppingCarList){
            System.out.println(shoppingCar);
        }
        return shoppingCarList!=null?MyRsp.success(shoppingCarList).
                msg("添加成功"):MyRsp.error().msg("添加失败");

    }

    @ApiOperation("删除物流")
    @RequestMapping(value = "/removeLogisticsByuserName/{userName}",method = RequestMethod.GET)
    public Object removeLogisticsByUserId(@PathVariable("userName") String userName){
        return logisticsService.removeLogisticsByUserName(userName)?MyRsp.success("删除成功"):MyRsp.error().msg("删除失败");
    }

    @ApiOperation("修改物流")
    //修改
    //传数据过来要用@RequestBody接收
    @PutMapping("/updateLogistics")
//    @Valid
    public Object updateLogistics(@RequestBody Logistics logistics) {
        return logisticsService.updateLogistics(logistics)?MyRsp.success(null)
                .msg("修改成功"):MyRsp.error().msg("修改失败");
    }

    @ApiOperation("根据id查询物流")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/getLogisticsById/{id}")
    public Object getLogisticsById(@PathVariable("id") int id){
        List<Logistics> logistics=logisticsService.getLogisticsById(id);
        return logistics!=null?MyRsp.success(logistics):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }

    @ApiOperation("根据用户名查询物流")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/getLogisticsByuserName/{userName}")
    public Object getLogisticsByuserName(@PathVariable("userName") String userName){
        List<Logistics> logistics=logisticsService.getLogisticsByuserName(userName);
        return logistics!=null?MyRsp.success(logistics):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }

    @ApiOperation("添加物流信息")//可以给文档显示的方法名后面加中文解释
    @PostMapping("/addLogisticss")
    public Object addLogisticss(@RequestBody List<ShoppingCar> shoppingCarList){
        List<Logistics> logistics=logisticsService.createLogistics(shoppingCarList);
        return logistics!=null?MyRsp.success(logistics):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }
}
