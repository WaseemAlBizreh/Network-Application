package com.networkapplication.controllers;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ErrorDTO;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService services;
    private final GlobalExceptionHandler exceptionHandler;

    @PostMapping("/login")
    public ResponseEntity<MainDTO> login(@RequestBody UserDTORequest user) {
        try {
            return ResponseEntity.ok(services.login(user));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<MainDTO> register(@RequestBody UserDTORequest user) {
        return ResponseEntity.ok(services.register(user));
    }

//    @DeleteMapping("/logout")
//    public ResponseEntity<MessageDTO> logout(@RequestHeader String token) {
//        return ResponseEntity.ok(services.logout(token));
//    }

}
