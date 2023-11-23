package com.networkapplication.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "files")
public class File {

    @Id
    @SequenceGenerator(
            name = "file_id_sequence",
            sequenceName = "file_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "file_id_sequence",
            strategy = GenerationType.SEQUENCE

    )
    @Column(
            name = "id",
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

    public File(String fileName, LocalDate lastEditDate, byte[] content) {
        this.fileName = fileName;
        this.lastEditDate = lastEditDate;
        this.content = content;
    }

    public File() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDate getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDate lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
