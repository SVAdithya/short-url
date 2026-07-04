package com.shorturl.app.exception;

public class InvalidUrlException extends ShortUrlException {
    public InvalidUrlException(String url) {
        super(
            "Invalid URL provided: '" + url + "'",
            ErrorCode.E_0003
        );
    }
}
