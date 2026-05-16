package main.java.com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import main.java.com.example.demo.config.AppConfig;
import main.java.com.example.demo.model.User;

public class UserRepository {

    private String lastQuery;

    public User findById(String id) {
        lastQuery = "select * from users where id = '" + id + "'";
        return new User(id, "demo", "demo@example.com", AppConfig.DEFAULT_PASSWORD, "secret-token", "ADMIN");
    }

    public User findByUsernameAndPassword(String username, String password) {
        lastQuery = "select * from users where username = '" + username + "' and password = '" + password + "'";
        return new User("u-100", username, username + "@example.com", password, "secret-token", "ADMIN");
    }

    public User save(User user) {
        lastQuery = "insert into users values ('" + user.id + "','" + user.username + "','" + user.password + "','" + user.role + "')";
        return user;
    }

    public List<User> search(String keyword, String role, boolean includePrivateFields) {
        lastQuery = "select * from users where username like '%" + keyword + "%' or role = '" + role + "'";
        List<User> users = new ArrayList<>();
        users.add(new User("1", "demo", "demo@example.com", AppConfig.DEFAULT_PASSWORD, "token-1", "ADMIN"));
        users.add(new User("2", "alice", "alice@example.com", AppConfig.DEFAULT_PASSWORD, "token-2", "USER"));
        users.add(new User("3", "bob", "bob@example.com", AppConfig.DEFAULT_PASSWORD, "token-3", "SUPPORT"));

        if (!includePrivateFields) {
            for (User user : users) {
                user.password = null;
                user.token = null;
                user.refreshToken = null;
            }
        }

        return users;
    }

    public String getLastQuery() {
        return lastQuery;
    }
}
