package li.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import li.dao.AppDAO;
import li.dao.CustomerDAO;
import li.helper.DataHelper;
import li.helper.PageHelper;
import li.model.Appointment;
import li.model.Customer;
import li.helper.ConversionsHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This MainController class manages the main functionality of the application, including navigation, data display, and report generation.
 */
public class MainController implements Initializable {
    public ToggleGroup appSelect;
    public Button addAppBTN;
    public Button updateAppBTN;
    public Button delAppBTN;
    public Button logoutBTN;
    public Button addCusBTN;
    public Button updateCusBTN;
    public Button delCusBTN;
    public TabPane maintabpane;
    public Tab APPTab;
    public Tab CusTab;
    public Tab RepTab;
    public static String activeTab;
    public static String user;
    public Label uName;
    public TableView<Appointment> APPTable;
    public TableView<Customer> CustomerTable;
    public TableColumn<Customer, Integer> CustomerTableColID;
    public TableColumn<Customer, String> CustomerTableColName;
    public TableColumn<Customer, String> CustomerTableColAddress;
    public TableColumn<Customer, String> CustomerTableColPostal;
    public TableColumn<Customer, String> CustomerTableColPhone;
    public TableColumn<Customer, Integer> CustomerTableColDIvID;
    public Label mainMsgBox;
    public TableColumn<Appointment, Integer> appTableID;
    public TableColumn<Appointment, String> appTableTitle;
    public TableColumn<Appointment, String> appTableDes;
    public TableColumn<Appointment, String> appTableLocat;
    public TableColumn<Appointment, Integer> appTableContact;
    public TableColumn<Appointment, String> appTableType;
    public TableColumn<Appointment, LocalDateTime> appTableStart;
    public TableColumn<Appointment, LocalDateTime> appTableEnd;
    public TableColumn<Appointment, Integer> appTableCID;
    public TableColumn<Appointment, Integer> appTableUID;

    public static ObservableList<String> types = FXCollections.observableArrayList();
    public static ObservableList<String> locations = FXCollections.observableArrayList();
    public static ObservableList<String> months = FXCollections.observableArrayList();

    public Label reportTypeCountLabel;
    public ComboBox<String> reportTypeSelect;
    public TableView<Appointment> reportAppTypeTable;
    public TableColumn<Appointment, Integer> reportAppTypeAppIDTab;
    public TableColumn<Appointment, String> reportAppTypeTitleTab;
    public TableColumn<Appointment, String> reportAppTypeDescriptionTab;
    public TableColumn<Appointment, String> reportAppTypeLocationTab;
    public TableColumn<Appointment, Integer> reportAppTypeContactTab;
    public TableColumn<Appointment, String> reportAppTypeTypeTab;
    public TableColumn<Appointment, LocalDateTime> reportAppTypeStartTab;
    public TableColumn<Appointment, LocalDateTime> reportAppTypeEndTab;
    public TableColumn<Appointment, Integer> reportAppTypeCIDTab;
    public TableColumn<Appointment, Integer> reportAppTypeUIDTab;
    public ComboBox<String> reportAppMonthSelect;
    public TableView<Appointment> reportAppMonthTable;
    public TableColumn<Appointment, Integer> reportAppMonthAppIDTab;
    public TableColumn<Appointment, String> reportAppMonthTitleTab;
    public TableColumn<Appointment, String> reportAppMonthDescriptionTab;
    public TableColumn<Appointment, String> reportAppMonthLocationTab;
    public TableColumn<Appointment, Integer> reportAppMonthContactTab;
    public TableColumn<Appointment, String> reportAppMonthTypeTab;
    public TableColumn<Appointment, LocalDateTime> reportAppMonthStartTab;
    public TableColumn<Appointment, LocalDateTime> reportAppMonthEndTab;
    public TableColumn<Appointment, Integer> reportAppMonthCIDTab;
    public TableColumn<Appointment, Integer> reportAppMonthUIDTab;
    public Label reportMonthCountLabel;
    public ComboBox<String> reportAppContactSelect;
    public TableView<Appointment> reportAppContactTable;
    public TableColumn<Appointment, Integer> reportAppContactAppIDTab;
    public TableColumn<Appointment, String> reportAppContactTitleTab;
    public TableColumn<Appointment, String> reportAppContactDescriptionTab;
    public TableColumn<Appointment, String> reportAppContactLocationTab;
    public TableColumn<Appointment, Integer> reportAppContactContactTab;
    public TableColumn<Appointment, String> reportAppContactTypeTab;
    public TableColumn<Appointment, LocalDateTime> reportAppContactStartTab;
    public TableColumn<Appointment, LocalDateTime> reportAppContactEndTab;
    public TableColumn<Appointment, Integer> reportAppContactCIDTab;
    public TableColumn<Appointment, Integer> reportAppContactUIDTab;
    public Label reportAppContactCountLabel;
    public ComboBox<String> reportCustomerCountrySelect;
    public TableColumn<Customer, Integer> reportCustomerCustomerIDTab;
    public TableColumn<Customer, String> reportCustomerNameTab;
    public TableColumn<Customer, String> reportCustomerAddressTab;
    public TableColumn<Customer, String> reportCustomerPostalTab;
    public TableColumn<Customer, String> reportCustomerPhoneTab;
    public TableColumn<Customer, Integer> reportCustomerDivisionTab;
    public TableView<Customer> reportCustomerTable;
    public Label reportCustomerCountLabel;


