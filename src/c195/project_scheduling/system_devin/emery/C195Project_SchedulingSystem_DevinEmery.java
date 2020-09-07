/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.project_scheduling.system_devin.emery;

import Models.Appointment;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;

/**
 *
 * @author emery
 */

public class C195Project_SchedulingSystem_DevinEmery extends Application {
    
    /**
     * @param args the command line arguments
     */
private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    public static void main(String[] args) throws SQLException {
        
        Connection conn = DBConnection.getConnection(); //Establish DB connection
        
        launch(args);
        
        DBConnection.closeConnection(); //Close DB connection
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //Load View and Set Controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Login.fxml"));
        ViewController.LoginController controller = new ViewController.LoginController();
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }
    
}

//Raw SQL insert statement
        //String insertStatement = "insert into country(country, createDate, createdBy, lastUpdateBy) values('US', '2020-08-02 00:00:00', 'admin', 'admin')";
        /*
        //Variable Insert
        String countryName = "Canada";
        String createDate = "2020-08-02 00:00:00";
        String createdBy = "admin";
        String lastUpdateBy = "admin";
        
        String insertStatement = "insert into country(country, createDate, createdBy, lastUpdateBy)" +
                                "values(" +
                                "'" + countryName +"'," +
                                "'" + createDate +"'," +
                                "'" + createdBy +"'," +
                                "'" + lastUpdateBy +"'" +
                                ")";
        */
        
        //Raw Update statement
        //String updateStatement = "update country set country = 'Japan' where country = 'Canada'";
        
        //Raw Delete statement
        //String deleteStatement = "delete from country where country = 'Japan'";
        
        
        
        //Execute SQL statement
        //statement.execute(insertStatement);
       // statement.execute(updateStatement);
       //statement.execute(deleteStatement);

/* DBQuery.setStatement(conn); //Create Statement Object
        Statement statement = DBQuery.getStatement(); //Get statement
        
        String selectStatement = "select * from country"; //Select statement
        
        try {
            statement.execute(selectStatement); //Execute Statement
            ResultSet rs = statement.getResultSet(); //Get ResultSet

            //Forward Scroll ResultSet
            while(rs.next()) { // Cycles to next row
                int countryId = rs.getInt("countryId");
                String countryName = rs.getString("country");
                LocalDate date = rs.getDate("createDate").toLocalDate();
                LocalTime time = rs.getTime("createDate").toLocalTime();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();

                //Display record
                System.out.println(countryId + " | " + countryName + " | " + date + " | " + time + " | " + createdBy + " | " + lastUpdate);
            }
        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());
        } 

//Confirm rows affected
        if(statement.getUpdateCount() > 0) {
            System.out.println(statement.getUpdateCount() + " rows(s) affected!");
        }
        else {
            System.out.println("No change!");
        }
        */

/* String insertStatement = "insert into country(country, createDate, createdBy, lastUpdateBy) values(?, ?, ?, ?)";
        DBQuery.setStatement(conn);// need to keep is using normal statement anywhere
        DBQuery.setPreparedStatement(conn, insertStatement); //create prepared statment
        PreparedStatement ps = DBQuery.getPreparedStatement();
                 
        //String country;
        String country = "Japan";
        String createDate = "2020-03-28 00:00:00";
        String createdBy = "admin";
        String lastUpdateBy = "admin";
        
        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter a country: ");
        //country = keyboard.nextLine();
        
        try {
            ps.setString(1, country);
            ps.setString(2, createDate);
            ps.setString(3, createdBy);
            ps.setString(4, lastUpdateBy);

            //ps.execute(); //execute PreparedStatement

            //check rows affected
            if(ps.getUpdateCount() > 0) {
                System.out.println(ps.getUpdateCount() + " row(s) affected!");
            }
            else {
                System.out.println("No change.");
            }
        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());
        } */