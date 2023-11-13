package li.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import li.helper.ConversionsHelper;
import li.helper.DataHelper;
import li.helper.PageHelper;
import li.model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This LoginController class manages user authentication, language preferences, and page transitions during logins.
 */
public class LoginController implements Initializable {

    public Label timeZoneID;
    public Button loginBTN;
    public Button ExitBTN;
    public TextField uNameTextField;
    public Label msgBox;
    public PasswordField pwdPasswordField;
    public Label uNameLabel;
    public Label titleLabel;
    public Label pwdLabel;
    public Label timeZoneLabel;
    public ResourceBundle resource;

    /**
     * This method validates credentials, logs activity, and alerts for upcoming appointments.
     * @param mouseEvent
     * @throws IOException
     */
    public void onLoginBTNClicked(MouseEvent mouseEvent) throws IOException {
        String uName = uNameTextField.getText();
        String pwd = pwdPasswordField.getText();
        System.out.println(uName + " " + pwd);

        FileWriter writer = new FileWriter("login_activity.txt", true);

        if (uName.equals("admin")){
            if(pwd.equals("admin")){
                MainController.user = "admin";
                PageHelper.switchPages(mouseEvent, "mainPage");

                writer.append("User: admin " + "successfully logged in at " +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getYear() + "-" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMonth() + "-" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getDayOfMonth() + " " +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getHour() + ":" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMinute() + ":" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getSecond() + " "
                        + " UTC\n");

                    //15 minutes alarm
//                        LocalDateTime now = LocalDateTime.of(2023, 8, 26, 13, 0, 0);
                        LocalDateTime now = LocalDateTime.now();

                        ObservableList<Appointment> appsThisWeek = null;
                        try {
                            appsThisWeek = DataHelper.fetchAllAppointmentsByWeek();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        StringBuilder alertHeader = new StringBuilder();
                        StringBuilder alertContent = new StringBuilder();

                        //Lambda Expression
                        StringBuilder finalAlertContent = alertContent;
                        appsThisWeek.forEach(app -> {
                            Duration duration = Duration.between(now, app.getStart());
                            if(duration.toMinutes() > 0 && duration.toMinutes() < 15){
                                String appString = "Appointment ID: " + app.getAppID() + " "
                                        + "Title: " + app.getTitle() + " "
                                        + "Start Time: " + app.getStart().toLocalTime().toString() + " "
                                        + "End Time: " + app.getEnd().toLocalTime().toString() + "\n"
                                        ;
                                finalAlertContent.append(appString);
                            }
                        });

                        if(alertContent.isEmpty()){
                            alertHeader.append("No upcoming appointments");
                            alertContent.append("Enjoy your shift!");
                        } else {
                            alertHeader.append("The following appointments are scheduled within a 15-minute timeframe: ");
                            alertContent = finalAlertContent;
                        }

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Upcoming appointments within 15 minutes");
                        alert.setHeaderText(alertHeader.toString());
                        alert.setContentText(alertContent.toString());
                        alert.showAndWait();
                writer.close();
            } else{
                msgBox.setText(resource.getString("Invalidpassword"));
                msgBox.setTextFill(Color.YELLOW);

                writer.append("User: admin " + "gave invalid log-in at " +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getYear() + "-" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMonth() + "-" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getDayOfMonth() + " " +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getHour() + ":" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMinute() + ":" +
                        ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getSecond() + " "
                        + " UTC\n");
                writer.close();
            }
        } else{
            msgBox.setText(resource.getString("Invaliduser"));
            msgBox.setTextFill(Color.RED);

            writer.append("User: Non-user " + "gave invalid log-in at " +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getYear() + "-" +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMonth() + "-" +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getDayOfMonth() + " " +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getHour() + ":" +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getMinute() + ":" +
                    ConversionsHelper.LocalTimeToUTC(LocalDateTime.now()).getSecond() + " "
                    + " UTC\n");
            writer.close();
        }

        writer.close();


    }

    /**
     * This method confirms app exit upon user interaction.
     * @param mouseEvent
     */
    public void onExitBTNClicked(MouseEvent mouseEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(resource.getString("exitTitle"));
        confirmationAlert.setHeaderText(resource.getString("exitHeader"));
        confirmationAlert.setContentText(resource.getString("exitContentText"));

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * This initialize method sets UI text, time zone, and language based on user preferences.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Locale.setDefault(new Locale("fr"));

        ZoneId zone = ZoneId.systemDefault();
        timeZoneID.setText(zone.toString());

        Locale userLocale = Locale.getDefault();
        String user_lang = userLocale.toString().substring(0,2);

        if (user_lang.equals("fr")){
            resourceBundle = ResourceBundle.getBundle("loginPage_fr");
        } else {
            resourceBundle = ResourceBundle.getBundle("loginPage_en");
        }
        resource = resourceBundle;
        titleLabel.setText(resourceBundle.getString("title"));
        uNameLabel.setText(resourceBundle.getString("username"));
        pwdLabel.setText(resourceBundle.getString("password"));
        loginBTN.setText(resourceBundle.getString("login"));
        ExitBTN.setText(resourceBundle.getString("exit"));
        timeZoneLabel.setText(resourceBundle.getString("timeZone"));
        msgBox.setText(resourceBundle.getString("welcome"));

    }
}

