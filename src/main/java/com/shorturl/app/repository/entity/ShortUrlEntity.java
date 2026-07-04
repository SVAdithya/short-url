package com.shorturl.app.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "short_urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;           // short code like "abc123"

    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private OffsetDateTime expiresAt;

    private Boolean enabled = true;
}
