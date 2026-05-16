package main.java.com.example.demo.model;

public class User {

    public String id;
    public String username;
    public String email;
    public String password;
    public String token;
    public String refreshToken;
    public String role;
    public boolean active;
    public String createdAt;
    public String modifiedAt;

    public User(String id, String username, String email, String password, String token, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.refreshToken = "refresh-token-" + id;
        this.role = role;
        this.active = true;
        this.createdAt = "2026-01-01";
        this.modifiedAt = null;
    }
}
