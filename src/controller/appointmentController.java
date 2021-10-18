package controller;

import dbConnect.dbAppointment;
import dbConnect.dbContact;
import dbConnect.dbCustomer;
import dbConnect.dbUsers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * FXML Controller class
 * appointmentController add/update/cancel appointments
 * @author Piano Hhagens
 */
public class appointmentController extends sceneController implements Initializable {
    @FXML protected ComboBox<userModel> userCombo;
    private ArrayList<String> userTable;

    @FXML private  ComboBox<customerModel> customerCombo;
    private ArrayList<Integer>  customerTable;

    @FXML private ComboBox<contactModel> contactCombo;
    private ArrayList<String>  contactTable;

    public Button saveApptB;
    private appointmentModel apptToEdit = null;

    private ObservableList<appointmentModel> appointmentTable;
    @FXML private  TableView<appointmentModel> appointmentTV;
    @FXML private  TableColumn<appointmentModel, Integer> apptIDCol;
    @FXML private  TableColumn<appointmentModel, String> titleCol;
    @FXML private  TableColumn<appointmentModel, String> descriptionCol;
    @FXML private  TableColumn<appointmentModel, String> locationCol;
    @FXML private  TableColumn<appointmentModel, String> contactCol;
    @FXML private  TableColumn<appointmentModel, String> apptTypeCol;
    @FXML private  TableColumn<appointmentModel, Integer> apptDateCol;
    @FXML private  TableColumn<appointmentModel, Integer> startCol;
    @FXML private  TableColumn<appointmentModel, Integer> endCol;
    @FXML private  TableColumn<appointmentModel, Integer> customerIDColAppt;
    @FXML private  DatePicker apptDatePicker;

    @FXML private TextField apptIDTF;
    @FXML private TextField locaTF;
    @FXML private TextField typeTF;
    @FXML private TextField titleTF;
    @FXML private TextField descriTF;

    @FXML private ComboBox<String> startHrCombo;
    @FXML private ComboBox<String> startMinCombo;
    @FXML private ComboBox<String> ampmSTART;
    @FXML private ComboBox<String> endHrCombo;
    @FXML private ComboBox<String> endMinCombo;
    @FXML private ComboBox<String> ampmEND;

    @FXML private ToggleGroup selectedRadioButton;
    @FXML private Label inValidInputMSG;
    @FXML private Label onEditingMSG;
    @FXML private Label busHoursMSG;
    @FXML private Label overLapMSG;


    /*
     * Initializes the Appointment table objects.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Display appointment table without sorting - all
        appointmentTV.setItems(dbAppointment.getAllappointments("all"));

        //Set appointment options 1-12, am or pm
        startHrCombo.setItems(FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        startMinCombo.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        ampmSTART.setItems(FXCollections.observableArrayList("am", "pm"));

        endHrCombo.setItems(FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        endMinCombo.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        ampmEND.setItems(FXCollections.observableArrayList("am", "pm"));

        this.populateLoginDB();
        this.populateCustomerCombo();
        this.populateContactCombo();
        this.populateApptEditFields();
        this.populateApptmentTable("all");
    }
    /**
     * This method will sort appointment view by month, week, and all
     * <p>There three radiobutton appear about of the appointment table, all, week, and month.
     * This function default display all appointments when appointment page launch. When the
     * user pressed week, the appointment table only display current week appointments; and when the
     * user pressed month, the appointment table will display current month appointments; and when the
     * user pressed all, the appointment table will display all appointments.     *
     */

    @FXML protected void onViewFilterRB() {
        RadioButton selectedRB = (RadioButton) selectedRadioButton.getSelectedToggle();
        String groupRadioButton = selectedRB.getText();
        //sorted View Value by all, week or month
        String sortedViewValue = "all";
        switch (groupRadioButton){
            case "Week View":
                sortedViewValue = "week";
                break;
            case "Month View":
                sortedViewValue = "month";
                break;
            default:
                break;
        }
        populateApptmentTable(sortedViewValue);
    }

    /** This method will populate the contact Combo box */
    public void populateContactCombo() {
        this.contactTable = new ArrayList();
        contactCombo.setItems(FXCollections.observableArrayList(dbContact.getAllContacts()));
    }

    /**
     * This method will populate the customer Combo box
     * */
    private void populateCustomerCombo() {
        this.customerTable = new ArrayList();
        customerCombo.setItems(FXCollections.observableArrayList(dbCustomer.getAllCustomers()));
    }

    /** This method will populate the user Combo box */
    private void populateLoginDB() {
        this.userTable = new ArrayList();
        userCombo.setItems(dbUsers.getUsers());
    }

