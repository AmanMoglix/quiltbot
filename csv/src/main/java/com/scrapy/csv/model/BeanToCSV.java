package com.scrapy.csv.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;
@Data
public class BeanToCSV {
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "About_this_Items")
    private String About_this_Items;

    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Additional_Details")
    private String Additional_Details;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Brand_name")
    private String Brand_name;
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Category_L1")
    private String Category_L1;
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Category_L2")
    private  String Category_L2;
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Category_L3")
    private String Category_L3;
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "Category_L4")
    private String Category_L4;
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "Category_L5")
    private String Category_L5;
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "Mrp_Amazon")
    private String Mrp_Amazon;
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "Product_Name")
    private String Product_Name;
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "Product_Specification")
    private String Product_Specification;
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "SP_Amazon")
    private String SP_Amazon;
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "Specification")
    private String Specification;
    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "Stock_status")
    private String Stock_status;
    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "Technical_Details")
    private String Technical_Details;
    @CsvBindByPosition(position = 15)
    @CsvBindByName(column = "Url")
    private String Url;
    @CsvBindByPosition(position = 16)
    @CsvBindByName(column = "image")
    private String image;
    @CsvBindByPosition(position = 17)
    @CsvBindByName(column = "Is Discontinued By Manufacturer")
    private String isDiscont;
    @CsvBindByPosition(position = 18)
    @CsvBindByName(column = "Product Dimensions")
    private String pDimension;
    @CsvBindByPosition(position = 19)
    @CsvBindByName(column = "Date First Available")
    private String dateFirstAvailable;
    @CsvBindByPosition(position = 20)
    @CsvBindByName(column = "Manufacturer")
    private String manufacture;
    @CsvBindByPosition(position = 21)
    @CsvBindByName(column = "ASIN")
    private String asin;
    @CsvBindByPosition(position = 22)
    @CsvBindByName(column = "Item model number")
    private String itemMNumber;
    @CsvBindByPosition(position = 23)
    @CsvBindByName(column = "Country of Origin")
    private String coo;
    @CsvBindByPosition(position = 24)
    @CsvBindByName(column = "Packer")
    private String packer;
    @CsvBindByPosition(position = 25)
    @CsvBindByName(column = "Importer")
    private String importer;
    @CsvBindByPosition(position = 26)
    @CsvBindByName(column = "Item Weight")
    private String itemWeight;

    @CsvBindByPosition(position = 27)
    @CsvBindByName(column = "Item Dimensions LxWxH")
    private String iDimension;

    @CsvBindByPosition(position = 28)
    @CsvBindByName(column = "Net Quantity")
    private String netQ;

    @CsvBindByPosition(position = 29)
    @CsvBindByName(column = "Included Components")
    private String includedC;


    public static BeanToCSV mapFormBeanToCsv(ADCsvTOBean csvtobeans) {
        BeanToCSV beanToCSV= new BeanToCSV();



        try {
            beanToCSV.setAbout_this_Items(csvtobeans.getAbout_this_Items());
            beanToCSV.setAdditional_Details(csvtobeans.getAdditional_Details());
            beanToCSV.setBrand_name(csvtobeans.getBrand_name());
            beanToCSV.setCategory_L1(csvtobeans.getCategory_L1());
            beanToCSV.setCategory_L2(csvtobeans.getCategory_L2());
            beanToCSV.setCategory_L3(csvtobeans.getCategory_L3());
            beanToCSV.setCategory_L4(csvtobeans.getCategory_L4());
            beanToCSV.setCategory_L5(csvtobeans.getCategory_L5());
            beanToCSV.setMrp_Amazon(csvtobeans.getMrp_Amazon());
            beanToCSV.setProduct_Name(csvtobeans.getProduct_Name());
            beanToCSV.setProduct_Specification(csvtobeans.getProduct_Specification());
            beanToCSV.setSP_Amazon(csvtobeans.getSP_Amazon());
            beanToCSV.setSpecification(csvtobeans.getSpecification());
            beanToCSV.setStock_status(csvtobeans.getStock_status());
            beanToCSV.setTechnical_Details(csvtobeans.getTechnical_Details());
            beanToCSV.setUrl(csvtobeans.getUrl());
            beanToCSV.setImage(csvtobeans.getImage());
            //capture the productDEtails
            if (!csvtobeans.getProduct_details().isEmpty() && csvtobeans.getProduct_details() != null)
                beanToCSV = addProductDetails(beanToCSV, csvtobeans.getProduct_details());
        }catch (Exception e){

        }
        return beanToCSV;

    }

    private static BeanToCSV addProductDetails(BeanToCSV beanToCSV, String product_details) {
        String [] slit=product_details.split("[||]");
        for(String e:slit) {
            String [] s2=e.split(":");
            if(s2.length>=2) {
                String columnNAme=e.split(":")[0].replace("\\n","").replace("\\u200f","").replace("\\u200e","").trim();
                String value=e.split(":")[1].replace("\\n","").replace("\\u200e","").trim();
                if(columnNAme.equalsIgnoreCase("Is Discontinued By Manufacturer"))
                    beanToCSV.setIsDiscont(value);
                if(columnNAme.equalsIgnoreCase("Product Dimensions"))
                    beanToCSV.setPDimension(value);
                if(columnNAme.equalsIgnoreCase("Date First Available"))
                    beanToCSV.setDateFirstAvailable(value);
                if(columnNAme.equalsIgnoreCase("Manufacturer"))
                    beanToCSV.setManufacture(value);
                if(columnNAme.equalsIgnoreCase("ASIN"))
                    beanToCSV.setAsin(value);
                if(columnNAme.equalsIgnoreCase("Item model number"))
                    beanToCSV.setItemMNumber(value);
                if(columnNAme.equalsIgnoreCase("Country of Origin"))
                    beanToCSV.setCoo(value);
                if(columnNAme.equalsIgnoreCase("Packer"))
                    beanToCSV.setPacker(value);
                if(columnNAme.equalsIgnoreCase("Importer"))
                    beanToCSV.setImporter(value);
                if(columnNAme.equalsIgnoreCase("Item Weight"))
                    beanToCSV.setItemWeight(value);
                if(columnNAme.equalsIgnoreCase("Item Dimensions LxWxH"))
                    beanToCSV.setIDimension(value);
                if(columnNAme.equalsIgnoreCase("Net Quantity"))
                    beanToCSV.setNetQ(value);
                if (columnNAme.equalsIgnoreCase("Included Components"))
                    beanToCSV.setIncludedC(value);

            }
        }
        return beanToCSV;
    }
}
