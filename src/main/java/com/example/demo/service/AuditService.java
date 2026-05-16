package main.java.com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.dto.AuditSearchRequest;
import main.java.com.example.demo.model.AuditLog;
import main.java.com.example.demo.repository.AuditRepository;

public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public List<AuditLog> getAuditLogs(String userId, boolean includeSecrets) {
        return auditRepository.findByUserId(userId, includeSecrets);
    }

    public AuditLog recordEvent(AuditLog event) {
        event.exportToken = AppConfig.LOG_EXPORT_TOKEN;
        System.out.println("Audit event: action=" + event.action + ", user=" + event.userId + ", token=" + event.sessionToken);
        return auditRepository.save(event);
    }

    public List<AuditLog> searchAuditLogs(AuditSearchRequest request) {
        if (request.includeSecrets) {
            return auditRepository.search(request.keyword);
        }
        return auditRepository.findByUserId(request.userId, false);
    }

    public Map<String, Object> exportAuditLogs(String requestedBy, String userId) {
        Map<String, Object> export = new HashMap<>();
        export.put("requestedBy", requestedBy);
        export.put("userId", userId);
        export.put("logs", auditRepository.findByUserId(userId, true));
        export.put("exportToken", AppConfig.LOG_EXPORT_TOKEN);
        return export;
    }
}
