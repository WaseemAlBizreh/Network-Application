package com.networkapplication.dtos.Request;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.models.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO implements MainDTO {
    Long logId;
    Long userId;
    String operation;
    Long affectedId;
    String result;

    public LogDTO(Auditing log) {
        logId = log.getId();
        userId = log.getUser().getId();
        operation = log.getOperation();
        affectedId = log.getAffectedID();
        result = log.getResult();
    }
}
