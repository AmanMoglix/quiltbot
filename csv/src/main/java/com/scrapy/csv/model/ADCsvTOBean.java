package com.scrapy.csv.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.net.CacheRequest;
import java.util.List;
@Data
public class ADCsvTOBean {
    @CsvBindByName(column = "About_this_Items")
    private String About_this_Items;
    @CsvBindByName(column = "Additional_Details")
    private String Additional_Details;
    @CsvBindByName(column = "Brand_name")
    private String Brand_name;
    @CsvBindByName(column = "Category_L1")
    private String Category_L1;
    @CsvBindByName(column = "Category_L2")
    private  String Category_L2;
    @CsvBindByName(column = "Category_L3")
    private String Category_L3;
    @CsvBindByName(column = "Category_L4")
    private String Category_L4;
    @CsvBindByName(column = "Category_L5")
    private String Category_L5;
    @CsvBindByName(column = "Mrp_Amazon")
    private String Mrp_Amazon;
    @CsvBindByName(column = "Product_Name")
    private String Product_Name;
    @CsvBindByName(column = "Product_Specification")
    private String Product_Specification;
    @CsvBindByName(column = "Product_details")
    private String Product_details;
    @CsvBindByName(column = "SP_Amazon")
    private String SP_Amazon;
    @CsvBindByName(column = "Specification")
    private String Specification;
    @CsvBindByName(column = "Stock_status")
    private String Stock_status;
    @CsvBindByName(column = "Technical_Details")
    private String Technical_Details;
    @CsvBindByName(column = "Url")
    private String Url;
    @CsvBindByName(column = "image")
    private String image;



}
