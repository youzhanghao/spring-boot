package com.demo.springUtil.laboratory;

import org.springframework.context.annotation.Configuration;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/7/6 上午11:23
 */
@Configuration
public class TestConfig {
//    @Value("${redis.timeout}")
    private String hash ;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
        System.out.println(hash);
    }
}
