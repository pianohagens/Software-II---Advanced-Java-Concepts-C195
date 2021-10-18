package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *  FXML Controller class
 *  This is scene controller, Switches between scenes when user click buttons.
 * @author Piano Hhagens
 */

//Initializes initialize locale property bundle and Switch between scenes.
public class sceneController {
    //Initializes initialize locale property bundle.
    public static Locale locale = Locale.getDefault();
    protected final Properties property = getProperty(locale);

    //Initial the absolute path for the login log file
    protected String loginFilePath() { return new File("src/loginLog").getAbsolutePath();}

    /**
     * @param language
     * Description: This method will get property from resource bundle
     * @return returnProperty
     * */
    protected Properties getProperty(Locale language) {
        Properties returnProperty = new Properties();
        try {
            ResourceBundle res = ResourceBundle.getBundle("resource/language_file", language);
            res.keySet().stream().forEach(k -> returnProperty.put(k, res.getString(k)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnProperty;
    }
    /*-------------------------------------------------------
    -----------------------Switch between scenes---------------------
    --------------------------------------------------------*/
    // Description: This method is the parent pane. Run this function when user logged in or clicked Home button of the app */
    @FXML protected void toHomePage(ActionEvent e){
        try {
            Parent pane = loadHomePage();
            switchScene(pane, e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // This method will load home page from user login page */
    protected Parent loadHomePage() throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("/view/homePage.fxml"));
        return pane;

    }
    // This method will switch scenes from page to page */
    protected void switchScene(Parent pane, ActionEvent e) {
        Scene switchScene = new Scene(pane);
        Stage stage = this.getStageInfo(e);
        stage.setScene(switchScene);
        stage.show();
    }
    // This method will bring the app to a stage */
    private Stage getStageInfo(ActionEvent e) {
        Stage stage = (Stage) this.getSceneInfo(e).getWindow();
        return stage;
    }
    // This method will trig the fx scene feature */
    protected Scene getSceneInfo(ActionEvent e) {
        Scene stage = (Scene)((Node)e.getSource()).getScene();
        return stage;
    }
    // This method will let user sign out and login again with different user as need */
    @FXML protected void onLogout(ActionEvent e)throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
        this.switchScene(pane, e);
    }

    // This method will load customer record page */
    @FXML protected void onLoadCustomerTable(ActionEvent actionEvent){
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
            this.switchScene(pane, actionEvent);
        } catch (IOException ex) {
        }
    }
    // This method will load appointment app page */
    @FXML protected void onLoadAppointment(ActionEvent e) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        this.switchScene(pane, e);
    }

    // This method will bring user back to home page */
    @FXML protected void onBackToHomePage(ActionEvent e)throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("/view/homePage.fxml"));
        this.switchScene(pane, e);
    }
    // This method will load reports page */
    @FXML protected void onLoadReports(ActionEvent e)throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        this.switchScene(pane, e);
    }
}
