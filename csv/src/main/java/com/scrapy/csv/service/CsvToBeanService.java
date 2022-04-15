package com.scrapy.csv.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvToBeanService {
    public String uploadFile(MultipartFile file);

    String uploadMappingFile(MultipartFile file);
}
