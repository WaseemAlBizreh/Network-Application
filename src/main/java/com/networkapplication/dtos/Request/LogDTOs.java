package com.networkapplication.dtos.Request;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.models.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class LogDTOs implements MainDTO {
    List<LogDTO>logs;
    public LogDTOs(List <Auditing> auditings){
        logs=new ArrayList<>();
        for (Auditing log:auditings
             ) {
            logs.add(new LogDTO(log));
        }


    }
}
