package com.example.phonecallapp.configuration;
//import com.example.phonecallapp.configuration.CountryCodes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AutoConfiguration {

    @Bean(name = "countriesCodes")
    @ConfigurationProperties(prefix = "config.country-codes")
    public List<CountryCodes> countriesCodes() {
        return new ArrayList<>();
    }

}
