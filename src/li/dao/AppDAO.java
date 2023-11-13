package li.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.helper.ConversionsHelper;
import li.helper.JDBCHelper;
import li.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This AppDAO class handles database operations related to appointments, including fetching, updating, and adding appointment information
 */
public class AppDAO {

    /**
     * This method fetches appointments based on a specific customer's ID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByCustomerID(int customerID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

    /**
     * This method fetches appointments based on a specific contact's ID
     * @param contact
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByContactID(int contact) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, contact);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

    /**
     * This method fetches appointments for a specific month and the current year
     * @param month
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByMonth(int month) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE MONTH(Start) = ? AND YEAR(Start) = YEAR(CURDATE());";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, month);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

    /**
     * This method fetches appointments based on a specific appointment type
     * @param seltype
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByType(String seltype) throws SQLException {

        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Type = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, seltype);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;

    }

    /**
     * This method deletes an appointment based on the appointment ID
     * @param appID
     * @throws SQLException
     */
    public static void delApp(int appID) throws SQLException {
        String sqlQuery = "delete FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, appID);
        ps.executeUpdate();
    }

    /**
     * This method fetches appointments for the current week
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByWeek() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Start >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AND Start < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY), INTERVAL 7 DAY)";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

    /**
     * This method fetches appointments for the current month
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppByCurrentMonth() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE MONTH(Start) = MONTH(CURDATE()) AND YEAR(Start) = YEAR(CURDATE());";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

    /**
     * This method updates appointment details in the database
     * @param title
     * @param desciption
     * @param location
     * @param type
     * @param start
     * @param end
     * @param cID
     * @param uID
     * @param conID
     * @param appID
     * @throws SQLException
     */
    public static void updateAPP(String title, String desciption, String location, String type, String start, String end, int cID, int uID, int conID, int appID) throws SQLException {

        String sqlQuery = "UPDATE client_schedule.appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? Where Appointment_ID = ?;";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, title);
        ps.setString(2, desciption);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setInt(7, cID);
        ps.setInt(8, uID);
        ps.setInt(9, conID);
        ps.setInt(10, appID);

        ps.executeUpdate();

    }

    /**
     * This method adds a new appointment to the database
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void addAPP(String title, String description, String location, String type, String start, String end, int customerID, int userID, int contactID ) throws SQLException {

        String sqlQuery = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) \n" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);

        ps.executeUpdate();

    }

    /**
     * This method fetches all appointments from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllApp() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        while(rs.next()){
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = ConversionsHelper.UTCtoLocalTime(rs.getTimestamp("End").toLocalDateTime());
            int cID = rs.getInt("Customer_ID");
            int uID = rs.getInt("User_ID");
            int conID = rs.getInt("Contact_ID");

            Appointment newApp = new Appointment(appID, title, description, location, type, start, end, cID, uID, conID);

            list.add(newApp);
        }
        return list;
    }

}
