package com.networkapplication.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ResponseException extends ResponseStatusException {
    public ResponseException(int statusCode, String message) {
        super(HttpStatusCode.valueOf(statusCode), message);
    }
}
