package com.shorturl.app.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    E_0001("DUPLICATE_ALIAS", HttpStatus.CONFLICT, "Short URL alias already exists"),
    E_0002("SHORT_URL_NOT_FOUND", HttpStatus.NOT_FOUND, "Short URL not found"),
    E_0003("INVALID_URL", HttpStatus.BAD_REQUEST, "Invalid URL format provided"),
    E_0004("INVALID_ARGUMENT", HttpStatus.BAD_REQUEST, "Invalid argument provided"),
    E_0005("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
