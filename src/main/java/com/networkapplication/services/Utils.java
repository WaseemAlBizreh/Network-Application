package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class Utils {
    @NonNull
    HttpServletRequest Request;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getCurrentUser() {
        String header = Request.getHeader("Authorization");
        String token = header.substring(7);

        return userRepository.findUserByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
    }
}
