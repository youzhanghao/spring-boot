package com.demo.springUtil.redis;

import com.demo.springUtil.config.RedisConfig;
import net.logstash.logback.encoder.org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/11/1 下午5:18
 */
@Configuration
public class RedisConnector {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConnector.class);

    private static final String DEFAULT_REDIS_SEPARATOR = ";";

    private static final String HOST_PORT_SEPARATOR = ":";

    @Autowired
    private RedisConfig redisConfig;

    public JedisPool[] jedisPools = new JedisPool[0];

    public Jedis  getConnection(){
        Jedis jedis = null;
        // 连接本地redis服务
//        try{
//            jedis = new Jedis(redisConfig.getUrl(),redisConfig.getPort());
//            logger.info(">>>{}:{} 服务正在运行：{} <<<",redisConfig.getUrl(), redisConfig.getPort(),jedis.ping());
//        }catch (Exception e){
//            logger.error(">>>redis连接异常<<<",e);
//        }
        return jedis;
    }

    // 测试redisTemplate性能，注释此处
//    @Bean
    public boolean initPool() {

        System.out.println(redisConfig.getUrl());
        LOGGER.info("---- 开始读取redis配置 --- ");
        // 操作超时时间,默认2秒
        int timeout = NumberUtils.toInt(redisConfig.getTimeout(), 2000);
        // jedis池最大连接数总数，默认8
        int maxTotal = NumberUtils.toInt(redisConfig.getMaxActive(), 8);
        // jedis池最大空闲连接数，默认8
        int maxIdle = NumberUtils.toInt(redisConfig.getMaxIdel(), 8);
        // jedis池最少空闲连接数
        int minIdle = NumberUtils.toInt(redisConfig.getMinIdel(), 0);
        // jedis池没有对象返回时，最大等待时间单位为毫秒
        long maxWaitMillis = NumberUtils.toLong(redisConfig.getMaxWatiTime(), -1);
        // 在borrow一个jedis实例时，是否提前进行validate操作
        boolean testOnBorrow = Boolean.parseBoolean(redisConfig.getTestOnBorrow());

        // 设置jedis连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);

        // 取得redis的url
        String redisUrls = redisConfig.getUrl();
        if (redisUrls == null || redisUrls.trim().isEmpty()) {
            throw new IllegalStateException("the urls of redis is not configured");
        }
        logger.info("the urls of redis is {}", redisUrls);

        // 生成连接池
        List<JedisPool> jedisPoolList = new ArrayList<JedisPool>();
        for (String redisUrl : redisUrls.split(DEFAULT_REDIS_SEPARATOR)) {
            try{
                String[] redisUrlInfo = redisUrl.split(HOST_PORT_SEPARATOR);
                JedisPool jedisPool = new JedisPool(poolConfig, redisUrlInfo[0], Integer.parseInt(redisUrlInfo[1]), timeout);
                jedisPoolList.add(jedisPool);
                logger.info(">>>{} 服务正在运行：{} <<<",redisUrl);
            }catch (Exception e){
                logger.error(">>>redis:{}连接异常<<<",redisUrl,e);
                return false;
            }

        }
        jedisPools = jedisPoolList.toArray(jedisPools);
        return true;
    }

}
