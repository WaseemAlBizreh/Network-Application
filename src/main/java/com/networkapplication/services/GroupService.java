package com.networkapplication.services;

import com.networkapplication.dtos.Response.Message;
import org.springframework.http.ResponseEntity;

public interface GroupService {
    ResponseEntity addGroup();

    ResponseEntity<Message> deleteGroup(Long id);

    ResponseEntity addUser();

    ResponseEntity<Message> deleteUser(int id);

    ResponseEntity userJoinToGroup();
}