    /** This method is to provide time pattern */
    private LocalTime timeFormatter(String timePattern) {
        return LocalTime.parse(timePattern.toUpperCase(), DateTimeFormatter.ofPattern("hh:mm a", Locale.US));
    }

    /**
     * @param pickTime
     * Description: This method will collect time input(hours, minute, am or pm) from combo boxes and put into one string like StartTime or EndTime
     * @return timeFormatter(forTime)
     * */
    protected LocalTime getPickAtime(String pickTime) {
        String hrCombo = "";
        String minCombo = "";
        String amORpm = "";

        switch (pickTime) {
            case "Start":
                hrCombo = startHrCombo.getValue().toString();
                minCombo = startMinCombo.getValue().toString();
                amORpm = ampmSTART.getValue().toString();
                break;
            case "End":
                hrCombo = endHrCombo.getValue().toString();
                minCombo = endMinCombo.getValue().toString();
                amORpm = ampmEND.getValue().toString();
                break;
            default:
                break;
        }
        String forTime = hrCombo + ":" + minCombo + " " + amORpm;
        return this.timeFormatter(forTime);
    }

    /**
     * @param filtedAppt
     * Description: This method will display the appointment table
     *
     * */
    private void populateApptmentTable(String filtedAppt) {
        appointmentTable = FXCollections.observableArrayList(dbAppointment.getAllappointments(filtedAppt));
        appointmentTV.getItems().setAll(appointmentTable);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointments_id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        customerIDColAppt.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));
    }

    /**
     * @param actionEvent
     * Description: This method will  clear all input when clicked
     * */
    public void onClearInput(ActionEvent actionEvent) {
        apptIDTF.setText("");
        locaTF.setText("");
        typeTF.setText("");
        titleTF.setText("");
        descriTF.setText("");
        onEditingMSG.setText("");
        inValidInputMSG.setText("");
        busHoursMSG.setText("");
        overLapMSG.setText("");
        userCombo.setValue(null);
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        apptDatePicker.setValue(null);
        startHrCombo.setValue(null);
        startMinCombo.setValue(null);
        ampmSTART.setValue(null);
        endHrCombo.setValue(null);
        endMinCombo.setValue(null);
        ampmEND.setValue(null);
    }

    /**
     * @param actionEvent
     * Description: This method will close the appointment app
     *
     * */
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param
     * @return valid
     * Description: This method will validate each input and display a custom message specific for each error input of appointment interface
     * */
    private boolean validated() {
        inValidInputMSG.setText("");
        boolean valid = true;
        try {
            if(descriTF.getText().equals("")){
                inValidInputMSG.setText("Description must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(titleTF.getText().equals("")){
                inValidInputMSG.setText("Title must be filled.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(typeTF.getText().equals("")){
                inValidInputMSG.setText("Type must be filled.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(locaTF.getText().equals("")){
                inValidInputMSG.setText("Location must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(ampmEND.getValue() == null){
                inValidInputMSG.setText("Please select appointment end am or pm.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(endMinCombo.getValue() == null){
                inValidInputMSG.setText("Please select appointment end minute.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(endHrCombo.getValue() == null){
                inValidInputMSG.setText("Please select appointment end hour.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(ampmSTART.getValue() == null){
                inValidInputMSG.setText("Please select appointment start am or pm.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(startMinCombo.getValue() == null){
                inValidInputMSG.setText("Please select appointment start minute.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(startHrCombo.getValue() == null){
                inValidInputMSG.setText("Please select appointment start hour.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(apptDatePicker.getValue() == null){
                inValidInputMSG.setText("Please select a date.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }

            if(contactCombo.getValue() == null){
                inValidInputMSG.setText("Please select a contact.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(customerCombo.getValue() == null){
                inValidInputMSG.setText("Please select a customer.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(userCombo.getValue() == null){
                inValidInputMSG.setText("Please select a user.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
        } catch (Exception ex) {
            valid = false;
        }
        return valid;
    }

     /**
     * @param actionEvent
      * Description: When user hit save button, this method will save all input data to the database creating a new appointment or editing an appointment
      * <p>If the user hit save with one or more empty field, or without select an appointment to edit,
      * it will display an appropriate complete input message base on each field.
     */
    public void onSaveApptButton(ActionEvent actionEvent) {
        if(!validated()){
            return;
        }
        userModel userID = userCombo.getValue();
        customerModel Customer = customerCombo.getValue();

        LocalDate apptDateValue = apptDatePicker.getValue();
        LocalTime startSQL = this.getPickAtime("Start");
        LocalTime endSQL = this.getPickAtime("End");

        LocalDateTime Start = LocalDateTime.of(apptDateValue, startSQL);
        LocalDateTime End = LocalDateTime.of(apptDateValue, endSQL);

        //check business hours
        if(!isBusinessHour(Start, End)){
            //If check business hour return false, display business hour range message.
            busHoursMSG.setText("Please select our business hours from 8:00 a.m. to 10:00 p.m. EST!");
            return;
        }
        int Appointment_ID = 0;
        if(apptToEdit != null){
            Appointment_ID = apptToEdit.getAppointments_id();
        }
        //check if any overlapping
        if(overLapAppt(Start, End, Customer.getCustomer_id(), Appointment_ID)){
            //If check overlapping return false, display overLap occurred message.
            overLapMSG.setText("Overlap appointment occurred!");
            return;
        }
        String Location = locaTF.getText();
        contactModel Contact = contactCombo.getValue();
        String Type = typeTF.getText();
        String Title = titleTF.getText();
        String Description = descriTF.getText();

        //If selected appointment edit, write all entries in to editAppointment.
        if(apptToEdit != null){
            dbAppointment.editAppointment(apptToEdit.getAppointments_id(), Title, Description, Location, Type, Start, End, Customer.getCustomer_id(), userID.getUser_ID(), Contact.getContact_ID());
            //If no selected appointment, write all entries as an new addAppointment.
        }else {
            dbAppointment.addAppointment(Title, Description, Location, Type, Start,  End, Customer.getCustomer_id(), userID.getUser_ID(), Contact.getContact_ID());
        }
        //Display to the appointment table and clear the edit field after hit save
        apptToEdit = null;
        populateApptEditFields();
        appointmentTV.setItems(dbAppointment.getAllappointments("all"));
        onEditingMSG.setText("");
    }

    /**
     * @param Start End Customer_ID Appointment_ID
     * @return false
     * Description: This method will check if any overlap occur
     * */
    private boolean overLapAppt(LocalDateTime Start, LocalDateTime End, int Customer_ID, int Appointment_ID) {
         //get appointment list for customerID
        ObservableList<appointmentModel> appointmentList = dbAppointment.getAllappointments("all");
        for(appointmentModel a : appointmentList){
            //do overlap check if not same customerID
            if(a.getCustomer_id() != Customer_ID){
                continue;
            }
            //do overlap check if same appointmentID
            if(a.getAppointments_id() == Appointment_ID){
                continue;
            }
            //1.If appointment start time is after Start or appointment start time is equal to Start and appointment start time is before End
            if((a.getStart().isAfter(Start) || a.getStart().isEqual(Start)) && a.getStart().isBefore(End)){
                return true;
            }
            //2. if appointment end time is after Start and appointment end time is before or equal to End
            if(a.getEnd().isAfter(Start) && (a.getEnd().isBefore(End) || a.getEnd().isEqual(End))){
                return true;
            }
            // 3. if appointment start time is before or equal to Start and  appointment end time is after or equal to End
            if((a.getStart().isBefore(Start) || a.getStart().isEqual(Start)) && a.getEnd().isAfter(End) || a.getEnd().isEqual(End)){
                return true;
            }
        }
        return false;
  }

    /**
     * @param Start, End
     * @return true
     * Description: This method will scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. EST, including weekends
     * */
    private boolean isBusinessHour(LocalDateTime Start, LocalDateTime End) {

        //convert 8am EST to system default start
        ZonedDateTime start8amEST = ZonedDateTime.of(Start.toLocalDate(), LocalTime.of(8, 00), ZoneId.of("America/New_York"));//EST
        ZonedDateTime startLocal = start8amEST.withZoneSameInstant(ZoneId.systemDefault());

        //convert 10 pm EST to system default end
        ZonedDateTime end10pmEST = ZonedDateTime.of(End.toLocalDate(), LocalTime.of(22, 00), ZoneId.of("America/New_York"));//EST
        ZonedDateTime endLocal = end10pmEST.withZoneSameInstant(ZoneId.systemDefault());

        //if startTime is before (8am) system default start, return false
        if(Start.toLocalTime().isBefore(startLocal.toLocalTime())) return false;

        //if endTime is after (10pm) system default end, return false
        if(End.toLocalTime().isAfter(endLocal.toLocalTime())) return false;

        //if startTime is after (10pm)  system default end, return false
        if(Start.toLocalTime().isAfter(endLocal.toLocalTime())) return false;

        //if endTime is before (8am) system default start, return false
        if(End.toLocalTime().isBefore(startLocal.toLocalTime())) return false;

        return true;
    }

    /**
     * @param actionEvent
     * Description: This method will cancel an appointment. It displays confirmation and alert before sending delete query to the database
     *
     * */
    public void onCancelAppt(ActionEvent actionEvent) {
        appointmentModel selectAppt = appointmentTV.getSelectionModel().getSelectedItem();
        if (selectAppt == null) return;

        //if Appointment selected, pop up a delete confirmation window with appointmentID and customerID
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You have selected Appointment ID: " + selectAppt.getAppointments_id() + " and Customer ID: "  + selectAppt.getCustomer_id());
        alert.setContentText("Are you sure wish to delete it from the system?");
        //Wait till hit Ok, then deleted it from the db system
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dbAppointment.deleteAppt(selectAppt.getAppointments_id(), selectAppt.getCustomer_id());
            populateApptmentTable("all");
        }
        appointmentTable = FXCollections.observableArrayList(dbAppointment.getAllappointments("all"));
        appointmentTV.getItems().setAll(appointmentTable);
    }

    /**
     * @param actionEvent
     * Description: This method will display which appointment is editing will store data that make changed
     *
     * */
    public void onEdit(ActionEvent actionEvent) {
        apptToEdit = appointmentTV.getSelectionModel().getSelectedItem();

        if(apptToEdit != null){
            //Display which appointment is editing with the appointmentID
            onEditingMSG.setText("Appointments_ID: " + apptToEdit.getAppointments_id() + " is editing.....");

            //Pull all fields of the appointment to get ready for any change
            apptIDTF.setText(Integer.toString(apptToEdit.getAppointments_id()));
            titleTF.setText(apptToEdit.getTitle());
            descriTF.setText(apptToEdit.getDescription());
            locaTF.setText(apptToEdit.getLocation());
            typeTF.setText(apptToEdit.getType());

            apptDatePicker.setValue(apptToEdit.getStart().toLocalDate());

            startHrCombo.setValue(apptToEdit.getStartHH());
            startMinCombo.setValue(apptToEdit.getStartMM());
            ampmSTART.setValue(apptToEdit.getStartA());

            endHrCombo.setValue(apptToEdit.getEndHH());
            endMinCombo.setValue(apptToEdit.getEndMM());
            ampmEND.setValue(apptToEdit.getEndA());

            // for(customers)
            for(customerModel custom : customerCombo.getItems()){
                customerCombo.setValue(custom);
            }
            //for (contacts)
            for (contactModel contact : contactCombo.getItems()){
                contactCombo.setValue(contact);
            }
            //for (users)
            for (userModel user : userCombo.getItems()){
                userCombo.setValue(user);
            }
        }else {
                //Displays select require message, if hit edit button without select an appointment,
                onEditingMSG.setText("Please select an appointment to edit!");
            }
    }

    /**
     * @param
     * Description: This method will pull out appointment to edit
     *
     * */
    private void populateApptEditFields() {
        if(apptToEdit == null){
            apptIDTF.setText("");
            titleTF.setText("");
            descriTF.setText("");
            locaTF.setText("");
            typeTF.setText("");
            apptDatePicker.setValue(null);
            startHrCombo.setValue(null);
            startMinCombo.setValue(null);
            ampmSTART.setValue(null);
            endHrCombo.setValue(null);
            endMinCombo.setValue(null);
            ampmEND.setValue(null);
            contactCombo.setValue(null);
            customerCombo.setValue(null);
            userCombo.setValue(null);
        }else {
            apptIDTF.setText(Integer.toString(apptToEdit.getAppointments_id()));
            titleTF.setText(apptToEdit.getTitle());
            descriTF.setText(apptToEdit.getDescription());
            locaTF.setText(apptToEdit.getLocation());
            typeTF.setText(apptToEdit.getType());

            apptDatePicker.setValue(apptToEdit.getStart().toLocalDate());

            startHrCombo.setValue(apptToEdit.getStartHH());
            startMinCombo.setValue(apptToEdit.getStartMM());
            ampmSTART.setValue(apptToEdit.getStartA());

            endHrCombo.setValue(apptToEdit.getEndHH());
            endMinCombo.setValue(apptToEdit.getEndMM());
            ampmEND.setValue(apptToEdit.getEndA());

            // for(customers)
            for(customerModel custom : customerCombo.getItems()){
                customerCombo.setValue(custom);
            }
            //for (contacts)
            for (contactModel contact : contactCombo.getItems()){
                contactCombo.setValue(contact);
            }
            //for (users)
            for (userModel user : userCombo.getItems()){
                userCombo.setValue(user);
            }
        }
    }
}
