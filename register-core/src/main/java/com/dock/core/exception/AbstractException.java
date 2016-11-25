package com.dock.core.exception;//


public abstract class AbstractException extends RuntimeException {
    private static final long serialVersionUID = 712661576725020479L;
    private Integer code;
    private Object[] args;

    public AbstractException(Integer code) {
        this.code = code;
    }

    public AbstractException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AbstractException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public AbstractException(Integer code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public AbstractException(Integer code, String message, Throwable cause, Object... args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return this.code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public boolean hasArgs() {
        return this.args != null && this.args.length > 0;
    }
}
