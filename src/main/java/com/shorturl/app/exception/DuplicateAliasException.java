package com.shorturl.app.exception;

public class DuplicateAliasException extends ShortUrlException {
    public DuplicateAliasException(String alias) {
        super(
            "Short URL alias '" + alias + "' already exists",
            ErrorCode.E_0001
        );
    }
}
