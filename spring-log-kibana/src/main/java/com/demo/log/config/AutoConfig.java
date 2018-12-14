package com.demo.log.config;

import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/12/13 下午5:27
 */
@Configuration
public class AutoConfig {


    @Value("${app.record:true}")
    private boolean isRecord;

    public boolean isRecord() {
        return isRecord;
    }
}


