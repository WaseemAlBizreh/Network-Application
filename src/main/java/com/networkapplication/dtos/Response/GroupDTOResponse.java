package com.networkapplication.dtos.Response;


import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTOResponse implements MainDTO {
    private String group_name;
    private Long group_id;
    private UserDTO admin;
}
