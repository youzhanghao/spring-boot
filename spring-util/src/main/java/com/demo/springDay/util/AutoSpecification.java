package com.demo.springUtil.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.util.*;

public class AutoSpecification<Entity> implements Specification<Entity> {
	private Map<String, Object> params;
	private Sort sort;
	public AutoSpecification(Map<String, Object> params, Sort sort){
		this.params = params;
		this.sort = sort;
	}
	public AutoSpecification(Map<String, Object> params){
		this(params, null);
	}
	/**
	 * 解析字段的查询类型
	 * :LK	全包围like '%  %'
	 * :LKL 左包围like '__%'
	 * :LKR	右包围like '%__'
	 * :NEQ 不等于
	 * :LT 	小于
	 * :LTE 小于等于
	 * :GT  大于
	 * :GTE	大于等于	 	
	 */
	@SuppressWarnings("rawtypes")
	private Predicate getPredicate(From<Entity, ?> root, CriteriaBuilder cb, String key, Object value){
		if(value == null || StringUtils.isBlank(String.valueOf(value))){
			return null;
		}
		if(key.endsWith(":LK")){
			Path<String> field = root.get(key.replace(":LK", ""));//全包围like '%  %'
			return cb.like(field.as(String.class), "%" + value +"%");
		}
		if(key.endsWith(":LKL")){
			Path<String> field = root.get(key.replace(":LKL", ""));//左包围like '__%'
			return cb.like(field.as(String.class), value +"%");
		}
		if(key.endsWith(":LKR")){
			Path<String> field = root.get(key.replace(":LKR", ""));//右包围like '%__'
			return cb.like(field.as(String.class), "%" + value);
		}
		if(key.endsWith(":NEQ")){
			Path<Object> field = root.get(key.replace(":NEQ", ""));//不等于
			return cb.notEqual(field, value);
		}
		if(key.endsWith(":LT")){
			Path<Comparable> field = root.get(key.replace(":LT", ""));//小于
			return cb.lessThan(field, (Comparable)value);
		}
		if(key.endsWith(":LTE")){
			Path<Comparable> field = root.get(key.replace(":LTE", ""));//小于等于
			return cb.lessThanOrEqualTo(field, (Comparable)value);
		}
		if(key.endsWith(":GT")){
			Path<Comparable> field = root.get(key.replace(":GT", ""));//大于
			return cb.greaterThan(field, (Comparable)value);
		}
		if(key.endsWith(":GTE")){
			Path<Comparable> field = root.get(key.replace(":GTE", ""));//大于等于
			return cb.greaterThanOrEqualTo(field, (Comparable)value);
		}
		if(key.endsWith(":IN")){
			Path<Object> field = root.get(key.replace(":IN", ""));
			In<Object> in = cb.in(field);
			for(Object obj:(Collection<?>)value){
				in.value(obj);
			}
			return in;
		}
		if(key.endsWith(":NOTIN")){
			Path<Object> field = root.get(key.replace(":NOTIN", ""));
			In<Object> in = cb.in(field);
			for(Object obj:(Collection<?>)value){
				in.value(obj);
			}
			return cb.not(in);
		}
		Path<String> field = root.get(key);
		return cb.equal(field, value);
	}
	
	public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Map<String,From<Entity, ?>> joins = getPathList(root, params, sort);
		if(params!=null && !params.isEmpty()){
			Iterator<String> it = params.keySet().iterator();
			List<Predicate> predicateList = new ArrayList<Predicate>();
			while(it.hasNext()){
				String key = it.next();
				Predicate p;
				if(key.indexOf(".") != -1){
					String pp = key.substring(0, key.indexOf("."));
					String property = key.substring(key.indexOf(".") + 1);
					p = getPredicate(joins.get(pp), cb, property, params.get(key));
				}else{
					p = getPredicate(root, cb, key, params.get(key));
				}
				if(p != null){
					predicateList.add(p);
				}
			}
			if(predicateList.size() > 0){
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		}
		dealOrder(root, query, sort, joins);
		return null;
	}
	/**
	 * 解析出需要join的对象
	 */
	private Map<String,From<Entity, ?>> getPathList(Root<Entity> root, Map<String, Object> params, Sort sort){
		Map<String,From<Entity, ?>> result = new HashMap<String, From<Entity,?>>();
		if(params != null && !params.isEmpty()){
			for(String key : params.keySet()){
				if(key.indexOf(".") != -1){
					String property = key.substring(0, key.indexOf("."));
					if(!result.containsKey(property)){
						Join<Entity, ?> join = root.join(property, JoinType.LEFT);
						result.put(property, join);
					}
				}
			}
		}
		if(sort != null){
			Iterator<Sort.Order> sit = sort.iterator();
			while(sit.hasNext()){
				Sort.Order order = sit.next();
				String property[] = order.getProperty().split(":");
				if(property[0].indexOf(".") != -1){
					String pp = property[0].substring(0, property[0].indexOf("."));
					if(!result.containsKey(pp)){
						Join<Entity, ?> join = root.join(pp, JoinType.LEFT);
						result.put(pp, join);
					}
				}
			}
		}
		return result;
	}
	/**
	 * 处理排序
	 */
	private void dealOrder(Root<Entity> root, CriteriaQuery<?> query, Sort sort, Map<String,From<Entity, ?>> joins){
		List<Order> orderList = new ArrayList<Order>();
		if(sort != null){
			Iterator<Sort.Order> sit = sort.iterator();
			while(sit.hasNext()){
				Sort.Order order = sit.next();
				Direction d = order.getDirection();
				String property[] = order.getProperty().split(":");
				Order o;
				Path<String> field;
				if(property[0].indexOf(".") != -1){
					String pp = property[0].substring(property[0].indexOf(".") + 1);
					String ppp = property[0].substring(0, property[0].indexOf("."));
					field = joins.get(ppp).get(pp);
				}else{
					field = root.get(property[0]);
				}
				if(property.length > 1){
					try {
						o = new OrderImpl(field.as(Class.forName(property[1])), d.isAscending());
					} catch (ClassNotFoundException e) {
						o = new OrderImpl(field, d.isAscending());
						e.printStackTrace();
					}
				}else{
					o = new OrderImpl(field, d.isAscending());
				}
				orderList.add(o);
			}
		}
		if(orderList.size() > 0){
			query.orderBy(orderList);
		}
	}
}