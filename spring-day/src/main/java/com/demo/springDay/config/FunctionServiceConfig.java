package com.demo.springUtil.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/23 下午11:43
 */

/**
 * 声明当前为配置类
 */
@Configuration
/**
 * 包扫描指定 自动扫描该包下所有的@Service @Component @Controller @Repository
 */
@ComponentScan("com.demo.springUtil")
public class FunctionServiceConfig {
}
