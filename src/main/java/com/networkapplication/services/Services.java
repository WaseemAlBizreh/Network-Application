package com.saria.Project.services;
import com.saria.Project.DTO.Response.Message;
import com.saria.Project.DTO.Request.UserRequest;
import com.saria.Project.DTO.Response.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Services {



    @Autowired
    public Services() {

    }

    public ResponseEntity<User> login(UserRequest user){
       return null;
    }

    public ResponseEntity<User> register(UserRequest userRequest) {
        return null;
    }

    public ResponseEntity<Message> logout(String token){
        return null;
    }

    public ResponseEntity addGroup(){
        return null;
    }

    public ResponseEntity<Message> deleteGroup(int id) {
        return null;
    }

    public ResponseEntity addUser() {
        return null;
    }

    public ResponseEntity<Message> deleteUser(int id){
        return null;
    }

    public ResponseEntity userJoinToGroup(){
        return  null;
    }

    public ResponseEntity addFile() {
        return null;
    }

    public ResponseEntity getFile() {
        return null;
    }
}
