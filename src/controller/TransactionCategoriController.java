package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TransactionCategori;

/**
 * class untuk operasi yg berkaitan dengan database dan user
 * @author Leonovo
 */
public class TransactionCategoriController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public TransactionCategoriController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new user
    public String createCategory(TransactionCategori categori) {
        System.out.println("---- Creating new categori -----");
        try {
            String sql = "INSERT INTO transaction_categories (category_id, name, account_id, type, description) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, categori.getCategoryId());
            ps.setString(2, categori.getNama());
            ps.setString(3, categori.getAccountId());
            ps.setString(4, categori.getType());
            ps.setString(5, categori.getDescription());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data kategori berhasil ditambah : "+categori.getCategoryId();
            } else {
                return "Data categori gagal ditambah";
            }
        } catch (SQLException e) {
            return "Terjadi error : "+e.getMessage();
        }
    }
    
    // READ - Get all users with search functionality
    public List<TransactionCategori> getData(String searchItem) {
        List<TransactionCategori> listData = new ArrayList<>();
        System.out.println("---- Getting categori data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT * FROM transaction_categories WHERE " +
                      "category_id LIKE ? OR " +
                      "name LIKE ? OR " +
                      "account_id LIKE ? " +
                      "ORDER BY category_id";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT * FROM transaction_categories ORDER BY category_id";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all category");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                TransactionCategori category = new TransactionCategori();
                category.setCategoryId(rs.getString("category_id"));
                category.setNama(rs.getString("name"));
                category.setAccountId(rs.getString("account_id"));
                category.setType(rs.getString("type"));
                category.setDescription(rs.getString("description"));
                listData.add(category);
                System.out.println("    Categori ID: " + rs.getString("category_id"));
            }
            
            System.out.println("Found " + listData.size() + " categories ");
            
        } catch (SQLException e) {
            System.out.println("Error getting categories data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get category by ID
    public TransactionCategori getTransactionCategoryById(String categoryId) {
        System.out.println("---- Getting category by ID: " + categoryId + " -----");
        
        try {
            String sql = "SELECT * FROM transaction_categories WHERE category_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, categoryId);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                TransactionCategori category = new TransactionCategori();
                category.setCategoryId(rs.getString("category_id"));
                category.setNama(rs.getString("name"));
                category.setAccountId(rs.getString("account_id"));
                category.setType(rs.getString("type"));
                category.setDescription(rs.getString("description"));
                
                System.out.println("Category found: " + category.getNama());
                return category;
            } else {
                System.out.println("Category not found with ID: " + categoryId);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting category by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing CATEGORY
    public String updateCategory(TransactionCategori category) {
        System.out.println("---- Updating category: " + category.getCategoryId()+ " -----");
        
        try {
            String sql = "UPDATE transaction_categories SET name = ?, account_id = ?, type = ?, description = ? WHERE category_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, category.getNama());
            ps.setString(2, category.getAccountId());
            ps.setString(3, category.getType());
            ps.setString(4, category.getDescription());
            ps.setString(5, category.getCategoryId());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Category updated successfully: " + category.getCategoryId();
            } else {
                return "No category found with ID: " + category.getCategoryId();
            }
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }
    
    // DELETE - Delete category by ID
    public boolean deleteCategory(String categoryId) {
        System.out.println("---- Deleting category: " + categoryId + " -----");
        
        try {
            String sql = "DELETE FROM transaction_category WHERE category_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, categoryId);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Category deleted successfully: " + categoryId);
                return true;
            } else {
                System.out.println("No category found with ID: " + categoryId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
        }
        return false;
    }
    
    
    
    
    
    // UTILITY - Get category by type
    public List<TransactionCategori> getUsersByType(String type) {
        List<TransactionCategori> listData = new ArrayList<>();
        System.out.println("---- Getting users by type: " + type + " -----");
        
        try {
            String sql = "SELECT * FROM transaction_categories WHERE type = ? ORDER BY category_id";
            ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                TransactionCategori category = new TransactionCategori();
                category.setCategoryId(rs.getString("category_id"));
                category.setNama(rs.getString("name"));
                category.setAccountId(rs.getString("account_id"));
                category.setType(rs.getString("type"));
                category.setDescription(rs.getString("description"));
                listData.add(category);
            }
            
            System.out.println("Found " + listData.size() + " category with type: " + type);
            
        } catch (SQLException e) {
            System.out.println("Error getting category by role: " + e.getMessage());
        }
        
        return listData;
    }
    
    
    
   
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
        TransactionCategoriController controller = new TransactionCategoriController();
        
        // Test CREATE
        System.out.println("=== TESTING CREATE ===");
        TransactionCategori newCategory = new TransactionCategori();
        newCategory.setCategoryId("CAT999");
        newCategory.setNama("testuser");
        newCategory.setAccountId("ACC999");
        newCategory.setType("debit");
        newCategory.setDescription("LALALALALALALALA LALALALLALA LALALALAL");
        
        String created = controller.createCategory(newCategory);
        System.out.println("Create result: " + created);
        
        // Test READ
        System.out.println("\n=== TESTING READ ===");
        List<TransactionCategori> users = controller.getData("");
        System.out.println("Total category: " + users.size());
        
        // Test READ BY ID
        System.out.println("\n=== TESTING READ BY ID ===");
        TransactionCategori foundCategory = controller.getTransactionCategoryById("CAT999");
        if (foundCategory != null) {
            System.out.println("Found user: " + foundCategory.getNama());
        }
        
        // Test UPDATE
        System.out.println("\n=== TESTING UPDATE ===");
        if (foundCategory != null) {
            foundCategory.setNama("Updated Test Category");
            String updated = controller.updateCategory(foundCategory);
            System.out.println("Update result: " + updated);
        }
        
       
        
        
        // Test DELETE
        System.out.println("\n=== TESTING DELETE ===");
        boolean deleted = controller.deleteCategory("USR999");
        System.out.println("Delete result: " + deleted);
    }
}