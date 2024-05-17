package com.ShortLinker.utility;

import com.ShortLinker.dto.ShortUrlUpdateDTO;
import lombok.experimental.UtilityClass;
import org.apache.coyote.BadRequestException;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.Optional;

@UtilityClass
public class ValidateShortUrlUpdate {
    static public void validate(ShortUrlUpdateDTO shortUrlUpdate) throws BadRequestException {
        ensureNonNullDto(shortUrlUpdate);

        boolean isUrlEmpty = checkIsEmpty(shortUrlUpdate.getLongUrl());
        boolean isStatusEmpty = checkIsEmptyBoolean(shortUrlUpdate.getStatus());

        validateFieldsPresence(isUrlEmpty, isStatusEmpty);
        validateUrlIfPresent(shortUrlUpdate.getLongUrl(), isUrlEmpty);
    }

    private void ensureNonNullDto(ShortUrlUpdateDTO dto) throws BadRequestException {
        if (dto == null) {
            throw new BadRequestException("ShortUrlUpdateDTO cannot be null");
        }
    }

    private boolean checkIsEmpty(Optional<String> field) {
        return field.isEmpty() || StringUtils.isEmpty(field.get());
    }

    private boolean checkIsEmptyBoolean(Optional<Boolean> field) {
        return field.isEmpty() || StringUtils.isEmpty(field.get());
    }

    private void validateFieldsPresence(boolean isUrlEmpty, boolean isStatusEmpty) throws BadRequestException {
        if (isUrlEmpty && isStatusEmpty) {
            throw new BadRequestException("Both longUrl and status cannot be empty");
        }
        if (isStatusEmpty) {
            if (!isUrlEmpty) {
                return;
            }
            throw new BadRequestException("Status cannot be empty");
        }
    }

    private void validateUrlIfPresent(Optional<String> url, boolean isUrlEmpty) throws BadRequestException {
        if (!isUrlEmpty && !validateURL(url.get())) {
            throw new BadRequestException("Invalid URL format");
        }
    }

    private boolean validateURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}