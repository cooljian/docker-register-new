package com.dock.core;

import com.dock.utils.JsonUtils;
import com.dock.utils.ModelMapUtils;
import com.dock.utils.StringUtils;
import com.dock.utils.XmlUtils;

import java.io.Serializable;

public abstract class AbstractEntity
        implements Serializable
{
    private static final long serialVersionUID = -5018704597605640564L;
    private String id;
    private Integer version = Integer.valueOf(0);
    private Long lastAccess = Long.valueOf(0L);
    private String createdBy;
    private Long createdTime = Long.valueOf(0L);
    private String updatedBy;
    private Long updatedTime;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public Long getLastAccess() {
        return this.lastAccess;
    }
    public void setLastAccess(Long lastAccess) {
        this.lastAccess = lastAccess;
    }
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Long getCreatedTime() {
        return this.createdTime;
    }
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String toString() {
        return StringUtils.toObjectString(this);
    }

    public String toXmlString() {
        return XmlUtils.toXmlString(this);
    }

    public String toJsonString() {
        return JsonUtils.toJsonString(this);
    }

    public ModelMap toModelMap() {
        return ModelMapUtils.toModelMap(this);
    }

    public static <T> T fromXmlString(String xml, Class<T> cls) {
        return XmlUtils.parseJavaObject(xml, cls);
    }

    public static <T> T fromJsonString(String json, Class<T> cls) {
        return JsonUtils.parseJavaObject(json, cls);
    }

    public static <T> T fromModelMap(ModelMap model, Class<T> cls) {
        return ModelMapUtils.parseJavaObject(model, cls);
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }
}