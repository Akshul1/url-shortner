package com.example.url_shortner.Service;

import com.example.url_shortner.Entity.UrlMapping;
import com.example.url_shortner.Repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepo urlRepo;

    private static final String CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private final Random random = new Random();

    public UrlMapping shortenUrl(String originalUrl) {
        String shortCode = generateUniqueShortCode();

        UrlMapping mapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createAt(LocalDateTime.now())
                .accessCount(0) // Initialize count to 0
                .build();

        return urlRepo.save(mapping);
    }

    // This method is for REDIRECTION (Increments count)
    @Cacheable(value = "urls", key = "#shortCode")
    public UrlMapping getOriginalUrl(String shortCode) {
        UrlMapping mapping = urlRepo.findByShortCode(shortCode);

        if (mapping != null) {
            mapping.setAccessCount(mapping.getAccessCount() + 1);
            return urlRepo.save(mapping);
        }
        return null; // Return null so Controller can send 404
    }

    // This method is for STATS (Does NOT increment count)
    public UrlMapping getUrlStats(String shortCode) {
        return urlRepo.findByShortCode(shortCode);
    }

    private String generateUniqueShortCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(CHARACTER.charAt(random.nextInt(CHARACTER.length())));
            }
            code = sb.toString();
        } while (urlRepo.findByShortCode(code) != null);
        return code;
    }
}