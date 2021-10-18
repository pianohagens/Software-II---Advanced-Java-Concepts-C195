package controller;

import dbConnect.NewDBConnection;
import dbConnect.dbCustomer;
import dbConnect.dbReport;
import dbConnect.dbUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.appointmentModel;
import model.userModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  FXML Controller class
 * userController, this class allow users to enter user name and password, and then verify both user name and password.
 * <p>This method will also track user activity by recording all user log-in attempts, dates, and
 * time stamps and whether each attempt was successful in a file named login_activity.txt.
 * Append each new record to the existing file, and save to the root folder of the application.
 * @author Piano Hhagens
 *
 */

public class userController extends sceneController implements Initializable {
    @FXML private Label errorLabel;
    @FXML private PasswordField loginPasswordField;
    @FXML private TextField usernameField;
    @FXML private Label apptReminder;
    @FXML private Label userTimeZone;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;

    private ResourceBundle myBundle = ResourceBundle.getBundle("resource/language_file");
    /**
     *
     * @param url re
     * Initializes user login objects
     */
    @Override
    public void initialize(URL url, ResourceBundle re) {

        // Displays country name and zone Id on the user login form
        String Country_Name = locale.getDisplayCountry();
        String Zone_ID = ZoneId.systemDefault().getDisplayName(TextStyle.FULL, locale);
        userTimeZone.setText("Time Zone: " + Zone_ID + " - " + Country_Name);
        // get user name label translation
        userNameLabel.setText(myBundle.getString("userNameL"));
        passwordLabel.setText(myBundle.getString("passwordL"));
    }
    /**
     * @param e
     * This method will accepts a user ID and password and provides an appropriate error message
     * */
    @FXML public void onLogin(ActionEvent e) {
        String tryLoginUser = usernameField.getText();
        String tryPassword = loginPasswordField.getText();

         if (dbUsers.isValidUserName(tryLoginUser, tryPassword)) {
             logTriedUser(tryLoginUser, true);
             userModel.setLoggedUser(tryLoginUser);
            //List all appointment that created by user
             ObservableList<appointmentModel> getApptListForUser = dbReport.getUserReport(dbUsers.User_ID);
             boolean found = false;
             //Get all appointments into the loop
             for(appointmentModel a : getApptListForUser) {

                 //if appointment start time isAfter now and appointment start time isBefore now plus 15 minutes
                 if(a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                    //** Thi is the pop up alert window with the appointment ID
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setHeaderText("Up coming appointment # " + a.getAppointments_id());
                     alert.setContentText(a.getStart().toString());
                     Optional<ButtonType> result = alert.showAndWait();
                     found = true;
                 }
             };
             if(!found){
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setHeaderText("No up coming appointment. ");
                 alert.setContentText("");
                 Optional<ButtonType> result = alert.showAndWait();
             }
             this.toHomePage(e);
        }else {
             logTriedUser(tryLoginUser, false);
             errorLabel.setText(myBundle.getString("loginError"));
             errorLabel.setVisible(true);
         }
    }

    /**
     * @param tryLoginUser successful
     * If user logged in successful, do work
     * */
    private void logTriedUser(String tryLoginUser, boolean successful) {
        //get timestamp once user logged in
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String successOrNot = "unsuccessful";
        if (successful) {
            successOrNot = "successful";
        }
            // Write country name and zone Id with the login log file
            String Country_Name = locale.getDisplayCountry();
            String Zone_ID = ZoneId.systemDefault().getDisplayName(TextStyle.FULL, locale);
            userTimeZone.setText(Zone_ID + " - " + Country_Name);
            //String log Directory Path
            String logDirectory = loginFilePath();

            try {
                File directory = new File(logDirectory);
                if (!directory.exists()){
                    directory.mkdir();
                }

                //give login log a file name and file type as login_activity.txt
                File logfile = new File(logDirectory + "/login_activity.txt");
                logfile.createNewFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logfile,true))) {

                    //give format how and what to write into the log, name + time, zoneID + country
                    writer.write("User Name: " + tryLoginUser +  ", logged in " + successOrNot + " at " + timestamp.toString() + ". Time Zone: " + Zone_ID + " - " + Country_Name);
                    writer.newLine();
                }
            } catch (IOException ex) {
                System.out.println("Error occurred writing login log");
                Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * @param actionEvent
     * This method will clear all input fields when clicked
     * */
    public void onClearInput(ActionEvent actionEvent) {
        usernameField.setText("");
        loginPasswordField.setText("");
        errorLabel.setText("");
    }
}
