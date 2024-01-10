package com.networkapplication.services;

import com.networkapplication.dtos.Request.LogDTOs;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UsersSearchDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Auditing;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final Utils utils;
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

    @Override
    public UsersSearchDTO getUsers() throws ResponseException {
        User admin = utils.getCurrentUser();
        if (admin.getRole().equals(Utils.role.User))
            throw new ResponseException(403, "you are not admin");
        List<User> users = userRepository.findAll();
        List<User> notAdmins = new ArrayList<>();
        for (User user : users
        ) {
            if (user.getRole().equals(Utils.role.User))
                notAdmins.add(user);
        }
        UsersSearchDTO response = new UsersSearchDTO();
        response.setUsers(notAdmins);

        return response;
    }

    @Override
    public LogDTOs getUserLogs(Long userId) throws ResponseException {
        User admin = utils.getCurrentUser();
        if (admin.getRole().equals(Utils.role.User))
            throw new ResponseException(403, "you are not admin");
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseException(404, "User Not Found"));
        List<Auditing> Logs;
        if (user.getLogs() != null)
            Logs = user.getLogs();
        else Logs = List.of();
        return new LogDTOs(Logs);

    }

    @Override
    public UsersSearchDTO getUsersWithFaultCount() throws ResponseException {
        User admin = utils.getCurrentUser();
        if (admin.getRole().equals(Utils.role.User))
            throw new ResponseException(403, "you are not admin");
        UsersSearchDTO usersSearchDTO = new UsersSearchDTO();
        usersSearchDTO.setUsers(userRepository.findByFaultCountGreaterThanEqual(3));
        return usersSearchDTO;
    }

    @Transactional(rollbackOn = ResponseException.class)
    @Override
    public MessageDTO UnBanUser(Long userId) throws ResponseException {
        User admin = utils.getCurrentUser();
        if (admin.getRole().equals(Utils.role.User))
            throw new ResponseException(403, "you are not admin");
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseException(404, "User Not Found"));
        if (user.getFaultCount()<3)
            throw new ResponseException(404,"user is not banned");
        user.setFaultCount(0);
        userRepository.save(user);
        return new MessageDTO("Account unbanned successfully");


    }

}


