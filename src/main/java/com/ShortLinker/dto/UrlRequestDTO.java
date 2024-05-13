package com.ShortLinker.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;


@Getter
public class UrlRequestDTO {
    @NotBlank(message = "URL cannot be empty")
    @URL(message = "Invalid URL format")
    private String longUrl;
    private String status;
}