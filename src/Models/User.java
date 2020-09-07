/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author emery
 */
public class User {
    
    private String username;
    private String password;
    private int userId;
    
    Connection conn = DBConnection.getConnection();//Ger connection
    Statement statement = DBQuery.getStatement(); //Get statement if not using prepared statement.

    public User(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }
                
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
        
    public int loginAttempt (String user, String pass) throws SQLException {
        String selectStatement = "select * from user";
        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        int id = 0;
        
        while (rs.next()) { 
            String userName = rs.getString("userName");
            String passWord = rs.getString("password");
            int userId = rs.getInt("userId");
            if ((userName.equals(user)) && (passWord.equals(pass))) {
                id = userId;
            }
            else {
                //Make log code
            }
        }
        return id;
    }
    
}
