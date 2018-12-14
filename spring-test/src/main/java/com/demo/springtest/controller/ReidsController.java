package com.demo.springtest.controller;


import com.demo.springtest.dto.RedisHsetRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import com.demo.springday.util.HashRedisUtil;


/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/11/2 上午9:34
 */
@RestController
@RequestMapping("/redis")
public class ReidsController {


//
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/RedisTemplateHkeys")
    public Set<String> redisTemplatehKeys(@RequestBody RedisHsetRequestDto requestDto){
        return stringRedisTemplate.keys(requestDto.getKey());
    }

    @PostMapping("/RedisTemplateHset")
    public Map<String,String> redisTemplateHset(@RequestBody RedisHsetRequestDto requestDto){
        stringRedisTemplate.opsForHash().put(requestDto.getKey(),requestDto.getField(),requestDto.getValue());

        Map<String,String> res = new HashMap<>();
        res.put("result","ok");
        return res;
    }

    @PostMapping("/RedisTemplateSet")
    public Map<String,String> redisTemplateSet(@RequestBody RedisHsetRequestDto requestDto){

        redisTemplate.opsForValue().set(requestDto.getKey(),requestDto.getValue());
        Map<String,String> res = new HashMap<>();
        res.put("result","ok");
        return res;
    }

}
