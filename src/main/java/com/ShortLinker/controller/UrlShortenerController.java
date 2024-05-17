package com.ShortLinker.controller;

import com.ShortLinker.application.metrics.Metric;
import com.ShortLinker.domain.ShortUrl;
import com.ShortLinker.dto.ShortUrlUpdateDTO;
import com.ShortLinker.dto.UrlRequestDTO;
import com.ShortLinker.dto.UrlResponseDTO;
import com.ShortLinker.service.UrlShortenerService;
import com.ShortLinker.utility.ValidateShortUrlUpdate;
import org.apache.coyote.BadRequestException;
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
    @Metric("createShortUrl")
    public ResponseEntity<UrlResponseDTO> createShortUrl(@Valid @RequestBody UrlRequestDTO request) {
        UrlResponseDTO response = service.createShortUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/short/{shortUrl}")
    @Metric("redirectToLongUrl")
    public ResponseEntity<Void> getShortUrl(@PathVariable String shortUrl) {
        ShortUrl result = service.getShortUrl(shortUrl);
        if (result != null && result.getStatus()) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(result.getLongUrl())).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateUrlProperties/{id}")
    @Metric("updateUrlProperties")
    public ResponseEntity<ShortUrl> updateShortUrl(@PathVariable String id, @Valid @RequestBody ShortUrlUpdateDTO updateDTO) {
        try {
            ValidateShortUrlUpdate.validate(updateDTO);
            ShortUrl updatedShortUrl = service.updateShortUrl(id, updateDTO);
            if (updatedShortUrl != null) {
                return ResponseEntity.ok(updatedShortUrl);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}