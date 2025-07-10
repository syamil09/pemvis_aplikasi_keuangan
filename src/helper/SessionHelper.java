package helper;

import model.User;
import javax.swing.JOptionPane;

/**
 * SessionHelper - Class untuk mengelola session user
 */
public class SessionHelper {
    private static SessionHelper instance;
    private User currentUser;
    private boolean isLoggedIn = false;
    
    // Private constructor untuk Singleton
    private SessionHelper() {}
    
    // Get instance (Singleton)
    public static SessionHelper getInstance() {
        if (instance == null) {
            instance = new SessionHelper();
        }
        return instance;
    }
    
    /**
     * Login user
     * @param user
     * @return 
     */
    public boolean login(User user) {
        if (user != null) {
            this.currentUser = user;
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }
    
    /**
     * Logout user
     */
    public void logout() {
        this.currentUser = null;
        this.isLoggedIn = false;
    }
    
    /**
     * Check apakah user sudah login
     * @return 
     */
    public boolean isLoggedIn() {
        return isLoggedIn && currentUser != null;
    }
    
    /**
     * Get current user
     * @return 
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Get user ID
     */
    public String getUserId() {
        return currentUser != null ? currentUser.getUserId() : null;
    }
    
    /**
     * Get username
     */
    public String getUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
    
    /**
     * Get user role
     */
    public String getUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
    
    /**
     * Get full name
     * @return 
     */
    public String getFullName() {
        return currentUser != null ? currentUser.getFullname() : "Unknown User";
    }
    
    public boolean isAdmin() {
        return this.getUserRole().equalsIgnoreCase("admin");
    }
    
}