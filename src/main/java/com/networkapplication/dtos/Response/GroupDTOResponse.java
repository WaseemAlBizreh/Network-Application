package com.networkapplication.dtos.Response;


import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTOResponse {
    private String group_name;
    private Long group_id;
    private UserDTOResponse admin;
}
