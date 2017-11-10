/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBO implements Serializable {

    public static Connection openConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionString = "jdbc:sqlserver://localhost;database=MotorbikeStore;username=sa;password=tu3816329";
            con = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
//    public static Connection openConnection() {
//        Connection con = null;
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String connectionString = "jdbc:sqlserver://sql12.freesqldatabase.com;database=sql12204106;username=sql12204106";
//            con = DriverManager.getConnection(connectionString);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return con;
//    }

}
