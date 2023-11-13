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
import li.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This AddAppointment class helps adding new appointments by managing form validation, time considerations, and database interactions.
 */
public class AddAppointment implements Initializable {
    public Button addAPPSaveBTN;
    public Button addAPPCancelBTN;
    public TextField addAppTitleField;
    public TextArea addAppDescriptionField;
    public ComboBox<String> addAppContactField;
    public DatePicker addAppStartDateField;
    public DatePicker addAppEndDateField;
    public TextField addAppStartHourField;
    public TextField addAppStartMinField;
    public TextField addAppEndHourField;
    public TextField addAppEndMinField;
    public TextField addAppCustomerField;
    public TextField addAPPUserIDField;
    public ComboBox<String> addAppTypeField;
    public ComboBox<String> addAppLocationField;
    public Label addAppMsgBoxLabel;

    /**
     * This method validates input fields, including time, date, and contact, then records and adds the appointment if valid.
     * @param mouseEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddAPPSaveBTNClicked(MouseEvent mouseEvent) throws SQLException, IOException {

        //Form validations - Missing fields
            if (addAppTitleField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("Title is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppDescriptionField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("Description is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppLocationField.getSelectionModel().isEmpty()){
                addAppMsgBoxLabel.setText("Location is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppTypeField.getSelectionModel().isEmpty()){
                addAppMsgBoxLabel.setText("Type is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppContactField.getSelectionModel().isEmpty()){
                addAppMsgBoxLabel.setText("Contact is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppStartDateField.getValue() == null){
                addAppMsgBoxLabel.setText("Start date is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppEndDateField.getValue() == null){
                addAppMsgBoxLabel.setText("End date is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppStartHourField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("Start Hour is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }if (addAppStartMinField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("Start Min is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppEndHourField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("End Hour is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }if (addAppEndMinField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("End Min is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAppCustomerField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("Customer ID is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if (addAPPUserIDField.getText().isEmpty()){
                addAppMsgBoxLabel.setText("User ID is missing");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Collecting information from fields
            String title = addAppTitleField.getText();
            String description = addAppDescriptionField.getText();
            String location = addAppLocationField.getValue();
            String type = addAppTypeField.getValue();

            LocalDate start = addAppStartDateField.getValue();
            LocalDate end = addAppEndDateField.getValue();
            int startHour = Integer.parseInt(addAppStartHourField.getText());
            int startMin = Integer.parseInt(addAppStartMinField.getText());
            int endHour = Integer.parseInt(addAppEndHourField.getText());
            int endMin = Integer.parseInt(addAppEndMinField.getText());

            LocalDateTime startTimeLocal = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), startHour, startMin);
            LocalDateTime endTimeLocal = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), endHour, endMin);

            LocalDateTime startTimeET = ConversionsHelper.LocalTimeToET(startTimeLocal);
            LocalDateTime endTimeET = ConversionsHelper.LocalTimeToET(endTimeLocal);

            LocalDateTime startTimeUTC = ConversionsHelper.LocalTimeToUTC(startTimeLocal);
            LocalDateTime endTimeUTC = ConversionsHelper.LocalTimeToUTC(endTimeLocal);

            String startTime = startTimeUTC.toString();
            String endTimeString = endTimeUTC.toString();

            int customerID = Integer.parseInt(addAppCustomerField.getText());
            int userID = Integer.parseInt(addAPPUserIDField.getText());
            String contactString = addAppContactField.getValue();
            int contactID = ContactDAO.getContactByName(contactString).getContactID();

        //Form validations - Set DateTime before current time
            if(startTimeLocal.compareTo(LocalDateTime.now()) < 0 | startTimeLocal.compareTo(LocalDateTime.now()) == 0){
                addAppMsgBoxLabel.setText("Booking a time that has already elapsed is not possible");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(endTimeLocal.compareTo(LocalDateTime.now()) < 0 | endTimeLocal.compareTo(LocalDateTime.now()) == 0){
                addAppMsgBoxLabel.setText("Booking a time that has already elapsed is not possible");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Form validations - End Date before Start Date
            if( startTimeLocal.compareTo(endTimeLocal) > 0 ){
                addAppMsgBoxLabel.setText("End Time can't be earlier than Start Time");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            } else if(startTimeLocal.compareTo(endTimeLocal) == 0){
                addAppMsgBoxLabel.setText("Star Time and End Time can't be the same");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Form validations - Office hour time check
            if(startTimeET.getHour() < 8 | startTimeET.getHour() > 20 ){
                addAppMsgBoxLabel.setText("StartTime is Outside of Office Hours in New York Office");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }
            if(endTimeET.getHour() < 8 | endTimeET.getHour() > 20){
                addAppMsgBoxLabel.setText("EndTime is Outside of Office Hours in New York Office");
                addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                return;
            }

        //Form validations - overlapping appointments
            ObservableList<Appointment> customerAppointments =  DataHelper.fetchAllAppointmentsByCustomerID(customerID);
            //Lambda Expression
            AtomicBoolean appTimeCollided = new AtomicBoolean(false);
            customerAppointments.forEach(appointment -> {
                //Start Time validation
                    if ( startTimeLocal.isEqual(appointment.getStart()) | (startTimeLocal.isAfter(appointment.getStart()) && startTimeLocal.isBefore(appointment.getEnd())) ){
                        addAppMsgBoxLabel.setText("Start Time is overlapped with appointment ID: " + appointment.getAppID());
                        addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                        appTimeCollided.set(true);
                    }
                //End Time validation
                    if ( endTimeLocal.isEqual(appointment.getEnd()) | ( endTimeLocal.isAfter(appointment.getStart()) && endTimeLocal.isBefore(appointment.getEnd())) ){
                        addAppMsgBoxLabel.setText("End Time is overlapped with appointment ID: " + appointment.getAppID());
                        addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                        appTimeCollided.set(true);
                    }
                //Selected period collided with appointment
                    if( (startTimeLocal.isEqual(appointment.getStart()) && endTimeLocal.isEqual(appointment.getEnd())) | ( startTimeLocal.isBefore(appointment.getStart()) && endTimeLocal.isAfter(appointment.getEnd()) )){
                        addAppMsgBoxLabel.setText("Selected Time period is overlapped with appointment ID: " + appointment.getAppID());
                        addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                        appTimeCollided.set(true);
                    }
            });
            if (appTimeCollided.get()){
                return;
            }

        //Executing
            try {
                AppDAO.addAPP(title, description, location, type, startTime, endTimeString, customerID, userID, contactID);
            }
            catch(SQLException throwables) {

                //Form validations - Customer and User ID
                    if(throwables.getMessage().equals("Cannot add or update a child row: a foreign key constraint fails (`client_schedule`.`appointments`, CONSTRAINT `fk_customer_id` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`Customer_ID`) ON DELETE RESTRICT ON UPDATE CASCADE)")){
                        addAppMsgBoxLabel.setText("Invalid Customer ID");
                        addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                    }
                    if(throwables.getMessage().equals("Cannot add or update a child row: a foreign key constraint fails (`client_schedule`.`appointments`, CONSTRAINT `fk_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE)")){
                        addAppMsgBoxLabel.setText("Invalid user ID");
                        addAppMsgBoxLabel.setTextFill(Color.YELLOW);
                    }

                return;
            }

        //Back to main page
            MainController.activeTab = "app";
            PageHelper.switchPages(mouseEvent,"mainPage");
    }

    /**
     * This method cancels appointment creation action and return to the main page.
     * @param mouseEvent
     * @throws IOException
     */
    public void onAddAPPCancelBTNClicked(MouseEvent mouseEvent) throws IOException {
        MainController.activeTab = "app";
        PageHelper.switchPages(mouseEvent,"mainPage");
    }

    /**
     * This method initializes UI components with contact names, appointment types, and locations.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppContactField.setItems(DataHelper.fetchAllContactNames());
        addAppTypeField.setItems(MainController.types);
        addAppLocationField.setItems(MainController.locations);
    }

}
