package com.demo.springUtil.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


/**
 * Created by base.it on 2017/1/18.
 */
public class BaseQuery<T> {
    private int page = 1;

    private int rows = 10;

    private String keyword;

    private String order;

    private String sidx;
    
    private Specification<T> specification;

    private PageRequest pageRequest;
    
    public PageRequest getPageReq() {
    	if(this.pageRequest!=null){
    		return this.pageRequest;
    	}
        //不排序
        if (StringUtils.isEmpty(this.sidx)) {
            //return new PageRequest(this.page - 1, this.rows);
        	return PageRequest.of(this.page - 1, this.rows);
        }
        //需要排序，通用分页只支持单字段
        //return new PageRequest(this.page - 1, this.rows, new Sort(this.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, this.sidx));
        return PageRequest.of(this.page - 1, this.rows, new Sort(this.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, this.sidx));
    }
    
    public void setPageRequest(PageRequest page){
    	this.pageRequest = page;
    }
    
    public Specification<T> getSpecification() {
		return specification;
	}

	public void setSpecification(Specification<T> specification) {
		this.specification = specification;
	}
	
    public Specification<T> where() {
    	return specification;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isAsc() {
        return "ASC".equals(this.order.toUpperCase());
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
