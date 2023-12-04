package com.networkapplication.exceptions;

import com.networkapplication.dtos.MainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO implements MainDTO {
    private String error;
}
