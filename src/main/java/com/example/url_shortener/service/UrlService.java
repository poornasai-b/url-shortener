package com.example.url_shortener.service;

import com.example.url_shortener.entity.Url;
import com.example.url_shortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        while (urlRepository.existsByShortCode(shortCode)) {
            shortCode = generateShortCode();
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);

        return urlRepository.save(url);
    }

    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}