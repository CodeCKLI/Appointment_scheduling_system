package li.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import li.dao.CustomerDAO;
import li.dao.DivisionDAO;
import li.helper.ConversionsHelper;
import li.helper.DataHelper;
import li.helper.PageHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This AddCustomer class manages adding new customers by validating inputs, handling data, and interfacing with the database.
 */
public class AddCustomer implements Initializable {
    public Button addCusSaveBTN;
    public Button addCusCancelBTN;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField customerPostalField;
    public TextField customerPhoneField;
    public ComboBox<String> customerCountryField;
    public ComboBox<String> customerDivisionField;
    public String selectedCountry;
    public String selectedDivision;
    public Label addCustomerMsgLabel;

    /**
     * This method validates and processes input fields, then adds a new customer record to the database.
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddCusSaveBTNClicked(MouseEvent mouseEvent) throws SQLException, IOException {

        //Form validations - Missing fields
            if(customerNameField.getText().isEmpty()){
                addCustomerMsgLabel.setText("Customer's Name is missing");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(customerAddressField.getText().isEmpty()){
                addCustomerMsgLabel.setText("Customer's Address is missing");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(customerPostalField.getText().isEmpty()){
                addCustomerMsgLabel.setText("Postal Code is missing");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(customerPhoneField.getText().isEmpty()){
                addCustomerMsgLabel.setText("Phone Number is missing");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(customerCountryField.getSelectionModel().isEmpty()){
                addCustomerMsgLabel.setText("Country Field is empty");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(customerDivisionField.getSelectionModel().isEmpty()){
                addCustomerMsgLabel.setText("Division field is empty");
                addCustomerMsgLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Collecting data from fields
            String customerName = customerNameField.getText();
            String Address = customerAddressField.getText();
            String Postal = customerPostalField.getText();
            String Phone = customerPhoneField.getText();
            int divID = DivisionDAO.getDivisionIDByDivisionName(selectedDivision).getDivisionID();

        CustomerDAO.addCustomer(customerName, Address, Postal, Phone, divID);

        //Back to main page
            MainController.activeTab = "cus";
            PageHelper.switchPages(mouseEvent, "mainPage");
    }

    /**
     * This method cancels customer addition action and returns to the main page.
     * @param mouseEvent
     * @throws IOException
     */
    public void onAddCusCancelBTNClicked(MouseEvent mouseEvent) throws IOException {
        MainController.activeTab = "cus";
        PageHelper.switchPages(mouseEvent, "mainPage");
    }

    /**
     * This method fetches corresponding divisions for selection based on the chosen country.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCustomerCountryFieldAction(ActionEvent actionEvent) throws SQLException {
        selectedCountry = customerCountryField.getValue();
        int selectedCountryID =  ConversionsHelper.countryNameToCountryID(selectedCountry);
        customerDivisionField.setItems(DataHelper.fetchDivisionNamesByCountryID(selectedCountryID));
    }

    /**
     * This method captures the selected division upon user interaction.
     * @param actionEvent
     */
    public void onCustomerDivisionFieldAction(ActionEvent actionEvent) {
        selectedDivision = customerDivisionField.getValue();
    }

    /**
     * This initialize methodInitializes UI components, including populating the country field with options for selection.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountryField.setItems(DataHelper.fetchAllCountryNames());
    }

}
