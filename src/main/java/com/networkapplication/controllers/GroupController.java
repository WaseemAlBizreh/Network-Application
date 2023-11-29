package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
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
    public ResponseEntity<GroupDTOResponse> addGroup(@RequestBody GroupDTORequest request) {
        return ResponseEntity.ok(services.addGroup(request));
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<MessageDTO> deleteGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(services.deleteGroup(groupId));
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody UserDTORequest user) {
        return ResponseEntity.ok(services.addUser(user));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<MessageDTO> deleteUser (@PathVariable Long userId) {
        return ResponseEntity.ok(services.deleteUser(userId));
    }
    @PostMapping("/userJoinToGroup")
    public ResponseEntity userJoinToGroup(@RequestBody UserDTORequest user) {
        return ResponseEntity.ok(services.userJoinToGroup());
    }
}