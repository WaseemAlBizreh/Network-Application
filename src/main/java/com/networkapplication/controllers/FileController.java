package com.saria.Project.controller;

import com.saria.Project.DTO.Response.User;
import com.saria.Project.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
public class FileController {

    private Services services;

    @Autowired
    public FileController(Services services) {
        this.services = services;
    }

    @PostMapping("/addFile")
    public ResponseEntity addFile(@RequestBody User user){
        return services.addFile();
    }

    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestBody User user) {
        return services.getFile();
    }


}
