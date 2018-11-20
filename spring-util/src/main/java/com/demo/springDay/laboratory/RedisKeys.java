/*
package com.demo.springboot.laboratory;

import java.util.Set;

*/
/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/6/8 下午6:13
 *//*

public class RedisKeys {

    public void testRedis(){

        System.out.println(">>>默认序列："+redisTemplate.getDefaultSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        valueOperations.set(RedisConsts.WEATHER_CITY_PREFIX_RULE , "1");

        Set<String> set = redisTemplate.keys((RedisConsts.WEATHER_CITY_PREFIX_RULE+"*"));
        Set<byte[]> setRedis = redisTemplate.getConnectionFactory().getConnection().keys((RedisConsts.WEATHER_CITY_PREFIX_RULE+"*").getBytes());
        String val = valueOperations.get(RedisConsts.WEATHER_CITY_PREFIX_RULE).toString();


//        Set<String> ruleSet = redisTemplate.keys("RULE_*");
        System.out.println("set"+set);
        System.out.println("setRedis:"+setRedis);
        System.out.println("val:"+val);
        System.out.println(">>>weather redis result<<<");
        setRedis.stream().forEach(i -> System.out.println("结果是:"+(byte[])i));
        setRedis.stream().count();
    }
}
*/
