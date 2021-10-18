package controller;

import dbConnect.dbCountries;
import dbConnect.dbCustomer;
import dbConnect.dbFirstLevelDiv;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.customerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 *  FXML Controller class
 *  Customer Controller add/update/cancel Customers
 *  @author Piano Hhagens
 */


public class customerController extends sceneController implements Initializable {
    @FXML private TableColumn<customerModel, Integer> custmIdCol;
    @FXML private TableColumn<customerModel, String> custmNameCol;
    @FXML private TableColumn<customerModel, Integer> phoneCol;
    @FXML private TableColumn<customerModel, String> addressCol;
    @FXML private TableColumn<customerModel, String> postCol;
    @FXML private TableColumn<customerModel, String> fldCol;
    @FXML private TableColumn<customerModel, String> countryCol;

    @FXML protected TextField customerNameTF;
    @FXML protected TextField zipCodeTF;
    @FXML protected TextField addressTF;
    @FXML protected TextField phoneTF;
    @FXML protected TextField custmIDTF;

    @FXML protected ComboBox<String> countryCombo;
    private dbCountries countryDB;
    private ArrayList<String> countryTable;

    @FXML protected ComboBox<String> diviCombo;
    private dbFirstLevelDiv diviDB;
    private ArrayList<String> diviTable;

    private dbCustomer customerDB;
    private ObservableList<customerModel> customerTable;
    @FXML private TableView<customerModel> customerTV;
    @FXML private Label onEditingMasage; //
    @FXML private Label inValidInputMSG;

    private customerModel customerToModify = null;

