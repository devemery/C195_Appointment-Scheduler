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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author emery
 */
public class ModifyCustomerController implements Initializable {
    
    @FXML
    private TextField idField;
    @FXML
    private CheckBox activeBox;
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
    private Button modifyCustExitButton;
    @FXML
    private Button modifyCustSaveButton;

    /**
     * Initializes the controller class.
     */
    
    User currentUser;
    int customerId;
    Customer currentCustomer;
    
    public ModifyCustomerController(User currentUser, int customerId) {
        this.currentUser = currentUser;
        this.customerId =customerId;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentCustomer = Customer.getCurrentCustomer(customerId);
        if (currentCustomer.isCustomerActive()) {
            activeBox.setDisable(false);
        }
        else {
            activeBox.setDisable(true);
        }
        idField.setText(Integer.toString(customerId));
        customerNameField.setText(currentCustomer.getCustomerName());
        streetAddrField.setText(currentCustomer.getCustomerAddr());
        streetAddrField2.setText(currentCustomer.getCustomerAddr2());
        cityField.setText(currentCustomer.getCustomerCity());
        postalCodeField.setText(currentCustomer.getCustomerPostal());
        countryField.setText(currentCustomer.getCustomerCountry());
        phoneField.setText(currentCustomer.getCustomerPhone());
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
        boolean active = true;
        if(activeBox.isDisable()) {
            active = false;
        }
        Customer.updateCurrentCustomer(customerId, active, customerNameField.getText(), 
                streetAddrField.getText(), streetAddrField2.getText(), 
                cityField.getText(), postalCodeField.getText(), 
                countryField.getText(), phoneField.getText(), currentUser.getUsername());
        
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
    
}
