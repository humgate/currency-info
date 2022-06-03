package com.humga.currencyinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients
public class CurrencyInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyInfoApplication.class, args);
    }

}