    /**
     * Description: Initializes the Customer table objects.
     * @param url rb
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {//
        onEditingMasage.setText("");

        try {
            this.populateCustomerTable();
            this.populateCountryCombo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: This method will populate the customer table
     *
     * */
    @FXML protected void populateCustomerTable() {
        customerTable = FXCollections.observableArrayList(customerDB.getAllCustomers());
        customerTV.getItems().setAll(customerTable);
        custmIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));//
        custmNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));//relate to the getter OF THE MODEL not the database
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));//
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));//
        postCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        fldCol.setCellValueFactory(new PropertyValueFactory<>("Division"));//
        countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));//
    }

    /**
     * @param e
     * Description: This method will delete an customer. It displays confirmation and alert before sending delete query to the database
     * */
    @FXML public void onDeleteCustomer(ActionEvent e) {
        customerModel selectCustomer = customerTV.getSelectionModel().getSelectedItem();
        int Customer_ID = 0;
        try {
            //if customer selected, pop up a delete confirmation window with customerID
            Customer_ID = selectCustomer.getCustomer_id();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You have selected customer of " + selectCustomer.getCustomerName() + ", ID: " + selectCustomer.getCustomer_id());
            alert.setContentText("Are you sure wish to cancel this Customer?");
            //Wait till hit Ok, then deleted it from the db system
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    customerDB.deleteCustomer(Customer_ID);
                    populateCustomerTable();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Description: This method will populate customer edit fields
     *
     * */
    private void populateCustomerEditField() {
        if(customerToModify == null){
            custmIDTF.setText("");
            customerNameTF.setText("");
            phoneTF.setText("");
            addressTF.setText("");
            zipCodeTF.setText("");
            countryCombo.setValue(null);
            diviCombo.setValue(null);
        }else {
            custmIDTF.setText(Integer.toString(customerToModify.getCustomer_id()));
            customerNameTF.setText(customerToModify.getCustomerName());
            phoneTF.setText(customerToModify.getPhone());
            addressTF.setText(customerToModify.getAddress());
            zipCodeTF.setText(customerToModify.getPostalCode());
            diviCombo.setValue(customerToModify.getDivision());
            countryCombo.setValue(customerToModify.getCountry());
        }
    }

    /**
     * @param actionEvent
     * Description: This method will  clear all input when clicked
     * */
    @FXML protected void onClearInput(ActionEvent actionEvent) {
        customerNameTF.setText("");
        phoneTF.setText("");
        zipCodeTF.setText("");
        addressTF.setText("");
        custmIDTF.setText("");
        diviCombo.getSelectionModel().clearSelection();
        countryCombo.getSelectionModel().clearSelection();
        onEditingMasage.setText("");
    }

    /**
     * @param actionEvent
     * Description: This method will close the app
     * */
    @FXML protected void onExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * This method will populate country combo box
     * */
    @FXML protected void populateCountryCombo() {
        this.countryTable = new ArrayList();
        countryCombo.setItems(FXCollections.observableArrayList(countryDB.getAllCountries()));
    }

    /**
     * This method will allow select a country from country combo box and than bring out divisions according selected country
     * */
    @FXML protected void onCountryCombo() {
        this.diviTable = new ArrayList();
        String selectedCountry = (String)countryCombo.getSelectionModel().getSelectedItem();
        diviCombo.setItems(FXCollections.observableArrayList(countryDB.getCountryDivisions(selectedCountry)));
    }

    /**
     * Description: This method will validate each input and display a custom message specific for each error input of customer interface
     * @return valid
     * */
    private boolean validated() {
        inValidInputMSG.setText("");
        boolean valid = true;
        try {
            if(countryCombo.getValue() == null || diviCombo.getValue() == null){
                inValidInputMSG.setText("Please select a country and a division.");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(zipCodeTF.getText().equals("")){
                inValidInputMSG.setText("Zipcode must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(addressTF.getText().equals("")){
                inValidInputMSG.setText("The address must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(phoneTF.getText().equals("")){
                inValidInputMSG.setText("Phone must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
            if(customerNameTF.getText().equals("")){
                inValidInputMSG.setText("Customer name must be filled");
                inValidInputMSG.setVisible(true);
                valid = false;
            }
        } catch (Exception ex) {
            valid = false;
        }
        return valid;
    }

    /**
     * Description: This method will display which customer record is editing will store data that make changed
     *
     *
     * */
    public void onEditCustomer() {
        customerToModify = customerTV.getSelectionModel().getSelectedItem();
        onEditingMasage.setText("");
        inValidInputMSG.setText("");

            if(customerToModify != null) {
            onEditingMasage.setText("Customer_ID: " + customerToModify.getCustomer_id() + " is on editing .....");
            custmIDTF.setText(Integer.toString(customerToModify.getCustomer_id()));
            customerNameTF.setText(customerToModify.getCustomerName());
            phoneTF.setText((customerToModify.getPhone()));
            addressTF.setText(customerToModify.getAddress());
            zipCodeTF.setText(customerToModify.getPostalCode());
            diviCombo.setValue(customerToModify.getDivision());
            countryCombo.setValue(customerToModify.getCountry());
        }else {
            onEditingMasage.setText("Please select a customer to edit!");
        }
    }

     /**
     *  @param e
      * Description: When user hit save button, this method will save all input data to the database creating a new customer record or editing a customer record
      * <p>If the user hit save with one or more empty field, or without select an customer to edit,
      * it will display an appropriate complete input message base on each field.
      *
     */
    @FXML protected void onSaveCustomer(ActionEvent e) {
        if(!validated()){
            return;
        }
        String Customer_Name = customerNameTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        String postco = zipCodeTF.getText();

        int division_id =0;
        String diviName = diviCombo.getValue();

        if (diviName != null){
            division_id = diviDB.getDivisionID(diviName);
        }
        if(customerToModify != null){
            customerDB.updateCustomer(customerToModify.getCustomer_id(), Customer_Name, address, postco, phone, division_id);
        }else{
            customerDB.addNewCustomer(Customer_Name, address, postco, phone, division_id);
        }
        customerToModify = null;
        populateCustomerEditField();
        customerTV.setItems(customerDB.getAllCustomers());
        onEditingMasage.setText("");
    }
}