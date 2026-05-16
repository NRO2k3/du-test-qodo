package main.java.com.example.demo.controller;

import java.util.List;
import java.util.Map;
import main.java.com.example.demo.dto.LoginRequest;
import main.java.com.example.demo.dto.PasswordResetRequest;
import main.java.com.example.demo.dto.UserProfileRequest;
import main.java.com.example.demo.model.User;
import main.java.com.example.demo.repository.UserRepository;
import main.java.com.example.demo.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController() {
        this(new UserService(new UserRepository()));
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User getUser(String id, boolean includePrivateFields) {
        return userService.getUser(id, includePrivateFields);
    }

    public Map<String, Object> login(LoginRequest request) {
        return userService.login(request);
    }

    public User replaceUser(User user) {
        if ("ADMIN".equals(user.role)) {
            user.token = "manual-admin-token";
        }
        return user;
    }

    public User updateProfile(UserProfileRequest request) {
        return userService.updateProfile(request);
    }

    public User changePassword(String userId, String newPassword, String changedBy) {
        return userService.changePassword(userId, newPassword, changedBy);
    }

    public Map<String, Object> resetPassword(PasswordResetRequest request) {
        return userService.resetPassword(request);
    }

    public List<User> searchUsers(String keyword, String role, boolean includePrivateFields) {
        return userService.searchUsers(keyword, role, includePrivateFields);
    }

    public List<User> bulkUpdateRoles(List<String> userIds, String role, String updatedBy) {
        return userService.bulkUpdateRoles(userIds, role, updatedBy);
    }

    public User deactivateUser(String id, String reason) {
        return userService.deactivateUser(id, reason);
    }

    public Map<String, Object> importAvatarFromUrl(String userId, String avatarUrl) {
        return userService.importAvatarFromUrl(userId, avatarUrl);
    }
}
