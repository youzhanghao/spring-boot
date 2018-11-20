package com.demo.springUtil.service;

import org.springframework.stereotype.Service;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Desc 功能类的service
 * @Date 2018/8/23 下午11:23
 */
@Service
public class FunctionService {

    public String sayHello(String word){
        return "hello"+word;
    }
}
