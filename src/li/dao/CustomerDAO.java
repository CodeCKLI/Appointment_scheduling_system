package li.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.helper.ConversionsHelper;
import li.helper.JDBCHelper;
import li.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database operations related to customers, including fetching, adding, updating, and deleting customer information
 */
public class CustomerDAO {

    /**
     * Fetches customers based on a specific country ID
     * @param selectedCountryID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomerByCountryID(int selectedCountryID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.customers;";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customer> list = FXCollections.observableArrayList();

        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divID = rs.getInt("Division_ID");

            int countryID = ConversionsHelper.divisionIDToCountryID(divID);

            if (countryID == selectedCountryID){
                Customer newCustomer = new Customer(customerID, customerName, address, postal, phone, divID);
                list.add(newCustomer);
            }

        }

        return list;

    }

    /**
     * Adds a new customer to the database
     * @param cusName
     * @param address
     * @param postal
     * @param phone
     * @param div
     * @throws SQLException
     */
    public static void addCustomer(String cusName, String address, String postal, String phone, int div) throws SQLException {

        String sqlQuery = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) values (?, ?, ?, ?, ?)";

        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, cusName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setInt(5, div);

        ps.executeUpdate();

    }

    /**
     * Updates customer information in the database
     * @param cusName
     * @param address
     * @param postal
     * @param phone
     * @param div
     * @param customerID
     * @throws SQLException
     */
    public static void updateCustomer(String cusName, String address, String postal, String phone, int div, int customerID) throws SQLException {

        String sqlQuery = "UPDATE client_schedule.customers set Customer_NAME = ? , Address = ? , Postal_Code = ? , Phone = ? , Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, cusName);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setInt(5, div);
        ps.setInt(6, customerID);

        ps.executeUpdate();

    }

    /**
     * Deletes a customer from the database
     * @param customerID
     * @throws SQLException
     */
    public static void delCustomer(int customerID) throws SQLException {

        String sqlQuery = "delete FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);

        ps.setInt(1, customerID);

        ps.executeUpdate();

    }

    /**
     * Fetches all customers from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomer() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.customers;";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customer> list = FXCollections.observableArrayList();

        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divID = rs.getInt("Division_ID");

            Customer newCustomer = new Customer(customerID, customerName, address, postal, phone, divID);

            list.add(newCustomer);

        }

        return list;

    }

}
