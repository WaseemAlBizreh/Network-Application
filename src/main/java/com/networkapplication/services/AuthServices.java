package com.saria.Project.services;


import com.saria.Project.DTO.Request.UserRequest;
import com.saria.Project.DTO.Response.Message;
import com.saria.Project.DTO.Response.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServices extends Services {

    @Autowired
    public AuthServices() {

    }

    @Override
    public ResponseEntity<User> login(UserRequest user) {
        User user1 = new User();
        user1.setId("1");
        user1.setUserName("saria");
        user1.setToken("token");
        System.out.println(user.getUserName());
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> register(UserRequest userRequest) {
        User userResponse = new User();
        userResponse.setToken("sariyaToken");
        userResponse.setId("1");
        userResponse.setUserName("SariyaAlzoubi");
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> logout(String token) {
        Message message = new Message();
        message.setMessage("logoutSuccess");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}