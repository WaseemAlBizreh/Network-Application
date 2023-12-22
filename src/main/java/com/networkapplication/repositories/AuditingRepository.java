package com.networkapplication.repositories;

import com.networkapplication.models.Auditing;
import com.networkapplication.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditingRepository extends JpaRepository<Auditing, Long> {
}
