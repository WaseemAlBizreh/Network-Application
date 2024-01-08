package com.networkapplication.controllers;


import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Response.UsersSearchDTO;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.GroupService;
import com.networkapplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final GlobalExceptionHandler exceptionHandler;

    @GetMapping("/getUsers/{group_id}")
    public ResponseEntity<MainDTO> getUsers(@PathVariable Long group_id) {
        try {
            return ResponseEntity.ok(userService.getAllUsers(group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<MainDTO> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getUsers());
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @GetMapping("/getUserLogs/{user_id}")
    public ResponseEntity<MainDTO> getUserLogs(@PathVariable Long user_id) {
        try {
            return ResponseEntity.ok(userService.getUserLogs(user_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }
}
