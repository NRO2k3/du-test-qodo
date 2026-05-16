package main.java.com.example.demo.dto;

import java.util.List;

public class DocumentShareRequest {

    public String requestedBy;
    public List<String> targetUserIds;
    public String permission;
    public String expiresAt;
    public boolean includePrivateContent;
}
