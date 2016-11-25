package com.dock.core.exception;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class ServiceException extends AbstractException {
    private static final long serialVersionUID = 8942560122796902237L;

    public ServiceException(Integer code) {
        super(code);
    }

    public ServiceException(Integer code, String message) {
        super(code, message);
    }

    public ServiceException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ServiceException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public ServiceException(Integer code, String message, Throwable cause, Object... args) {
        super(code, message, cause, args);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
