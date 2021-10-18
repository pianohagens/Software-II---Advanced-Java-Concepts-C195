package controller;

import dbConnect.NewDBConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  FXML Controller class
 *  Main Controller, this is a user login form, where the app start, and set default login language as French
 * @author Piano Hhagens
 */

public class mainController extends Application {

    /** This method will open up the app by user login */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
        primaryStage.setTitle("Welcome Appointment Scheduler");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //set default login language as French
        Locale.setDefault(new Locale("en"));
        // This method will start connection to database
        NewDBConnection.startConnection();
        launch(args);
    }
}
