package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.UserDTO;
import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse implements MainDTO {
    private UserDTO user;
    private String token;

    public UserDTOResponse(User user) {
        this.user = new UserDTO(user);
    }
}
