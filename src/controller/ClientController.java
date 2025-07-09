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
            String sql = "INSERT INTO users (client_id, name, contact_person, phone, email, payment_terms, credit_limit) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, client.getClientId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getContactPerson());
            ps.setString(4, client.getPhone());
            ps.setString(5, client.getEmail());
            ps.setString(6, client.getPaymentTerms());
            ps.setDouble(7, client.getCreditLimit());
            
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
    public List<Client> getData(String searchItem) {
        List<Client> listData = new ArrayList<>();
        System.out.println("---- Getting user data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM clients WHERE " +
                      "client_id LIKE ? OR " +
                      "name LIKE ? OR " +
                      "contact_person LIKE ? " +
                      "ORDER BY client_id";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM clients ORDER BY client_id";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all Client");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getString("client_id"));
                client.setName(rs.getString("name"));
                client.setContactPerson(rs.getString("contact_person"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                client.setPaymentTerms(rs.getString("payment_terms"));
                client.setCreditLimit(rs.getDouble("credit_limit"));
                listData.add(client);
                System.out.println("    ClientId: " + rs.getString("client_id"));
            }
            
            System.out.println("Found " + listData.size() + " Client");
            
        } catch (SQLException e) {
            System.out.println("Error getting user data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get user by ID
    public Client getClientById(String clientId) {
        System.out.println("---- Getting user by ID: " + clientId + " -----");
        
        try {
            String sql = "SELECT * FROM clients WHERE client_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, clientId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
               Client client = new Client();
                client.setClientId(rs.getString("client_id"));
                client.setName(rs.getString("name"));
                client.setContactPerson(rs.getString("contact_person"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                client.setPaymentTerms(rs.getString("payment_terms"));
                client.setCreditLimit(rs.getDouble("credit_limit"));
                
                System.out.println("Client found: " + client.getClientId());
                return client;
            } else {
                System.out.println("User not found with ID: " + clientId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting Client by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing user
    public String updateClient(Client client) {
        System.out.println("---- Updating client: " + client.getClientId() + " -----");
        
        try {
            String sql = "UPDATE clients SET name = ?, contact_person = ?, phone = ?, email = ?, payment_terms = ? WHERE client_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.setString(2, client.getContactPerson());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPaymentTerms());
            ps.setDouble(6, client.getCreditLimit());
            ps.setString(7, client.getClientId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Client updated successfully: " + client.getClientId();
            } else {
                return "No client found with ID: " + client.getClientId();
            }
        } catch (SQLException e) {
            return "Error updating client: " + e.getMessage();
        }
    }
    
    // DELETE - Delete user by ID
    public boolean deleteClient(String clientId) {
        System.out.println("---- Deleting user: " + clientId + " -----");
        
        try {
            String sql = "DELETE FROM clients WHERE Client_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, clientId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Client deleted successfully: " + clientId);
                return true;
            } else {
                System.out.println("No client found with ID: " + clientId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting client: " + e.getMessage());
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
        Client newUser = new Client();
        newUser.setClientId("USR999");
        newUser.setName("testuser");
        newUser.setContactPerson("password123");
        newUser.setEmail("Test User");
        newUser.setPaymentTerms("Admin");
        newUser.setCreditLimit(10000000.0);
        
        String created = controller.createClient(newUser);
        System.out.println("Create result: " + created);
        
        // Test READ
        System.out.println("\n=== TESTING READ ===");
        List<Client> clients = controller.getData("");
        System.out.println("Total users: " + clients.size());
        
        // Test READ BY ID
        System.out.println("\n=== TESTING READ BY ID ===");
        Client foundClient = controller.getClientById("USR999");
        if (foundClient != null) {
            System.out.println("Found user: " + foundClient.getName());
        }
        
        // Test UPDATE
        System.out.println("\n=== TESTING UPDATE ===");
        if (foundClient != null) {
            foundClient.setName("Updated Test Client");
            String updated = controller.updateClient(foundClient);
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
        boolean deleted = controller.deleteClient("USR999");
        System.out.println("Delete result: " + deleted);
    }
}