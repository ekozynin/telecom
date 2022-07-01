package com.telecom.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@SpringBootApplication
@EnableWebMvc
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class TelecomApp {
    public static void main(final String[] args) {
        SpringApplication.run(TelecomApp.class, args);
    }
}
