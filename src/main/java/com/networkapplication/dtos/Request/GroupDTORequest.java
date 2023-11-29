package com.networkapplication.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTORequest {
    private String groupName;
    private Long userId;
}

