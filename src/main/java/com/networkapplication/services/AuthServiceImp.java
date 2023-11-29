package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.UserDTORequest;
import jakarta.servlet.http.HttpServletRequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTOResponse login(UserDTORequest request) {
        Optional<User> user=userRepository.findUserByUsername(request.getUserName());
        if (!user.isPresent()){
            throw new IllegalStateException("username is not exist");
        }
        User user1=user.get();
        if (!passwordEncoder.matches(request.getPassword(), user1.getPassword())){

            throw new IllegalStateException("wrong password");
        }

        // Generate a JWT token
        String token = jwtService.generateToken(user1);

        return  UserDTOResponse.builder()
                .user(user1)
                .token(token)
                .build();
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


}
