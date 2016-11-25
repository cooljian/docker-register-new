package com.dock.core.exception;

public class DAOException extends AbstractException {
    private static final long serialVersionUID = -3451002831309091545L;

    public DAOException(Integer code) {
        super(code);
    }

    public DAOException(Integer code, String message) {
        super(code, message);
    }

    public DAOException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public DAOException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public DAOException(Integer code, String message, Throwable cause, Object... args) {
        super(code, message, cause, args);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
