package com.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@SpringBootApplication
public class Startup {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Startup.class, args);
    }
}
