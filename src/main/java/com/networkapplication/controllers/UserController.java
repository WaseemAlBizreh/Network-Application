package com.networkapplication.controllers;


import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final GroupService groupService;
    private final GlobalExceptionHandler exceptionHandler;
}
