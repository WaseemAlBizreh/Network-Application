package com.networkapplication.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "groups"
)
public class Group {

    @Id
    @SequenceGenerator(
            name = "group_id_sequence",
            sequenceName = "group_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "group_id_sequence",
            strategy = GenerationType.SEQUENCE

    )
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
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    foreignKey = @ForeignKey(name = "user_id_fk")

            ),
            inverseJoinColumns = @JoinColumn(
                    name = "group_id",
                    foreignKey = @ForeignKey(name = "group_id_fk")
            )
    )
    private List<User> members;

    public Long getId() {
        return id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> users) {
        this.members = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Group() {
    }

    public Group(String groupName, User admin) {
        this.groupName = groupName;
        this.admin = admin;
    }
}
