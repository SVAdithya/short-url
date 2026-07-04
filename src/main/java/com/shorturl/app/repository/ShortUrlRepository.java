package com.shorturl.app.repository;

import com.shorturl.app.repository.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ShortUrlRepository extends CrudRepository<ShortUrlEntity, String> {
    ShortUrlEntity findByCode(String code);
    
    boolean existsByCode(String code);
    
    @Query("SELECT e.longUrl FROM ShortUrlEntity e WHERE e.code = :code")
    String findLongUrlByCode(@Param("code") String code);
    
    ShortUrlEntity getByCode(String code);
    
    void deleteByCode(String code);
}
