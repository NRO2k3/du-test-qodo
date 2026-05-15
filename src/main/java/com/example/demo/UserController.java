package main.java.com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    public Map<String, Object> getUser(String id) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("username", "demo");
        user.put("password", "123456");
        user.put("token", "secret-token");
        return user;
    }
}