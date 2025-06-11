/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author Leonovo
 */
public class UserController {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserController() {
        conn = DBConnection.getConnection();
    }
    
    public List<User> getAll(String cariItem) {
        List<User> listData = new ArrayList<User>();
        System.out.println("---- getting data -----");
        try {
            String sql = "SELECT * FROM users WHERE "
                    + "user_id LIKE '%"+cariItem+"%' OR "
                    + "fullname LIKE '%"+cariItem+"%' OR "
                    + "username LIKE '%"+cariItem+"%' ";
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                listData.add(user);
                System.out.println("    id user : " + rs.getString("user_id"));
            }
        } catch (SQLException e) {
            System.out.println("error get data book : "+e);
        }
        return listData;
    }
    
    
    // untuk tes fungsionalitas sebelum dipanggil di view
    public static void main(String[] args) {
        UserController ctr = new UserController();
        List<User> users = ctr.getAll("");
        System.out.println(users);
    }
}
