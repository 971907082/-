package org.wlxy.example.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;


@ApiModel(value = "user" ,description = "用户实体类")//@ApiModel：用在模型类上，对模型类做注释
@Data
public class User implements Serializable {//让我们的User类继承一个可视化序列接口
    @ApiModelProperty(value="用户名" ,name = "userName")//@ApiModelProperty用在属性上，对属性做注释
    //一般自己用get，set来生成，这里用另一种方法隐式生成
    //使用注解前，需要新引入一个jar包
    @NotEmpty(message = "此用户名不能为空")//跟valid相对应
    @Size(max = 12,min = 6,message = "用户名长度必须是6-12位")
    private  String userName;

    @ApiModelProperty(value="用户密码" ,name = "password")//@ApiModelProperty用在属性上，对属性做注释
//    @Size(min = 5,max = 10,message = "密码")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,10})$",message = "密码必须是包含数字和字母的8到10位")
    private  String password;

//    @Valid
//    private Banji banji;
//    public static void main(String[] args) {
//        User user = new User();
//        user.setUserName("张三");
//        user.setPassword("123");
//
//        System.out.println(user);
//    }
    @ApiModelProperty(value="用户主键" ,name = "id")//@ApiModelProperty用在属性上，对属性做注释
    private int id;
    @ApiModelProperty(value="用户角色Id" ,name = "roleId")//@ApiModelProperty用在属性上，对属性做注释
    @Null(message = "不允许直接修改用户角色")
    private String  roleId;

    @ApiModelProperty(value="用户邮箱" ,name = "email")//@ApiModelProperty用在属性上，对属性做注释
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value="是否被激活" ,name = "isActive")//@ApiModelProperty用在属性上，对属性做注释
    private int isActive;

    @ApiModelProperty(value="用户收货地址" ,name = "deliveryAdress")//@ApiModelProperty用在属性上，对属性做注释
    private String deliveryAdress;

    @ApiModelProperty(value="用户头像" ,name = "myPic")
    private String myPic;
}
