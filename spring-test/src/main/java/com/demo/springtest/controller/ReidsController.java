package com.demo.springtest.controller;


import com.demo.springtest.dto.RedisHsetRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${redis.redisHost}")
    private String redisHost;

    @Value("${redis.redisPort}")
    private int port;


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

    @GetMapping("/_health")
    public Map<String,String> healthCheck(){
        Map<String,String> res = new HashMap<>();
        Jedis jedis = null;
        try{
            jedis = new Jedis(redisHost,port);
            logger.info(">>>服务正在运行：{} <<<", jedis.ping());
            res.put("redis_status","ok");
        }catch (Exception e){
            logger.error(">>>redis连接异常<<<",e);
            res.put("redis_status","error");
        }
        return res;
    }
}
