package com.networkapplication.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<User> {
    @CreatedBy
    User CreatedBy;
    @CreatedDate
    LocalDate CreatedDate;
    @LastModifiedBy
    User LastModifiedBy;
    @LastModifiedDate
    LocalDate LastModifiedDate;

}
