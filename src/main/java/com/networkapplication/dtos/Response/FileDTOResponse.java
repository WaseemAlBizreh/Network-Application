package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.Resolution;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTOResponse implements MainDTO {
    private Long file_id;
    private String file_name;
    private Resource path;
    private String message;
}