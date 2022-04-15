package com.scrapy.csv.repo;

import com.scrapy.csv.model.CSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvREpo extends JpaRepository<CSV,String> {
}
