package com.networkapplication.services.Auth;
import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.dtos.Response.UserResponse;
import com.networkapplication.models.User;
import org.springframework.http.ResponseEntity;

public interface Auth {

     ResponseEntity<UserResponse> login(UserRequest user);

     ResponseEntity<UserResponse> register(UserRequest userRequest);

     ResponseEntity<Message> logout(String token);
}
