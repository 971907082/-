 package org.wlxy.example.common.task;

 import lombok.extern.slf4j.Slf4j;
 import org.apache.ibatis.annotations.Mapper;
 import org.apache.ibatis.annotations.Select;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.scheduling.annotation.EnableScheduling;
 import org.springframework.scheduling.annotation.SchedulingConfigurer;
 import org.springframework.scheduling.config.ScheduledTaskRegistrar;
 import org.springframework.scheduling.support.CronTrigger;
 import org.springframework.stereotype.Component;

 @Component
 @Configuration
// @EnableScheduling
 @Slf4j
public class TestTask2 implements SchedulingConfigurer {


     //创建mapper
     @Mapper
     public  interface CronMapper{
     @Select("select cron_expresion from cron where id=#{id}")
     public String getCronById(String id);
     }

     //注入到类里
     @Autowired
     CronMapper cronMapper;


     @Override
     public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {//定时任务的注册参数
      scheduledTaskRegistrar.addTriggerTask(
              ()->log.info("执行的是动态定时任务"), //定时任务执行的内容
              triggerContext -> { // 定时任务执行的时间  从数据库动态获取
               String cron = cronMapper.getCronById("excute_per_second");
               return  new CronTrigger(cron).nextExecutionTime(triggerContext);
              }
      );
     }

 }

