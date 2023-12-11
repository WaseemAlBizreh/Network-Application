package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersDTO implements MainDTO {
    private Long user_id;
    private String user_name;

    public MembersDTO(User user){
        this.user_id=user.getId();
        this.user_name=user.getUsername();
    }
}
