package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.dtos.Response.UserResponse;
import com.networkapplication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService services;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest user) {
        return services.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest user) {
        return services.register(user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Message> logout(@RequestHeader String token) {
        return services.logout(token);
    }

}
