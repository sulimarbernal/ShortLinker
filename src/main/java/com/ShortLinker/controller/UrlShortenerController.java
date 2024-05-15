package com.ShortLinker.controller;

import com.ShortLinker.domain.ShortUrl;
import com.ShortLinker.dto.UrlRequestDTO;
import com.ShortLinker.dto.UrlResponseDTO;
import com.ShortLinker.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;


@RestController
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/createShortUrl")
    public UrlResponseDTO createShortUrl(@Valid @RequestBody UrlRequestDTO request) {
        UrlResponseDTO response = service.createShortUrl(request);
        return response;
    }

    @GetMapping("/short/{shortUrl}")
    public ResponseEntity<Void> getShortUrl(@PathVariable String shortUrl) {
        ShortUrl result = service.getShortUrl(shortUrl);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(result.getLongUrl())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}