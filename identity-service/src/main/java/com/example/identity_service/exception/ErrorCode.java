package com.example.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    INVALID_KEY(1001, "Invalid message key"),
    USER_EXSISTED(1002, "User exsisted"),

    USER_NOT_EXSISTED(1005, "User not exsisted"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    INVALID_PASSWORD(1003,"Password must be at least 3 characters"),

    USER_NOTFOUND(1004, "User not found"),
    UNAUTHENTICATED(1006, "Unauthenticated");
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
