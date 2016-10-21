package com.ecloud.page;

import java.io.Serializable;

public class PageParam
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int pageNo;
    private int pageSize;

    public PageParam(int pageNo, int pageSize)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}