package com.ShortLinker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlRequestDTO {
    @NotBlank(message = "URL cannot be empty")
    @URL(message = "Invalid URL format")
    private String longUrl;
    private String status;
}