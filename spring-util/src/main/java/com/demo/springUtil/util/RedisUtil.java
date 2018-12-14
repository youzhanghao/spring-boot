//
///**
// * @Title: RedisUtil.java
// * @Package com.vipabc.vjr.vbs.common.redis
// * @Description: redis工具类
// * @author jasonsun
// * @date 2017年4月7日
// * @version V1.0
// */
//
//package com.demo.springday.util;
//
//import com.demo.springday.redis.JsonUtil;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Type;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
///**
// * @ClassName: RedisUtil
// * @Description: redis工具类
// * @author jasonsun
// * @date 2017年4月7日
// *
// */
//@Component
//public class RedisUtil {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private final static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//
//    private static String toJson(Object obj) {
//        if (obj == null) {
//            return null;
//        } else {
//            return gson.toJson(obj);
//        }
//
//    }
//
//    public static <T> T fromJson(String str, Class<T> cls) {
//
//        return gson.fromJson(str, cls);
//    }
//
//    public static <T> T fromJson(String str, Type typeToken) {
//        return gson.fromJson(str, typeToken);
//    }
//
//    public <T> void set(String redisKey, T obj) {
//        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
//        valueOperations.set(redisKey, toJson(obj));
//    }
//
//    public <T> void set(String redisKey, T obj, long seconds) {
//        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
//        valueOperations.set(redisKey, toJson(obj), seconds, TimeUnit.SECONDS);
//    }
//
//    public Object getAndSet(String redisKey, Object obj) {
//        ValueOperations<String, Object> valueOperations = this.redisTemplate.opsForValue();
//        Object object = valueOperations.getAndSet(redisKey, obj);
//        return object;
//    }
//
//    public void expire(String redisKey, Long seconds, TimeUnit unit) {
//        if (redisKey == null || seconds == null || unit == null) {
//            return;
//        }
//        redisTemplate.expire(redisKey, seconds, unit);
//    }
//
//    public <T> T get(String key) {
//        ValueOperations<String, T> valueOperations = this.redisTemplate.opsForValue();
//        return valueOperations.get(key);
//    }
//
//    public <T> List<T> getByClass(String key, Class<T> clazz) {
//        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
//        String value = valueOperations.get(key);
//        List<T> list = JsonUtil.formatList(value, clazz);
//        return list;
//    }
//
//    public <T> void delete(String key) {
//        this.redisTemplate.delete(key);
//    }
//
//    public <T> boolean hasKey(String key) {
//        return this.redisTemplate.hasKey(key);
//    }
//
//    public <T> List<T> getList(String key, int beginindex, int endindex) {
//        ListOperations<String, T> opsForList = redisTemplate.opsForList();
//        return opsForList.range(key, beginindex, endindex);
//    }
//
//    public <T> void listRightPush(String key, T obj) {
//        ListOperations opsForList = redisTemplate.opsForList();
//        opsForList.rightPush(key, obj);
//    }
//
//    public <T> void listLeftPush(String key, T obj) {
//        ListOperations opsForList = redisTemplate.opsForList();
//        opsForList.leftPush(key, obj);
//    }
//
//    public <T> List<T> getListObject(String key, int beginIndex, int endIndex, Class<T> clz) {
//        ListOperations<String, String> opr = this.redisTemplate.opsForList();
//        List<String> list = opr.range(key, beginIndex, endIndex);
//        return list.stream().map(str -> fromJson(str, clz)).collect(Collectors.toList());
//    }
//
//    public <T> void setListValue(String key, long index, T value) {
//        ListOperations opsForList = redisTemplate.opsForList();
//        opsForList.set(key, index, value);
//    }
//
//    public <T> void removeListValue(String key, long index, T value) {
//        ListOperations opsForList = redisTemplate.opsForList();
//        opsForList.remove(key, index, value);
//    }
//
//    public <T> void rightPushAll(String key, Collection<T> values) {
//        ListOperations<String, String> opr = this.redisTemplate.opsForList();
//        opr.rightPushAll(key, values.stream().map(RedisUtil::toJson).collect(Collectors.toList()));
//    }
//
//    public <T> T get(String key, Class<T> cls) {
//        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
//        return fromJson(valueOperations.get(key), cls);
//    }
//
//    public <T> T get(String key, Type typeToken) {
//        ValueOperations<String, String> valueOperations = this.redisTemplate.opsForValue();
//        return fromJson(valueOperations.get(key), typeToken);
//    }
//
//
//    public <T> void hset(String key, String field, T value) {
//        HashOperations opr = this.redisTemplate.opsForHash();
//        opr.put(key, field, toJson(value));
//    }
//
//    public <T> T hget(String key, String hashKey, Class<T> clazz) {
//        HashOperations opr = this.redisTemplate.opsForHash();
//        return gson.fromJson(gson.toJson(opr.get(key, hashKey)), clazz);
//    }
//
//    public Map<String, String> entries(String key) {
//        HashOperations opr = this.redisTemplate.opsForHash();
//        return opr.entries(key);
//    }
//
//    public <T> Map<String, T> hgetAllToMap(String key, Class<T> cls) {
//        HashOperations<String, String, String> opr = this.redisTemplate.opsForHash();
//        Map<String, String> map = opr.entries(key);
//        Map<String, T> resultMap = new HashMap<String, T>();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            resultMap.put(entry.getKey(), fromJson(entry.getValue(), cls));
//        }
//        return resultMap;
//    }
//
//    public <T> List<T> hgetAllToList(String key, Class<T> cls) {
//        HashOperations<String, String, String> opr = this.redisTemplate.opsForHash();
//        Map<String, String> map = opr.entries(key);
//        List<T> list = new ArrayList<T>();
//        map.entrySet().stream().forEach(stream -> {
//            list.add(fromJson(stream.getValue(), cls));
//        });
//        return list;
//    }
//}
