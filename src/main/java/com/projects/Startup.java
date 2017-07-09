package com.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@EnableWebMvc
@Configuration
@EnableConfigurationProperties
@SpringBootApplication
public class Startup {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Startup.class, args);
    }
}
