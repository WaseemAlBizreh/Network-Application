package com.networkapplication.services.Group;
import com.networkapplication.dtos.Response.Message;
import org.springframework.http.ResponseEntity;

public class GroupServices implements Group{
    @Override
    public ResponseEntity addGroup() {
        return null;
    }

    @Override
    public ResponseEntity<Message> deleteGroup(int id) {
        return null;
    }

    @Override
    public ResponseEntity addUser() {
        return null;
    }

    @Override
    public ResponseEntity<Message> deleteUser(int id) {
        return null;
    }

    @Override
    public ResponseEntity userJoinToGroup() {
        return null;
    }
}
