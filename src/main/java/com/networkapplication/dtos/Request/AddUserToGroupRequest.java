package com.networkapplication.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserToGroupRequest {
    Long group_id;
    Long user_id;
}
