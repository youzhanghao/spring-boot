package com.demo.springUtil.aop;

import java.lang.annotation.*;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/24 上午12:53
 */

/**
 * 注解本身是没用功能的，和xml一样属于元数据，属于配置
 */
// 拦截规则注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String name();
}
