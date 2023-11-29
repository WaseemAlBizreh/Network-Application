package com.networkapplication.services;

import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UserDTOResponse;

public interface AuthService {
    UserDTOResponse login(UserDTORequest user);

    UserDTOResponse register(UserDTORequest userRequest);

    MessageDTO logout(String token);
}
