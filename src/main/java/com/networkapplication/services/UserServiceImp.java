package com.networkapplication.services;

import com.networkapplication.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.networkapplication.NetworkApplication;
import com.networkapplication.models.User;
import com.networkapplication.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

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


    public NetworkApplication.UserResponse login(NetworkApplication.UserRequest request) {
        Optional<User> user=userRepository.findUserByUsername(request.username());
        if (!user.isPresent()){
            throw new IllegalStateException("username is not exist");
        }
        User user1=user.get();
        if (!user1.getPassword().equals(request.password())){
            throw new IllegalStateException("wrong password");
        }


        return new NetworkApplication.UserResponse("logged in",200);
    }}


