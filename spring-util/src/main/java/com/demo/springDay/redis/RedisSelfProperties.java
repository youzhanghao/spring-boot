package com.demo.springUtil.redis;

/**
 * Created by Alexa on 2017/8/6.
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

// 需要自定义时开启
@ConfigurationProperties(prefix = "redis")
@EnableConfigurationProperties
@Component
public class RedisSelfProperties {
    private String redisHost;
    private int redisPort;
    private String redisPassword;
    private Long expireTime;
    private int maxIdle = 8;
    private int maxTotal = 800;
    private long maxWaitMillis = 1000L;
    private String clusterHost;
    private String clusterPort;
    private String minIdle;
    private String maxActive;
    private String maxRedirects;
    private String maxWait;

    public RedisSelfProperties() {
    }

    public String getClusterHost() {
        return clusterHost;
    }

    public void setClusterHost(String clusterHost) {
        this.clusterHost = clusterHost;
    }

    public String getClusterPort() {
        return clusterPort;
    }

    public void setClusterPort(String clusterPort) {
        this.clusterPort = clusterPort;
    }

    public String getRedisHost() {
        return this.redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return this.redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return this.redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public Long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public long getMaxWaitMillis() {
        return this.maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxRedirects(String maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public String getMaxRedirects() {
        return maxRedirects;
    }

    public String getMaxWait() {
        return maxWait;
    }
}

