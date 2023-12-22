package com.networkapplication.services;

import com.networkapplication.dtos.Request.AddUserToGroupRequest;
import com.networkapplication.dtos.Request.DeleteDTOUser;
import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Response.*;
import com.networkapplication.exceptions.ResponseException;

public interface GroupService {
    GroupDTOResponse addGroup(GroupDTORequest request) throws ResponseException;

    MessageDTO deleteGroup(Long id) throws ResponseException;

    MessageDTO addUser(AddUserToGroupRequest request) throws ResponseException;

    MessageDTO deleteUser(DeleteDTOUser deleteDTOUser) throws ResponseException;

    MessageDTO leaveGroup(Long group_id) throws ResponseException;

    ListUserGroupsDTOResponse getAllGroup() throws ResponseException;

    ListMembersDTO getMembers(Long id) throws ResponseException;

    UsersSearchDTO getNotMembers(Long id) throws ResponseException;

}
