package com.dock.core.service;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.dock.core.entity.AbstractEntity;
import com.dock.core.exception.ServiceException;
import com.dock.core.page.PageParam;
import com.dock.core.page.Pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface AbstractService<T extends AbstractEntity, ID extends Serializable> {
    T save(T var1) throws ServiceException;

    T create(T var1) throws ServiceException;

    int modify(T var1) throws ServiceException;

    int remove(ID var1) throws ServiceException;

    int remove(T var1) throws ServiceException;

    boolean exists(ID var1) throws ServiceException;

    T get(ID var1) throws ServiceException;

    T findOne(Map<String, Object> var1) throws ServiceException;

    List<T> findBy(Map<String, Object> var1) throws ServiceException;

    Pagination<T> queryPage(PageParam var1, Map<String, Object> var2) throws ServiceException;
}
