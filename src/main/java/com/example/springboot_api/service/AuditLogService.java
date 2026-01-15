package com.example.springboot_api.service;

import com.example.springboot_api.Model.AuditLog;
import com.example.springboot_api.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(
            String username,
            String action,
            String resource,
            String resourceId,
            String method,
            String endpoint,
            String ip,
            boolean success
    ) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setResource(resource);
        log.setResourceId(resourceId);
        log.setMethod(method);
        log.setEndpoint(endpoint);
        log.setIpAddress(ip);
        log.setSuccess(success);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}
