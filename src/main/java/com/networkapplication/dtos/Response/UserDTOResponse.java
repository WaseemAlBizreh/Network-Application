package com.networkapplication.dtos.Response;

import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private User user;
    private String token;
}
