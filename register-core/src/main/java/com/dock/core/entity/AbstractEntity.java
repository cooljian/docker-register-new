package com.dock.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dock.core.utils.JsonUtils;
import com.dock.core.utils.ModelMapUtils;
import com.dock.core.utils.StringUtils;
import com.dock.core.utils.XmlUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = -7018528945717931887L;
    @JSONField(
            ordinal = 1
    )
    private String id;
    @JSONField(
            ordinal = 2
    )
    private String tokenId;
    @JsonIgnore
    @JSONField(
            ordinal = 200
    )
    private Integer version;
    @JsonIgnore
    @JSONField(
            ordinal = 201
    )
    private Boolean locked;
    @JsonIgnore
    @JSONField(
            ordinal = 202
    )
    private Long lastAccess;
    @JSONField(
            ordinal = 203
    )
    private Boolean disabled;
    @JSONField(
            ordinal = 204
    )
    private String creatorId;
    @JSONField(
            ordinal = 205
    )
    private String createdBy;
    @JSONField(
            ordinal = 206
    )
    private Long createdTime;
    @JSONField(
            ordinal = 207
    )
    private String updaterId;
    @JSONField(
            ordinal = 208
    )
    private String updatedBy;
    @JsonIgnore
    @JSONField(
            ordinal = 998
    )
    private Boolean isNew;
    @JsonIgnore
    @JSONField(
            ordinal = 999
    )
    private Long logTime;

    public AbstractEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Boolean isLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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

    public Boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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

    public String getUpdaterId() {
        return this.updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Long getLogTime() {
        return this.logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
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
}
