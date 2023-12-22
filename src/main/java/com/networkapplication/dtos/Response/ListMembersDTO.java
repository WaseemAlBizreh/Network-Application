package com.networkapplication.dtos.Response;

import com.networkapplication.dtos.MainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMembersDTO implements MainDTO {
    private List<MembersDTO> membersDTOS = new ArrayList();
}
