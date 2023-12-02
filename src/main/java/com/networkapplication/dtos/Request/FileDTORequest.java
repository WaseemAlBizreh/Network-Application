package com.networkapplication.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTORequest {
    String fileName;
    Long group_id;
    byte[]bytes=new byte[1024];
}
