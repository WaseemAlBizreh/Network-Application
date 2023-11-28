package com.networkapplication.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}
