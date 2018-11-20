package com.demo.springUtil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/11/1 下午5:07
 */
@Component
@ConfigurationProperties(prefix = "jedis")
public class RedisConfig {
    private String url;
    private String timeout;
    private String maxActive;
    private String maxIdel;
    private String minIdel;
    private String maxWatiTime;
    private String testOnBorrow;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxIdel() {
        return maxIdel;
    }

    public void setMaxIdel(String maxIdel) {
        this.maxIdel = maxIdel;
    }

    public String getMinIdel() {
        return minIdel;
    }

    public void setMinIdel(String minIdel) {
        this.minIdel = minIdel;
    }

    public String getMaxWatiTime() {
        return maxWatiTime;
    }

    public void setMaxWatiTime(String maxWatiTime) {
        this.maxWatiTime = maxWatiTime;
    }

    public String getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(String testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
}
