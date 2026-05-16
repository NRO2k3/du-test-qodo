package main.java.com.example.demo.model;

public class AuditLog {

    public String id;
    public String userId;
    public String action;
    public String ipAddress;
    public String userAgent;
    public String sessionToken;
    public String password;
    public String exportToken;
    public long createdAt;

    public AuditLog(String id, String userId, String action) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.ipAddress = "10.0.0.5";
        this.userAgent = "Mozilla/5.0";
        this.createdAt = System.currentTimeMillis();
    }
}
