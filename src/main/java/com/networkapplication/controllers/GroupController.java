package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.dtos.Response.Message;
import com.networkapplication.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/group")
@RequiredArgsConstructor
public class GroupController {
    private GroupService services;

    @PostMapping("/addGroup")
    public ResponseEntity addGroup(@RequestBody UserRequest user) {
        return services.addGroup();
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<Message> deleteGroup(@PathVariable Long id) {
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