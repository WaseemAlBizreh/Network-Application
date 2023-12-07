package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.models.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupsDTOResponse implements MainDTO {
    private Long groupID;
    private String groupName;

    public UserGroupsDTOResponse(Group group) {
        groupID = group.getId();
        groupName = group.getGroupName();
    }
}
