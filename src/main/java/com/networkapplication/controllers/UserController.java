package com.networkapplication.controllers;


import com.networkapplication.NetworkApplication;
import com.networkapplication.services.GroupServiceImp;
import com.networkapplication.services.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api" )
@RequiredArgsConstructor
public class UserController {
    private final GroupServiceImp groupService;
    private final UserServiceImp userService;
    @PostMapping("/login")
    public NetworkApplication.UserResponse login(@RequestBody NetworkApplication.UserRequest request){
        return userService.login(request);

    }

    @PostMapping("/register")
    public NetworkApplication.UserResponse register(@RequestBody NetworkApplication.UserRequest request){
    return userService.register(request);

    }
    @PostMapping("/create_group")
    public NetworkApplication.GroupCreateResponse createGroup(@RequestBody NetworkApplication.GroupCreateRequest request){
    return groupService.createGroup(request);
    }


}
