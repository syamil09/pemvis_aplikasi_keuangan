package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LoginResult;
import model.User;

/**
 * class untuk operasi yg berkaitan dengan database dan user
 * @author Leonovo
 */
public class UserController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public UserController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new user
    public String createUser(User user) {
        System.out.println("---- Creating new user -----");
        try {
            String sql = "INSERT INTO users (user_id, username, password, fullname, role) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getRole());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data user berhasil ditambah : "+user.getUserId();
            } else {
                return "Data user gagal ditambah";
            }
        } catch (SQLException e) {
            return "Terjadi error : "+e.getMessage();
        }
    }
    
    // READ - Get all users with search functionality
    public List<User> getData(String searchItem) {
        List<User> listData = new ArrayList<>();
        System.out.println("---- Getting user data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM users WHERE " +
                      "user_id LIKE ? OR " +
                      "fullname LIKE ? OR " +
                      "username LIKE ? " +
                      "ORDER BY user_id";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM users ORDER BY user_id";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all users");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                listData.add(user);
                System.out.println("    User ID: " + rs.getString("user_id"));
            }
            
            System.out.println("Found " + listData.size() + " users");
            
        } catch (SQLException e) {
            System.out.println("Error getting user data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get user by ID
    public User getUserById(String userId) {
        System.out.println("---- Getting user by ID: " + userId + " -----");
        
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                
                System.out.println("User found: " + user.getUsername());
                return user;
            } else {
                System.out.println("User not found with ID: " + userId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting user by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing user
    public String updateUser(User user) {
        System.out.println("---- Updating user: " + user.getUserId() + " -----");
        
        try {
            String sql = "UPDATE users SET username = ?, password = ?, fullname = ?, role = ? WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getUserId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "User updated successfully: " + user.getUserId();
            } else {
                return "No user found with ID: " + user.getUserId();
            }
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }
    
    // DELETE - Delete user by ID
    public boolean deleteUser(String userId) {
        System.out.println("---- Deleting user: " + userId + " -----");
        
        try {
            String sql = "DELETE FROM users WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("User deleted successfully: " + userId);
                return true;
            } else {
                System.out.println("No user found with ID: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }
    
    // UTILITY - Check if username already exists
    public boolean isUsernameExists(String username) {
        return isUsernameExists(username, null);
    }
    
    // UTILITY - Check if username exists (excluding specific user ID for updates)
    public boolean isUsernameExists(String username, String excludeUserId) {
        try {
            String sql;
            if (excludeUserId != null) {
                sql = "SELECT COUNT(*) FROM users WHERE username = ? AND user_id != ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, excludeUserId);
            } else {
                sql = "SELECT COUNT(*) FROM users WHERE username = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
            }
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
        return false;
    }
    
    // UTILITY - Check if user ID already exists
    public boolean isUserIdExists(String userId) {
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking user ID: " + e.getMessage());
        }
        return false;
    }
    
    // UTILITY - Get total user count
    public int getTotalUsers() {
        try {
            String sql = "SELECT COUNT(*) FROM users";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting total users: " + e.getMessage());
        }
        return 0;
    }
    
    // UTILITY - Get users by role
    public List<User> getUsersByRole(String role) {
        List<User> listData = new ArrayList<>();
        System.out.println("---- Getting users by role: " + role + " -----");
        
        try {
            String sql = "SELECT * FROM users WHERE role = ? ORDER BY user_id";
            ps = conn.prepareStatement(sql);
            ps.setString(1, role);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                listData.add(user);
            }
            
            System.out.println("Found " + listData.size() + " users with role: " + role);
            
        } catch (SQLException e) {
            System.out.println("Error getting users by role: " + e.getMessage());
        }
        
        return listData;
    }
    
    // UTILITY - Generate next user ID
    public String generateNextUserId() {
        try {
            String sql = "SELECT user_id FROM users WHERE user_id LIKE 'USR%' ORDER BY user_id DESC LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String lastId = rs.getString("user_id");
                // Extract number from USR001, USR002, etc.
                String numberPart = lastId.substring(3);
                int nextNumber = Integer.parseInt(numberPart) + 1;
                return String.format("USR%03d", nextNumber);
            } else {
                return "USR001"; // First user
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error generating user ID: " + e.getMessage());
            return "USR001";
        }
    }
    
    // AUTHENTICATION - Validate user login
    public LoginResult loginUser(String username, String password) {
        System.out.println("---- Authenticating user: " + username + " -----");
        
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                
                System.out.println("Authentication successful for: " + username);
                return new LoginResult(true, "Login berhasil", user);
            } else {
                return new LoginResult(false, "Username atau password salah", null);
            }
            
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
            return new LoginResult(false, "Database error: " + e.getMessage(), null);
        }
    }
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
        UserController controller = new UserController();
        
        // Test CREATE
        System.out.println("=== TESTING CREATE ===");
        User newUser = new User();
        newUser.setUserId("USR999");
        newUser.setUsername("testuser");
        newUser.setPassword("password123");
        newUser.setFullname("Test User");
        newUser.setRole("Admin");
        
        String created = controller.createUser(newUser);
        System.out.println("Create result: " + created);
        
        // Test READ
        System.out.println("\n=== TESTING READ ===");
        List<User> users = controller.getData("");
        System.out.println("Total users: " + users.size());
        
        // Test READ BY ID
        System.out.println("\n=== TESTING READ BY ID ===");
        User foundUser = controller.getUserById("USR999");
        if (foundUser != null) {
            System.out.println("Found user: " + foundUser.getUsername());
        }
        
        // Test UPDATE
        System.out.println("\n=== TESTING UPDATE ===");
        if (foundUser != null) {
            foundUser.setFullname("Updated Test User");
            String updated = controller.updateUser(foundUser);
            System.out.println("Update result: " + updated);
        }
        
        // Test UTILITIES
        System.out.println("\n=== TESTING UTILITIES ===");
        System.out.println("Username exists: " + controller.isUsernameExists("testuser"));
        System.out.println("Total users: " + controller.getTotalUsers());
        System.out.println("Next user ID: " + controller.generateNextUserId());
        
        // Test AUTHENTICATION
        System.out.println("\n=== TESTING AUTHENTICATION ===");
        LoginResult authUser = controller.loginUser("testuser", "password123");
        if (authUser.success) {
            System.out.println("Login successful for: " + authUser.user.getFullname());
        }
        
        // Test DELETE
        System.out.println("\n=== TESTING DELETE ===");
        boolean deleted = controller.deleteUser("USR999");
        System.out.println("Delete result: " + deleted);
    }
}