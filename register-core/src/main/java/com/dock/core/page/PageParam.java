package com.dock.core.page;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(PageParam.class);
    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private int pageNo = 1;
    private int pageSize = 10;

    public PageParam() {
    }

    public PageParam(String json) {
        try {
            JSONObject e = JSON.parseObject(json);
            if(e != null) {
                this.pageNo = e.getIntValue("pageNo");
                this.pageSize = e.getIntValue("pageSize");
            }
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
        }

    }

    public PageParam(int pageNo, int pageSize) {
        this.pageNo = pageNo > 0?pageNo:1;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo > 0?pageNo:1;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
