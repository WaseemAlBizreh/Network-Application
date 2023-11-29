package com.networkapplication.services.Group;

import com.networkapplication.dtos.Response.Message;
import org.springframework.http.ResponseEntity;

public interface Group {

     ResponseEntity addGroup();

     ResponseEntity<Message> deleteGroup(int id);

     ResponseEntity addUser() ;

     ResponseEntity<Message> deleteUser(int id);

     ResponseEntity userJoinToGroup();
}
