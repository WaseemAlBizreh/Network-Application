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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auditing_seq"
    )
    @SequenceGenerator(name = "auditing_seq",
            sequenceName = "auditing_seq",
            allocationSize = 1)
    private Long id;

    private LocalDate date;

    private String operation;


    @ManyToOne
    @JoinColumn(
            name = "userID",
            foreignKey = @ForeignKey(name = "log_userID")
    )
    private User user;

    private Long affectedID;

    private String result;

}
