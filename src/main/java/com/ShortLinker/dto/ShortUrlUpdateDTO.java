package com.ShortLinker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class ShortUrlUpdateDTO {
    private String longUrl;
    private Boolean status;

}
