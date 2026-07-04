package com.shorturl.app.controller;

import com.shorturl.app.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import java.net.URI;

@RestController
@AllArgsConstructor
public class ShortUrlController  implements ShortUrlApi {
    private final ShortUrlService shortUrlService;

    @Override
    public ResponseEntity<Void> redirect(String code){
        return ResponseEntity
                .status(302)
                .location(URI.create(shortUrlService.getOriginalUrl(code)))
                .build();
    }
}
