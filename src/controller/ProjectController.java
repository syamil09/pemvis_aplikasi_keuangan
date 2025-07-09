package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;


/**
 * class untuk operasi yg berkaitan dengan database dan project
 * @author Leonovo
 */
public class ProjectController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ProjectController() {
        conn = DBConnection.getConnection();
    }
    
    // CREATE - Add new project
    public String createProject(Project project) {
        System.out.println("---- Creating new project -----");
        try {
            String sql = "INSERT INTO projects (project_id, client_id, name, start_date, end_date, budget, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getProject_id());
            ps.setString(2, project.getClient_id());
            ps.setString(3, project.getName());
            ps.setString(4, project.getStart_date());
            ps.setString(5, project.getEnd_date());
            ps.setDouble(6, project.getBudget());
            ps.setString(7,project.getStatus() );
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Data project berhasil ditambah : "+project.getProject_id();
            } else {
                return "Data project gagal ditambah";
            }
        } catch (SQLException e) {
            return "Terjadi error : "+e.getMessage();
        }
    }
    
    // READ - Get all users with search functionality
    public List<Project> getData(String searchItem) {
        List<Project> listData = new ArrayList<>();
        System.out.println("---- Getting project data -----");
        
        try {
            String sql;
            
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                sql = "SELECT p.*, c.name AS client_name FROM projects p " +
               "LEFT JOIN clients c ON p.client_id = c.client_id " +
               "WHERE " +
               "p.project_id LIKE ? OR " +
               "p.name LIKE ? OR " +
               "p.start_date LIKE ? OR " +
               "p.end_date LIKE ? OR " +
               "c.name LIKE ? " +
               "ORDER BY p.project_id";
                ps = conn.prepareStatement(sql);
                
                String searchPattern = "%" + searchItem.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                
                System.out.println("Searching for: " + searchItem);
            } else {
                sql = "SELECT p.*, c.name AS client_name FROM projects p " +
                "LEFT JOIN clients c ON p.client_id = c.client_id " +
                "ORDER BY p.project_id";
                ps = conn.prepareStatement(sql);
                System.out.println("Getting all project");
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Project project = new Project();
                project.setProject_id(rs.getString("project_id"));
                project.setClient_id(rs.getString("client_id"));
                project.setClient_name(rs.getString("client_name"));
                project.setName(rs.getString("name"));
                project.setStart_date(rs.getString("start_date"));
                project.setEnd_date(rs.getString("end_date"));
                project.setBudget(rs.getDouble("budget"));
                project.setStatus(rs.getString("status"));
                listData.add(project);
                System.out.println("    Project ID: " + rs.getString("project_id"));
            }
            
            System.out.println("Found " + listData.size() + " projects");
            
        } catch (SQLException e) {
            System.out.println("Error getting project data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listData;
    }
    
    // READ - Get project by ID
    public Project getProjectById(String project_id) {
        System.out.println("---- Getting projects by ID: " + project_id + " -----");
        
        try {
            String sql = "SELECT p.*, c.name AS client_name FROM projects p " +
             "LEFT JOIN clients c ON p.client_id = c.client_id " +
             "WHERE p.project_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, project_id);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Project project = new Project();
                project.setProject_id(rs.getString("project_id"));
                project.setClient_id(rs.getString("client_id"));
                project.setClient_name(rs.getString("client_name"));
                project.setName(rs.getString("name"));
                project.setStart_date(rs.getString("start_date"));
                project.setEnd_date(rs.getString("end_date"));
                project.setBudget(rs.getDouble("budget"));
                project.setStatus(rs.getString("status"));
                
                System.out.println("Project found: " + project.getProject_id());
                return project;
            } else {
                System.out.println("Project not found with ID: " + project_id);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting project by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update existing project
    public String updateProject(Project project) {
        System.out.println("---- Updating project: " + project.getProject_id() + " -----");
        
        try {
            String sql = "UPDATE projects SET client_id = ?, name = ?, start_date = ?, end_date = ?, budget = ?, status = ? WHERE project_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, project.getClient_id());
            ps.setString(2, project.getName());
            ps.setString(3, project.getStart_date());
            ps.setString(4, project.getEnd_date());
            ps.setDouble(5, project.getBudget());
            ps.setString(6, project.getStatus());
            ps.setString(7, project.getProject_id());
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return "Project updated successfully: " + project.getProject_id();
            } else {
                return "No project found with ID: " + project.getProject_id();
            }
        } catch (SQLException e) {
            return "Error updating project: " + e.getMessage();
        }
    }
    
    // DELETE - Delete project by ID
    public boolean deleteProject(String project_id) {
        System.out.println("---- Deleting project: " + project_id + " -----");
        
        try {
            String sql = "DELETE FROM projects WHERE project_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, project_id);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Project deleted successfully: " + project_id);
                return true;
            } else {
                System.out.println("No project found with ID: " + project_id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting project: " + e.getMessage());
        }
        return false;
    }
    
    // TESTING - Test all CRUD operations
    public static void main(String[] args) {
       
    }
}