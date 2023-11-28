package com.networkapplication.services.Auth;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.dtos.Response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServices implements Auth {
    @Override
    public ResponseEntity<UserResponse> login(UserRequest user) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> register(UserRequest userRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Message> logout(String token) {
        return null;
    }
}