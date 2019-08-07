package org.wlxy.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.common.HttpCode;
import org.wlxy.example.common.MyException;
import org.wlxy.example.common.MyRsp;
import org.wlxy.example.common.PageParam;
import org.wlxy.example.model.User;
import org.wlxy.example.service.UserService;

import javax.validation.Valid;

@Api(value = "user模块接口",description = "这是一个用户模块的接口文档")//用在controller上，对controller进行注释
@RequestMapping("user")
@RestController //所有接口的参数都已json格式返回
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("查询所有用户支持多条件")//用在API方法上，对该API做注释，说明API的作用；
    //查询全部信息
   // 因为加了@RestController所以不用写此注解@ResponseBody
    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
//    @RequiresRoles("admin")//角色注解
//    @RequiresPermissions("general")
//    查询全部 public Object getAllUser(@RequestBody(required = false) User u) {
    public Object getAllUser(@RequestBody PageParam<User> pageParam) {
//        1.return UserData.userList;
//        2.return MyRsp.success(UserData.userList);

        return MyRsp.success(userService.getAllUser(pageParam));
    }

    @ApiOperation("添加用户")
    //添加(新学注解 是requestMapping的升级版)
    //@Valid 校验注解
    @PostMapping("/addUser" )
    public Object addUser(@RequestBody @Valid User user) {
//        2.UserData.userList.add(user);
////        1.return UserData.userList;
//        2.return MyRsp.success(UserData.userList);
//        *return userService.addUser(user)?MyRsp.success(null).msg("添加成功"):MyRsp.error().msg("添加失败");

        //以下两句是为了测试h缓存@CachePut注解的功能，不然就只写上面一句
        User u=(User)userService.addUser(user);
        return u!=null?MyRsp.success(u).
                msg("添加成功"):MyRsp.error().msg("添加失败");

    }

    @ApiOperation("删除用户")
    //根据用户名进行删除
    // @PathVariable 将路径中的值映射到参数上
    ///2.removeUserByUserName/{userName}
    @RequestMapping(value = "/removeUserById/{id}",method = RequestMethod.GET)
    public Object removeUserByUserName(@PathVariable("id") int id){

//        2.for (int i=0;i<UserData.userList.size();i++){
//            if(UserData.userList.get(i).getUserName().equals(userName)){
//                UserData.userList.remove(i);
//            }
//        }
////       1. return UserData.userList;
//       2.return MyRsp.success(UserData.userList);
        return userService.removeUserById(id)?MyRsp.success(null):MyRsp.error().msg("删除失败");
    }

    @ApiOperation("修改用户")
    //修改
    //传数据过来要用@RequestBody接收
    @PutMapping("/updateUser")
//    @Valid
    public Object updateUser(@RequestBody User user) {
//        2.for (int i = 0; i < UserData.userList.size(); i++) {
//            if (UserData.userList.get(i).getUserName().equals(user.getUserName())) {
//                UserData.userList.get(i).setPassword(user.getPassword());
//            }
//        }
////        1.return UserData.userList;
//        2.return MyRsp.success(UserData.userList);

        user.setUserName(null);
        User user1 = userService.getUserById(user.getId());
        user.setIsActive(user1.getIsActive()); // 将userName和isActive设置死

        return userService.updateUser(user)?MyRsp.success(null)
                .msg("修改成功"):MyRsp.error().msg("修改失败");
    }

    @ApiOperation("根据id查询用户")//可以给文档显示的方法名后面加中文解释
    @GetMapping("/getUserById/{id}")
    public Object getUserById(@PathVariable("id") int id){

        User user=userService.getUserById(id);
        return user!=null?MyRsp.success(user):MyRsp.wrapper(new MyException(HttpCode.ITEM_NOT_FOUND));
    }
}
