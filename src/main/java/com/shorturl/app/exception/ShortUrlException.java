package com.shorturl.app.exception;

import lombok.Getter;

@Getter
public abstract class ShortUrlException extends RuntimeException {
    private final ErrorCode errorCode;

    public ShortUrlException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
