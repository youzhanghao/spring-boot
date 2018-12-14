package com.demo.log.controller;

import com.demo.log.config.AutoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/12/11 上午10:59
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/_health")
    public Map<String,String> testHealth(){
        Map<String,String> res = new HashMap<>(16);
        res.put("ok","health");
        return res;
    }
}
