package com.demo.springUtil.service;


import com.demo.springUtil.domain.BaseDomain;
import com.demo.springUtil.exception.RRException;
import com.demo.springUtil.query.BaseQuery;
import com.demo.springUtil.repository.BaseRepository;
import com.demo.springUtil.util.AutoSpecification;
import com.demo.springUtil.util.CommUtil;
import com.demo.springUtil.util.PageUtils;
import com.demo.springUtil.vo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by base.it on 2017/1/19.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseService<T extends BaseRepository, Q extends BaseQuery> {
	@Autowired
	protected T repository;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/***
	 * 通用分页查询
	 */
	public ResultVo getPage(Q query) {
		ResultVo result = ResultVo.ok();
		try {
			Page page = null;
			Specification<T> where = query.where();
			if (where == null) {
				page = repository.findAll(query.getPageReq());
			} else {
				page = repository.findAll(where, query.getPageReq());
			}
			HashMap<String, Object> pageResult = new HashMap<>();
			pageResult.put("currPage", query.getPage());
			pageResult.put("totalPage", page.getTotalPages());
			pageResult.put("totalCount", page.getTotalElements());
			pageResult.put("list", page.getContent());
			result.put("page", pageResult);

		} catch (Exception e) {
			return ResultVo.error(e.getMessage());
		}
		return result;
	}

	/***
	 * 通用分页查询
	 */
	public PageUtils getPage_PageUtils(Q query) {
		PageUtils pageUtil = null;
		try {
			Page page = null;
			Specification<T> where = query.where();
			if (where == null) {
				page = repository.findAll(query.getPageReq());
			} else {
				page = repository.findAll(where, query.getPageReq());
			}
			pageUtil = new PageUtils(page.getContent(), CommUtil.null2Int(page.getTotalElements()), query.getRows(),
					query.getPage());
		} catch (Exception e) {
			throw new RRException(e.getMessage());
		}
		return pageUtil;
	}
	
	/**
	 * 根据SQL查询数据，返回map类型
	 */
	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params){
		return findMapBySql(sql, params, false);
	}
	/**
	 * 根据SQL查询数据，返回map类型
	 */
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, boolean camel){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createNativeQuery(sql);
		if(params!=null && !params.isEmpty()){
			for (String name : params.keySet()) {
				query.setParameter(name, params.get(name));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if(camel){
			list = changeToCamel(list);
		}
		entityManager.close();
		return list;
	}
	
	/**
	 * @author zhengqiang
	 * 2018年7月22日下午4:32:59
	 * @description 字段名转为驼峰命名法
	 */
	private List<Map<String, Object>> changeToCamel(List<Map<String, Object>> list){
		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> map : list) {
			result.add(CommUtil.changeKeyNamesToCamel(map));
		}
		return result;
	}
	
	/**
	 * 根据sql查询分页
	 */
	public Page<Map<String, Object>> findMapPageBySql(String sql, Map<String, Object> params, Pageable page){
		return findMapPageBySql(sql, params, page, false);
	}
	
	/**
	 * 根据sql查询分页
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String, Object>> findMapPageBySql(String sql, Map<String, Object> params, Pageable page, boolean camel){
		int total = findCount(sql, params);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createNativeQuery(sql);
		if(params!=null && !params.isEmpty()){
			for (String name : params.keySet()) {
				query.setParameter(name, params.get(name));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(page != null){
			query.setFirstResult((int)page.getOffset());
			query.setMaxResults(page.getPageSize());
		}
		List<Map<String, Object>> list = query.list();
		if(camel){
			list = changeToCamel(list);
		}
		if(page == null){
			total = list.size();
		}
		Page<Map<String, Object>> pageInfo = new PageImpl<>(list, page, total);
		entityManager.close();
		return pageInfo;
	}
	
	/**
	 * 查询条数
	 */
	public int findCount(String sql, Map<String, Object> params){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createNativeQuery("select count(*) from (" + sql + ") __counter");
		if(params!=null && !params.isEmpty()){
			for (String name : params.keySet()) {
				query.setParameter(name, params.get(name));
			}
		}
		int result = Integer.valueOf(query.uniqueResult().toString());
		entityManager.close();
		return result;
	}
	

	/**
	 * 根据条件分页查询
	 * :LK	全包围like '%  %'
	 * :LKL 左包围like '__%'
	 * :LKR	右包围like '%__'
	 * :NEQ 不等于
	 * :LT 	小于
	 * :LTE 小于等于
	 * :GT  大于
	 * :GTE	大于等于	 	
	 */
	public <S> Page<S> findPageByParams(final Map<String, Object> params, Pageable page){
		final Sort sort = page.getSort();//获取到排序信息，自己处理
		return repository.findAll(new AutoSpecification(params, sort), PageRequest.of(page.getPageNumber(), page.getPageSize()));
	}
	
	/**
	 * 根据条件查询
	 * :LK	全包围like '%  %'
	 * :LKL 左包围like '__%'
	 * :LKR	右包围like '%__'
	 * :NEQ 不等于
	 * :LT 	小于
	 * :LTE 小于等于
	 * :GT  大于
	 * :GTE	大于等于	 	
	 */
	public <S> List<S> findAllByParams(final Map<String, Object> params){
		return findAllByParams(params, null);
	}
	/**
	 * 根据条件查询<br/>
	 * :LK	全包围like '%  %'<br/>
	 * :LKL 左包围like '__%'<br/>
	 * :LKR	右包围like '%__'<br/>
	 * :NEQ 不等于<br/>
	 * :LT 	小于<br/>
	 * :LTE 小于等于<br/>
	 * :GT  大于<br/>
	 * :GTE	大于等于	 <br/>		
	 */
	public <S> List<S> findAllByParams(final Map<String, Object> params, final Sort sort){
		return repository.findAll(new AutoSpecification(params, sort));
	}
	
	
	public <S> List<S> getAll() {
		return repository.findAll();
	}

	public <S> List<S> getAll(Sort sort) {
		return repository.findAll(sort);
	}

	public <S> S findById(Serializable id) {
		return (S) repository.findOne(id);

	}

	public <S> List<S> findAll(List<?> ids) {
		return repository.findAllById(ids);
	}

	public void deleteById(Serializable id) {
		repository.deleteById(id);
	}

	public <S> void deleteById(S entity) {
		repository.delete(entity);
	}

	public <S> void deleteAll(Iterable<S> list) {
		repository.delete(list);
	}

	public <S> void deleteAll() {
		repository.deleteAll();
	}
	@Transactional
	public <S> S save(S entity) {
		((BaseDomain)entity).setUpdateTime(new Date());
		return (S) repository.save(entity);
	}

	public <S> S saveAndFlush(S entity) {
		repository.saveAndFlush(entity);
		return entity;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public boolean exist(Serializable id) {
		return repository.existsById(id);
	}
	
	/**
     * 检查用户
     * @param newT 传进来的实体
     * @param localT 本地数据中的实体
     */
    public <S> void chenckT(S newT,S localT){
    	Class<?> actualEditable = localT.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(newT.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(newT);
							if(value != null && StringUtils.isNotBlank(value.toString())){
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(localT, value);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
    }
}
