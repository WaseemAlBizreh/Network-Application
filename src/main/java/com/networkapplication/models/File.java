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
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue
    @Column(
            name = "file_id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "file_name",
            columnDefinition = "TEXT"
    )
    private String fileName;

    @Column(
           name = "last_edit_date",
           columnDefinition = "DATE"
   )
    private LocalDate lastEditDate;

    @Lob
    private byte[] content;
}
