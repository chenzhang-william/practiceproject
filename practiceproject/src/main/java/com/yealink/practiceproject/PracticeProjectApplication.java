package com.yealink.practiceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PracticeProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeProjectApplication.class, args);
    }

}
