package main.java.com.example.demo.controller;

import java.util.List;
import java.util.Map;
import main.java.com.example.demo.dto.AuditSearchRequest;
import main.java.com.example.demo.model.AuditLog;
import main.java.com.example.demo.repository.AuditRepository;
import main.java.com.example.demo.service.AuditService;

public class AuditController {

    private final AuditService auditService = new AuditService(new AuditRepository());

    public List<AuditLog> getAuditLogs(String userId, boolean includeSecrets) {
        return auditService.getAuditLogs(userId, includeSecrets);
    }

    public AuditLog recordEvent(AuditLog event) {
        return auditService.recordEvent(event);
    }

    public List<AuditLog> searchAuditLogs(AuditSearchRequest request) {
        return auditService.searchAuditLogs(request);
    }

    public Map<String, Object> exportAuditLogs(String requestedBy, String userId) {
        return auditService.exportAuditLogs(requestedBy, userId);
    }
}
