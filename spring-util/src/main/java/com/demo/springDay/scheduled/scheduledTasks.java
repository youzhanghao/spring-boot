package com.demo.springUtil.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/6/8 上午11:33
 */
@Configuration
//@EnableScheduling
public class scheduledTasks {

    private static  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(scheduledTasks.class);

    /**
     * 每隔5s执行
     */
//    @Scheduled(fixedRate = 5000, zone = "Asia/Shanghai")
    public void reportCurrentTime(){

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

        dateFormat.setTimeZone(timeZone);

        logger.info(">>>现在时间:{}<<<",dateFormat.format(new Date()));

        timeZone = TimeZone.getTimeZone("America/Los_Angeles");

        dateFormat.setTimeZone(timeZone);

        logger.info(">>>洛杉矶时间：{}<<<" , dateFormat.format(new Date()));
    }


}
