package com.networkapplication.services;
import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.dtos.Response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     ResponseEntity<UserResponse> login(UserRequest user);
     ResponseEntity<UserResponse> register(UserRequest userRequest);
     ResponseEntity<Message> logout(String token);
}
