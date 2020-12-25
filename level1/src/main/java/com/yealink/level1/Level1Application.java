package com.yealink.level1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Level1Application {

    public static void main(String[] args) {
        SpringApplication.run(Level1Application.class, args);
    }

}
