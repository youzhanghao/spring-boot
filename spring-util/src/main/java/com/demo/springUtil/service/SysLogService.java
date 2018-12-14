package com.demo.springUtil.service;

import com.demo.springUtil.domain.SysLog;
import com.demo.springUtil.query.BaseQuery;
import com.demo.springUtil.repository.SysLogRepository;
import com.demo.springUtil.util.HttpContextUtils;
import com.demo.springUtil.util.IPUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SysLogService extends BaseService<SysLogRepository, BaseQuery<SysLog>> {

	@Transactional
    public void deleteBatch(List<Long> ids) {
        //repository.delete(repository.findAll(ids));
    	repository.deleteByIdIn_logic(ids);
    }
	
	@Transactional
	public void save(String username,String operation,String method,String params){
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		SysLog sysLog = new SysLog();
        sysLog.setMethod(method);
        sysLog.setUsername(username);
        sysLog.setOperation(operation);
        sysLog.setParams(params);
        sysLog.setIp(IPUtils.getIpAddr(request));
        repository.save(sysLog);
	}
	
	@Transactional
	public void deleteOneMonth(Date time){
		repository.deleteOneMonth(time);
	}
	
	@Transactional
	@Override
	public void deleteAll(){
		repository.deleteAll();
	}
}
