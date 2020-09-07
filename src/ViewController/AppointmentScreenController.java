/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Models.Appointment;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author emery
 */
public class AppointmentScreenController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> apptDateCol;
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    private Button apptAddButton;
    @FXML
    private Button apptDeleteButton;
    @FXML
    private Button apptDetailsButton;
    @FXML
    private RadioButton allRadioButton;
    @FXML
    private RadioButton monthRadioButtom;
    @FXML
    private ToggleGroup Calendar;
    @FXML
    private RadioButton weekRadioButton;
    @FXML
    private Button mainScreenExit;
           
    User currentUser;
                   
    public AppointmentScreenController(User currentUser) {
        this.currentUser = currentUser;    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.Calendar.selectToggle(this.allRadioButton);
        
        // Initialize Table Columns
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        
        try {
            generateApptTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    private void generateApptTable() throws SQLException {
        AppointmentTable.setItems(Appointment.getAllAppts(currentUser.getUserId()));
        AppointmentTable.refresh();
    }
    
    private void generateMonthlyApptTable() throws SQLException {
        AppointmentTable.setItems(Appointment.getMonthlyAppts(currentUser.getUserId()));
        AppointmentTable.refresh();
    }
    
    private void generateWeeklyApptTable() throws SQLException {
        AppointmentTable.setItems(Appointment.getWeeklyAppts(currentUser.getUserId()));
        AppointmentTable.refresh();
    }
    
    @FXML
    private void handlerApptAddButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AddAppointment.fxml"));
        ViewController.AddAppointmentController controller = new ViewController.AddAppointmentController(currentUser);
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }

    @FXML
    private void handlerApptDeleteButton(MouseEvent event) throws SQLException {
        Appointment apptToRemove = AppointmentTable.getSelectionModel().getSelectedItem();
        System.out.println(apptToRemove.getApptId());
        Appointment.deleteAppt(apptToRemove.getApptId());
        generateApptTable();
    }

    @FXML
    private void handlerApptDetailsButton(MouseEvent event) throws IOException {
        Appointment currentAppt = AppointmentTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ModifyAppointment.fxml"));
        ViewController.ModifyAppointmentController controller = new ViewController.ModifyAppointmentController(currentUser, currentAppt.getApptId());
        loader.setController(controller);
        Parent root = loader.load(); //marries view to controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false); //sets screen to be resizable
        stage.show(); //show view
    }
    
    @FXML
    private void handlerCustomerScreen (MouseEvent event) throws IOException {
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
    private void handlerAllRadio (MouseEvent event) {
        try {
            generateApptTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
    @FXML
    private void handlerMonthRadio(MouseEvent event) {
        try {
            generateMonthlyApptTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void handlerWeekRadio(MouseEvent event) {
        try {
            generateWeeklyApptTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handlerMainScreenExit(MouseEvent event) {
        Platform.exit();
    }
    
}
