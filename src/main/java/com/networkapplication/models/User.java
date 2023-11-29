package com.networkapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
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

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private List<Group> userGroups;

    @JsonIgnore
    @ManyToMany(
            mappedBy = "members"
    )

    private List<Group> groups;
    @JsonIgnore
    @OneToMany(mappedBy = "ownerFile")
    private List<File> files;


    @OneToMany(mappedBy = "checkin")
    private List<File> myFiles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
