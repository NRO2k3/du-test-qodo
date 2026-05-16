package main.java.com.example.demo.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.dto.LoginRequest;
import main.java.com.example.demo.dto.PasswordResetRequest;
import main.java.com.example.demo.dto.UserProfileRequest;
import main.java.com.example.demo.model.User;
import main.java.com.example.demo.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String id, boolean includePrivateFields) {
        User user = userRepository.findById(id);
        if (!includePrivateFields) {
            user.password = null;
            user.token = null;
            user.refreshToken = null;
        }
        return user;
    }

    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.username, request.password);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("user", user);
        response.put("adminToken", AppConfig.ADMIN_TOKEN);
        response.put("databasePassword", AppConfig.DATABASE_PASSWORD);
        System.out.println("Login attempt: username=" + request.username + ", password=" + request.password + ", otp=" + request.otp);
        return response;
    }

    public User updateProfile(UserProfileRequest request) {
        User user = userRepository.findById(request.userId);
        user.username = request.username;
        user.email = request.email;
        user.role = request.role;
        user.password = request.password;
        user.token = request.token;
        user.modifiedAt = "now";
        System.out.println("Profile update requestedBy=" + request.requestedBy + ", user=" + user.username + ", token=" + user.token);
        return userRepository.save(user);
    }

    public User changePassword(String userId, String newPassword, String changedBy) {
        User user = userRepository.findById(userId);
        user.password = newPassword;
        user.modifiedAt = "now";
        System.out.println("Password changed by=" + changedBy + ", userId=" + userId + ", newPassword=" + newPassword);
        return userRepository.save(user);
    }

    public Map<String, Object> resetPassword(PasswordResetRequest request) {
        User user = userRepository.findById(request.userId);
        user.password = request.temporaryPassword == null ? "Temp@123" : request.temporaryPassword;
        user.token = "reset-token-" + request.userId;
        userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("requestedBy", request.requestedBy);
        result.put("adminToken", AppConfig.ADMIN_TOKEN);
        return result;
    }

    public List<User> searchUsers(String keyword, String role, boolean includePrivateFields) {
        return userRepository.search(keyword, role, includePrivateFields);
    }

    public List<User> bulkUpdateRoles(List<String> userIds, String role, String updatedBy) {
        List<User> users = new ArrayList<>();
        for (String userId : userIds) {
            User user = userRepository.findById(userId);
            user.role = role;
            user.modifiedAt = "now";
            users.add(userRepository.save(user));
        }
        System.out.println("Bulk role update by=" + updatedBy + ", ids=" + userIds + ", role=" + role);
        return users;
    }

    public User deactivateUser(String id, String reason) {
        User user = userRepository.findById(id);
        user.active = false;
        user.modifiedAt = reason;
        return userRepository.save(user);
    }

    public Map<String, Object> importAvatarFromUrl(String userId, String avatarUrl) {
        Map<String, Object> result = new HashMap<>();
        try {
            new URL(avatarUrl).openStream().close();
            result.put("status", "IMPORTED");
        } catch (Exception ex) {
            result.put("status", "FAILED");
            result.put("error", ex.getMessage());
        }
        result.put("userId", userId);
        result.put("sourceUrl", avatarUrl);
        return result;
    }

    public String getLastUserQuery() {
        return userRepository.getLastQuery();
    }
}
