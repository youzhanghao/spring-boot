package com.demo.springboot.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/6/8 上午11:33
 */
@Configuration
@EnableScheduling
public class scheduledTasks {

    private static  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 每隔5s执行
     */
    @Scheduled(fixedRate = 5000, zone = "Asia/Shanghai")
    public void reportCurrentTime(){
        TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar calendar = Calendar.getInstance(timeZone);

        System.out.println("时区："+ timeZone);

        System.out.println("现在时间："+dateFormat.format(new Date()));

        dateFormat.setTimeZone(timeZone);
        System.out.println("洛杉矶时间：" + dateFormat.format(new Date()));
    }


}
