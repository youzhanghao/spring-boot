package com.demo.springUtil.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/24 上午1:18
 */
@Configuration
@ComponentScan("com.demo.springUtil.aop")
// 开启Aspect支持
@EnableAspectJAutoProxy
public class AopConfig {
}
