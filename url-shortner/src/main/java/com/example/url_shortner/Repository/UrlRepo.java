package com.example.url_shortner.Repository;

import com.example.url_shortner.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends JpaRepository<UrlMapping,Integer> {
    UrlMapping findByShortCode(String shortCode);
}
