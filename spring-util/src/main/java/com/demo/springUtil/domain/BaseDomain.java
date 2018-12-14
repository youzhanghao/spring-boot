package com.demo.springUtil.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>统一定义id和version的domain基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。</p>
 *
 *
 */
@MappedSuperclass
public class BaseDomain implements Serializable{

	/**
	 * ID
	 */
	@Id
	@Column(name = "id", nullable = false, length = 19)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8870504230827463383L;
	
	public BaseDomain() {
		setAddTime(new Date());
		setUpdateTime(new Date());
	}
	
	/**
	 * 数据版本号
	 */
    @Version
    @Column(nullable = false,columnDefinition="bigint(20) DEFAULT 0 COMMENT '数据版本号'")
    protected Long version;

    /**
     * 删除状态 0/false未删除 1/true已删除
     */
    @Column(nullable = false,columnDefinition="bit DEFAULT b'0' COMMENT '删除状态 0未删除 1已删除'")
    protected boolean deleted;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(nullable = false,columnDefinition="timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date addTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(nullable = false,columnDefinition="timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'")
    private Date updateTime;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
    	this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = (updateTime == null ? new Date() : updateTime);
    }
}
