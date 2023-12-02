package com.networkapplication.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTOResponse {
    private String fileName;
    private String path;
    private Long group_id;
    private Long owner_id;
    private Long checkin_id;
}
