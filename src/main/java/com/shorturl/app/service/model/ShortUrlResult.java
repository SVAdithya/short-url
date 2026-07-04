package com.shorturl.app.service.model;

import java.net.URI;
import java.time.OffsetDateTime;

public record ShortUrlResult(
        String code,
        String shortUrl,
        URI longUrl,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt,
        Integer clickCount,
        Boolean enabled
) {
}
