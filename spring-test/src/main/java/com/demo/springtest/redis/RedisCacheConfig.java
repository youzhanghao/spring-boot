package com.demo.springtest.redis;

/**
 * Created by Alexa on 2017/8/6.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    // 需要自定义redis开启
    @Autowired
    private RedisSelfProperties redisSelfProperties;
    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Autowired
    private RedisProperties redisProperties;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RedisCacheConfig() {
    }
// 需要自定义redis开启
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(this.redisSelfProperties.getMaxIdle());
        jedisPoolConfig.setMaxTotal(this.redisSelfProperties.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(this.redisSelfProperties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    // 需要自定义redis开启 自定义连接工厂
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = null;

        String hosts = this.redisSelfProperties.getClusterHost();
        if(hosts != null && !StringUtils.isEmpty(hosts.trim())) {
            //如果配置了集群:则redis集群设置
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
            String[] hostArray = hosts.split(",");
//            JedisPoolConfig poolConfig = new JedisPoolConfig();
//            poolConfig.setMaxIdle(this.redisSelfProperties.getMaxIdle());
//            poolConfig.setMaxTotal(this.redisSelfProperties.getMaxTotal());
//            poolConfig.setMaxWaitMillis(redisSelfProperties.getExpireTime());
//            poolConfig.setMinIdle(Integer.parseInt(this.redisSelfProperties.getMinIdle()));

            String ports = this.redisSelfProperties.getClusterPort();
            String[] portArray = ports.split(",");

            if(hostArray.length != portArray.length) {
                throw new RuntimeException("亲,你的redis集群配置有误~~~~,集群IP数量需要和集群端口数量一致!");
            }

            for(int i = 0; i < hostArray.length; i++) {
                RedisNode redisNode = new RedisNode(hostArray[i], Integer.parseInt(portArray[i]));
                clusterConfiguration.setMaxRedirects(4);
                clusterConfiguration.addClusterNode(redisNode);
            }
            redisConnectionFactory = new JedisConnectionFactory(clusterConfiguration, getJedisPoolConfig());
        } else {
            //如果没有配置集群:则redis单机设置
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(this.redisSelfProperties.getRedisHost());
            redisStandaloneConfiguration.setPort(this.redisSelfProperties.getRedisPort());
            redisStandaloneConfiguration.setPassword(RedisPassword.of(this.redisSelfProperties.getRedisPassword()));

            JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
            jedisClientConfiguration.usePooling().poolConfig(getJedisPoolConfig());
            redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration.build());


        }
        logger.info(""+redisConnectionFactory.getClientConfiguration());
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(cf);
//        redisTemplate.setConnectionFactory();
        redisTemplate.setKeySerializer(stringRedisSerializer);

//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }


    @Bean
    @Override
    public CacheManager cacheManager(){
        RedisCacheManager redisCacheManager = RedisCacheManager.create(redisConnectionFactory());
        return redisCacheManager;
    }

}
