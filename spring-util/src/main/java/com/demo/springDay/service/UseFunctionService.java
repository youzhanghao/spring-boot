package com.demo.springUtil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description 使用功能类的service
 * @date 2018/8/23 下午11:40
 */
// @Service声明当前是spring管理的bean 春天里的豆子
@Service
public class UseFunctionService {
    /**
     * Autowird将当前的实体bean注入
     */
    @Autowired
    private FunctionService functionService;

    public String sayHello(String word){
        return  functionService.sayHello(word);
    }
}
