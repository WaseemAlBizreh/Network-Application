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
    private boolean status;

    public GroupFilesDTOResponse(File file,boolean status,Long group_id) {
        fileId = file.getId();
        fileName = file.getFileName().substring(0,file.getFileName().length()-group_id.toString().length());
        this.status=status;
    }
}
