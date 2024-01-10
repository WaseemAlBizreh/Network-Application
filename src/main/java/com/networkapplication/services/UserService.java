package com.networkapplication.services;

import com.networkapplication.dtos.Request.LogDTOs;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UsersSearchDTO;
import com.networkapplication.exceptions.ResponseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username);

    UsersSearchDTO getAllUsers(Long group_id) throws ResponseException;

    UsersSearchDTO getUsers() throws ResponseException;

    LogDTOs getUserLogs(Long userId)throws ResponseException;
    UsersSearchDTO getUsersWithFaultCount() throws ResponseException;

    MessageDTO UnBanUser(Long userId) throws ResponseException;
}
