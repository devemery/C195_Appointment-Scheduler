/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Models.Appointment;
import Models.Customer;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
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
public class ModifyAppointmentController implements Initializable {

    @FXML
    private TextField apptIDField;
    @FXML
    private TextField customerSearchField;
    @FXML
    private Button customerSearchButton;
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
    private TextField currentCustomer;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField location;
    @FXML
    private TextField contact;
    @FXML
    private TextField type;
    @FXML
    private TextField urlField;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private Button custPageButton;
    @FXML
    private Button modifySaveButton;
    @FXML
    private Button modifyExitButton;

    /**
     * Initializes the controller class.
     */
    
    User currentUser;
    int appointmentId;
    Appointment currentAppt;
       
    public ModifyAppointmentController(User currentUser, int appointmentId) {
        this.currentUser = currentUser;
        this.appointmentId =appointmentId;        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentAppt = Appointment.getCurrentAppt(appointmentId);
        
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
        String custName = Customer.getCurrentCustomer(currentAppt.getCustId()).getCustomerName();
        
        apptIDField.setText(Integer.toString(appointmentId));
        currentCustomer.setText(custName);
        title.setText(currentAppt.getApptTitle());
        description.setText(currentAppt.getDescription());
        location.setText(currentAppt.getLocation());
        contact.setText(currentAppt.getContact());
        type.setText(currentAppt.getType());
        urlField.setText(currentAppt.getUrl());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStart = currentAppt.getStartTime().format(formatter);
        String formattedEnd = currentAppt.getEnd().format(formatter);
        
        start.setText(formattedStart);
        end.setText(formattedEnd);
    }   
    
    private void generateCustomerTable() throws SQLException {
        CustomerTable.setItems(Customer.getAllCusomers());
        CustomerTable.refresh();
    }

    @FXML
    private void handlerCustomerSearchButton(MouseEvent event) {
    }

    @FXML
    private void handlerSaveButton(MouseEvent event) throws IOException {
        Customer chosenCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        Timestamp startTime = Timestamp.valueOf(start.getText());
        Timestamp endTime = Timestamp.valueOf(end.getText());
        Appointment.updateCurrentAppt (appointmentId, chosenCustomer.getCustomerId(), title.getText(), 
            description.getText(), location.getText(), contact.getText(), 
            type.getText(), urlField.getText(), startTime, endTime);
        
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

    @FXML
    private void handlerExitButton(MouseEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handlerCustPage (MouseEvent event) throws IOException {
        
        int customerId = Customer.getCurrentCustomer(currentCustomer.getText());
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ModifyCustomer.fxml"));
        ViewController.ModifyCustomerController controller = new ViewController.ModifyCustomerController(currentUser, customerId);
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }
    
}
