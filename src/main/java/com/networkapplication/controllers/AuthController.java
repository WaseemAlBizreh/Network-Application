package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService services;

    @PostMapping("/login")
    public ResponseEntity<UserDTOResponse> login(@RequestBody UserDTORequest user) {
        return ResponseEntity.ok(services.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTOResponse> register(@RequestBody UserDTORequest user) {
        return ResponseEntity.ok(services.register(user));
    }

//    @DeleteMapping("/logout")
//    public ResponseEntity<MessageDTO> logout(@RequestHeader String token) {
//        return ResponseEntity.ok(services.logout(token));
//    }

}
