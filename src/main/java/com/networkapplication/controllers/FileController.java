package com.networkapplication.controllers;

import com.networkapplication.FileStorage.FileStorageManager;
import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final GlobalExceptionHandler exceptionHandler;
    private final FileService services;
    @Autowired
    private FileStorageManager fileStorageManager;


    @DeleteMapping("/deleteAllFiles/{groupId}")
    public ResponseEntity<MainDTO> deleteAllFiles(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.deleteAllInGroup(groupId));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @GetMapping("getAllFiles/{group_id}")
    public ResponseEntity<MainDTO> getAllFiles(@PathVariable Long group_id) {
        try {
            return ResponseEntity.ok(services.loadAllGroupFiles(group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @PostMapping("/getFile")
    public ResponseEntity<MainDTO> getFile(@RequestBody FileDTORequest fileDTORequest) throws ResponseException {
        return ResponseEntity.ok(services.getFile(fileDTORequest));
    }

    @PostMapping("/addFile/{group_id}")
    public ResponseEntity<MainDTO> createFile(@RequestParam MultipartFile file, @PathVariable Long group_id) throws ResponseException, IOException {
        try {
            return ResponseEntity.ok(services.createFile(file, group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    @PostMapping("/checkIn")
    public ResponseEntity<MainDTO> checkIn(@RequestBody CheckInDTO check) throws ResponseException {
        return ResponseEntity.ok(services.checkIn(check));
    }

    @PostMapping("/checkOut")
    public ResponseEntity<MainDTO> checkOut(@RequestBody CheckInDTO checkOut) throws ResponseException {
        return ResponseEntity.ok(services.checkOut(checkOut));
    }

    @PostMapping("/updateFile/{group_id}")
    public ResponseEntity<MainDTO> updateFile(@RequestParam MultipartFile file, @PathVariable Long group_id) throws ResponseException, IOException {
        try {
            return ResponseEntity.ok(services.updateFile(file, group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }
}
