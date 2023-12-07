package com.networkapplication.repositories;

import com.networkapplication.models.File;
import com.networkapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f from File f where f.fileName =?1 ")
    Optional<File> findFileByUsername(String fileName);

}
