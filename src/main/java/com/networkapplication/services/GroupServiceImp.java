package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @NonNull HttpServletRequest Request;
    @Override
    public GroupDTOResponse addGroup(GroupDTORequest request) {
        String header= Request.getHeader("Authorization");
        String token=header.substring(7);

        User user = userRepository.findUserByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        List<User> members = new ArrayList<>();
        members.add(user);
        group.setMembers(members);
        group.setAdmin(user);
        List<Group> userGroups = user.getUserGroups();
        userGroups.add(group);
        user.setUserGroups(userGroups);
        groupRepository.save(group);
        userRepository.save(user);
        return GroupDTOResponse.builder()
                .group_id(group.getId())
                .group_name(group.getGroupName())
                .admin(user)
                .build();
    }

    @Override
    public MessageDTO deleteGroup(Long id) throws AuthenticationException {
        String header= Request.getHeader("Authorization");
        String token=header.substring(7);
        User user = userRepository.findUserByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
        Group group=groupRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No group Found"));
       if (group.getAdmin().getId().equals(user.getId())){
           groupRepository.delete(group);
           return MessageDTO.builder().message("deleted successfully").build();
       }else {
           throw new AuthenticationException("you dont own this group");
       }

    }

    @Override
    public MessageDTO addUser(UserDTORequest request) {
        return null;
    }

    @Override
    public MessageDTO deleteUser(Long id) {
        return null;
    }

    @Override
    public MessageDTO userJoinToGroup() {
        return null;
    }
}


