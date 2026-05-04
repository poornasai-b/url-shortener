package com.example.url_shortener.controller;

import com.example.url_shortener.entity.Url;
import com.example.url_shortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestParam String originalUrl) {
        Url url = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", originalUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}