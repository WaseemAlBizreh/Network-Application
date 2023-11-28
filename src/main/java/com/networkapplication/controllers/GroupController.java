package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.services.Group.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/group")
public class GroupController {
    private GroupServices services;

    @Autowired
    public GroupController(GroupServices services) {
        this.services = services;
    }

    @PostMapping("/addGroup")
    public ResponseEntity addGroup(@RequestBody UserRequest user) {
        return services.addGroup();
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<Message> deleteGroup(@PathVariable int id) {
        return services.deleteGroup(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody UserRequest user) {
        return services.addUser();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Message> deleteUser (@PathVariable int id) {
        return  services.deleteUser(id);
    }
    @PostMapping("/userJoinToGroup")
    public ResponseEntity userJoinToGroup(@RequestBody UserRequest user) {
        return services.userJoinToGroup();
    }



}