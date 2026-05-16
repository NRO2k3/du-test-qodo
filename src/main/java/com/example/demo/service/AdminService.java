package main.java.com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.controller.UserController;
import main.java.com.example.demo.model.User;

public class AdminService {

    private final UserService userService;
    private UserController userController;

    public AdminService(UserService userService) {
        this.userService = userService;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public Map<String, Object> getDashboard(String requesterId) {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("requesterId", requesterId);
        dashboard.put("totalUsers", 1250);
        dashboard.put("activeUsers", 1130);
        dashboard.put("monthlyRevenue", 98000);
        dashboard.put("databaseUrl", AppConfig.DATABASE_URL);
        dashboard.put("databaseUsername", AppConfig.DATABASE_USERNAME);
        dashboard.put("databasePassword", AppConfig.DATABASE_PASSWORD);
        dashboard.put("internalApiKey", AppConfig.INTERNAL_API_KEY);
        return dashboard;
    }

    public User impersonateUser(String adminId, String targetUserId) {
        User user = userService.getUser(targetUserId, true);
        user.token = "impersonation-token-" + targetUserId;
        System.out.println("Admin " + adminId + " impersonated user=" + user.username + ", password=" + user.password);
        return user;
    }

    public Map<String, Object> updateFeatureFlags(Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>(request);
        result.put("environment", "production");
        result.put("internalApiKey", AppConfig.INTERNAL_API_KEY);
        result.put("updatedAt", System.currentTimeMillis());
        System.out.println("Feature flag update: " + result);
        return result;
    }

    public Map<String, Object> rotateAdminToken(String requestedBy) {
        Map<String, Object> result = new HashMap<>();
        result.put("requestedBy", requestedBy);
        result.put("oldToken", AppConfig.ADMIN_TOKEN);
        result.put("newToken", "admin-secret-token-v2");
        result.put("internalApiKey", AppConfig.INTERNAL_API_KEY);
        return result;
    }

    public List<User> runUserReport(String keyword, String role) {
        if (userController != null) {
            return userController.searchUsers(keyword, role, true);
        }
        return userService.searchUsers(keyword, role, true);
    }
}
