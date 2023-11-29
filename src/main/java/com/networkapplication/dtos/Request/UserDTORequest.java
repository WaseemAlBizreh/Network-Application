package com.networkapplication.dtos.Request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest {
    private String userName;
    private String password;
    private String confirmPassword;
}
