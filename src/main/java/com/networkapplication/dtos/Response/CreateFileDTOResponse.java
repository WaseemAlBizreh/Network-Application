package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileDTOResponse implements MainDTO {
    String message;
    Long id;
}
