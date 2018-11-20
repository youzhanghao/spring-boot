package com.demo.springUtil.controller;

import com.demo.springUtil.dto.RedisHsetRequestDto;
//import com.demo.springday.util.HashRedisUtil;
import com.demo.springUtil.util.HashRedisUtil;
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


/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/11/2 上午9:34
 */
@RestController
@RequestMapping("/redis")
public class ReidsController {

    // 全局捕获异常提取defaultMessage
    @Autowired
    private HashRedisUtil hashRedisUtil;
//
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/hset")
    public Map<String,String> hset(@RequestBody RedisHsetRequestDto requestDto){
        Long data = hashRedisUtil.hset(requestDto.getKey(),requestDto.getField(),requestDto.getValue());
        Map<String,String> res = new HashMap<>();
        res.put("result",data.toString());
        return res;
    }

    @PostMapping("/hkeys")
    public Set<String> hkeys(@RequestBody RedisHsetRequestDto requestDto){
        return hashRedisUtil.hkeys(requestDto.getKey());
    }

    @PostMapping("/RedisTemplateHkeys")
    public Set<String> redisTemplatehKeys(@RequestBody RedisHsetRequestDto requestDto){
//        return hashRedisUtil.hkeys(requestDto.getKey());

//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
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


//        redisTemplate.getConnectionFactory().getConnection().set(requestDto.getKey().getBytes(),requestDto.getValue().getBytes());
        redisTemplate.opsForValue().set(requestDto.getKey(),requestDto.getValue());
//        stringRedisTemplate.opsForValue().set(requestDto.getKey(),requestDto.getValue());
        Map<String,String> res = new HashMap<>();
        res.put("result","ok");
        return res;
    }

    @PostMapping("/RedisTemplateConfig")
    public Map<String,String> redisTemplateConfig(@RequestBody RedisHsetRequestDto requestDto){
        stringRedisTemplate.opsForHash().put(requestDto.getKey(),requestDto.getField(),requestDto.getValue());
        Map<String,String> res = new HashMap<>();
        res.put("result","ok");
        return res;
    }

    @PostMapping("/lettuce/hkeys")
    public Set<String> lettuceHkeys(@RequestBody RedisHsetRequestDto requestDto){
        return redisTemplate.opsForHash().keys(requestDto.getKey());
    }

    @PostMapping("/lettuce/hset")
    public Map<String,String> lettuceHset(@RequestBody RedisHsetRequestDto requestDto){


//        redisTemplate.getConnectionFactory().getConnection().set(requestDto.getKey().getBytes(),requestDto.getValue().getBytes());
        redisTemplate.opsForHash().put(requestDto.getKey(),requestDto.getField(),requestDto.getValue());

//        stringRedisTemplate.opsForValue().set(requestDto.getKey(),requestDto.getValue());
        Map<String,String> res = new HashMap<>();
        res.put("result","ok");
        return res;
    }
}
