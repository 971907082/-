package org.wlxy.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wlxy.example.config.SystemConfig;


//1.控制层注解
@Controller
@SpringBootApplication
@Slf4j
@MapperScan({"org.wlxy.example.dao","org.wlxy.example.common.task"})
//@EnableScheduling //在这里可以统一管理定时任务
public class ExampleApplication {
    // 给它一个键值
    @Value("${app.info}")
    private String appInfo;

    // 2.@Autowired
    // private Environment environment;
    @Autowired
    SystemConfig systemConfig;

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @ResponseBody
    //接口的访问路径
    @RequestMapping(value = "/hello", method = RequestMethod.GET)//3
    public String hello() {
// return "8888888";
// 测试异常      System.out.println(1/0);
        return systemConfig.toString();
    }
}
