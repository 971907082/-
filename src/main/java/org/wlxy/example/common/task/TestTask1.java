package org.wlxy.example.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration  //主要用于标记配置类，兼备component的效果
//@EnableScheduling //开启定时任务
@Slf4j
public class TestTask1 {
//    @Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(fixedDelay = 5000)
    @Scheduled(fixedRate = 5000)
      public  void  Task1(){
        log.info("我是task1");
      }
}
