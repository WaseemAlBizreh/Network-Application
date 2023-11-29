package com.networkapplication.services;

import com.networkapplication.dtos.Request.GroupDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.GroupDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;

public interface GroupService {
    GroupDTOResponse addGroup(GroupDTORequest request);

    MessageDTO deleteGroup(Long id);

    MessageDTO addUser(UserDTORequest request);

    MessageDTO deleteUser(Long id);

    MessageDTO userJoinToGroup();
}
