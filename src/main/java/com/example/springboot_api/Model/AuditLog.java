package com.example.springboot_api.Model;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;     // email
    private String action;       // CREATE_PRODUCT, DELETE_PRODUCT, LOGIN...
    private String resource;     // Product, Auth, etc.
    private String resourceId;   // id si disponible

    private String method;       // HTTP method
    private String endpoint;     // /api/products/1

    private String ipAddress;
    private boolean success;

    private LocalDateTime timestamp;
}
