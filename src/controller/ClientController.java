package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import model.User;

/**
 * class untuk operasi yg berkaitan dengan database dan user
 * @author Leonovo
 */
public class ClientController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ClientController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new user
    public String createClient(Client client) {
        System.out.println("---- Creating new user -----");
        try {
            String sql = "INSERT INTO users (ClientId, Name, Contact_Person, Phone, Email, Payment_Terms, Credit_Limit) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, client.getClientId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getContact_Person());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getPayment_Terms());
            ps.setString(7, client.getCredit_Limit());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data user berhasil ditambah : "+client.getClientId();
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
                sql = "SELECT * FROM Client ORDER BY ClientId";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all Client");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Client user = new Client();
                user.setClientId(rs.getString("ClientId"));
                user.setName(rs.getString("Name"));
                user.setContact_Person(rs.getString("Contact_Person"));
                user.setPhone(rs.getString("Phone"));
                user.setEmail(rs.getString("Email"));
                user.setPayment_Terms(rs.getString("Payment_Terms"));
                user.setCredit_Limit(rs.getString("Credit_Limit"));
                listData.add(Client);
                System.out.println("    ClientId: " + rs.getString("ClientId"));
            }
            
            System.out.println("Found " + listData.size() + " Client");
            
        } catch (SQLException e) {
            System.out.println("Error getting user data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get user by ID
    public User getUserById(String userId) {
        System.out.println("---- Getting user by ID: " + ClientId + " -----");
        
        try {
            String sql = "SELECT * FROM users WHERE ClientId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, ClientId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
               Client user = new Client();
                user.setClientId(rs.getString("Client_Id"));
                user.setName(rs.getString("Name"));
                user.setContact_Person(rs.getString("Contact_Person"));
                user.setPhone(rs.getString("Phone"));
                user.setEmail(rs.getString("Email"));
                user.setPayment_Terms(rs.getString("Payment_Terms"));
                user.setCredit_Limit(rs.getString("Credit_Limit"));
                
                System.out.println("Client found: " + Client.getClientId());
                return Client;
            } else {
                System.out.println("User not found with ID: " + ClientId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting Client by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing user
    public String updateUser(User user) {
        System.out.println("---- Updating user: " + user.getUserId() + " -----");
        
        try {
            String sql = "UPDATE users SET username = ?, password = ?, fullname = ?, role = ? WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getClientId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getContact_Person());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPayment_Terms());
            ps.setString(7, user.getCredit_Limit());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "User updated successfully: " + Client.getClient_Id();
            } else {
                return "No user found with ID: " + Client.getClient_Id();
            }
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }
    
    // DELETE - Delete user by ID
    public boolean deleteUser(String userId) {
        System.out.println("---- Deleting user: " + ClientId + " -----");
        
        try {
            String sql = "DELETE FROM users WHERE Client_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Client_Id);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("User deleted successfully: " + ClientId);
                return true;
            } else {
                System.out.println("No user found with ID: " + ClientId);
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
    public User authenticateUser(String username, String password) {
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
                return user;
            } else {
                System.out.println("Authentication failed for: " + username);
            }
            
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        
        return null;
    }
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
        ClientController controller = new ClientController();
        
        // Test CREATE
        System.out.println("=== TESTING CREATE ===");
        User newUser = new User();
        newUser.setUserId("USR999");
        newUser.setUsername("testuser");
        newUser.setPassword("password123");
        newUser.setFullname("Test User");
        newUser.setRole("Admin");
        
        String created = controller.createClient(newUser);
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
        User authUser = controller.authenticateUser("testuser", "password123");
        if (authUser != null) {
            System.out.println("Login successful for: " + authUser.getFullname());
        }
        
        // Test DELETE
        System.out.println("\n=== TESTING DELETE ===");
        boolean deleted = controller.deleteUser("USR999");
        System.out.println("Delete result: " + deleted);
    }
}