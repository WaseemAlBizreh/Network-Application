package com.networkapplication.services;

import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.DeleteDTOUser;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UserGroupsDTOResponse;
import com.networkapplication.models.Group;

import java.util.*;

import javax.naming.AuthenticationException;

public interface GroupService {
    GroupDTOResponse addGroup(GroupDTORequest request);

    MessageDTO deleteGroup(Long id) throws AuthenticationException;

    MessageDTO addUser(AddUserToGroupRequest request);

    MessageDTO deleteUser(DeleteDTOUser request);

    MessageDTO leaveGroup(Long group_id);

    List<UserGroupsDTOResponse> getAllGroup();


}
