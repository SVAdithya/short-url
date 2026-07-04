package com.shorturl.app.exception;

public class ShortUrlNotFoundException extends ShortUrlException {
    public ShortUrlNotFoundException(String code) {
        super(
            "Short URL with code '" + code + "' not found",
            ErrorCode.E_0002
        );
    }
}
