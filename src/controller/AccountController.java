package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.User;

/**
 * class untuk operasi yg berkaitan dengan database dan account
 * @author Leonovo
 */
public class AccountController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public AccountController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new user
    public String createAccount(Account account) {
        System.out.println("---- Creating new user -----");
        try {
            String sql = "INSERT INTO accounts (account_id, name, type) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getAccountId());
            ps.setString(2, account.getName());
            ps.setString(3, account.getType());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data account berhasil ditambah : "+account.getAccountId();
            } else {
                return "Data account gagal ditambah";
            }
        } catch (SQLException e) {
            return "Terjadi error : "+e.getMessage();
        }
    }
    
    // READ - Get all users with search functionality
    public List<Account> getData(String searchItem) {
        List<Account> listData = new ArrayList<>();
        System.out.println("---- Getting account data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM accounts WHERE " +
                      "account_id LIKE ? OR " +
                      "name LIKE ? OR " +
                      "type LIKE ? " +
                      "ORDER BY account_id";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM accounts ORDER BY account_id";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all account");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setType(rs.getString("type"));
                listData.add(account);
                System.out.println("    Account ID: " + rs.getString("account_id"));
            }
            
            System.out.println("Found " + listData.size() + " account");
            
        } catch (SQLException e) {
            System.out.println("Error getting account data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get user by ID
    public Account getAccountById(String accountId) {
        System.out.println("---- Getting user by ID: " + accountId + " -----");
        
        try {
            String sql = "SELECT * FROM accounts WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setType(rs.getString("type"));
                
                System.out.println("Account found: " + account.getName());
                return account;
            } else {
                System.out.println("Account not found with ID: " + accountId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting account by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing user
    public String updateAccount(Account account) {
        System.out.println("---- Updating account: " + account.getAccountId() + " -----");
        
        try {
            String sql = "UPDATE accounts SET name = ?, type = ? WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getType());
            ps.setString(3, account.getAccountId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Account updated successfully: " + account.getAccountId();
            } else {
                return "No account found with ID: " + account.getAccountId();
            }
        } catch (SQLException e) {
            return "Error updating account: " + e.getMessage();
        }
    }
    
    // DELETE - Delete user by ID
    public boolean deleteAccount(String accountId) {
        System.out.println("---- Deleting account: " + accountId + " -----");
        
        try {
            String sql = "DELETE FROM accounts WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Account deleted successfully: " + accountId);
                return true;
            } else {
                System.out.println("No account found with ID: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
        return false;
    }
    
    // UTILITY - Check if username already exists
//    public boolean isUsernameExists(String username) {
//        return isUsernameExists(username, null);
//    }
    
    // UTILITY - Check if username exists (excluding specific user ID for updates)
//    public boolean isUsernameExists(String username, String excludeUserId) {
//        try {
//            String sql;
//            if (excludeUserId != null) {
//                sql = "SELECT COUNT(*) FROM users WHERE username = ? AND user_id != ?";
//                ps = conn.prepareStatement(sql);
//                ps.setString(1, username);
//                ps.setString(2, excludeUserId);
//            } else {
//                sql = "SELECT COUNT(*) FROM users WHERE username = ?";
//                ps = conn.prepareStatement(sql);
//                ps.setString(1, username);
//            }
//            
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        } catch (SQLException e) {
//            System.out.println("Error checking username: " + e.getMessage());
//        }
//        return false;
//    }
    
    // UTILITY - Check if user ID already exists
    public boolean isAccountIdExists(String accountId) {
        try {
            String sql = "SELECT COUNT(*) FROM accounts WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking account ID: " + e.getMessage());
        }
        return false;
    }
    
    // UTILITY - Get total user count
    public int getTotalAccounts() {
        try {
            String sql = "SELECT COUNT(*) FROM accounts";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting total accounts: " + e.getMessage());
        }
        return 0;
    }
    
    // UTILITY - Get users by role
    public List<Account> getAccountsByType(String type) {
        List<Account> listData = new ArrayList<>();
        System.out.println("---- Getting accounts by type: " + type + " -----");
        
        try {
            String sql = "SELECT * FROM accounts WHERE type = ? ORDER BY account_id";
            ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                account.setType(rs.getString("type"));
                listData.add(account);
            }
            
            System.out.println("Found " + listData.size() + " accounts with type: " + type);
            
        } catch (SQLException e) {
            System.out.println("Error getting accounts by type: " + e.getMessage());
        }
        
        return listData;
    }
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
        AccountController controller = new AccountController();
        
        // Test CREATE
        System.out.println("=== TESTING CREATE ===");
        Account newAccount = new Account();
        newAccount.setAccountId("ACC999");
        newAccount.setName("testaccount");
        newAccount.setType("asset");
        
        String created = controller.createAccount(newAccount);
        System.out.println("Create result: " + created);
        
        // Test READ
        System.out.println("\n=== TESTING READ ===");
        List<Account> accounts = controller.getData("");
        System.out.println("Total accounts: " + accounts.size());
        
        // Test READ BY ID
        System.out.println("\n=== TESTING READ BY ID ===");
        Account foundAccount = controller.getAccountById("USR999");
        if (foundAccount != null) {
            System.out.println("Found account: " + foundAccount.getName());
        }
        
        // Test UPDATE
        System.out.println("\n=== TESTING UPDATE ===");
        if (foundAccount != null) {
            foundAccount.setName("Updated Test User");
            String updated = controller.updateAccount(foundAccount);
            System.out.println("Update result: " + updated);
        }
        
        // Test DELETE
        System.out.println("\n=== TESTING DELETE ===");
        boolean deleted = controller.deleteAccount("USR999");
        System.out.println("Delete result: " + deleted);
    }
}