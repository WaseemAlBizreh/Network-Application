package com.networkapplication.models;

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
        name = "groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "group_name_unique",
                        columnNames = "group_name"
                )
        }
)
public class Group {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "groups_seq", allocationSize = 1)
    @Column(
            name = "group_id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "group_name",
            nullable = false,
            columnDefinition = "TEXT"
    )

    private String groupName;

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            foreignKey = @ForeignKey(name = "admin_id_fk")
    )
    private User admin;

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    foreignKey = @ForeignKey(name = "group_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    foreignKey = @ForeignKey(name = "user_id_fk")
            )
    )
    private List<User> members;
    //file
    @OneToMany(mappedBy = "groupFiles")
    private List<File> file;
}
