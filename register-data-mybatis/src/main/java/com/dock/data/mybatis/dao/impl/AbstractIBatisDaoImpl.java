package com.dock.data.mybatis.dao.impl;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.dock.core.annotation.LogRequired;
import com.dock.core.entity.AbstractEntity;
import com.dock.core.exception.DAOException;
import com.dock.core.page.PageParam;
import com.dock.core.page.Pagination;
import com.dock.core.utils.StringUtils;
import com.dock.data.mybatis.dao.AbstractIBatisDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AbstractIBatisDaoImpl<T extends AbstractEntity, ID extends Serializable> extends SqlSessionDaoSupport implements AbstractIBatisDao<T, ID> {
    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_BATCH_UPDATE = "batchUpdate";
    public static final String SQL_GET_BY_ID = "getById";
    public static final String SQL_DELETE_BY_ID = "deleteById";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_LOG_SUFFIX = "_log";
    public static final String SQL_LOCK = "lock";
    public static final String SQL_UNLOCK = "unlock";
    public static final String SQL_BATCH_LOCK = "batchLock";
    public static final String SQL_BATCH_UNLOCK = "batchUnlock";
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public AbstractIBatisDaoImpl() {
    }

    public T save(T entity) throws DAOException {
        if(StringUtils.isNotEmpty(entity.getId())) {
            AbstractEntity obj = this.get((ID) entity.getId());
            if(obj != null) {
                this.update(entity);
            } else {
                entity = this.insert(entity);
            }
        } else {
            entity = this.insert(entity);
        }

        return entity;
    }

    public T insert(T entity) throws DAOException {
        long time = System.currentTimeMillis();
        entity.setCreatedTime(Long.valueOf(time));
        entity.setLastAccess(Long.valueOf(time));
        int result = this.getSessionTemplate().insert(this.getStatement("insert"), entity);
        if(result > 0) {
            LogRequired logRequired = (LogRequired)entity.getClass().getAnnotation(LogRequired.class);
            if(logRequired != null) {
                entity.setIsNew(Boolean.valueOf(true));
                entity.setLogTime(Long.valueOf(System.currentTimeMillis()));
                this.getSessionTemplate().insert(this.getStatement("insert_log"), entity);
            }

            return entity;
        } else {
            return null;
        }
    }

    public List<T> insert(Iterable<T> entities) throws DAOException {
        List list = convertIterableToList(entities);
        if(list.isEmpty()) {
            return list;
        } else {
            this.getSessionTemplate().insert(this.getStatement("batchInsert"), list);
            return list;
        }
    }

    public int update(T entity) throws DAOException {
        LogRequired logRequired = (LogRequired)entity.getClass().getAnnotation(LogRequired.class);
        if(logRequired != null) {
            entity.setIsNew(Boolean.valueOf(false));
            entity.setLogTime(Long.valueOf(System.currentTimeMillis()));
            this.getSessionTemplate().insert(this.getStatement("insert_log"), entity);
        }

        entity.setLastAccess(Long.valueOf(System.currentTimeMillis()));
        int result = this.getSessionTemplate().update(this.getStatement("update"), entity);
        if(logRequired != null) {
            entity.setIsNew(Boolean.valueOf(true));
            entity.setLogTime(Long.valueOf(System.currentTimeMillis()));
            this.getSessionTemplate().insert(this.getStatement("insert_log"), entity);
        }

        return result;
    }

    public int update(Iterable<T> entities) throws DAOException {
        List list = convertIterableToList(entities);
        if(list.isEmpty()) {
            return 0;
        } else {
            int result = this.getSessionTemplate().update(this.getStatement("batchUpdate"), list);
            return result;
        }
    }

    public int delete(ID id) throws DAOException {
        AbstractEntity entity = this.get(id);
        if(entity != null) {
            LogRequired logRequired = (LogRequired)entity.getClass().getAnnotation(LogRequired.class);
            if(logRequired != null) {
                entity.setIsNew(Boolean.valueOf(false));
                entity.setLogTime(Long.valueOf(System.currentTimeMillis()));
                this.getSessionTemplate().insert(this.getStatement("insert_log"), entity);
            }
        }

        return this.getSessionTemplate().delete(this.getStatement("deleteById"), id);
    }

    public int delete(T entity) throws DAOException {
        LogRequired logRequired = (LogRequired)entity.getClass().getAnnotation(LogRequired.class);
        if(logRequired != null) {
            entity.setIsNew(Boolean.valueOf(false));
            entity.setLogTime(Long.valueOf(System.currentTimeMillis()));
            this.getSessionTemplate().insert(this.getStatement("insert_log"), entity);
        }

        return this.getSessionTemplate().delete(this.getStatement("deleteById"), entity.getId());
    }

    public T get(ID id) throws DAOException {
        return (T) this.getSessionTemplate().selectOne(this.getStatement("getById"), id);
    }

    public boolean exists(ID id) throws DAOException {
        return this.get(id) != null;
    }

    public Pagination<T> queryPage(PageParam page, Map<String, Object> params) throws DAOException {
        return this.queryPage(page, params, (String)null, (String)null);
    }

    public Pagination<T> queryPage(PageParam page, Map<String, Object> params, String countSQL, String pageSQL) throws DAOException {
        if(params == null) {
            params = Maps.newHashMapWithExpectedSize(8);
        }

        if(page != null && page.getPageSize() > 0) {
            if(page.getPageNo() < 0) {
                page.setPageNo(1);
            }

            ((Map)params).put("pageFirst", Integer.valueOf((page.getPageNo() - 1) * page.getPageSize()));
            ((Map)params).put("pageSize", Integer.valueOf(page.getPageSize()));
            ((Map)params).put("startRowNum", Integer.valueOf((page.getPageNo() - 1) * page.getPageSize()));
            ((Map)params).put("endRowNum", Integer.valueOf(page.getPageNo() * page.getPageSize()));
            Long count = (Long)this.getSessionTemplate().selectOne(this.getStatement(StringUtils.msNull(countSQL, "listPageCount")), params);
            int maxPage = (new BigDecimal(count.longValue())).divide(new BigDecimal(page.getPageSize()), RoundingMode.CEILING).intValue();
            if(maxPage < page.getPageNo()) {
                page.setPageNo(maxPage);
            }

            List list = this.getSessionTemplate().selectList(this.getStatement(StringUtils.msNull(pageSQL, "listPage")), params);
            return new Pagination(page.getPageNo(), page.getPageSize(), count.intValue(), list);
        } else {
            return new Pagination(this.findBy((Map)params));
        }
    }

    public List<T> findBy(Map<String, Object> params) throws DAOException {
        return this.getSessionTemplate().selectList(this.getStatement("listBy"), params);
    }

    public T findOne(Map<String, Object> params) throws DAOException {
        return params != null && !params.isEmpty()? (T) this.getSessionTemplate().selectOne(this.getStatement("listBy"), params) :null;
    }

    public boolean lock(ID id) throws DAOException {
        int success = this.getSessionTemplate().update(this.getStatement("lock"), id);
        return success == 1;
    }

    public boolean unlock(ID id) throws DAOException {
        int success = this.getSessionTemplate().update(this.getStatement("unlock"), id);
        return success == 1;
    }

    public boolean batchLock(List<ID> ids) throws DAOException {
        boolean success = false;
        List list = this.getSessionTemplate().selectList(this.getStatement("listBy"), ids);
        if(list != null && !list.isEmpty()) {
            int count = this.getSessionTemplate().update(this.getStatement("batchLock"), ids);
            success = count == list.size();
            if(!success) {
                throw new DAOException(Integer.valueOf(10104));
            }
        }

        return success;
    }

    public boolean batchUnlock(List<ID> ids) throws DAOException {
        boolean success = false;
        List list = this.getSessionTemplate().selectList(this.getStatement("listBy"), ids);
        if(list != null && !list.isEmpty()) {
            int count = this.getSessionTemplate().update(this.getStatement("batchUnlock"), ids);
            success = count == list.size();
            if(!success) {
                throw new DAOException(Integer.valueOf(10105));
            }
        }

        return success;
    }

    public SqlSessionTemplate getSessionTemplate() {
        return this.sessionTemplate;
    }

    public SqlSession getBaseSqlSession() {
        return super.getSqlSession();
    }

    public String getStatement(String sqlId) {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getClass().getName()).append(".").append(sqlId);
        return sb.toString();
    }

    private static <T> List<T> convertIterableToList(Iterable<T> entities) {
        if(entities instanceof List) {
            return (List)entities;
        } else {
            int capacity = tryDetermineRealSizeOrReturn(entities, 10);
            if(capacity != 0 && entities != null) {
                ArrayList list = Lists.newArrayListWithCapacity(capacity);
                Iterator var3 = entities.iterator();

                while(var3.hasNext()) {
                    Object entity = var3.next();
                    list.add(entity);
                }

                return list;
            } else {
                return Collections.emptyList();
            }
        }
    }

    private static int tryDetermineRealSizeOrReturn(Iterable<?> iterable, int defaultSize) {
        return iterable == null?0:(iterable instanceof Collection?((Collection)iterable).size():defaultSize);
    }
}
