package com.networkapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "user_app",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "username_unique",
                        columnNames = "username"
                )
        }
)
public class User {

    @Id
    @GeneratedValue
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )

    private String username;
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @OneToMany(mappedBy = "admin")
    private List<Group> userGroups;

    @ManyToMany(
            mappedBy = "members"
    )

    private List<Group> groups;

    @OneToMany(mappedBy = "ownerFile")
    private List<File> files;


    @OneToMany(mappedBy = "checkin")
    private List<File> myFiles;



}
