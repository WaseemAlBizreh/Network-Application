package com.networkapplication.services;

import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.DeleteDTOUser;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.ListUserGroupsDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.dtos.Response.UserGroupsDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Group;

import java.lang.module.ResolutionException;
import java.util.*;

import javax.naming.AuthenticationException;

public interface GroupService {
    GroupDTOResponse addGroup(GroupDTORequest request) throws ResponseException;

    MessageDTO deleteGroup(Long id) throws AuthenticationException;

    MessageDTO addUser(AddUserToGroupRequest request) throws ResponseException;

    MessageDTO deleteUser(Long id) throws ResponseException;

    MessageDTO leaveGroup(Long group_id) throws ResponseException;

    ListUserGroupsDTOResponse getAllGroup() throws ResponseException;


}
