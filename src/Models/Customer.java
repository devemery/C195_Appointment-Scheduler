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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author emery
 */
public class Customer {
    static Connection conn = DBConnection.getConnection();
    private static ObservableList<Customer> allCusts = FXCollections.observableArrayList();
    
    private int customerId;
    private String customerName;
    private String customerAddr;
    private String customerAddr2;
    private String customerCity;
    private String customerPostal;
    private String customerCountry;
    private String customerPhone;
    private boolean customerActive;

    public Customer(int customerId, String customerName, String customerAddr, String customerAddr2, 
            String customerCity, String customerPostal, String customerCountry, String customerPhone, boolean customerActive) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddr = customerAddr;
        this.customerAddr2 = customerAddr2;
        this.customerCity = customerCity;
        this.customerPostal = customerPostal;
        this.customerCountry = customerCountry;
        this.customerPhone = customerPhone;
        this.customerActive = customerActive;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerAddr2() {
        return customerAddr2;
    }

    public void setCustomerAddr2(String customerAddr2) {
        this.customerAddr2 = customerAddr2;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPostal() {
        return customerPostal;
    }

    public void setCustomerPostal(String customerPostal) {
        this.customerPostal = customerPostal;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public boolean isCustomerActive() {
        return customerActive;
    }

    public void setCustomerActive(boolean customerActive) {
        this.customerActive = customerActive;
    }

    
    
    public static ObservableList<Customer> getAllCusomers() {
        allCusts.clear();
        try {
            
            String custselectStatement = "select customerId, customerName, active, address, address2, postalCode, city, country, phone "
                    + "from customer "
                    + "join address on customer.addressId = address.addressId "
                    + "join city on address.cityId = city.cityId "
                    + "join country on city.countryId = country.countryId;";
            DBQuery.setPreparedStatement(conn, custselectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            
            while(rs.next()) {              
                Customer cust = new Customer(
                rs.getInt("customerId"),
                rs.getString("customerName"),
                rs.getString("address"),
                rs.getString("address2"),
                rs.getString("city"),
                rs.getString("postalCode"),
                rs.getString("country"),
                rs.getString("phone"),
                rs.getBoolean("active"));
                                
                allCusts.add(cust);
            }
        }
        catch (SQLException ex) {
            System.out.println("Get all customers error:" +ex.getMessage());
        }
        return allCusts;
    }
    
    public static void deleteCustomer(int CustomerToRemove) {
       try {
            
            String deleteStatement = "delete from customer where customerId = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            Integer customerId = CustomerToRemove;
            
            ps.setInt(1, customerId);
            
            ps.execute();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public static void addCustomer(String name, String address, String address2, String city, String postalCode, String country, String phoneNum, String user) {
        boolean newCountry = true;
        boolean newCity = true;
        try {
            String selectStatement = "select country from country";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            
            while(rs.next()) {
                if (rs.getString("country").equalsIgnoreCase(country)) {
                    newCountry = false;
                }
            }
            
            if (newCountry) {
                String insertStatement = "insert into country(country, createDate, createdBy, lastUpdateBy)"
                        + " values(?, ?, ?, ?)";
                DBQuery.setPreparedStatement(conn, insertStatement);
                ps = DBQuery.getPreparedStatement();
                
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                
                ps.setString(1, country);
                ps.setTimestamp(2, createDate);
                ps.setString(3, user);
                ps.setString(4, user);
                
                ps.execute();
            }
            
            //selectStatement = "select" ***SELECT STATEMENT FOR CITY COPY COUNTRY
            selectStatement = "select countryId from country where country = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, country);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int countryId = rs.getInt("countryId");
            
            selectStatement = "select city from city";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.execute();
            rs = ps.getResultSet();
            
            while(rs.next()) {
                if (rs.getString("city").equalsIgnoreCase(city)) {
                    newCity = false;
                }
            }
            
            if (newCity) {
                String insertStatement = "insert into city(city, countryId, createDate, createdBy, lastUpdateBy)"
                        + " values(?, ?, ?, ?, ?)";
                DBQuery.setPreparedStatement(conn, insertStatement);
                ps = DBQuery.getPreparedStatement();
                
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                
                ps.setString(1, city);
                ps.setInt(2, countryId);
                ps.setTimestamp(3, createDate);
                ps.setString(4, user);
                ps.setString(5, user);
                
                ps.execute();
            }
            
            selectStatement = "select cityId from city where city = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, city);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int cityId = rs.getInt("cityId");
            
            String insertStatement = "insert into address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStatement);
            ps = DBQuery.getPreparedStatement();
            
            Timestamp createDate = new Timestamp(System.currentTimeMillis());
            
            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phoneNum);
            ps.setTimestamp(6, createDate);
            ps.setString(7, user);
            ps.setString(8, user);
            ps.execute();
            
            selectStatement = "select addressId from address where address = ? and address2 = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, address);
            ps.setString(2, address2);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int addressId = rs.getInt("addressId");
            
            insertStatement = "insert into customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) "
                    + "values(?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setInt(2, addressId);
            ps.setInt(3, 1);
            ps.setTimestamp(4, createDate);
            ps.setString(5, user);
            ps.setString(6, user);
            ps.execute();
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static Customer getCurrentCustomer(int customerId) {
       int i;
       for(i = 0; i < allCusts.size(); ++i) {
           if (allCusts.get(i).getCustomerId() == customerId) {
                break;
           }
       }          
       return allCusts.get(i);
    }
    
    public static int getCurrentCustomer(String customerName) {
       int i;
       for(i = 0; i < allCusts.size(); ++i) {
           if (allCusts.get(i).getCustomerName().equalsIgnoreCase(customerName)) {
                break;
           }
       }          
       return allCusts.get(i).getCustomerId();
    }
    
    //Update the currently modified Customer
    public static void updateCurrentCustomer(int customerId, boolean active, String customerName, 
            String address, String address2, String city, String postalCode, 
            String country, String phone, String user) {
        
        boolean newCountry = true;
        boolean newCity = true;
        
        try {                        
            String selectStatement = "select country from country";
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            
            while(rs.next()) {
                if (rs.getString("country").equalsIgnoreCase(country)) {
                    newCountry = false;
                }
            }
            
            if (newCountry) {
                String insertStatement = "insert into country(country, createDate, createdBy, lastUpdateBy)"
                        + " values(?, ?, ?, ?)";
                DBQuery.setPreparedStatement(conn, insertStatement);
                ps = DBQuery.getPreparedStatement();
                
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                
                ps.setString(1, country);
                ps.setTimestamp(2, createDate);
                ps.setString(3, user);
                ps.setString(4, user);
                
                ps.execute();
            }
                        
            selectStatement = "select countryId from country where country = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, country);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int countryId = rs.getInt("countryId");
            
            selectStatement = "select city from city";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.execute();
            rs = ps.getResultSet();
            
            while(rs.next()) {
                if (rs.getString("city").equalsIgnoreCase(city)) {
                    newCity = false;
                }
            }
            
            if (newCity) {
                String insertStatement = "insert into city(city, countryId, createDate, createdBy, lastUpdateBy)"
                        + " values(?, ?, ?, ?, ?)";
                DBQuery.setPreparedStatement(conn, insertStatement);
                ps = DBQuery.getPreparedStatement();
                
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                
                ps.setString(1, city);
                ps.setInt(2, countryId);
                ps.setTimestamp(3, createDate);
                ps.setString(4, user);
                ps.setString(5, user);
                
                ps.execute();
            }
            
            selectStatement = "select cityId from city where city = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, city);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int cityId = rs.getInt("cityId");
            
            selectStatement = "select addressId from customer where customerId = ?";
            DBQuery.setPreparedStatement(conn, selectStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setInt(1, customerId);
            ps.execute();
            rs = ps.getResultSet();
            rs.first();
            int addressId = rs.getInt("addressId");
                        
            String updateStatement = "update address set address = ?, address2 = ?, cityId = ?,"
                    + " postalCode = ?, phone = ?, lastUpdateBy = ? where addressId = ?";
            DBQuery.setPreparedStatement(conn, updateStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setString(6, user);
            ps.setInt(7, addressId);
            ps.execute();
            
            Timestamp updateDate = new Timestamp(System.currentTimeMillis());
            
            updateStatement = "update customer set customerName = ?, "
                    + "active = ?, lastUpdateBy = ? "
                    + "where customerId = ?";
            DBQuery.setPreparedStatement(conn, updateStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setString(1, customerName);
            ps.setBoolean(2, active);            
            ps.setString(3, user);
            ps.setInt(4, customerId);
            ps.execute();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
