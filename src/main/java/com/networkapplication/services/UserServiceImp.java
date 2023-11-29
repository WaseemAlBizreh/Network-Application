package com.networkapplication.services;

import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.UserDTOResponse;
import com.networkapplication.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.networkapplication.NetworkApplication;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository ;
    private final GroupRepository groupRepository;


    public NetworkApplication.UserResponse register(@RequestBody NetworkApplication.UserRequest request){
        Optional<User> user=userRepository.findUserByUsername(request.username());
        if (user.isPresent()){
            throw new IllegalStateException("username is taken");
        }
        User user1=new User();
        user1.setUsername(request.username());
        user1.setPassword(request.password());
        userRepository.save(user1);

    return  new NetworkApplication.UserResponse("register complete",200);
}


    public UserDTOResponse login(UserDTORequest request) {
        Optional<User> user = userRepository.findUserByUsername(request.getUserName());
        if (!user.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        User user1=user.get();
        if (!user1.getPassword().equals(request.getPassword())){
            throw new IllegalStateException("wrong password");
        }
        return UserDTOResponse.builder().user(user1).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}


