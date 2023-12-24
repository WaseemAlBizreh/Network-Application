package com.networkapplication.services;

import com.networkapplication.dtos.Response.UsersSearchDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
    }

    @Override
    public UsersSearchDTO getAllUsers(Long group_id) throws ResponseException {
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "Group Not Found")
        );
        List<User> users = userRepository.findAll();
        if (group.getMembers() == null) {
            group.setMembers(List.of());
        }
        for (int i = 0; i < group.getMembers().size(); i++) {
            if (users.contains(group.getMembers().get(i))) {
                users.remove(group.getMembers().get(i));
            }
        }
        UsersSearchDTO response = new UsersSearchDTO();
        response.setUsers(users);
        return response;
    }
}


