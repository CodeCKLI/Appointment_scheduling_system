package li.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.model.Contact;
import li.helper.JDBCHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database operations related to contacts, including fetching contact information
 */
public class ContactDAO {

    /**
     * Fetches all contacts from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContact() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> list = FXCollections.observableArrayList();

        while (rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact newContact = new Contact(contactID, contactName, email);

            list.add(newContact);

        }
        return list;
    }

    /**
     * Fetches a contact based on a specific contact ID
     * @param conID
     * @return
     * @throws SQLException
     */
    public static Contact getContactByID(int conID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);

        ps.setInt(1, conID);

        ResultSet rs = ps.executeQuery();

        Contact newContact = null;
        if (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            newContact = new Contact(contactID, contactName, email);
        }
        return newContact;
    }

    /**
     * Fetches a contact based on a specific contact name
     * @param conName
     * @return
     * @throws SQLException
     */
    public static Contact getContactByName(String conName) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);

        ps.setString(1, conName);

        ResultSet rs = ps.executeQuery();

        Contact newContact = null;
        if (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            newContact = new Contact(contactID, contactName, email);
        }
        return newContact;
    }

}
