package com.example.saleappv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "thjava.thbuoi2.repository")
@EntityScan(basePackages = "thjava.thbuoi2.models")
public class Saleappv1Application {

    public static void main(String[] args) {
        SpringApplication.run(Saleappv1Application.class, args);
    }
}