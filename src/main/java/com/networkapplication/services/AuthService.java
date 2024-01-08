package com.networkapplication.services;

import com.networkapplication.dtos.Request.AdminRegisterDTO;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import jakarta.transaction.Transactional;

public interface AuthService {
    UserDTOResponse login(UserDTORequest user) throws ResponseException;

    UserDTOResponse register(UserDTORequest userRequest) throws ResponseException;

    @Transactional
    UserDTOResponse adminRegister(AdminRegisterDTO adminRequest) throws ResponseException;
}
