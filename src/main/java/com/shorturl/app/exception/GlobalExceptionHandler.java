package com.shorturl.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShortUrlException.class)
    public ResponseEntity<ErrorResponse> handleShortUrlException(
            ShortUrlException ex,
            WebRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("ShortUrl exception occurred: {} - {}", errorCode.name(), ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.name())
                .errorName(errorCode.getCode())
                .message(ex.getMessage())
                .status(errorCode.getHttpStatus().value())
                .timestamp(OffsetDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            WebRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());
        
        ErrorCode errorCode = ErrorCode.E_0004;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.name())
                .errorName(errorCode.getCode())
                .message(ex.getMessage())
                .status(errorCode.getHttpStatus().value())
                .timestamp(OffsetDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {
        log.error("Unexpected exception occurred", ex);
        
        ErrorCode errorCode = ErrorCode.E_0005;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.name())
                .errorName(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .timestamp(OffsetDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}
