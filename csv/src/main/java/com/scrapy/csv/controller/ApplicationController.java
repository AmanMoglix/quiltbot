package com.scrapy.csv.controller;

import com.scrapy.csv.service.CsvToBeanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload/*")
public class ApplicationController {

    private static final Logger logger=  LogManager.getLogger(ApplicationController.class);

    @Autowired
    CsvToBeanService service;

    @RequestMapping(value = "files",method = RequestMethod.POST)
    public  String upload(@RequestParam("file")MultipartFile file){
        logger.info("Got request to parse the file with file_name :{}",file.getOriginalFilename());
        return  service.uploadFile(file);
    }
}
