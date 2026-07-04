package com.shorturl.app.controller;

import com.shorturl.app.controller.model.CreateShortUrlRequest;
import com.shorturl.app.controller.model.ShortUrlResponse;
import com.shorturl.app.controller.model.StatisticsResponse;
import com.shorturl.app.controller.model.UpdateShortUrlRequest;
import com.shorturl.app.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class AdminUrlController implements AdminUrlApi {
    private final ShortUrlService shortUrlService;

    @Override
    public ResponseEntity<ShortUrlResponse> createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        ShortUrlResponse response = shortUrlService.createShortUrl(
                createShortUrlRequest.getLongUrl(),
                createShortUrlRequest.getCustomAlias()
        );
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteShortUrl(String code) {
        shortUrlService.deleteShortUrl(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ShortUrlResponse> getShortUrl(String code) {
        ShortUrlResponse response = shortUrlService.getShortUrlDetails(code);
        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<StatisticsResponse> getStatistics(String code) {
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<Void> updateShortUrl(String code, UpdateShortUrlRequest updateShortUrlRequest) {
        if(Objects.isNull(updateShortUrlRequest) || Objects.isNull(code)
                || Objects.isNull(updateShortUrlRequest.getLongUrl())
                || Objects.isNull(updateShortUrlRequest.getEnabled())) {
            return ResponseEntity.badRequest().build();
        }
        shortUrlService.updateShortUrl(code, updateShortUrlRequest.getLongUrl(), updateShortUrlRequest.getEnabled());
        return ResponseEntity.noContent().build();
    }
}
