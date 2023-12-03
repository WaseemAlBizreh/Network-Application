package com.networkapplication.dtos.Response;

import com.networkapplication.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private Long user_id;
    private String user_name;
    private List<String> user_groups;
    private List<String> groups;
    private List<String> files;
    private List<String> my_files;
    private String token;

    public UserDTOResponse(User user) {
        user_id=user.getId();
        user_name=user.getUsername();
        for (int i=0;i<user.getUserGroups().size();i++){
            user_groups=new ArrayList<>();
            user_groups.add(user.getUserGroups().get(i).getGroupName());
        }
        for (int i=0;i<user.getGroups().size();i++){
            groups=new ArrayList<>();
            groups.add(user.getGroups().get(i).getGroupName());
        }
        for (int i=0;i<user.getFiles().size();i++){
            files=new ArrayList<>();
            files.add(user.getFiles().get(i).getFileName());
        }
        for (int i=0;i<user.getMyFiles().size();i++){
            my_files=new ArrayList<>();
            my_files.add(user.getMyFiles().get(i).getFileName());
        }
    }
}
