package controller;

import dbConnect.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.appointmentModel;
import model.customerModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.now;

/**
 *  FXML Controller class
 *  Homepage Controller, the dashboad of the application. It allow to cancel appointment, delete Customers record, and provide option to pages of Customer, Appointment or Report
 * @author Piano Hhagens
 */
public class homepageController extends sceneController implements Initializable {
   private ObservableList<customerModel> customerDBtable;
   private ObservableList<appointmentModel> appointmentTable;

   @FXML private  TableView<customerModel> customerTV;
   @FXML private  TableView<appointmentModel> appointmentTV;
   @FXML private  TableColumn<customerModel, Integer> customerIDColCust;
   @FXML private  TableColumn<customerModel, String> customerNameCol;
   @FXML private  TableColumn<customerModel, String> divisionCol;
   @FXML private  TableColumn<customerModel, Integer> phoneCol;

    @FXML private  TableColumn<customerModel, Integer> customerIDColAppt;
    @FXML private  TableColumn<appointmentModel, String> apptTypeCol;
    @FXML private  TableColumn<appointmentModel, String> descriptionCol;
    @FXML private  TableColumn<appointmentModel, String> startCol;
    @FXML private  TableColumn<appointmentModel, String> endCol;
    @FXML private  TableColumn<appointmentModel, String> locationCol;
    @FXML private Label deleteCustAlert;
    @FXML private Label apptReminder;

    /**
     * @param url resourceBundle
     * Initializes the Appointment table and Customer Table objects briefly, and up coming appointment reminder.
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.deleteCustAlert.setText("");
        this.apptReminder.setText("");
         this.populateCustomerTable();
         this.populateApptmentTable();

    /**
     * Lambda expressions locations:
     * Use lambda expression to list all appointments in order to determine if any appointment is upcoming.
     * Display appointment reminder 15 minute before the appointment
     */

        ObservableList<appointmentModel> getApptListForUser = dbReport.getUserReport(dbUsers.User_ID);

        getApptListForUser.forEach(appList -> {

            if(appList.getStart().isAfter(now()) && (appList.getStart().isBefore(now().plusMinutes(15)))) {
                apptReminder.setText("Alert: 15 minutes appointment reminder has been delivered!");
            }
        });
    }

    /**
     * This method will display appointment table
     * */
    private void populateApptmentTable() {
        appointmentTable = FXCollections.observableArrayList(dbAppointment.getAllappointments("all"));
        appointmentTV.getItems().setAll(appointmentTable);
        customerIDColAppt.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    }

    /**
     * This method will display customer table
     * */
    private void populateCustomerTable() {
    customerDBtable = FXCollections.observableArrayList(dbCustomer.getAllCustomers());
    customerTV.getItems().setAll(customerDBtable);
    customerIDColCust.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));
    customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    divisionCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
    phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
   }

    /**
     * @param actionEvent
     * This method will delete an customer. It displays confirmation and alert before sending delete query to the database
     * */
    @FXML public void onDeleteCustomer(ActionEvent actionEvent) {
        customerModel selectCustomer = customerTV.getSelectionModel().getSelectedItem();
        int customer_id = 0;
        try {
            //if Appointment selected, pop up a delete confirmation window with appointmentID and customerID
            customer_id = selectCustomer.getCustomer_id();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You have selected customer of " + selectCustomer.getCustomerName());
            alert.setContentText("Are you sure wish to delete from the system?");
            //Wait till hit Ok, then deleted it from the db system
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    dbCustomer.deleteCustomer(customer_id);
                    populateCustomerTable();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent
     * This method will close the app
     * */
    @FXML protected void onExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param actionEvent     *
     * This method will delete an appointment. It displays confirmation and alert before sending delete query to the database
     * */
    public void onDeleteAppt(ActionEvent actionEvent) {
        appointmentModel selectAppt = appointmentTV.getSelectionModel().getSelectedItem();
        int Appointments_id = 0;
        int Customer_ID = 0;
        //if Appointment selected, pop up a delete confirmation window with appointmentID and customerID
        try {
            Appointments_id = selectAppt.getAppointments_id();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You have selected customer of " + selectAppt.getCustomer_id()  + " ApptID: " + selectAppt.getAppointments_id());
            alert.setContentText("Are you sure wish to cancel this appointment?");
            //Wait till hit Ok, then deleted it from the db system
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK){
                try {
                    dbAppointment.deleteAppt(Appointments_id, Customer_ID);
                    populateCustomerTable();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
