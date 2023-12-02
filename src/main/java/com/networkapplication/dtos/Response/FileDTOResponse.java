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
    private Long file_id;
    private String file_name;
    private String path;
    private String message;
}