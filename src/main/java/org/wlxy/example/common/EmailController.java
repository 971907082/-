package org.wlxy.example.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlxy.example.model.User;
import org.wlxy.example.service.UserService;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping("email")
@CrossOrigin
public class  EmailController {
    @Autowired
    UserService userService;

    @GetMapping ("/sendPassWordToEmail/{email}")
    public Object user(@PathVariable("email") String email) throws Exception {

        User u = userService.getUserByEmail(email);

        if (u != null) {
            if (u.getIsActive() == 1) {
                String mima = (new Random().nextInt(899999) + 100000) + "mm";
                u.setPassword(mima);
                userService.updateUser(u);
                EmailUtil.sendEmail(mima, email);
                return MyRsp.success(null).msg("发送邮件成功，请注意查收");
            } else {
                return MyRsp.error().msg("该邮箱未激活");
            }
        } else {
            return MyRsp.error().msg("该邮箱未注册");
        }

    }

}
