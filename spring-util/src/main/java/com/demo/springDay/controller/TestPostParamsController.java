package com.demo.springUtil.controller;

import com.demo.springUtil.annotation.SysLogAnn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/7/30 下午5:46
 */
@RestController
@RequestMapping("/test")
public class TestPostParamsController {

    @GetMapping(value = "/logConfig")
    @SysLogAnn("测试日志打印")
    public void test(){
        System.out.println(1);
    }
}
