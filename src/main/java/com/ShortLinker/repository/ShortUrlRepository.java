package com.ShortLinker.repository;

import com.ShortLinker.domain.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    ShortUrl findByShortUrl(String shortUrl);
}