package com.networkapplication.config;

import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, GroupRepository groupRepository) {
        return args -> {
            User bashar = new User(
                    "Bashar",
                    "Password"
            );
            User waseem = new User(
                    "Waseem",
                    "Password"
            );
            User refat = new User(
                    "Refat",
                    "Password"
            );
            Group group1 = new Group("group1", waseem);
            Group group2 = new Group("group2", bashar);

            List<Group> basharGroups = List.of(group1,group2);
            List<Group> waseemGroups = List.of(group1);
            List<Group> refatGroups = List.of(group2);
            bashar.setGroups(basharGroups);
            waseem.setGroups(waseemGroups);
            refat.setGroups(refatGroups);

            group1.setMembers(List.of());
        };
    }
}
