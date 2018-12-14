package com.demo.springUtil.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 系统日志
 * 
 */
@Entity
@Table(name = "sys_log")
@Where(clause = "deleted = 0")
public class SysLog extends BaseDomain {

	private static final long serialVersionUID = -7210647176457156512L;
	
	//用户名
	@Column(name = "username", nullable = true, length = 50)
	private String username;
	//用户操作
	@Column(name = "operation", nullable = true, length = 50)
	private String operation;
	//请求方法
	@Column(name = "method", nullable = true, length = 200)
	private String method;
	//请求参数
	@Column(name = "params", nullable = true, length = 5000)
	private String params;
	//IP地址
	@Column(name = "ip", nullable = true, length = 64)
	private String ip;

	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：用户操作
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * 获取：用户操作
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * 设置：请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：请求方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：请求参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：请求参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：IP地址
	 */
	public String getIp() {
		return ip;
	}
}
