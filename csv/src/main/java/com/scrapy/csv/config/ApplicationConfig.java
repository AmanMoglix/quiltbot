package com.scrapy.csv.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationConfig {
    @Value("${upload-dir}")
    private String uploadDir;
}
