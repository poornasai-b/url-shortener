package com.example.url_shortener.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortCode;

    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long clickCount = 0L;
}