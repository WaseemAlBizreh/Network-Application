package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersSearchDTO implements MainDTO {
    List<UserSearchDTO> userSearchDTOS = new ArrayList<>();

    public void setUsers(List<User> users) {
        for (User user :
                users) {
            userSearchDTOS.add(new UserSearchDTO(user.getId(), user.getUsername()));
        }
    }
}
