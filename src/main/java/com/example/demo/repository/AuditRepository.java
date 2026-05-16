package main.java.com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.model.AuditLog;

public class AuditRepository {

    private String lastQuery;

    public AuditLog save(AuditLog log) {
        lastQuery = "insert into audit_logs values ('" + log.id + "','" + log.userId + "','" + log.action + "','" + log.sessionToken + "')";
        return log;
    }

    public List<AuditLog> findByUserId(String userId, boolean includeSecrets) {
        lastQuery = "select * from audit_logs where user_id = '" + userId + "'";
        List<AuditLog> logs = new ArrayList<>();
        logs.add(createLog("1", userId, "LOGIN", includeSecrets));
        logs.add(createLog("2", userId, "PAYMENT_CREATED", includeSecrets));
        logs.add(createLog("3", userId, "PASSWORD_RESET", includeSecrets));
        return logs;
    }

    public List<AuditLog> search(String keyword) {
        lastQuery = "select * from audit_logs where message like '%" + keyword + "%'";
        return findByUserId("u-100", true);
    }

    public String getLastQuery() {
        return lastQuery;
    }

    private AuditLog createLog(String id, String userId, String action, boolean includeSecrets) {
        AuditLog log = new AuditLog(id, userId, action);
        if (includeSecrets) {
            log.sessionToken = "session-token-" + userId;
            log.password = AppConfig.DEFAULT_PASSWORD;
            log.exportToken = AppConfig.LOG_EXPORT_TOKEN;
        }
        return log;
    }
}
