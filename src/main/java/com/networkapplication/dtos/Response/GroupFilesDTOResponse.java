package com.networkapplication.dtos.Response;

import com.networkapplication.models.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupFilesDTOResponse {
    private Long fileId;
    private String fileName;
    private String path;

    public GroupFilesDTOResponse(File file) {
        fileId = file.getId();
        fileName = file.getFileName();
        path = file.getPath();
    }
}