    /**
     * This method redirects to the page for adding a new appointment.
     * @param mouseEvent
     * @throws IOException
     */
    public void onAddAppBTNClicked(MouseEvent mouseEvent) throws IOException {
        PageHelper.switchPages(mouseEvent,"addAppointment");
    }

    /**
     *This method redirects to the page for updating a selected appointment.
     * @param mouseEvent
     * @throws IOException
     */
    public void onUpdateAppBTNClicked(MouseEvent mouseEvent) throws IOException {
        Appointment selAPP = APPTable.getSelectionModel().getSelectedItem();
        if (selAPP == null){
            mainMsgBox.setText("Must select an appointment to edit");
            mainMsgBox.setTextFill(Color.RED);
            return;
        }
        ModAppointment.selectedAPP = selAPP;
        PageHelper.switchPages(mouseEvent, "modAppointment");
    }

    /**
     * This method handles the deletion of a selected appointment after confirmation.
     * @param mouseEvent
     * @throws SQLException
     */
    public void onDelAppBTNClicked(MouseEvent mouseEvent) throws SQLException {
        Appointment delApp = APPTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel Appointment");
        alert.setContentText("Do you want to cancel appointment of ID: " + delApp.getAppID() + " Type: " + delApp.getType() + "?");
        Optional<ButtonType> result = alert.showAndWait();


        if(result.isEmpty()){
            System.out.println("Alert closed");
        } else if(result.get() == ButtonType.OK){

            AppDAO.delApp(delApp.getAppID());

            //Get Appointment data from DB
                ObservableList<Appointment> appList = DataHelper.fetchAllAppointments();

            //Display All appointments
                appTableID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
                appTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
                appTableDes.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
                appTableLocat.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
                appTableType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
                appTableStart.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
                appTableEnd.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
                appTableCID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
                appTableUID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
                appTableContact.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
                APPTable.setItems(appList);

            mainMsgBox.setText("AppID: " + delApp.getAppID() + " Type: " + delApp.getType() + " is deleted");
            mainMsgBox.setTextFill(Color.YELLOW);
        }

    }

    /**
     * This method handles user logout after confirmation.
     * @param mouseEvent
     * @throws IOException
     */
    public void onLogoutBTNClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();


