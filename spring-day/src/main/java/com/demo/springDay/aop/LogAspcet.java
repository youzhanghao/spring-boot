package com.demo.springUtil.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/8/24 上午1:01
 */
// @Aspect声明为一个切面
@Aspect
@Component
public class LogAspcet {

    // 注解式
    // 声明切点以及作用域
    @Pointcut(value = "@annotation(com.demo.springUtil.aop.Action)")
    public void annotationPointCut(){};

    // 在此切点之后的行为  建言 推荐注解形式 自定义内容丰富 易结合日志和swagger
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint){
        // ???
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截" + action.name());
    }

    // 在此切点之前的行为  建言 此建言直接使用拦截规则作为参数 注意 * com之间有空格
    @Before(value = "execution(* com.demo.springUtil.aop.DemoMethodService.*(..))")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("方法式式拦截" + method.getName());
    }


}
