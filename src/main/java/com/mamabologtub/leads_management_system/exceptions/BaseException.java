package com.mamabologtub.leads_management_system.exceptions;

/**
 * @Author Tshepo M Mahudu on Oct 20, 2025.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -7460427256795541992L;

    private final int code;

    public BaseException() {
        super();
        this.code = -1;
    }

    public BaseException(int code) {
        super();
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
        this.code = -1;
    }

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable e, int code) {
        super(message, e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
