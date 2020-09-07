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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author emery
 */

public class Appointment {
    static Connection conn = DBConnection.getConnection();       
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weeklyAppts = FXCollections.observableArrayList();
          
    private int apptId;
    private int custId;
    private String apptTitle;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime end;
    private LocalDateTime startTime;

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
        
    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    

    
    public Appointment(int apptId, int custId, String apptTitle,  LocalDateTime startTime,
            String description, String location, String contact, String type,
            String url, LocalDateTime end) {
        this.apptId = apptId;
        this.custId = custId;
        this.apptTitle = apptTitle;
        this.startTime = startTime;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.end = end;
    }
       
    public static ObservableList<Appointment> getAllAppts(int userId) {
        allAppts.clear();
        
        try {
            
            String selectStatement = "select * from appointment where userId = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, userId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
                                  
            while(rs.next()) { // Cycles to next row
                Appointment appt = new Appointment(rs.getInt("appointmentId"),
                        rs.getInt("customerId"),
                        rs.getString("title"),
                        rs.getTimestamp("start").toLocalDateTime(),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        rs.getTimestamp("end").toLocalDateTime());
                
                allAppts.add(appt);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allAppts;
    }
    
    public static ObservableList<Appointment> getMonthlyAppts(int userId) {
        monthlyAppts.clear();
        
        try {
            
            String selectStatement = "select * from appointment where userId = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, userId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
                                  
            while(rs.next()) { // Cycles to next row
                LocalDate currentDate = java.time.LocalDate.now();
                LocalDate monthRange = currentDate.plusMonths(1);
                LocalDate apptDate = rs.getDate("start").toLocalDate();
                
                if((currentDate.compareTo(apptDate) <= 0) && (apptDate.compareTo(monthRange) <= 0)) {
                    Appointment appt = new Appointment(rs.getInt("appointmentId"),
                        rs.getInt("customerId"),
                        rs.getString("title"),
                        rs.getTimestamp("start").toLocalDateTime(),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        rs.getTimestamp("end").toLocalDateTime());
                
                    monthlyAppts.add(appt);
                }
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return monthlyAppts;
    }
    
    public static ObservableList<Appointment> getWeeklyAppts(int userId) {
        weeklyAppts.clear();
        
        try {
            
            String selectStatement = "select * from appointment where userId = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, userId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
                                  
            while(rs.next()) { // Cycles to next row
                LocalDate currentDate = java.time.LocalDate.now();
                LocalDate weekRange = currentDate.plusWeeks(1);
                LocalDate apptDate = rs.getDate("start").toLocalDate();
                
                if((currentDate.compareTo(apptDate) <= 0) && (apptDate.compareTo(weekRange) <= 0)) {
                    Appointment appt = new Appointment(rs.getInt("appointmentId"),
                        rs.getInt("customerId"),
                        rs.getString("title"),
                        rs.getTimestamp("start").toLocalDateTime(),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        rs.getTimestamp("end").toLocalDateTime());
                
                    weeklyAppts.add(appt);
                }
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return weeklyAppts;
    }
    
    public static void deleteAppt(int apptToRemove) {
        try {
            
            String deleteStatement = "delete from appointment where appointmentId = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            Integer appointmentId = apptToRemove;
            
            ps.setInt(1, appointmentId);
            
            ps.execute();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void addAppointment(int customer, String title, String description, 
            String location, String contact, String type, String url, Timestamp start, Timestamp end, User currentUser) {
        try {                        
            String insertStatement = "insert into appointment(customerId, userId, title, "
                    + "description, location, contact, type, url, start, end, "
                    + "createDate, createdBy, lastUpdateBy) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            Timestamp createDate = new Timestamp(System.currentTimeMillis());
            
            ps.setInt(1, customer);
            ps.setInt(2, currentUser.getUserId());
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setTimestamp(9, start);
            ps.setTimestamp(10, end);
            ps.setTimestamp(11, createDate);
            ps.setString(12, currentUser.getUsername());
            ps.setString(13, currentUser.getUsername());
            ps.execute();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static Appointment getCurrentAppt(int apptId) {
       int i;
       for(i = 0; i < allAppts.size(); ++i) {
           if (allAppts.get(i).getApptId() == apptId) {
                break;
           }
       }          
       return allAppts.get(i);
    }
    
    public static void updateCurrentAppt (int apptId, int customerId, String title, 
            String description, String location, String contact, 
            String type, String url, Timestamp start, Timestamp end) {
        try {
            String updateStatement = "update appointment set customerId = ?, "
                    + "title = ?, description = ?, location = ?, "
                    + "contact = ?, type = ?, url = ?, start = ?, end = ? where appointmentId = ?";
            DBQuery.setPreparedStatement(conn, updateStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, customerId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, contact);
            ps.setString(6, type);
            ps.setString(7, url);
            ps.setTimestamp(8, start);
            ps.setTimestamp(9, end);
            ps.setInt(10, apptId);
            ps.execute();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
       
}
