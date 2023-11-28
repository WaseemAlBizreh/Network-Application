package com.saria.Project.controller;

import com.saria.Project.DTO.Response.Message;
import com.saria.Project.DTO.Response.User;
import com.saria.Project.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/group")
public class GroupController {
    private Services services;

    @Autowired
    public GroupController(Services services) {
        this.services = services;
    }

    @PostMapping("/addGroup")
    public ResponseEntity addGroup(@RequestBody User user) {
        return services.addGroup();
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<Message> deleteGroup(@PathVariable int id) {
        return services.deleteGroup(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody User user) {
        return services.addUser();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Message> deleteUser (@PathVariable int id) {
        return  services.deleteUser(id);
    }
    @PostMapping("/userJoinToGroup")
    public ResponseEntity userJoinToGroup(@RequestBody User user) {
        return services.userJoinToGroup();
    }



}