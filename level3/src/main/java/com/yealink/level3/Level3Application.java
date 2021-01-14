package com.yealink.level3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Level3Application {
    public static void main(String[] args) {
        SpringApplication.run(Level3Application.class, args);
    }

}
