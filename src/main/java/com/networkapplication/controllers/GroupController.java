package com.networkapplication.controllers;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.DeleteDTOUser;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService services;
    private final GlobalExceptionHandler exceptionHandler;

    @PostMapping("/addGroup")
    public ResponseEntity<MainDTO> addGroup(@RequestBody GroupDTORequest request) {
        try {
            return ResponseEntity.ok(services.addGroup(request));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    // TODO: 12/4/2023 يوجد معالجة للخطأ 
    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<MainDTO> deleteGroup(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.deleteGroup(groupId));
        } catch (ResponseException e) {
            return exceptionHandler.handleException(e);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<MainDTO> addUser(@RequestBody AddUserToGroupRequest request) {
        try {
            return ResponseEntity.ok(services.addUser(request));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<MainDTO> deleteUser(@RequestBody DeleteDTOUser deleteDTOUser) {
        try {
            return ResponseEntity.ok(services.deleteUser(deleteDTOUser));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @DeleteMapping("/leaveGroup/{groupId}")
    public ResponseEntity<MainDTO> leaveGroup(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.leaveGroup(groupId));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @GetMapping("/getAllGroups")
    public ResponseEntity<MainDTO> getAllGroups() {
        try {
            return ResponseEntity.ok(services.getAllGroup());
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }
}