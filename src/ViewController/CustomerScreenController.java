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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author emery
 */

public class CustomerScreenController implements Initializable {

    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerAddrCol;
    @FXML
    private TableColumn<Customer, Boolean> activeCol;
    @FXML
    private Button customerSearchButton;
    @FXML
    private TextField customerSearchField;
    @FXML
    private Button customerAddButton;
    @FXML
    private Button customerDeleteButton;
    @FXML
    private Button customerDetailsButton;
    @FXML
    private Button customerExit;
    @FXML
    private Button apptButton;

    /**
     * Initializes the controller class.
     */
    
    User currentUser;
          
    public CustomerScreenController(User currentUser) {
        this.currentUser = currentUser;    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        customerAddrCol.setCellValueFactory(new PropertyValueFactory<>("CustomerAddr"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("CustomerActive"));
        
        try {
            generateCustomerTable();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void generateCustomerTable() throws SQLException {
        CustomerTable.setItems(Customer.getAllCusomers());
        CustomerTable.refresh();
    }

    @FXML
    private void handlerCustomerSearchButton(MouseEvent event) {
    }

    @FXML
    private void handlerCustomerAddButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AddCustomer.fxml"));
        ViewController.AddCustomerController controller = new ViewController.AddCustomerController(currentUser);
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }

    @FXML
    private void handlerCustomerDeleteButton(MouseEvent event) throws SQLException {
        Customer CustomerToRemove = CustomerTable.getSelectionModel().getSelectedItem();
        System.out.println(CustomerToRemove.getCustomerId());
        Customer.deleteCustomer(CustomerToRemove.getCustomerId());
        generateCustomerTable();
    }

    @FXML
    private void handlerCustomerDetailsButton(MouseEvent event) throws IOException {
        Customer customerToModify = CustomerTable.getSelectionModel().getSelectedItem();
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ModifyCustomer.fxml"));
        ViewController.ModifyCustomerController controller = new ViewController.ModifyCustomerController(currentUser, customerToModify.getCustomerId());
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }

    @FXML
    private void handlerCustomerExit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    private void handlerApptScreen(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AppointmentScreen.fxml"));
        ViewController.AppointmentScreenController controller = new ViewController.AppointmentScreenController(currentUser);
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }
    
}
