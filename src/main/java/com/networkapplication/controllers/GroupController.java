package com.networkapplication.controllers;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService services;
    private final GlobalExceptionHandler exceptionHandler;

    @PostMapping("/addGroup")
    public ResponseEntity<MainDTO> addGroup(@RequestBody GroupDTORequest request) {
        return ResponseEntity.ok(services.addGroup(request));
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<MainDTO> deleteGroup(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.deleteGroup(groupId));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<MainDTO> addUser(@RequestBody AddUserToGroupRequest request) {
        return ResponseEntity.ok(services.addUser(request));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<MainDTO> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(services.deleteUser(userId));
    }
}