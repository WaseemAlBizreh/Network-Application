package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private FileService services;

    @PostMapping("/addFile")
    public ResponseEntity addFile(@RequestBody UserDTORequest user){
        return services.addFile();
    }

    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestBody UserDTORequest user) {
        return services.getFile();
    }


}
