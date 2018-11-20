package com.demo.springUtil.aop;

import org.springframework.stereotype.Service;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/24 上午12:59
 */
@Service
public class DemoMethodService {
    public void add(){
        System.out.println("开始调用方法add");
    }
}
