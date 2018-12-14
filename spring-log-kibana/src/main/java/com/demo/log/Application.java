package com.demo.log;

import com.demo.log.config.AutoConfig;
import com.demo.log.config.InterceptorConfig;
import com.demo.log.interceptor.LogInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/12/11 上午10:59
 */
@SpringBootApplication
// componentscan 指定扫描路径 不配置默认会报warning
@ComponentScan("com.demo.log")
// spring boot 默认会想config注入jdbc bean
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    // 显示注入
//    @Bean
//    public InterceptorConfig interceptorConfig(){
//        return  new InterceptorConfig(new LogInterceptor());
//    }
}
