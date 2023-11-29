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
    @SequenceGenerator(name = "files_seq", allocationSize = 1)
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

    @ManyToOne
    @JoinColumn(
            name = "group_file_id",
            foreignKey = @ForeignKey(name = "group_file_id_fk")
    )
    private Group groupFiles;

    @ManyToOne
    @JoinColumn(
            name = "owner_file_id",
            foreignKey = @ForeignKey(name = "owner_file_id")
    )
    private User ownerFile;

    @ManyToOne
    @JoinColumn(
            name = "checkin_id",
            foreignKey = @ForeignKey(name = "checkin_id")
    )
    private User checkin;

}
