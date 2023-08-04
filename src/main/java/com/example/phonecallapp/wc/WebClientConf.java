package com.example.phonecallapp.wc;

import com.example.phonecallapp.configuration.CountryCodes;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

//@Configuration
//public class WebClientConf {
//    private URI baseUri=URI.create("http://localhost:8080");
////    @PostConstruct
////    void init(){
////        baseUri=URI.create("http://localhost:8080");
////    }
//
//    @Bean(name = "webClientFraudNumbers")
//    public WebClient webClient() {
//        return WebClient.create(String.valueOf(baseUri));
//    }
//
//}
