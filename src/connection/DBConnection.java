/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Leonovo
 */
public class DBConnection {
        static Connection conn;
    
    public static Connection getConnection() {
        if (conn == null) {
                MysqlDataSource data = new MysqlDataSource();
                data.setUser("root");
                data.setPassword("");
                data.setDatabaseName("pemvis_aplikasi_keuangan");
                try {
                    conn = data.getConnection();
                    System.out.println("Koneksi Berhasil");
                } catch (SQLException e) {
                    System.out.println("Koneksi Gagal : "+e);
                }
        }
        
        return conn;
    }
    
    public static void main(String[] args) {
        getConnection();
        
    }
   
    
}
