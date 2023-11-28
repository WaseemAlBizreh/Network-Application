package com.saria.Project.controller;

import com.saria.Project.DTO.Response.Message;
import com.saria.Project.DTO.Request.UserRequest;
import com.saria.Project.DTO.Response.User;
import com.saria.Project.services.AuthServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthServices services;

    @Autowired
    public AuthController(AuthServices services) {
        this.services = services;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserRequest user) {

        return services.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRequest user) {
        return services.register(user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Message> logout(@RequestHeader String token) {
        return services.logout(token);
    }

}
