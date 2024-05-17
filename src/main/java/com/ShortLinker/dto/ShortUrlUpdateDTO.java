package com.ShortLinker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Data
@Builder
@Getter
@AllArgsConstructor
public class ShortUrlUpdateDTO {
    private Optional<String> longUrl;
    private Optional<Boolean> status;

}
