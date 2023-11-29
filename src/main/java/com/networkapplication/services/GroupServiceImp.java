package com.networkapplication.services;

import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public GroupDTOResponse addGroup(GroupDTORequest request) {
        User user = userRepository.findById(request.getUserId())
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
    public MessageDTO deleteGroup(Long id) {
        return null;
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


