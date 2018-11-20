package com.demo.springUtil.repository;

import com.demo.springUtil.domain.BaseDomain;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by wangwei on 2018/7/5.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default T findOne(ID id){
        T t = null;
        Optional<T> optional= findById(id);
        if(!Optional.empty().equals(optional)){
            t = optional.get();
        }
        return t;
    }

    default List<T> findAll(Iterable<ID> ids){
        return findAllById(ids);
    }
    @SuppressWarnings("unchecked")
	@Override
    default void deleteById(Serializable id) {
        softDeleteById((ID)id);
    }
    @Override
    default void delete(T entity) {
        softDelete(entity);
    }
    default void softDelete(T entity) {

        Assert.notNull(entity, "The entity must not be null!");
        Assert.isInstanceOf(BaseDomain.class, entity, "The entity must be soft deletable!");
        ((BaseDomain)entity).setDeleted(true);
        ((BaseDomain)entity).setUpdateTime(new Date());
        save(entity);
    }

    default void softDeleteById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        this.softDelete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", "", id), 1)));
    }

}
