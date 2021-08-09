package com.hz.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @program: OAuth-2.0
 * @author: zgr
 * @create: 2021-06-30 15:27
 **/
@SpringBootApplication
@ServletComponentScan
public class ResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }
}
