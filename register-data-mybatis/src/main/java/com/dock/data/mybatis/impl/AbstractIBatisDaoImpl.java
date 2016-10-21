package com.dock.data.mybatis.impl;

import com.dock.core.AbstractEntity;
import com.dock.data.mybatis.AbstractIBatisDao;
import com.dock.page.Pagination;
import com.dock.utils.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractIBatisDaoImpl<T extends AbstractEntity> extends SqlSessionDaoSupport
        implements AbstractIBatisDao<T>
{
    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_BATCH_UPDATE = "batchUpdate";
    public static final String SQL_GET_BY_ID = "getById";
    public static final String SQL_DELETE_BY_ID = "deleteById";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam";

    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public long save(T entity)
    {
        return insert(entity);
    }

    public long insert(T entity) {
        int result = this.sessionTemplate.insert(getStatement("insert"), entity);

        if ((entity != null) && (entity.getId() != null) && (result > 0)) {
            return NumberUtils.parseLong(entity.getId());
        }
        return result;
    }

    public long insert(List<T> list) {
        if ((list == null) || (list.size() <= 0)) {
            return 0L;
        }
        int result = this.sessionTemplate.insert(getStatement("batchInsert"), list);

        return result;
    }

    public int update(T entity) {
        int result = this.sessionTemplate.update(getStatement("update"), entity);

        return result;
    }

    public int update(List<T> list) {
        if ((list == null) || (list.size() <= 0)) {
            return 0;
        }
        int result = this.sessionTemplate.update(getStatement("batchUpdate"), list);

        return result;
    }

    public T get(Object id) {
        return getById(id);
    }

    public T getById(Object id) {
        return (T) this.sessionTemplate.selectOne(getStatement("getById"), id);
    }

    public int delete(Object id) {
        return deleteById(id);
    }

    public int deleteById(Object id) {
        return this.sessionTemplate.delete(getStatement("deleteById"), id);
    }

    public Pagination<T> findPage(Pagination<T> page, Map<String, Object> paramMap) {
        return queryPage(page, paramMap);
    }

    public Pagination<T> queryPage(Pagination<T> page, Map<String, Object> paramMap)
    {
        if (paramMap == null) {
            paramMap = new HashMap(8);
        }

        paramMap.put("pageFirst", Integer.valueOf((page.getPageNo() - 1) * page.getPageSize()));
        paramMap.put("pageSize", Integer.valueOf(page.getPageSize()));
        paramMap.put("startRowNum", Integer.valueOf((page.getPageNo() - 1) * page.getPageSize()));
        paramMap.put("endRowNum", Integer.valueOf(page.getPageNo() * page.getPageSize()));

        Long count = (Long)this.sessionTemplate.selectOne(getStatement("listPageCount"), paramMap);

        List list = this.sessionTemplate.selectList(getStatement("listPage"), paramMap);

        Object isCount = paramMap.get("isCount");

        if ((isCount != null) && ("1".equals(isCount.toString()))) {
            Map countResultMap = (Map)this.sessionTemplate.selectOne(getStatement("countByPageParam"), paramMap);

            return new Pagination(page.getPageNo(), page.getPageSize(), count.intValue(), list, countResultMap);
        }

        return new Pagination(page.getPageNo(), page.getPageSize(), count.intValue(), list);
    }

    public List<T> find(Map<String, Object> paramMap) {
        return queryBy(paramMap);
    }

    public List<T> queryBy(Map<String, Object> paramMap) {
        return this.sessionTemplate.selectList(getStatement("listBy"), paramMap);
    }

    public List<T> getAll() {
        return queryBy(null);
    }

    public T getBy(Map<String, Object> paramMap) {
        if ((paramMap == null) || (paramMap.isEmpty())) {
            return null;
        }
        return (T) this.sessionTemplate.selectOne(getStatement("listBy"), paramMap);
    }

    public SqlSessionTemplate getSessionTemplate() {
        return this.sessionTemplate;
    }

    public SqlSession getBaseSqlSession() {
        return super.getSqlSession();
    }

    public String getStatement(String sqlId) {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getClass().getName());
        sb.append(".");
        sb.append(sqlId);

        return sb.toString();
    }
}