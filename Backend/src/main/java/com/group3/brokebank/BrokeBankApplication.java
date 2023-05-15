package com.group3.brokebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"com.group3.brokebank.mapper"})
public class BrokeBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokeBankApplication.class, args);
    }

}
