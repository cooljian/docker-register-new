package com.dock.page;

import com.dock.core.AbstractEntity;
import com.dock.core.ModelMap;
import com.dock.utils.ModelMapUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Pagination<T extends AbstractEntity>
        implements Serializable
{
    private static final long serialVersionUID = 5432693588740139435L;
    public static final int DEFAULT_PAGE_NO_START = 1;
    public static final int DEFAULT_PAGE_SIZE = 15;
    private int pageNo = 1;
    private int pageSize = 15;
    private int pageCount;
    private int totalCount;
    private int beginPageIndex;
    private int endPageIndex;
    private List<T> recordList;
    private Map<String, Object> countResultMap;

    public Pagination()
    {
    }

    public Pagination(int pageNo, int pageSize)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Pagination(int pageNo, int pageSize, int totalCount, List<T> recordList) {
        this(pageNo, pageSize);
        this.totalCount = totalCount;
        this.recordList = recordList;

        this.pageCount = ((totalCount + pageSize - 1) / pageSize);

        if (this.pageCount <= 10) {
            this.beginPageIndex = 1;
            this.endPageIndex = this.pageCount;
        } else {
            this.beginPageIndex = (pageNo - 4);
            this.endPageIndex = (pageNo + 5);

            if (this.beginPageIndex < 1) {
                this.beginPageIndex = 1;
                this.endPageIndex = 10;
            }

            if (this.endPageIndex > this.pageCount) {
                this.endPageIndex = this.pageCount;
                this.beginPageIndex = (this.pageCount - 10 + 1);
            }
        }
    }

    public Pagination(int pageNo, int pageSize, int totalCount, List<T> recordList, Map<String, Object> countResultMap)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.recordList = recordList;
        this.countResultMap = countResultMap;

        this.pageCount = ((totalCount + pageSize - 1) / pageSize);

        if (this.pageCount <= 10) {
            this.beginPageIndex = 1;
            this.endPageIndex = this.pageCount;
        } else {
            this.beginPageIndex = (pageNo - 4);
            this.endPageIndex = (pageNo + 5);

            if (this.beginPageIndex < 1) {
                this.beginPageIndex = 1;
                this.endPageIndex = 10;
            }

            if (this.endPageIndex > this.pageCount) {
                this.endPageIndex = this.pageCount;
                this.beginPageIndex = (this.pageCount - 10 + 1);
            }
        }
    }

    public List<T> getRecordList() {
        return this.recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getBeginPageIndex() {
        return this.beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return this.endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    public Map<String, Object> getCountResultMap() {
        return this.countResultMap;
    }

    public void setCountResultMap(Map<String, Object> countResultMap) {
        this.countResultMap = countResultMap;
    }

    public ModelMap toModelMap() {
        return ModelMapUtils.toModelMap(this);
    }
}