package com.example.springboot_api.audit;

import com.example.springboot_api.service.AuditLogService;
import com.example.springboot_api.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogService auditLogService;
    private final HttpServletRequest request;

    @Around("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {

        String username = SecurityUtils.currentUsername();
        String method = request.getMethod();
        String endpoint = request.getRequestURI();
        String ip = request.getRemoteAddr();

        String action = joinPoint.getSignature().getName();
        String resource = joinPoint.getTarget().getClass().getSimpleName();

        try {
            Object result = joinPoint.proceed();

            auditLogService.log(
                    username,
                    action,
                    resource,
                    null,
                    method,
                    endpoint,
                    ip,
                    true
            );

            return result;

        } catch (Exception ex) {

            auditLogService.log(
                    username,
                    action,
                    resource,
                    null,
                    method,
                    endpoint,
                    ip,
                    false
            );

            throw ex;
        }
    }
}
