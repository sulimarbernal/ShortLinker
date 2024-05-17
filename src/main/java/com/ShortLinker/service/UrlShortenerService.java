package com.ShortLinker.service;

import com.ShortLinker.domain.ShortUrl;
import com.ShortLinker.dto.ShortUrlUpdateDTO;
import com.ShortLinker.dto.UrlRequestDTO;
import com.ShortLinker.dto.UrlResponseDTO;
import com.ShortLinker.repository.ShortUrlRepository;
import com.ShortLinker.utility.UrlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortenerService {
    private final ShortUrlRepository repository;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }


    public UrlResponseDTO createShortUrl(UrlRequestDTO request) {
        String uniqueKey = UrlGenerator.generateUniqueKey();
        ShortUrl shortUrl = new ShortUrl(request.getLongUrl(), baseUrl+"/short/" + uniqueKey);

        repository.save(shortUrl);
        return UrlResponseDTO.builder()
                .id(shortUrl.getId())
                .longUrl(request.getLongUrl())
                .shortUrl(shortUrl.getShortUrl())
                .status(shortUrl.getStatus()).build();
    }

    public ShortUrl getShortUrl(String shortUrl) {
        String shortUrlCompleted = baseUrl+"/short/"+shortUrl;
        return repository.findByShortUrl(shortUrlCompleted);
    }

    public ShortUrl updateShortUrl(String id, ShortUrlUpdateDTO updateDTO) {
        Optional<ShortUrl> shortUrlOptional = repository.findById(id);
        if (shortUrlOptional.isPresent()) {
            ShortUrl shortUrl = shortUrlOptional.get();

            updateDTO.getLongUrl().ifPresent(shortUrl::setLongUrl);
            updateDTO.getStatus().ifPresent(shortUrl::setStatus);

            return repository.save(shortUrl);
        } else {
            return null;
        }
    }
}