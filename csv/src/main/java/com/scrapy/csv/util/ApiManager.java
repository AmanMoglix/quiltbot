package com.scrapy.csv.util;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class ApiManager {
    private static final Logger logger = LoggerFactory.getLogger(ApiManager.class);
    @Autowired
    RestTemplate restTemplate;

    public String getApi(String apiUrl, Map<String, String> requestParam, Boolean isCompleteUrl, Integer timeOut) {
        if (!isCompleteUrl) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
            for (Map.Entry<String, String> entry : requestParam.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            apiUrl = builder.toUriString();
        }
        try {
            apiUrl=apiUrl.concat("&autoflip=false&wikify=false&fthresh=-1&inputLang=en&quoteIndex=-1");
            logger.info("Request for Entity Url : " + apiUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("sec-ch-ua-mobile","?0");
            headers.add("User-Agent","\tMozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36");
            headers.add("sec-ch-ua-platform","Linux");
            headers.add("sec-ch-ua",	" Not A;Brand ;v=99, Chromium;v=100, Google Chrome;v=100");
            headers.add("Accept","*");
            headers.add("if-none-match","W/\"2b9-jedW0x9kLq7Ov+x698R3a/Fvyw8\"");
            headers.add("Cookie","\tabID=25; anonID=b488f5cf1b773cd3; _gcl_au=1.1.150152844.1649397407; user_status=not registered; authenticated=false; premium=false; acceptedPremiumModesTnc=false; FPID=FPID2.2.ZaMEs0Np0cl8T3dutfQ%2BFrRRKBohkVZfr0OLLZksxBQ%3D.1649397406; _fbp=fb.1.1649397408146.1166455750; _ga_SM3NYRKGB1=GS1.1.1649658765.1.1.1649658893.0; _gid=GA1.2.1308952357.1650001812; connect.sid=s%3AQKnNU8lfQcsex8G12aEZ4CXjm-89F9Y3.VBwc%2FjBKtSf%2FLaSPLUl7lQ%2F5FKKtkpWSB3ve7FCP4BE; _clck=ee1cs2|1|f0n|0; qdid=00d0dd7e-1ca2-4bd6-b7e1-48bab319b26c; FPLC=EPLqxJ%2FyIqDvSZ3rD8yYLqziSbzAKV28e67pIkU884ucXLh7%2FyBxjpZEALUPq6zDlShFLTndJ7tDH5Tx5QuDfasu6KlzZbABG4QFP5Uvb1zlrgamCwYxVWnuU%2FuHDQ%3D%3D; __kla_id=eyIkcmVmZXJyZXIiOnsidHMiOjE2NDkzOTc0MDcsInZhbHVlIjoiIiwiZmlyc3RfcGFnZSI6Imh0dHBzOi8vcXVpbGxib3QuY29tLyJ9LCIkbGFzdF9yZWZlcnJlciI6eyJ0cyI6MTY1MDAxMTQ0NSwidmFsdWUiOiIiLCJmaXJzdF9wYWdlIjoiaHR0cHM6Ly9xdWlsbGJvdC5jb20vIn19; __insp_wid=379258038; __insp_slim=1650011445453; __insp_nv=true; __insp_targlpu=aHR0cHM6Ly9xdWlsbGJvdC5jb20v; __insp_targlpt=UGFyYXBocmFzaW5nIFRvb2wgfCBRdWlsbEJvdCBBSQ%3D%3D; _uetsid=ea984900bc7f11ecb5b0a5f4e6d4440a; _uetvid=acf2cfa0b70011ec981ca790a49f112d; __insp_norec_sess=true; _ga=GA1.1.1218747402.1649397406; _clsk=19h131y|1650011446747|3|0|l.clarity.ms/collect; _ga_D39F2PYGLM=GS1.1.1650011444.8.1.1650011696.0; amp_6e403e=huY6e12QV0ZAN3YM19eB3U...1g0m4hudm.1g0m62tsj.73.a.7d");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<String> entity = restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class);
            int httpStatusCode = entity.getStatusCodeValue();
            logger.info("Status Code :{}", new Gson().toJson(httpStatusCode));
            if (httpStatusCode == (HttpStatus.OK.value()))
                return getFirst(entity.getBody());
                //return entity.getBody();
        } catch (RestClientException | IllegalStateException e) {
            e.printStackTrace();
            logger.error("Error:", e);
        }
        return null;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(Integer timeOut) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeOut);
        return clientHttpRequestFactory;
    }

    private String getFirst(String response) {

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
            JSONArray parentCategory = (JSONArray) jsonObject.get("data");
            JSONObject jsonObject1=(JSONObject)parentCategory.get(0);

                JSONArray childrenArray = (JSONArray) jsonObject1.get("paras_3");

                    JSONObject secondLevelCategory = (JSONObject) childrenArray.get(0);
                    String alt=(String)secondLevelCategory.get("alt");
                    alt=alt.replaceAll("%20"," ");
                 logger.info(" parshed request "+ new Gson().toJson(alt));
                 return  alt;

        } catch (Exception e) {
            logger.error("Error in parsing category object.", e);

        }
        return null;
    }
}
