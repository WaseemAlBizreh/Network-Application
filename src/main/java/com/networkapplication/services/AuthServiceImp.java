package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.AdminRegisterDTO;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            throw new ResponseException(422,
                    "username or password is Empty");
        }
        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseException(404, "User Not Found"));
        if (user.getFaultCount() >= 3)
            throw new ResponseException(403, "your account is blocked");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setFaultCount(user.getFaultCount() + 1);
            userRepository.save(user);
            throw new ResponseException(422, "Wrong Password");
        }
        user.setFaultCount(0);
        userRepository.save(user);
        // Generate a JWT token
        String token = jwtService.generateToken(user);
        UserDTOResponse response = new UserDTOResponse(user);
        response.setToken(token);
        return response;
    }

    @Transactional(rollbackOn = ResponseException.class)
    @Override
    public UserDTOResponse register(UserDTORequest userRequest) throws ResponseException {
        if (userRequest.getUsername().isEmpty() || userRequest.getPassword().isEmpty()) {
            throw new ResponseException(422,
                    "username or password is Empty");
        }
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
                .faultCount(0)
                .role(Utils.role.User)
                .build();
        userRepository.save(user);
        UserDTOResponse response = new UserDTOResponse(user);

        response.setToken(jwtService.generateToken(user));
        return response;
    }

    @Transactional(rollbackOn = ResponseException.class)
    @Override
    public UserDTOResponse adminRegister(AdminRegisterDTO adminRequest) throws ResponseException {
        if (adminRequest.getUsername().isEmpty() || adminRequest.getPassword().isEmpty()) {
            throw new ResponseException(422,
                    "username or password is Empty");
        }
        if (userRepository.findUserByUsername(adminRequest.getUsername()).isPresent())
            throw new ResponseException(400,
                    "username is already taken");
        if (!adminRequest.getConfirm_password().equals(adminRequest.getPassword())) {
            throw new ResponseException(422,
                    "Password and Confirm Password Don't Match");
        }
        if (!adminRequest.getVerificationCode().equals("SHADOWEN")) {
            throw new ResponseException(422,
                    "verification code is incorrect");

        }
        User user = User.builder()
                .username(adminRequest.getUsername())
                .password(passwordEncoder.encode(adminRequest.getPassword()))
                .faultCount(0)
                .role(Utils.role.Admin)
                .build();
        userRepository.save(user);
        UserDTOResponse response = new UserDTOResponse(user);

        response.setToken(jwtService.generateToken(user));
        return response;
    }
}
