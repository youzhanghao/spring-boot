package com.demo.log.interceptor;

import com.demo.log.config.AutoConfig;
import com.demo.log.tools.Utils;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/12/11 上午11:34
 */
@Component
public class LogInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private AutoConfig autoConfig;

    private final ThreadLocal<Long> startTimeThreadLocal = new NamedInheritableThreadLocal<Long>("ThreadLocal StartTime");

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//    public LogInterceptor (AutoConfig autoConfig){
//        this.autoConfig = autoConfig;
//    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(autoConfig.isRecord()){
            long beginTime = System.currentTimeMillis();
            startTimeThreadLocal.set(beginTime);
//            logger.info("开始计时:{} URI:{}",new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime),request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {

        if(modelAndView != null){
            logger.info("ViewName: "+ modelAndView.getViewName());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Exception ex) throws Exception {
        if(autoConfig.isRecord()){
            long beginTime = startTimeThreadLocal.get();
            long endTime = System.currentTimeMillis();
            String uri = request.getRequestURI();
//            String ip =
            logger.info("计时结束：{}  耗时：{}ms  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), endTime - beginTime,
                    request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024,
                    (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
        }
        startTimeThreadLocal.remove();
    }
}
