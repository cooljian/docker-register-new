package com.dock.data.mybatis;

import com.dock.core.AbstractEntity;
import com.dock.page.Pagination;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public abstract interface AbstractIBatisDao<T extends AbstractEntity>
{
    public abstract long save(T paramT);

    public abstract long insert(T paramT);

    public abstract long insert(List<T> paramList);

    public abstract int update(T paramT);

    public abstract int update(List<T> paramList);

    public abstract T get(Object paramObject);

    public abstract T getById(Object paramObject);

    public abstract int delete(Object paramObject);

    public abstract int deleteById(Object paramObject);

    public abstract Pagination<T> findPage(Pagination<T> paramPagination, Map<String, Object> paramMap);

    public abstract Pagination<T> queryPage(Pagination<T> paramPagination, Map<String, Object> paramMap);

    public abstract List<T> find(Map<String, Object> paramMap);

    public abstract List<T> queryBy(Map<String, Object> paramMap);

    public abstract List<T> getAll();

    public abstract T getBy(Map<String, Object> paramMap);

    public abstract SqlSessionTemplate getSessionTemplate();

    public abstract SqlSession getBaseSqlSession();
}