        if(result.isEmpty()){
            System.out.println("Result is Empty");
        } else if(result.get() == ButtonType.OK){
            PageHelper.switchPages(mouseEvent, "login");
        }
    }

    /**
     * This method redirects to the page for adding a new customer.
     * @param mouseEvent
     * @throws IOException
     */
    public void onAddCusBTNClicked(MouseEvent mouseEvent) throws IOException {
        PageHelper.switchPages(mouseEvent, "addCustomer");
    }

    /**
     * this method redirects to the page for updating a selected customer.
     * @param mouseEvent
     * @throws IOException
     */
    public void onUpdateCusBTNClicked(MouseEvent mouseEvent) throws IOException {
        Customer selCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        if (selCustomer == null){
            mainMsgBox.setText("Must select a customer to edit");
            mainMsgBox.setTextFill(Color.RED);
            return;
        }
        ModCustomer.selectedCustomer = selCustomer;
        PageHelper.switchPages(mouseEvent, "modCustomer");

    }

    /**
     * This method handles the deletion of a selected customer after confirmation.
     * @param mouseEvent
     * @throws SQLException
     */
    public void onDelCusBTNClicked(MouseEvent mouseEvent) throws SQLException {
        Customer delCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm customer deletion");
        alert.setContentText("All appointments related to the customer will be deleted along with the customer");
        Optional<ButtonType> result = alert.showAndWait();


        if(result.isEmpty()){
            System.out.println("result is Empty");
        } else if(result.get() == ButtonType.OK){
            ObservableList<Appointment> appointmentList = AppDAO.getAppByCustomerID(delCustomer.getCustomerID());
            // Lambda #2
            appointmentList.forEach(app -> {
                try {
                    AppDAO.delApp(app.getAppID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            CustomerDAO.delCustomer(delCustomer.getCustomerID());

            //Get customer data from DB
            ObservableList<Customer> customerList = null;
            try {
                customerList = CustomerDAO.getAllCustomer();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            CustomerTableColID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
            CustomerTableColName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            CustomerTableColAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            CustomerTableColPostal.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            CustomerTableColPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            CustomerTableColDIvID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));

            CustomerTable.setItems(customerList);

            mainMsgBox.setText(delCustomer.getCustomerName() + " is deleted");
            mainMsgBox.setTextFill(Color.YELLOW);

            //Get Appointment data from DB
            ObservableList<Appointment> appList = null;
            try {
                appList = AppDAO.getAllApp();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                mainMsgBox.setText(throwables.getMessage());
            }
            appTableID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            appTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            appTableDes.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            appTableLocat.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            appTableType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            appTableStart.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
            appTableEnd.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
            appTableCID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            appTableUID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            appTableContact.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));

            APPTable.setItems(appList);
        } else if(result.get() == ButtonType.CANCEL){
            System.out.println("Cancel");
        }

    }

    /**
     * This method handles the changing of the view mode for appointments (all, by week, by month).
     * @param actionEvent
     */
    public void onAppViewToggleAction(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) appSelect.getSelectedToggle();
        String selectedRBString = selectedRadioButton.getText();

        ObservableList<Appointment> appList = null;

        if (selectedRBString.equals("View all")){
            appList = DataHelper.fetchAllAppointments();
        } else if(selectedRBString.equals("By week")){
            try {
                appList = AppDAO.getAppByWeek();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if(selectedRBString.equals("By month")){
            try {
                appList = AppDAO.getAppByCurrentMonth();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        appTableID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
        appTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appTableDes.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appTableLocat.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appTableType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appTableStart.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        appTableEnd.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
        appTableCID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appTableUID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        appTableContact.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));

        APPTable.setItems(appList);
    }

    /**
     * This method generates a report of appointments by type.
     * @param actionEvent
     * @throws SQLException
     */
    public void onReportTypeSelectAction(ActionEvent actionEvent) throws SQLException {

        mainMsgBox.setText(" ");

        //Fetch appointments by type
            String selectedType = reportTypeSelect.getValue();

            ObservableList<Appointment> appList = DataHelper.fetchAllAppointmentsByType(selectedType);

            if (appList.size() == 0){
                mainMsgBox.setText("No record");
                mainMsgBox.setTextFill(Color.RED);
            }

        //Display appointments on table
            reportAppTypeAppIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            reportAppTypeTitleTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            reportAppTypeDescriptionTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            reportAppTypeLocationTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            reportAppTypeTypeTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            reportAppTypeStartTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
            reportAppTypeEndTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
            reportAppTypeCIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            reportAppTypeUIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            reportAppTypeContactTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
            reportAppTypeTable.setItems(appList);

            reportTypeCountLabel.setText(String.valueOf(appList.size()));

    }

    /**
     * This method generates a report of appointments by selected month.
     * @param actionEvent
     */
    public void onReportMonthSelectAction(ActionEvent actionEvent) {

        mainMsgBox.setText(" ");

        //Fetch Appointments from database
            String selectedMonth = reportAppMonthSelect.getValue();
            int selectMonthInt = ConversionsHelper.monthsToInt(selectedMonth);
            ObservableList<Appointment> appList = DataHelper.fetchAllAppointmentsByMonth(selectMonthInt);

            if (appList.size() == 0){
                mainMsgBox.setText("No record");
                mainMsgBox.setTextFill(Color.RED);
            }

        //Display data on Appointment Table
            reportAppMonthAppIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            reportAppMonthTitleTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            reportAppMonthDescriptionTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            reportAppMonthLocationTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            reportAppMonthTypeTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            reportAppMonthStartTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
            reportAppMonthEndTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
            reportAppMonthCIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            reportAppMonthUIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            reportAppMonthContactTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));

            reportAppMonthTable.setItems(appList);

            reportMonthCountLabel.setText(String.valueOf(appList.size()));

    }

    /**
     * This method generates a report of appointments by selected contact.
     * @param actionEvent
     * @throws SQLException
     */
    public void onReportContactSelectAction(ActionEvent actionEvent) throws SQLException {

        mainMsgBox.setText(" ");

        //Fetch Appointment list from database by contact ID
            String selectedContact = reportAppContactSelect.getValue();
            int contactID = ConversionsHelper.contactNameToID(selectedContact);
            ObservableList<Appointment> appList = DataHelper.fetchAllAppointmentsByContactID(contactID);
            if (appList.size() == 0){
                mainMsgBox.setText("No record");
                mainMsgBox.setTextFill(Color.RED);
            }

        //Display data on Appointment Table
            reportAppContactAppIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            reportAppContactTitleTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            reportAppContactDescriptionTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            reportAppContactLocationTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            reportAppContactTypeTab.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            reportAppContactStartTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
            reportAppContactEndTab.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
            reportAppContactCIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            reportAppContactUIDTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            reportAppContactContactTab.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
            reportAppContactTable.setItems(appList);
            reportAppContactCountLabel.setText(String.valueOf(appList.size()));

    }

    /**
     *This method generates a report of customers by selected country.
     * @param actionEvent
     * @throws SQLException
     */
    public void onReportCustomerCountrySelectAction(ActionEvent actionEvent) throws SQLException {

        mainMsgBox.setText(" ");

        //Fetch Customer list by Country from database
            String selectedCountry = reportCustomerCountrySelect.getValue();
            int selectCountryID = ConversionsHelper.countryNameToCountryID(selectedCountry);
            ObservableList<Customer> customerList = DataHelper.fetchAllCustomersByCountryID(selectCountryID);

            if (customerList.size() == 0){
                mainMsgBox.setText("No record");
                mainMsgBox.setTextFill(Color.RED);
            }

        //Display Customer list on Customer Table
            reportCustomerCustomerIDTab.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
            reportCustomerNameTab.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            reportCustomerAddressTab.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            reportCustomerPostalTab.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            reportCustomerPhoneTab.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            reportCustomerDivisionTab.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));
            reportCustomerTable.setItems(customerList);

            reportCustomerCountLabel.setText(String.valueOf(customerList.size()));

    }

    /**
     * The initialize method Initializes the UI components, sets up listeners, and populates data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //ALL

            //Tabs selection
                if (activeTab == "app"){
                        maintabpane.getSelectionModel().select(0);
                    }
                if (activeTab == "cus"){
                        maintabpane.getSelectionModel().select(1);
                    }
                if (activeTab == "rep"){
                        maintabpane.getSelectionModel().select(2);
                    }

            //For user Name
                if (user.equals("admin")){
                uName.setText("Admin");
            }

            //Initiate types array
                if (types.size() == 0){
                types.add("De-briefing");
                types.add("Planning Session");
                types.add("Kickoff meeting");
                types.add("Onboarding meeting");
                types.add("Retrospective meeting");
                types.add("Status update meeting");
                }

            //Initiate Location array
                if (locations.size() == 0){
                locations.add("London Office");
                locations.add("New York Office");
                locations.add("Tornado Office");
                locations.add("Virtual Online Meeting");
                }

            //Initiate months array
                if(months.size() == 0){
                months.add("Jan");
                months.add("Feb");
                months.add("Mar");
                months.add("Apr");
                months.add("May");
                months.add("Jun");
                months.add("Jul");
                months.add("Aug");
                months.add("Sep");
                months.add("Oct");
                months.add("Nov");
                months.add("Dec");
                }

        //Appointment Tab

            //Get Appointment data from DB
            ObservableList<Appointment> appList = DataHelper.fetchAllAppointments();
            //Display data on appointment table
            appTableID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            appTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            appTableDes.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            appTableLocat.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            appTableType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            appTableStart.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
            appTableEnd.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
            appTableCID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            appTableUID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            appTableContact.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
            APPTable.setItems(appList);

        //Customer Tab

            //Get customer data from DB
            ObservableList<Customer> customerList = DataHelper.fetchAllCustomers();
            //Display data on Customer Table
            CustomerTableColID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
            CustomerTableColName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            CustomerTableColAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            CustomerTableColPostal.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            CustomerTableColPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            CustomerTableColDIvID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));
            CustomerTable.setItems(customerList);

        //Report Tab

            //Appointment by Type Tab
                //Populate type selector options
                    reportTypeSelect.setItems(types);

            //Appointment by Month Tab
                //Populate month selector options
                    reportAppMonthSelect.setItems(months);

            //Contact's Schedule Tab
                //Populate Contact selector options
                    reportAppContactSelect.setItems(DataHelper.fetchAllContactNames());

            //Customers by Country Tab
                //Populate Country selector options
                    reportCustomerCountrySelect.setItems(DataHelper.fetchAllCountryNames());


    }

}
