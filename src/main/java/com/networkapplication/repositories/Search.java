package com.networkapplication.repositories;

import com.networkapplication.config.JwtService;
import com.networkapplication.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class Search {
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
