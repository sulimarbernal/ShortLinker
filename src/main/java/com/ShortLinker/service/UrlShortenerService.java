package com.ShortLinker.service;

import com.ShortLinker.domain.ShortUrl;
import com.ShortLinker.dto.UrlRequestDTO;
import com.ShortLinker.dto.UrlResponseDTO;
import com.ShortLinker.repository.ShortUrlRepository;
import com.ShortLinker.utility.UrlGenerator;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {
    private final ShortUrlRepository repository;

    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public UrlResponseDTO createShortUrl(UrlRequestDTO request) {
        String uniqueKey = UrlGenerator.generateUniqueKey();
        ShortUrl shortUrl = new ShortUrl(request.getLongUrl(), "http://short/" + uniqueKey);

        repository.save(shortUrl);
        return UrlResponseDTO.builder()
                .longUrl(request.getLongUrl())
                .shortUrl(shortUrl.getShortUrl())
                .status(shortUrl.getStatus()).build();
    }
}