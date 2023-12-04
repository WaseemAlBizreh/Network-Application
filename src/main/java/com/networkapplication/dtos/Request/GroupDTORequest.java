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
public class GroupDTORequest implements MainDTO {
    private String groupName;
    private Long userId;
}

