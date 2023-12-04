package com.networkapplication.dtos.Request;

import com.networkapplication.dtos.MainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserToGroupRequest implements MainDTO {
    Long group_id;
    Long user_id;
}
