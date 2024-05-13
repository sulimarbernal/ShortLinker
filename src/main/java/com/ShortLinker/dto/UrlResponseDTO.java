package com.ShortLinker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class UrlResponseDTO {
    private String longUrl;
    private String shortUrl;
    private Boolean status;

    @Data
    public static class Origin {
        private String longUrl;
        private String shortUrl;
        private Boolean status;
    }
}
