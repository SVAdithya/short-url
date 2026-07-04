package com.shorturl.app.service;

import com.shorturl.app.controller.model.ShortUrlResponse;
import com.shorturl.app.exception.DuplicateAliasException;
import com.shorturl.app.exception.ShortUrlNotFoundException;
import com.shorturl.app.repository.ShortUrlRepository;
import com.shorturl.app.repository.entity.ShortUrlEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ShortUrlService {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlService.class);
    @Value("${shorturl.baseurl:http://localhost:8080/}")
    private String baseUrl;

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlResponse createShortUrl(URI longUrl, String customAlias) {
        if(shortUrlRepository.existsByCode(customAlias)) {
            throw new DuplicateAliasException(customAlias);
        }

        ShortUrlEntity entity = ShortUrlEntity.builder()
                .code(customAlias)
                .longUrl(validateFullUrl(String.valueOf(longUrl)))
                .expiresAt(null)           // null = never expires
                .enabled(true)
                .build();

        ShortUrlEntity res = shortUrlRepository.save(entity);
        return transformToResponse(res);
    }

    public String getOriginalUrl(String code) {
        log.info("Get original url for code {}", code);
        return shortUrlRepository.findLongUrlByCode(code);
    }

    public void deleteShortUrl(String code) {
        shortUrlRepository.deleteByCode(code);
    }

    public ShortUrlResponse getShortUrlDetails(String code) {
        ShortUrlEntity res = shortUrlRepository.getByCode(code);
        if (res == null) {
            throw new ShortUrlNotFoundException(code);
        }
        return transformToResponse(res);
    }

    public ShortUrlResponse updateShortUrl(String code, @Valid URI longUrl, Boolean enabled) {
        ShortUrlEntity entity = shortUrlRepository.getByCode(code);
        if (entity == null) {
            throw new ShortUrlNotFoundException(code);
        }
        entity.setLongUrl(longUrl.toString());
        entity.setEnabled(enabled);
        ShortUrlEntity res = shortUrlRepository.save(entity);
        return transformToResponse(res);
    }

    private ShortUrlResponse transformToResponse(ShortUrlEntity shortUrlEntity) {
        return new ShortUrlResponse()
                .code(shortUrlEntity.getCode())
                .shortUrl(baseUrl + "/" +shortUrlEntity.getCode())
                .longUrl(URI.create(shortUrlEntity.getLongUrl()))
                .createdAt(shortUrlEntity.getCreatedAt())
                .expiresAt(shortUrlEntity.getExpiresAt())
                .enabled(shortUrlEntity.getEnabled());
    }

    private static String validateFullUrl(String longUrl) {
        // Implementation for validating full URL
        if(!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "http://" + longUrl;
        }
        return longUrl;
    }
}
