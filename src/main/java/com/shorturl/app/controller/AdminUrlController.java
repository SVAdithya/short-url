package com.shorturl.app.controller;

import com.shorturl.app.controller.model.CreateShortUrlRequest;
import com.shorturl.app.controller.model.ShortUrlResponse;
import com.shorturl.app.controller.model.StatisticsResponse;
import com.shorturl.app.controller.model.UpdateShortUrlRequest;
import com.shorturl.app.service.ShortUrlService;
import com.shorturl.app.service.model.ShortUrlResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AdminUrlController implements AdminUrlApi {
    private final ShortUrlService shortUrlService;

    @Override
    public ResponseEntity<ShortUrlResponse> createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        ShortUrlResult result = shortUrlService.createShortUrl(
                createShortUrlRequest.getLongUrl(),
                createShortUrlRequest.getCustomAlias()
        );
        ShortUrlResponse response = new ShortUrlResponse()
                .code(result.code())
                .shortUrl(result.shortUrl())
                .longUrl(result.longUrl())
                .createdAt(result.createdAt())
                .expiresAt(result.expiresAt())
                .clickCount(result.clickCount())
                .enabled(result.enabled());
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteShortUrl(String code) {
        shortUrlService.deleteShortUrl(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ShortUrlResponse> getShortUrl(String code) {
        ShortUrlResult result = shortUrlService.getShortUrlDetails(code);
        ShortUrlResponse response = new ShortUrlResponse()
                .code(result.code())
                .shortUrl(result.shortUrl())
                .longUrl(result.longUrl())
                .createdAt(result.createdAt())
                .expiresAt(result.expiresAt())
                .clickCount(result.clickCount())
                .enabled(result.enabled());
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<StatisticsResponse> getStatistics(String code) {
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<Void> updateShortUrl(String code, UpdateShortUrlRequest updateShortUrlRequest) {
        shortUrlService.updateShortUrl(code, updateShortUrlRequest.getLongUrl(), updateShortUrlRequest.getEnabled());
        return ResponseEntity.noContent().build();
    }
}
