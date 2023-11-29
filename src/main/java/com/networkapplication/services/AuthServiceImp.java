package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTOResponse login(UserDTORequest user) {
        return null;
    }

    @Override
    public UserDTOResponse register(UserDTORequest userRequest) {
        if (!userRequest.getConfirmPassword().equals(userRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(422),
                    "Password and Confirm Password Don't Match");
        }
        User user = User.builder()
                .username(userRequest.getUserName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        userRepository.save(user);
        return UserDTOResponse.builder()
                .user(user)
                .token(jwtService.generateToken(user))
                .build();
    }

    @Override
    public MessageDTO logout(String token) {
        return null;
    }
}
