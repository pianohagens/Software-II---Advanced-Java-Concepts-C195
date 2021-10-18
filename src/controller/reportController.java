package controller;

import dbConnect.dbAppointment;
import dbConnect.dbContact;
import dbConnect.dbReport;
import dbConnect.dbUsers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.appointmentModel;
import model.contactModel;
import model.userModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *  FXML Controller class
 *  Report Controller, this class allow users pull report by user, contact or month and type
 * @author Piano Hhagens
 */


public class reportController extends sceneController implements Initializable {
    public Label apptCountValue;
    public ComboBox<String> typeCombo;

    @FXML protected ComboBox<userModel> userCombo;
    private dbUsers loginDB;
    private ArrayList<String> userTable;

    @FXML private ComboBox<contactModel> contactCombo;
    private dbContact contactDB;
    private ArrayList<String>  contactTable;

    @FXML private TableColumn<appointmentModel, Integer> apptIDCol;
    @FXML private  TableColumn<appointmentModel, String> titleCol;
    @FXML private  TableColumn<appointmentModel, String> apptTypeCol;
    @FXML private  TableColumn<appointmentModel, String> descriptionCol;
    @FXML private  TableColumn<appointmentModel, Integer> apptDateCol;
    @FXML private  TableColumn<appointmentModel, Integer> startCol;
    @FXML private  TableColumn<appointmentModel, Integer> endCol;
    @FXML private  TableColumn<appointmentModel, Integer> customerIDCol;

    @FXML private TableColumn<appointmentModel, Integer> apptIDColU;
    @FXML private  TableColumn<appointmentModel, String> titleColU;
    @FXML private  TableColumn<appointmentModel, String> apptTypeColU;
    @FXML private  TableColumn<appointmentModel, String> descriptionColU;
    @FXML private  TableColumn<appointmentModel, Integer> apptDateColU;
    @FXML private  TableColumn<appointmentModel, Integer> startColU;
    @FXML private  TableColumn<appointmentModel, Integer> endColU;
    @FXML private  TableColumn<appointmentModel, Integer> customerIDColU;
    private ObservableList<appointmentModel> reportTable;
    @FXML private TableView<appointmentModel> reportTV;
    @FXML private TableView<appointmentModel> reportTVU;
    @FXML private ComboBox<String> monthCombo;


    /**
     * @param url resourceBundle
     * Initializes the ContactReport table, UserReport table objects, report by month and type objects.
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //pull report by select month and type combo box options
        typeCombo.setItems(dbAppointment.getType());
        monthCombo.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May",  "June", "July", "August", "September", "October", "November", "December"));

        this.populateContactCombo();
        this.populateLoginDB();
        //For the ContactReport table
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointments_id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));

        //For the UserReport table
        apptIDColU.setCellValueFactory(new PropertyValueFactory<>("Appointments_id"));
        titleColU.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptTypeColU.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionColU.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptDateColU.setCellValueFactory(new PropertyValueFactory<>("Date"));
        startColU.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endColU.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        customerIDColU.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));

    }

    /**      *
     * @param actionEvent
     * This method will close the app
     * */
    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * This method will populate the user combo box
     * */
    private void populateLoginDB() {
        this.userTable = new ArrayList();
        userCombo.setItems(FXCollections.observableArrayList(loginDB.getUsers()));
    }

    /**     *
     * @param actionEvent
     * Description: Lambda expressions locations:
     * This method will allow select a user from user combo box and than bring out appointment report that created by user
     */
    public void onUserReportCombo(ActionEvent actionEvent) {
        userModel user = userCombo.getValue();
        //check if user return
      if(user == null){
            return;
        }
        int userID = user.getUser_ID();
        //List all appointment base on userID
        ObservableList<appointmentModel> getApptListForUser = dbReport.getUserReport(dbUsers.User_ID);
        ObservableList<appointmentModel> filtedByUser = getApptListForUser.filtered(a ->{
            if(a.getUser_ID() == userID)
                return true;
            return false;
                });
        reportTVU.setItems(filtedByUser);
        }

    /**
     * @param actionEvent
     * This method will display month combo box
     * */
    public void onMonthCombo(ActionEvent actionEvent) {
        showReportByMonth();
    }

    /**
     * This method will bring out appointment count after both month combo box and type combo box are selected
     * */
    private void showReportByMonth() {
        String month = monthCombo.getValue();
        String type = typeCombo.getValue();
        if(month == null || type == null){
            return;
        }
        int count = dbReport.getApptCount(month, type);
        apptCountValue.setText(String.valueOf(count));
    }

    /**
     * @param actionEvent
     * This method will display type combo box
     * */
    public void onTypeCombo(ActionEvent actionEvent) {
        showReportByMonth();
    }

    /** This method will populate the contact Combo box
     * */
    private void populateContactCombo() {
        this.contactTable = new ArrayList();
        contactCombo.setItems(FXCollections.observableArrayList(contactDB.getAllContacts()));
    }

    /**
     * @param actionEvent
     * This method will allow select a contact from contact combo box and than bring out appointment report according selected contact
     * */
    public void onContactReportCombo(ActionEvent actionEvent) {
        contactModel contact = contactCombo.getValue();
        if(contact == null){
            return;
        }
        reportTV.setItems(dbReport.getContactReport(contact.getContact_ID()));
    }
}
