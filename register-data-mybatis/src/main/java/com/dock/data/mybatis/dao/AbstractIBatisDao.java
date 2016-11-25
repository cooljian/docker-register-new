package com.dock.data.mybatis.dao;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.dock.core.entity.AbstractEntity;
import com.dock.core.exception.DAOException;
import com.dock.core.page.PageParam;
import com.dock.core.page.Pagination;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface AbstractIBatisDao<T extends AbstractEntity, ID extends Serializable> {
    T save(T var1) throws DAOException;

    T insert(T var1) throws DAOException;

    List<T> insert(Iterable<T> var1) throws DAOException;

    int update(T var1) throws DAOException;

    int update(Iterable<T> var1) throws DAOException;

    int delete(ID var1) throws DAOException;

    int delete(T var1) throws DAOException;

    boolean exists(ID var1) throws DAOException;

    T get(ID var1) throws DAOException;

    T findOne(Map<String, Object> var1) throws DAOException;

    List<T> findBy(Map<String, Object> var1) throws DAOException;

    Pagination<T> queryPage(PageParam var1, Map<String, Object> var2) throws DAOException;

    Pagination<T> queryPage(PageParam var1, Map<String, Object> var2, String var3, String var4) throws DAOException;

    boolean lock(ID var1) throws DAOException;

    boolean unlock(ID var1) throws DAOException;

    boolean batchLock(List<ID> var1) throws DAOException;

    boolean batchUnlock(List<ID> var1) throws DAOException;

    SqlSessionTemplate getSessionTemplate();

    SqlSession getBaseSqlSession();
}
