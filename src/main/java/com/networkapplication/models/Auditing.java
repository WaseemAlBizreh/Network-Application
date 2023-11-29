package com.networkapplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Auditing {

    @Id
    private Long id;

    private LocalDate date;
    private String operation;


    @ManyToOne
    @JoinColumn(
            name = "userID",
            foreignKey = @ForeignKey(name = "log_userID")
    )
    User user;

    @ManyToOne
    @JoinColumn(
            name = "fileID",
            foreignKey = @ForeignKey(name = "log_fileID")
    )
    File file;


}
