package com.networkapplication;

import com.networkapplication.models.File;
import com.networkapplication.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class NetworkApplication {


    public static void main(String[] args) {
        SpringApplication.run(NetworkApplication.class, args);

    }


}
