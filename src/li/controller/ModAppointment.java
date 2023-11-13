package li.controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import li.dao.AppDAO;
import li.dao.ContactDAO;
import li.helper.ConversionsHelper;
import li.helper.DataHelper;
import li.helper.PageHelper;
import li.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This ModAppointment class modifies appointment details, Handles user interactions, validations, and updates.
 */
public class ModAppointment implements Initializable {
    public Button modAPPSaveBTN;
    public Button modAPPCancelBTN;
    public static Appointment selectedAPP;
    public TextField modAppIDField;
    public TextField modAppTitleField;
    public TextArea modAppDescriptionField;
    public ComboBox<String> modAppContactField;
    public DatePicker modAppStartField;
    public DatePicker modAppEndField;
    public TextField modAppStartHour;
    public TextField modAppStartMin;
    public TextField modAppEndHour;
    public TextField modAppEndMin;
    public TextField modAppCustomerIDField;
    public TextField modAppUserIDField;
    public ComboBox<String> modAppLocationField;
    public ComboBox<String> modAppTypeField;
    public Label modAppMsgBoxLabel;

    /**
     * This method validates user inputs, handles time-related validations, and updates appointment details if inputs are valid.
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onModAPPSaveBTNClicked(MouseEvent mouseEvent) throws SQLException, IOException {

        //Form validations - Field missing
            if (modAppTitleField.getText().isEmpty()){
                modAppMsgBoxLabel.setText("Title is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppDescriptionField.getText().isEmpty()){
                modAppMsgBoxLabel.setText("Description is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppLocationField.getSelectionModel().isEmpty()){
                modAppMsgBoxLabel.setText("Location is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppTypeField.getSelectionModel().isEmpty()){
                modAppMsgBoxLabel.setText("Type is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppContactField.getSelectionModel().isEmpty()){
                modAppMsgBoxLabel.setText("Contact is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppStartField.getValue() == null){
                modAppMsgBoxLabel.setText("Start date is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppEndField.getValue() == null){
                modAppMsgBoxLabel.setText("End date is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppStartHour.getText().isEmpty()){
                modAppMsgBoxLabel.setText("Start Hour is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }if (modAppStartMin.getText().isEmpty()){
                modAppMsgBoxLabel.setText("Start Min is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppEndHour.getText().isEmpty()){
                modAppMsgBoxLabel.setText("End Hour is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }if (modAppEndMin.getText().isEmpty()){
                modAppMsgBoxLabel.setText("End Min is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppCustomerIDField.getText().isEmpty()){
                modAppMsgBoxLabel.setText("Customer ID is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (modAppUserIDField.getText().isEmpty()){
                modAppMsgBoxLabel.setText("User ID is missing");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Collecting information from the fields
            String title = modAppTitleField.getText();
            String description = modAppDescriptionField.getText();
            String location = modAppLocationField.getValue();
            String type = modAppTypeField.getValue();

            LocalDate start = modAppStartField.getValue();
            LocalDate end = modAppEndField.getValue();
            int startHour = Integer.parseInt(modAppStartHour.getText());
            int startMin = Integer.parseInt(modAppStartMin.getText());
            int endHour = Integer.parseInt(modAppEndHour.getText());
            int endMin = Integer.parseInt(modAppEndMin.getText());

            LocalDateTime startTimeLocal = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startHour, startMin);
            LocalDateTime endTimeLocal = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endHour, endMin);

            LocalDateTime startTimeET = ConversionsHelper.LocalTimeToET(startTimeLocal);
            LocalDateTime endTimeET = ConversionsHelper.LocalTimeToET(endTimeLocal);

            LocalDateTime startTimeUTC = ConversionsHelper.LocalTimeToUTC(startTimeLocal);
            LocalDateTime endTimeUTC = ConversionsHelper.LocalTimeToUTC(endTimeLocal);

            String startTimeString = startTimeUTC.toString();
            String endTimeString = endTimeUTC.toString();

            int customerID = Integer.parseInt(modAppCustomerIDField.getText());
            int userID = Integer.parseInt(modAppUserIDField.getText());
            String contactString = modAppContactField.getValue();
            int contactID = ContactDAO.getContactByName(contactString).getContactID();
            int appID = selectedAPP.getAppID();

        //Form validations - End Date before Start Date
            if( startTimeLocal.compareTo(endTimeLocal) > 0 ){
                modAppMsgBoxLabel.setText("End Time can't be earlier than Start Time");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            } else if(startTimeLocal.compareTo(endTimeLocal) == 0){
                modAppMsgBoxLabel.setText("Star Time and End Time can't be the same");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Form validations - Set DateTime before current time
            if(startTimeLocal.compareTo(LocalDateTime.now()) < 0 | startTimeLocal.compareTo(LocalDateTime.now()) == 0){
                modAppMsgBoxLabel.setText("Booking a time that has already elapsed is not possible");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(endTimeLocal.compareTo(LocalDateTime.now()) < 0 | endTimeLocal.compareTo(LocalDateTime.now()) == 0){
                modAppMsgBoxLabel.setText("Booking a time that has already elapsed is not possible");
                modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Form validations - End Date before Start Date
            if(startTimeET.getHour() < 8 | startTimeET.getHour() > 20 ){
                modAppMsgBoxLabel.setText("StartTime is Outside of Office Hours in New York Office");
                modAppMsgBoxLabel.setTextFill(Color.RED);
                return;
            }
            if(endTimeET.getHour() < 8 | endTimeET.getHour() > 20){
                modAppMsgBoxLabel.setText("EndTime is Outside of Office Hours in New York Office");
                modAppMsgBoxLabel.setTextFill(Color.RED);
                return;
            }

        //Form validations - overlapping appointments
            ObservableList<Appointment> customerAppointments =  DataHelper.fetchAllAppointmentsByCustomerID(customerID);
            //Lambda Expression
            AtomicBoolean appTimeCollided = new AtomicBoolean(false);
            customerAppointments.forEach(appointment -> {

                //Not to check current appointment
                if(appointment.getAppID() == selectedAPP.getAppID()){
                    return;
                }

                //Start Time validation
                if ( startTimeLocal.isEqual(appointment.getStart()) | (startTimeLocal.isAfter(appointment.getStart()) && startTimeLocal.isBefore(appointment.getEnd())) ){
                    modAppMsgBoxLabel.setText("Start Time is overlapped with appointment ID: " + appointment.getAppID());
                    modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                    appTimeCollided.set(true);
                }
                //End Time validation
                if ( endTimeLocal.isEqual(appointment.getEnd()) | ( endTimeLocal.isAfter(appointment.getStart()) && endTimeLocal.isBefore(appointment.getEnd())) ){
                    modAppMsgBoxLabel.setText("End Time is overlapped with appointment ID: " + appointment.getAppID());
                    modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                    appTimeCollided.set(true);
                }
                //Selected period collided with appointment
                if( (startTimeLocal.isEqual(appointment.getStart()) && endTimeLocal.isEqual(appointment.getEnd())) | ( startTimeLocal.isBefore(appointment.getStart()) && endTimeLocal.isAfter(appointment.getEnd()) )){
                    modAppMsgBoxLabel.setText("Selected Time period is overlapped with appointment ID: " + appointment.getAppID());
                    modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                    appTimeCollided.set(true);
                }
            });
            if (appTimeCollided.get()){
                return;
            }

        //Executing
            try {
                AppDAO.updateAPP(title, description, location, type, startTimeString, endTimeString, customerID, userID, contactID, appID);
            }
            catch(SQLException throwables) {

                //Form validations - Customer and User ID
                if(throwables.getMessage().equals("Cannot add or update a child row: a foreign key constraint fails (`client_schedule`.`appointments`, CONSTRAINT `fk_customer_id` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`Customer_ID`) ON DELETE RESTRICT ON UPDATE CASCADE)")){
                    modAppMsgBoxLabel.setText("Invalid Customer ID");
                    modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                }
                if(throwables.getMessage().equals("Cannot add or update a child row: a foreign key constraint fails (`client_schedule`.`appointments`, CONSTRAINT `fk_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE)")){
                    modAppMsgBoxLabel.setText("Invalid user ID");
                    modAppMsgBoxLabel.setTextFill(Color.YELLOW);
                }

                return;
            }

        //Back to main page
            MainController.activeTab = "app";
            PageHelper.switchPages(mouseEvent,"mainPage");
    }

    /**
     * This method cancels appointment modification action and returns to the main page.
     * @param mouseEvent
     * @throws IOException
     */
    public void onModAPPCancelBTNClicked(MouseEvent mouseEvent) throws IOException {
        MainController.activeTab = "app";
        PageHelper.switchPages(mouseEvent,"mainPage");
    }

