package com.example.url_shortner.Controller;

import com.example.url_shortner.Entity.UrlMapping;
import com.example.url_shortner.Exception.InvalidUrlException;
import com.example.url_shortner.Exception.UrlNotFoundException;
import com.example.url_shortner.Service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        if (originalUrl == null || originalUrl.isEmpty()) {
            throw new InvalidUrlException("The provided URL is empty or invalid.");
        }
        UrlMapping savedMapping = urlService.shortenUrl(originalUrl);
        return new ResponseEntity<>(savedMapping, HttpStatus.CREATED);
    }

    // REDIRECT ENDPOINT
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {
        UrlMapping mapping = urlService.getOriginalUrl(shortCode);
        if (mapping == null) {
            // This will now be caught by your GlobalExceptionHandler
            throw new UrlNotFoundException("Short code " + shortCode + " not found!");
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", mapping.getOriginalUrl())
                .build();
    }

    // STATS ENDPOINT
    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlMapping> getUrlStats(@PathVariable String shortCode) {
        UrlMapping mapping = urlService.getUrlStats(shortCode); // Only fetches data
        if (mapping != null) {
            return ResponseEntity.ok(mapping);
        }
        return ResponseEntity.notFound().build();
    }
}