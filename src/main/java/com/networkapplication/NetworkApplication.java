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

    public record FileRecord(List<File> files) {
    }

    public record FileRequest() {
    }

    public record UserResponse(String message, int status_code) {
    }

    public record UserResponse1(String message, int status_code, User user) {
    }

    public record UserRequest(String username, String password) {
    }

    public record FileResponse(String message, int status_code, File file) {
    }

}
