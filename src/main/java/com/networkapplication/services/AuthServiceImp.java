package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTOResponse login(UserDTORequest request) {
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseException(404, "User Not Found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            throw new IllegalStateException("wrong password");
        }

        // Generate a JWT token
        String token = jwtService.generateToken(user);
        UserDTOResponse response = new UserDTOResponse(user);
        response.setToken(token);
        return response;
    }

    @Override
    public UserDTOResponse register(UserDTORequest userRequest) {
        if (!userRequest.getConfirm_password().equals(userRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(422),
                    "Password and Confirm Password Don't Match");
        }
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        userRepository.save(user);
        UserDTOResponse response = new UserDTOResponse(user);
        response.setToken(jwtService.generateToken(user));
        return response;
    }
}
