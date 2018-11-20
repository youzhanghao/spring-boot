package com.demo.springUtil.aop;


import org.springframework.stereotype.Service;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/24 上午12:56
 */
@Service
public class DemoAnnotationService {

    @Action(name = "注解式拦截add操作")
    public void add(){
        System.out.println("开始调用注解add");
    }
}
