/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Models.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DBQuery;

/**
 * FXML Controller class
 *
 * @author emery
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button loginErrorOKButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private AnchorPane loginErrorBox;
    
    //Only need this to use queries
    Statement statement = DBQuery.getStatement(); //Get statement
    User currentUser = new User(null, null, 0);
                
    public LoginController() {
                
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handlerExitButton(MouseEvent event) {
        Platform.exit();
    }
    @FXML
    private void handlerLoginButton(MouseEvent event) throws SQLException, IOException {
       boolean success = false;
        
       currentUser.setUsername(usernameField.getText());
       currentUser.setPassword(passwordField.getText());
       
       int id;
       id = currentUser.loginAttempt(currentUser.getUsername(), currentUser.getPassword());
       currentUser.setUserId(id);
       if (id != 0) {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AppointmentScreen.fxml"));
           ViewController.AppointmentScreenController controller = new ViewController.AppointmentScreenController(currentUser);
           loader.setController(controller);
           Parent root = loader.load(); //marries view to controller
           Scene scene = new Scene(root);
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           stage.setScene(scene);
           stage.setResizable(false); //sets screen to be resizable
           stage.show(); //show view
           System.out.println("Successful login");
           success = true;
       }
       else {
           System.out.println("Unsuccessful login");
           loginErrorBox.setVisible(true);
           loginButton.setDisable(true);
           exitButton.setDisable(true);                      
        }
       //Create LoginAttempts file
       try {
            File myObj = new File("LoginAttempts.txt");
            if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            }     
            else {
            System.out.println("File already exists.");
            }
            } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
       //Write Login Attempt to the File
        try {
            FileWriter myWriter = new FileWriter("LoginAttempts.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            
            Timestamp attemptTime = new Timestamp(System.currentTimeMillis());
            
            bw.write(attemptTime + " | Username: " + usernameField.getText() + " | Password: "
            + passwordField.getText() + " | Success: " + success);
            bw.newLine();
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
       
    @FXML
    private void handlerLoginErrorOKButton(MouseEvent event) {
        loginErrorBox.setVisible(false);
        loginButton.setDisable(false);
        exitButton.setDisable(false);
    }
    
}
