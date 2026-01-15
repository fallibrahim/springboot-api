package com.example.springboot_api.repository;

import com.example.springboot_api.Model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
