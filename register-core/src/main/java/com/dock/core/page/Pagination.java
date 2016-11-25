package com.dock.core.page;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.dock.core.entity.ModelMap;
import com.dock.core.utils.ModelMapUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = 5432693588740139435L;
    public static final int DEFAULT_PAGE_NO_START = 1;
    public static final int DEFAULT_PAGE_SIZE = 15;
    public static final int DEFAULT_PAGE_COUNT = 10;
    private int pageNo;
    private int pageSize;
    private int pageCount;
    private int totalCount;
    private int beginPageIndex;
    private int endPageIndex;
    private List<T> dataList;
    private Map<String, Object> countResultMap;

    public Pagination() {
        this.pageNo = 1;
        this.pageSize = 15;
    }

    public Pagination(List<T> dataList) {
        this(0, 0);
        this.setDataList(dataList);
    }

    public Pagination(int pageNo, int pageSize) {
        this.pageNo = 1;
        this.pageSize = 15;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Pagination(int pageNo, int pageSize, int totalCount, List<T> dataList) {
        this(pageNo, pageSize);
        this.totalCount = totalCount;
        this.pageCount = (totalCount + pageSize - 1) / pageSize;
        if(this.pageCount < this.pageNo) {
            this.pageNo = this.pageCount;
        }

        this.setDataList(dataList);
        if(this.pageCount <= 10) {
            this.beginPageIndex = 1;
            this.endPageIndex = this.pageCount;
        } else {
            this.beginPageIndex = pageNo - 4;
            this.endPageIndex = pageNo + 5;
            if(this.beginPageIndex < 1) {
                this.beginPageIndex = 1;
                this.endPageIndex = 10;
            }

            if(this.endPageIndex > this.pageCount) {
                this.endPageIndex = this.pageCount;
                this.beginPageIndex = this.pageCount - 10 + 1;
            }
        }

    }

    public Pagination(int pageNo, int pageSize, int totalCount, List<T> dataList, Map<String, Object> countResultMap) {
        this(pageNo, pageSize);
        this.totalCount = totalCount;
        this.countResultMap = countResultMap;
        this.pageCount = (totalCount + pageSize - 1) / pageSize;
        if(this.pageCount < this.pageNo) {
            this.pageNo = this.pageCount;
        }

        this.setDataList(dataList);
        if(this.pageCount <= 10) {
            this.beginPageIndex = 1;
            this.endPageIndex = this.pageCount;
        } else {
            this.beginPageIndex = pageNo - 4;
            this.endPageIndex = pageNo + 5;
            if(this.beginPageIndex < 1) {
                this.beginPageIndex = 1;
                this.endPageIndex = 10;
            }

            if(this.endPageIndex > this.pageCount) {
                this.endPageIndex = this.pageCount;
                this.beginPageIndex = this.pageCount - 10 + 1;
            }
        }

    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getDraw() {
        return this.getPageNo();
    }

    public int getRecordsTotal() {
        return this.getTotalCount();
    }

    public int getRecordsFiltered() {
        return this.getTotalCount();
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
