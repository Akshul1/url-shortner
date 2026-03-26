package com.example.url_shortner.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import lombok.Data;

@Data
public class ShortenUrlRequest {
    @NotBlank(message = "URL cannot be empty")
    @URL(message = "Must be a valid URL")
    private String url;

    @Min(value = 1, message = "Expiry must be at least 1 day")
    @Max(value = 365, message = "Expiry cannot exceed 365 days")
    private int expiryDays = 30; // default 30 days
}