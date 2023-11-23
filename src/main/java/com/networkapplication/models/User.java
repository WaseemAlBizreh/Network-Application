package com.networkapplication.models;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table
public class User {

    @Id
    @SequenceGenerator(
            name = "userid_sequence",
            sequenceName = "userid_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "userid_sequence",
            strategy = GenerationType.SEQUENCE

    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "username",
            unique = true,
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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