    /**
     * This method sets up the form with existing appointment data and populates fields for modification.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Passing options into the comboBoxes
            modAppContactField.setItems(DataHelper.fetchAllContactNames());
            modAppTypeField.setItems(MainController.types);
            modAppLocationField.setItems(MainController.locations);

        Contact selectedContact = DataHelper.fetchContactByContactID(selectedAPP.getContactID());

        //Auto-populating appointment data into the fields
            modAppIDField.setText(String.valueOf(selectedAPP.getAppID()));
            modAppTitleField.setText(selectedAPP.getTitle());
            modAppDescriptionField.setText(selectedAPP.getDescription());
            modAppLocationField.getSelectionModel().select(selectedAPP.getLocation());
            modAppContactField.getSelectionModel().select(selectedContact.getContactName());
            modAppTypeField.getSelectionModel().select(selectedAPP.getType());
            modAppStartField.setValue(selectedAPP.getStart().toLocalDate());
                modAppStartHour.setText(String.valueOf(selectedAPP.getStart().getHour()));
                modAppStartMin.setText(String.valueOf(selectedAPP.getStart().getMinute()));
            modAppEndField.setValue(selectedAPP.getEnd().toLocalDate());
                modAppEndHour.setText(String.valueOf(selectedAPP.getEnd().getHour()));
                modAppEndMin.setText(String.valueOf(selectedAPP.getEnd().getMinute()));
            modAppCustomerIDField.setText(String.valueOf(selectedAPP.getCustomerID()));
            modAppUserIDField.setText(String.valueOf(selectedAPP.getUserID()));

    }
}
