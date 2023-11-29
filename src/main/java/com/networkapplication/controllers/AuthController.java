package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.dtos.Response.UserResponse;
import com.networkapplication.services.Auth.AuthServices;
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
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest user) {

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
