package com.networkapplication.services;

import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ResponseException;

public interface AuthService {
    UserDTOResponse login(UserDTORequest user) throws ResponseException;

    UserDTOResponse register(UserDTORequest userRequest) throws ResponseException;

}
