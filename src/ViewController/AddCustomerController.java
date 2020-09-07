/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Models.Customer;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author emery
 */
public class AddCustomerController implements Initializable {

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField streetAddrField;
    @FXML
    private TextField streetAddrField2;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button addCustExitButton;
    @FXML
    private Button addCustSaveButton;
    
    User currentUser;
    
    public AddCustomerController(User currentUser) {
        this.currentUser = currentUser;    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handlerExitButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerScreen.fxml"));
        ViewController.CustomerScreenController controller = new ViewController.CustomerScreenController(currentUser);
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }

    @FXML
    private void handlerSaveButton(MouseEvent event) throws IOException {
       String name = customerNameField.getText().trim();
       String address = streetAddrField.getText().trim();
       String address2 = streetAddrField2.getText().trim();
       String city = cityField.getText().trim();
       String postalCode = postalCodeField.getText().trim();
       String country = countryField.getText().trim();
       String phoneNum = phoneField.getText().trim();
       
       Customer.addCustomer(name, address, address2, city, postalCode, country, phoneNum, currentUser.getUsername());
       
       handlerExitButton(event);
    }
    
}
