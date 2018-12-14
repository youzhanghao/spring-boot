package com.demo.springUtil.run;

import com.demo.springUtil.config.FunctionServiceConfig;
import com.demo.springUtil.service.UseFunctionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description 测试运行类
 * @date 2018/8/24 上午12:17
 */
public class UseServiceMain {
    public static void main(String[] args) {

        // 指定配置类运行上下文  使用AnnotationConfigApplicationContext 作为spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FunctionServiceConfig.class);

        // 获取容器中的bean
        UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);

        // 调用bean中的方法
        System.out.println(useFunctionService.sayHello("hello reWorld"));

        // 容器释放
        context.close();

    }
}
