package com.scrapy.csv.service.impl;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.scrapy.csv.config.ApplicationConfig;
import com.scrapy.csv.config.CustomCSVMappingStrategy;
import com.scrapy.csv.config.FileStorageUtils;
import com.scrapy.csv.controller.ApplicationController;
import com.scrapy.csv.model.ADCsvTOBean;
import com.scrapy.csv.model.BeanToCSV;
import com.scrapy.csv.model.CSV;
import com.scrapy.csv.model.CSVTOBEAN;
import com.scrapy.csv.repo.CsvREpo;
import com.scrapy.csv.service.CsvToBeanService;
import com.scrapy.csv.util.ApiManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvToBeanServiceImple implements CsvToBeanService {
    private static final Logger logger=  LogManager.getLogger(CsvToBeanServiceImple.class);

    @Autowired
    FileStorageUtils fileStorageUtils;

    @Autowired
    CsvREpo rEpo;

    @Autowired
    private ApplicationConfig config;

    @Autowired
    ApiManager apiManager;
    @Override
    public String uploadFile(MultipartFile file) {

        Pair<Boolean, String> storedPair = fileStorageUtils.storeFile(file);

        if(storedPair.getFirst()){


        try (Reader reader = new FileReader(config.getUploadDir() + File.separator + storedPair.getSecond())) {
            CsvToBean<CSVTOBEAN> csvToBean=new CsvToBeanBuilder<CSVTOBEAN>(reader)
                    .withType(CSVTOBEAN.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                    .build();

            List<CSVTOBEAN> plafeed=csvToBean.parse();
            List<CSVTOBEAN> chunks= new ArrayList<>();

            for(int i=1; i<plafeed.size();i+=10){
                List<CSVTOBEAN> failedChunk= new ArrayList<>();

                chunks=plafeed.subList(i,i+Math.min(plafeed.size()-i,10));
                  //caputer api call
                   preporecessingOfPra(chunks,failedChunk);

                   writeFileToCsv(failedChunk,CSVTOBEAN.class);
            }




        }catch (Exception ex) {
            ex.printStackTrace();
            return "error occured while reading and writing the file";
        }}
        return null;
    }

    private void preporecessingOfPra(List<CSVTOBEAN> chunks, List<CSVTOBEAN> failedChunk) {
try {
    for (CSVTOBEAN csvtobean : chunks) {
        CSVTOBEAN failed = new CSVTOBEAN();
        if (StringUtils.isNotBlank(csvtobean.getR_1()))
            failed.setR_1(getParphrased(csvtobean.getR_1()));
        if (StringUtils.isNotBlank(csvtobean.getR_2()))
            failed.setR_2(getParphrased(csvtobean.getR_2()));
        if (StringUtils.isNotBlank(csvtobean.getR_3()))
            failed.setR_3(getParphrased(csvtobean.getR_3()));
        if (StringUtils.isNotBlank(csvtobean.getR_4()))
            failed.setR_4(getParphrased(csvtobean.getR_4()));
        if (StringUtils.isNotBlank(csvtobean.getR_1()))
            failed.setR_5(getParphrased(csvtobean.getR_5()));
        if (StringUtils.isNotBlank(csvtobean.getR_6()))
            failed.setR_6(getParphrased(csvtobean.getR_6()));
        if (StringUtils.isNotBlank(csvtobean.getR_1()))
            failed.setR_7(getParphrased(csvtobean.getR_7()));
        if (StringUtils.isNotBlank(csvtobean.getR_8()))
            failed.setR_8(getParphrased(csvtobean.getR_8()));
        if (StringUtils.isNotBlank(csvtobean.getR_9()))
            failed.setR_9(getParphrased(csvtobean.getR_9()));
        if (StringUtils.isNotBlank(csvtobean.getR_10()))
            failed.setR_10(getParphrased(csvtobean.getR_10()));

        failed.setMsn(csvtobean.getMsn());
        failedChunk.add(failed);

        logger.info("response size ", new Gson().toJson(failed) + " "+ failedChunk.size());
    }
}catch (Exception e){
    writeFileToCsv(failedChunk,CSVTOBEAN.class);

}
    }

    private String  getParphrased(String r_1) {
        StringBuilder response =new StringBuilder();
        //review validation

        if(r_1.contains(".")){
            //split the review and make quiltboat call
            String[] reviews=r_1.split("[.]");
            //make a api call
            getParphrasedResponse(reviews,response);
        }else{
            getParphrasedResponses(r_1,response);
        }


        return response.toString();
    }

    private void getParphrasedResponse(String[] reviews,StringBuilder response) {
        for(String review:reviews){
            if(StringUtils.isNotBlank(review)){
           Map<String,String> req=new HashMap<>();
           req.put("text",review);
            response.append(apiManager.getApi("https://quillbot.com/api/paraphraser/single-paraphrase/2",req,false,1000));
            System.out.println("response from "+response);
        }}
    }
    private void getParphrasedResponses(String reviews,StringBuilder response) {
        if(StringUtils.isNotBlank(reviews)) {

            Map<String, String> req = new HashMap<>();
            req.put("text", reviews);
            response.append(apiManager.getApi("https://quillbot.com/api/paraphraser/single-paraphrase/2", req, false, 1000));
            System.out.println("response from " + response);
        }
    }

    @Override
    public String uploadMappingFile(MultipartFile file) {
        Pair<Boolean, String> storedPair = fileStorageUtils.storeFile(file);

        if(storedPair.getFirst()) {


            try (Reader reader = new FileReader(config.getUploadDir() + File.separator + storedPair.getSecond())) {
                CsvToBean<ADCsvTOBean> csvToBean = new CsvToBeanBuilder<ADCsvTOBean>(reader)
                        .withType(ADCsvTOBean.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                        .build();

                List<ADCsvTOBean> plafeed = csvToBean.parse();
                List<BeanToCSV> csvtobeans = new ArrayList<>();
                for (ADCsvTOBean csvtobean : plafeed) {
                    BeanToCSV beanToCSV = BeanToCSV.mapFormBeanToCsv(csvtobean);
                    csvtobeans.add(beanToCSV);
                }
                //writeFileToCsv(csvtobeans);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }





//    public void saveMethod( List<CSVTOBEAN> csvtobeans,String product,String uri){
//        try
//        {
//           String url="https://www.amazon.in/dp/";
//           url=url.concat(product);
//           CSVTOBEAN  csvtobean= new CSVTOBEAN();
//           csvtobean.setProductId(url);
//           csvtobean.setUrl(uri);
//           csvtobeans.add(csvtobean);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    private <T>Pair<Boolean, String> writeFileToCsv(List<T> failedRows,Class<T> clazz) {
        final String fileName = "scrapped_product_upated" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".csv";

        try (Writer writer = Files.newBufferedWriter(Paths.get(config.getUploadDir() + fileName));){
            // Create Mapping Strategy to arrange the
            // column name in order
            final CustomCSVMappingStrategy<T> mappingStrategy = new CustomCSVMappingStrategy<>();

            mappingStrategy.setType(clazz);

            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            sbc.write(failedRows);

        } catch ( IOException e) {
            logger.error("Error in CSV IO operations. Make sure data directory exists with write permissions.", e);
            return Pair.of(false, "Error in creating file.");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error("Error in CSV Write operation", e);
            return Pair.of(false, "Error in CSV Write operation");
        }

        return Pair.of(true, fileName);

    }
//    private Pair<Boolean, String> writeFileToCsv(List<CSVTOBEAN> failedRows) {
//        final String fileName = "scrapped_product_url" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".csv";
//
//        try (Writer writer = Files.newBufferedWriter(Paths.get(config.getUploadDir() + fileName));){
//            // Create Mapping Strategy to arrange the
//            // column name in order
//            final CustomCSVMappingStrategy<CSVTOBEAN> mappingStrategy = new CustomCSVMappingStrategy<>();
//
//            mappingStrategy.setType(CSVTOBEAN.class);
//
//            StatefulBeanToCsv<CSVTOBEAN> sbc = new StatefulBeanToCsvBuilder<CSVTOBEAN>(writer)
//                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                    .withMappingStrategy(mappingStrategy)
//                    .build();
//
//            sbc.write(failedRows);
//
//        } catch ( IOException e) {
//            logger.error("Error in CSV IO operations. Make sure data directory exists with write permissions.", e);
//            return Pair.of(false, "Error in creating file.");
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            logger.error("Error in CSV Write operation", e);
//            return Pair.of(false, "Error in CSV Write operation");
//        }
//
//        return Pair.of(true, fileName);
//
//    }
}
