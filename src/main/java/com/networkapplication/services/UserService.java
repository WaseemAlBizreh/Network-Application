package com.networkapplication.services;

import com.networkapplication.dtos.Response.UserSearchDTO;
import com.networkapplication.dtos.Response.UsersSearchDTO;
import com.networkapplication.exceptions.ResponseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username);

    UsersSearchDTO getAllUsers (Long group_id) throws ResponseException;

}
