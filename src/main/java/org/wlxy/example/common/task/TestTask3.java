package org.wlxy.example.common.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
//@EnableScheduling
@Slf4j
public class TestTask3 {

    @Async//如果没有开启异步操作 2s执行完后需要等待
    @Scheduled(fixedRate = 500)
    public  void  task1() throws InterruptedException {
        Thread.sleep(2000);
        log.info(Thread.currentThread().getName()+"在执行任务");//任务每5s执行一次，但是执行一次需要2s
    }

//    @Async
//    @Scheduled(fixedRate = 500)
//    public  void  task2(){
//
//        log.info(Thread.currentThread().getName()+"在执行任务");
//
//    }
}
