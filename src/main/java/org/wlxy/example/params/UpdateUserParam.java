package org.wlxy.example.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.wlxy.example.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "UpdateUserParam" ,description = "用户修改参数")
@Data  // 自动生成get set 和构造器
public class UpdateUserParam extends User implements Serializable  {

    @ApiModelProperty(value = "用户密码" ,name = "password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,10})$",message = "密码必须是包含数字和字母的6到10位")
    private String password;

    @ApiModelProperty(value = "用户主键" ,name = "id")
    private int id;

    @ApiModelProperty(value = "用户邮箱" ,name = "email")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value="用户收货地址" ,name = "deliveryAdress")//@ApiModelProperty用在属性上，对属性做注释
    private String deliveryAdress;

    @ApiModelProperty(value="用户头像" ,name = "myPic")
    private String myPic;
}
