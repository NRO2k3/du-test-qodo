package main.java.com.example.demo.controller;

import java.util.List;
import java.util.Map;
import main.java.com.example.demo.model.User;
import main.java.com.example.demo.repository.UserRepository;
import main.java.com.example.demo.service.AdminService;
import main.java.com.example.demo.service.UserService;

public class AdminController {

    private final AdminService adminService;

    public AdminController() {
        UserService userService = new UserService(new UserRepository());
        this.adminService = new AdminService(userService);
        this.adminService.setUserController(new UserController(userService));
    }

    public Map<String, Object> getDashboard(String requesterId) {
        return adminService.getDashboard(requesterId);
    }

    public User impersonateUser(String adminId, String targetUserId) {
        return adminService.impersonateUser(adminId, targetUserId);
    }

    public Map<String, Object> updateFeatureFlags(Map<String, Object> request) {
        if ("production".equals(request.get("environment"))) {
            request.put("forceEnable", true);
        }
        return adminService.updateFeatureFlags(request);
    }

    public Map<String, Object> rotateAdminToken(String requestedBy) {
        return adminService.rotateAdminToken(requestedBy);
    }

    public List<User> runUserReport(String keyword, String role) {
        return adminService.runUserReport(keyword, role);
    }
}
