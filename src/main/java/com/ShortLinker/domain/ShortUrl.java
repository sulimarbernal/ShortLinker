package com.ShortLinker.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ShortUrl {
    @Id
    public  String id;
    private String longUrl;
    private String shortUrl;
    private Boolean status;

    public ShortUrl(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.status = true;
    }
}