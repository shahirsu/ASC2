package com.amz.jcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class JcService {
    public static void main(String[] args) {
        Postgres.setup();
        SpringApplication.run(JcService.class, args);
    }
}
