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
import li.model.Customer;
import li.model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This ModCustomer class handle modification of customer details, including interactions, validations, and updates.
 */
public class ModCustomer implements Initializable {
    public Button modCusSaveBTN;
    public Button modCusCancelBTN;
    public TextField modCustomerID;
    public TextField modCustomerName;
    public TextField modCustomerAddress;
    public TextField modCustomerPostal;
    public TextField modCustomerPhone;
    public ComboBox<String> modCustomerCountry;
    public ComboBox<String> modCustomerDiv;

    public static Customer selectedCustomer;
    public String selectedCountry;
    public String selectedDivision;
    public Label modCustomerMsgLabel;

    /**
     * This method validates input fields, collects customer data, updates customer information in the database, and navigates back to the main page.
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onModCusSaveBTNClicked(MouseEvent mouseEvent) throws SQLException, IOException {

        //Form validations - Missing fields
        if(modCustomerName.getText().isEmpty()){
            modCustomerMsgLabel.setText("Customer's Name is missing");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }
        if(modCustomerAddress.getText().isEmpty()){
            modCustomerMsgLabel.setText("Customer's Address is missing");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }
        if(modCustomerPostal.getText().isEmpty()){
            modCustomerMsgLabel.setText("Postal Code is missing");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }
        if(modCustomerPhone.getText().isEmpty()){
            modCustomerMsgLabel.setText("Phone Number is missing");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }
        if(modCustomerCountry.getSelectionModel().isEmpty()){
            modCustomerMsgLabel.setText("Country Field is empty");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }
        if(modCustomerDiv.getSelectionModel().isEmpty()){
            modCustomerMsgLabel.setText("Division field is empty");
            modCustomerMsgLabel.setTextFill(Color.YELLOW);
            return;
        }

        //Collecting data from fields
            int customerID = Integer.parseInt(modCustomerID.getText());
            String customerName = modCustomerName.getText();
            String Address = modCustomerAddress.getText();
            String Postal = modCustomerPostal.getText();
            String Phone = modCustomerPhone.getText();
            int divID = DataHelper.fetchDivisionByDivisionID(selectedCustomer.getDivisionID()).getDivisionID();

            if(selectedDivision != null && !selectedDivision.trim().isEmpty()) {
                divID = DivisionDAO.getDivisionIDByDivisionName(selectedDivision).getDivisionID();
            }

        CustomerDAO.updateCustomer(customerName, Address, Postal, Phone, divID, customerID);

        //Back to main page
            MainController.activeTab = "cus";
            PageHelper.switchPages(mouseEvent, "mainPage");
    }

    /**
     * This method cancels customer modification action and returns to the main page.
     * @param mouseEvent
     * @throws IOException
     */
    public void onModCusCancelBTNClicked(MouseEvent mouseEvent) throws IOException {
        MainController.activeTab = "cus";
        PageHelper.switchPages(mouseEvent, "mainPage");
    }

    /**
     * This method triggers when the country selection changes, updates the division options based on the selected country.
     * @param actionEvent
     * @throws SQLException
     */
    public void onModCustomerCountryAction(ActionEvent actionEvent) throws SQLException {
        selectedCountry = modCustomerCountry.getValue();
        int selectedCountryID =  ConversionsHelper.countryNameToCountryID(selectedCountry);
        modCustomerDiv.setItems(DataHelper.fetchDivisionNamesByCountryID(selectedCountryID));
    }

    /**
     * This method triggers when the division selection changes, stores the selected division.
     * @param actionEvent
     */
    public void onModCustomerDivAction(ActionEvent actionEvent) {
        selectedDivision = modCustomerDiv.getValue();
    }

    /**
     * This initialize method sets up the form with existing customer data, auto-populates fields, and handles division and country selections.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modCustomerCountry.setItems(DataHelper.fetchAllCountryNames());

        //Auto-populating customers' data fields
            modCustomerID.setText( Integer.toString(selectedCustomer.getCustomerID()) );
            modCustomerName.setText(selectedCustomer.getCustomerName());
            modCustomerAddress.setText(selectedCustomer.getAddress());
            modCustomerPostal.setText(selectedCustomer.getPostalCode());
            modCustomerPhone.setText(selectedCustomer.getPhone());

            Division resultDivision = DataHelper.fetchDivisionByDivisionID(selectedCustomer.getDivisionID());

            try {
                modCustomerCountry.getSelectionModel().select(ConversionsHelper.countryIDToCountryName(resultDivision.getCountyID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            modCustomerDiv.setItems(DataHelper.fetchDivisionNamesByCountryID(resultDivision.getCountyID()));
            modCustomerDiv.getSelectionModel().select(resultDivision.getDivision());

    }


}
