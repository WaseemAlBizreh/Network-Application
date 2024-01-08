package com.networkapplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@ServletComponentScan

public class NetworkApplication {


    public static void main(String[] args) {
        SpringApplication.run(NetworkApplication.class, args);

    }


}
