package com.demo.springUtil.config;

import com.alibaba.fastjson.JSON;
import com.demo.springUtil.annotation.SysLogAnn;
import com.demo.springUtil.domain.SysLog;
import com.demo.springUtil.service.SysLogService;
import com.demo.springUtil.util.HttpContextUtils;
import com.demo.springUtil.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 * 
 * @author base.it
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(com.demo.springUtil.annotation.SysLogAnn)")
	public void logPointCut() { 
		
	}
	
	@Before("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		SysLog sysLog = new SysLog();
		SysLogAnn syslog = method.getAnnotation(SysLogAnn.class);
		if(syslog != null){
			//注解上的描述 
			sysLog.setOperation(syslog.value());
		}
		
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		//请求的参数
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length > 0){
			String params = "";
			for (Object object : args) {
				params += JSON.toJSONString(object)+",";
			}
			params = params.substring(0, params.length()-1);
			sysLog.setParams(params);
		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		
		//用户名
//		SysUser loginSysUser = ShiroUtils.getUserEntity();
//		if(loginSysUser != null){
//			String username = loginSysUser.getUsername();
//			if(StringUtils.isNotBlank(username)) {
//                sysLog.setUsername(username);
//            } else {
//                sysLog.setUsername(loginSysUser.getId().toString());
//            }
//		}
		//保存系统日志
		sysLogService.save(sysLog);
	}
	
}
