package com.dock.data.mybatis.service.impl;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.dock.core.entity.AbstractEntity;
import com.dock.core.exception.ServiceException;
import com.dock.core.page.PageParam;
import com.dock.core.page.Pagination;
import com.dock.core.service.AbstractService;
import com.dock.data.mybatis.dao.AbstractIBatisDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractIbatisServiceImpl<T extends AbstractEntity, ID extends Serializable> implements AbstractService<T, ID> {
    public AbstractIbatisServiceImpl() {
    }

    protected abstract AbstractIBatisDao<T, ID> getAbstractIBatisDao();

    public T save(T entity) throws ServiceException {
        return this.getAbstractIBatisDao().save(entity);
    }

    public T create(T entity) throws ServiceException {
        return this.getAbstractIBatisDao().insert(entity);
    }

    public List<T> create(Iterable<T> entities) throws ServiceException {
        return this.getAbstractIBatisDao().insert(entities);
    }

    public int modify(T entity) throws ServiceException {
        return this.getAbstractIBatisDao().update(entity);
    }

    public int modify(Iterable<T> entities) throws ServiceException {
        return this.getAbstractIBatisDao().update(entities);
    }

    public int remove(ID id) throws ServiceException {
        return this.getAbstractIBatisDao().delete(id);
    }

    public int remove(T entity) throws ServiceException {
        return this.getAbstractIBatisDao().delete(entity);
    }

    public T get(ID id) throws ServiceException {
        return this.getAbstractIBatisDao().get(id);
    }

    public boolean exists(ID id) throws ServiceException {
        return this.getAbstractIBatisDao().exists(id);
    }

    public T findOne(Map<String, Object> params) throws ServiceException {
        return this.getAbstractIBatisDao().findOne(params);
    }

    public List<T> findBy(Map<String, Object> params) throws ServiceException {
        return this.getAbstractIBatisDao().findBy(params);
    }

    public Pagination<T> queryPage(PageParam page, Map<String, Object> params) throws ServiceException {
        return this.getAbstractIBatisDao().queryPage(page, params);
    }
}
