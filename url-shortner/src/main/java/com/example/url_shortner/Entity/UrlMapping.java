package com.example.url_shortner.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UrlMapping {

    @Id
    @GeneratedValue
    private Integer id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createAt;
    private int accessCount;



}
