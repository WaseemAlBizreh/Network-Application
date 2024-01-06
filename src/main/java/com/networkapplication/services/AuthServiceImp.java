package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.User;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTOResponse login(UserDTORequest request) throws ResponseException {

        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseException(404, "User Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseException(422, "Wrong Password");
        }

        // Generate a JWT token
        String token = jwtService.generateToken(user);
        UserDTOResponse response = new UserDTOResponse(user);
        response.setToken(token);
        return response;
    }

    @Override
    public UserDTOResponse register(UserDTORequest userRequest) throws ResponseException {
        if (userRepository.findUserByUsername(userRequest.getUsername()).isPresent())
            throw new ResponseException(400,
                    "username is already taken");
        if (!userRequest.getConfirm_password().equals(userRequest.getPassword())) {
            throw new ResponseException(422,
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
