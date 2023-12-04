package com.networkapplication.services;

import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.UserDTO;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final Utils search;

    @Override
    public GroupDTOResponse addGroup(GroupDTORequest request) {
        User user = search.getCurrentUser();
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
                .admin(new UserDTO(user))
                .build();
    }

    @Override
    public MessageDTO deleteGroup(Long id) throws AuthenticationException {
        User user = search.getCurrentUser();
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No group Found"));
        if (group.getAdmin().getId().equals(user.getId())) {
            groupRepository.delete(group);
            return MessageDTO.builder().message("deleted successfully").build();
        } else {
            throw new AuthenticationException("you dont own this group");
        }

    }

    @Override
    public MessageDTO addUser(AddUserToGroupRequest request) {

        User user = search.getCurrentUser();
        Group group = groupRepository.findById(request.getGroup_id())
                .orElseThrow(() -> new NoSuchElementException("No group Found"));
        if (!group.getAdmin().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "you are not the admin of this group");
        }
        User newUser = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
        if (group.getMembers().contains(newUser)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500),
                    "this user is already a member in  this group");
        }
        if (newUser.getGroups() != null) {
            newUser.getGroups().add(group);
        } else newUser.setGroups(List.of(group));
        group.getMembers().add(newUser);
        groupRepository.save(group);
        userRepository.save(newUser);
        return MessageDTO.builder().message("user added successfully").build();
    }

    @Override
    public MessageDTO deleteUser(Long id) {
        return null;
    }

    @Override
    public MessageDTO leaveGroup(Long group_id) {
        //get user
        User user = search.getCurrentUser();
        //get group
        Group group = groupRepository.findById(group_id)
                .orElseThrow(() -> new NoSuchElementException("no group found"));
        if (group.getAdmin().getId() == user.getId())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),
                    "admin can't leave the group");
        if (!group.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "you are not a member in this group");
        group.getMembers().remove(user);
        user.getGroups().remove(group);
        userRepository.save(user);
        groupRepository.save(group);
        return MessageDTO.builder().message("user left the group successfully").build();

    }


}


