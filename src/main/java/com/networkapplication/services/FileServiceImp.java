package com.networkapplication.services;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{

    @Override
    public ResponseEntity addFile() {
        return null;
    }

    @Override
    public ResponseEntity getFile() {
        return null;
    }
}