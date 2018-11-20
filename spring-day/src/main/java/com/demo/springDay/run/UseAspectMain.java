package com.demo.springUtil.run;

import com.demo.springUtil.aop.DemoAnnotationService;
import com.demo.springUtil.aop.DemoMethodService;
import com.demo.springUtil.config.AopConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description 测试运行类
 * @date 2018/8/24 上午12:17
 */
public class UseAspectMain {
    public static void main(String[] args) {

        // 指定配置类运行上下文  使用AnnotationConfigApplicationContext 作为spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);

        // 获取容器中的bean
        DemoMethodService demoMethodService = context.getBean(DemoMethodService.class);

        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);

        // 调用bean中的方法
        demoMethodService.add();

        demoAnnotationService.add();

        // 容器释放
        context.close();

    }
}
