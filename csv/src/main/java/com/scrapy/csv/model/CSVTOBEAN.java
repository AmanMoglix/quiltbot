package com.scrapy.csv.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class CSVTOBEAN {
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "msn")
    private String msn;

    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "r_1")
    private String r_1;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "r_2")
    private String r_2;

    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "r_3")
    private String r_3;
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "r_4")
    private String r_4;

    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "r_5")
    private String r_5;
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "r_6")
    private String r_6;

    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "r_7")
    private String r_7;
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "r_8")
    private String r_8;

    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "r_9")
    private String r_9;
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "r_10")
    private String r_10;

}
