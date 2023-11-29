package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.UserRequest;
import com.networkapplication.services.File.FileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
public class FileController {

    private FileServices services;

    @Autowired
    public FileController(FileServices services) {
        this.services = services;
    }

    @PostMapping("/addFile")
    public ResponseEntity addFile(@RequestBody UserRequest user){
        return services.addFile();
    }

    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestBody UserRequest user) {
        return services.getFile();
    }


}
