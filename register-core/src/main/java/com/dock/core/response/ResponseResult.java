package com.dock.core.response;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 4912047977785914583L;
    @JSONField(
            ordinal = 1
    )
    private String ticket;
    @JSONField(
            ordinal = 2
    )
    private String success;
    @JSONField(
            ordinal = 3
    )
    private Integer code;
    @JSONField(
            ordinal = 4
    )
    private String message;
    @JSONField(
            ordinal = 5
    )
    private String method;
    @JSONField(
            ordinal = 6
    )
    private String uri;
    @JSONField(
            ordinal = 7
    )
    private long timestamp;
    @JSONField(
            ordinal = 8
    )
    private Object data;
    @JSONField(
            ordinal = 9
    )
    private Object[] args;
    @JSONField(
            ordinal = 10
    )
    private String moreinfo;
    @JSONField(
            ordinal = 11
    )
    private Object detail;
    @JSONField(
            ordinal = 12
    )
    @JsonProperty("sub_code")
    private Integer subCode;
    @JSONField(
            ordinal = 13
    )
    @JsonProperty("sub_message")
    private String subMessage;
    public static final String MORE_INFO_URI = "http://help.1yd.me/article/";

    private ResponseResult() {
        this((Integer)null);
    }

    private ResponseResult(Integer code) {
        this.success = "T";
        if(null != code) {
            this.code = code;
            this.moreinfo = "http://help.1yd.me/article/" + code;
        }

        this.timestamp = DateTime.now().getMillis();
    }

    public static ResponseResult ok() {
        return (new ResponseResult()).setSuccess("T");
    }

    public static ResponseResult ok(Integer code) {
        return (new ResponseResult(code)).setSuccess("T");
    }

    public static ResponseResult getResponse() {
        return new ResponseResult();
    }

    public static ResponseResult getResponse(Integer code) {
        return (new ResponseResult(code)).setSuccess("F");
    }

    public boolean isSuccess() {
        return "T".equals(this.success);
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getSuccess() {
        return this.success;
    }

    public ResponseResult setSuccess(String success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public ResponseResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public ResponseResult setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUri() {
        return this.uri;
    }

    public ResponseResult setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public ResponseResult setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public ResponseResult setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public Object getDetail() {
        return this.detail;
    }

    public ResponseResult setDetail(Object detail) {
        this.detail = detail;
        return this;
    }

    public String getMoreinfo() {
        return this.moreinfo;
    }

    public ResponseResult setMoreinfo(String moreinfo) {
        this.moreinfo = moreinfo;
        return this;
    }

    public Integer getSubCode() {
        return this.subCode;
    }

    public ResponseResult setSubCode(Integer subCode) {
        this.subCode = subCode;
        this.success = "F";
        return this;
    }

    public String getSubMessage() {
        return this.subMessage;
    }

    public ResponseResult setSubMessage(String subMessage) {
        this.subMessage = subMessage;
        return this;
    }

    public interface RESPONSE {
        String SUCCESS = "T";
        String FAILED = "F";
    }
}
