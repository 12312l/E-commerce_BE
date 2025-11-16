package com.example.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXSISTED(1002, "User exsisted", HttpStatus.BAD_REQUEST),

    USER_NOT_EXSISTED(1005, "User not exsisted", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1010, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_FULLNAME(1009, "Fullname must be at least {min} and at most {max}", HttpStatus.BAD_REQUEST),

    USER_NOTFOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be as least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_INCORRECT(1011, "Password incorrect", HttpStatus.BAD_REQUEST),

    ADDRESS_NOTFOUND(2001, "Address not found", HttpStatus.NOT_FOUND),


    //Category
    CATEGORY_NOTFOUND(3001, "Category not found", HttpStatus.NOT_FOUND),


    //Genres
    GENRES_NOTFOUND(4001, "Genres not found", HttpStatus.NOT_FOUND),


    //Product
    PRODUCT_NOTFOUND(5001, "Không tìm thấy sản phẩm!", HttpStatus.NOT_FOUND),


    //ProductVariant
    VARIANT_NOT_FOUND(6001, "Không tìm thấy phiên bản sản phẩm", HttpStatus.NOT_FOUND),

    //Cart
    CART_NOT_EXSISTED(7001, "Không có sản phẩm nào trong giỏ hàng", HttpStatus.NOT_FOUND),
    INVALID_ACTION(7002, "Hành động không hợp lệ", HttpStatus.BAD_REQUEST);
    ;

    private int code;
    private String message;

    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
