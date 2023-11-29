package com.networkapplication.services;

import com.networkapplication.NetworkApplication;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroupServiceImp {

    private final UserRepository userRepository ;
    private final GroupRepository groupRepository;
    @Autowired
    public GroupServiceImp(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }


    public NetworkApplication.GroupCreateResponse createGroup(NetworkApplication.GroupCreateRequest request) {
        User user=userRepository.findUserByUsername(request.username()).orElseThrow(()->new NoSuchElementException("No User"));
        Group group=new Group();
        group.setGroupName(request.groupName());
        List<User> members=new ArrayList<>();
        members.add(user);
        group.setMembers(members);
        group.setAdmin(user);
        List<Group>usergroups=new ArrayList<>();
        usergroups.add(group);
        user.setUserGroups(usergroups);
        groupRepository.save(group);
        return new NetworkApplication.GroupCreateResponse("user.toString()",200);

    }
}


