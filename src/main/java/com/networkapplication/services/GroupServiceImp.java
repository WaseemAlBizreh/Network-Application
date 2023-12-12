package com.networkapplication.services;

import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.DeleteDTOUser;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Response.*;
import com.networkapplication.dtos.UserDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final Utils search;

    @Override
    public GroupDTOResponse addGroup(GroupDTORequest request) throws ResponseException {
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
    public MessageDTO deleteGroup(Long id) throws ResponseException {
        User user = search.getCurrentUser();
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResponseException(404, "Group Not Found"));
        if (group.getAdmin().getId().equals(user.getId())) {
            groupRepository.delete(group);
            return MessageDTO.builder().message("deleted successfully").build();
        } else {
            throw new ResponseException(401, "you dont own this group");
        }

    }

    @Override
    public MessageDTO addUser(AddUserToGroupRequest request) throws ResponseException {

        User user = search.getCurrentUser();
        Group group = groupRepository.findById(request.getGroup_id())
                .orElseThrow(() -> new ResponseException(404, "No group Found"));
        if (!group.getAdmin().getId().equals(user.getId())) {
            throw new ResponseException(403,
                    "you are not the admin of this group");
        }
        User newUser = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new ResponseException(404, "No User Found"));
        if (group.getMembers().contains(newUser)) {
            throw new ResponseException(500,
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
    public MessageDTO deleteUser(DeleteDTOUser deleteDTOUser) throws ResponseException {
        //get admin
        User admin = search.getCurrentUser();
        //get group
        Group group = groupRepository.findById(deleteDTOUser.getGroupId()).orElseThrow(
                () -> new ResponseException(404, "Group Not Found")
        );
        User user = userRepository.findById(deleteDTOUser.getUserId()).orElseThrow(
                () -> new ResponseException(404, "No User Found"));
        if (admin.getId().equals(group.getAdmin().getId())) {
            if (admin.getId().equals(deleteDTOUser.getUserId()))
                throw new ResponseException(403, "can't deleted yourself");
            if (group.getMembers().contains(user)) {
                group.getMembers().remove(user);
                groupRepository.save(group);
                userRepository.save(user);
                return MessageDTO.builder().message("User Deleted Successfully").build();
            } else
                return MessageDTO.builder().message("User Not Found").build();
        } else throw new ResponseException(404, "UnAuthorized");

    }

    @Override
    public MessageDTO leaveGroup(Long group_id) throws ResponseException {
        //get user
        User user = search.getCurrentUser();
        //get group
        Group group = groupRepository.findById(group_id)
                .orElseThrow(() -> new ResponseException(404, "no group found"));
        if (group.getAdmin().getId().equals(user.getId()))
            throw new ResponseException(400,
                    "admin can't leave the group");
        if (!group.getMembers().contains(user))
            throw new ResponseException(403,
                    "you are not a member in this group");
        group.getMembers().remove(user);
        user.getGroups().remove(group);
        userRepository.save(user);
        groupRepository.save(group);
        return MessageDTO.builder().message("user left the group successfully").build();
    }

    @Override
    public ListUserGroupsDTOResponse getAllGroup() throws ResponseException {
        User user = search.getCurrentUser();
        List<UserGroupsDTOResponse> userDTOGroups = new ArrayList<>();
        ListUserGroupsDTOResponse listUserGroupsDTOResponse = new ListUserGroupsDTOResponse();
        if (user.getGroups() == null) {
            user.setGroups(List.of());
        }
        for (Group group :
                user.getGroups()) {
            userDTOGroups.add(new UserGroupsDTOResponse(group));
            listUserGroupsDTOResponse.setUserGroupsDTOResponses(userDTOGroups);
        }
        return listUserGroupsDTOResponse;
    }

    @Override
    public ListMembersDTO getMembers(Long id) throws ResponseException {
        User user = search.getCurrentUser();
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new ResponseException(404, "Group Not Found")
        );
        if (!user.getId().equals(group.getAdmin().getId()))
            throw new ResponseException(403, "unAuthorized");
        else {
            if(group.getMembers() == null)
                group.setMembers(List.of());
            List<MembersDTO> membersDTOS = new ArrayList<>();
            ListMembersDTO listMembersDTO = new ListMembersDTO();
            List<User> members = group.getMembers();
            for (User user1 :
                    members
            ) {
                membersDTOS.add(new MembersDTO(user1));
            }
            listMembersDTO.setMembersDTOS(membersDTOS);
            return listMembersDTO;
        }
    }



}


