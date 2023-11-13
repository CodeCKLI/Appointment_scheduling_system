package li.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.dao.*;
import li.model.*;

import java.sql.SQLException;

/**
 * Provides helper methods to fetch and manipulate data for various entities
 */
public class DataHelper {

    /**
     * Fetches all appointments for the current week
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> fetchAllAppointmentsByWeek() throws SQLException {
        return AppDAO.getAppByWeek();
    }

    /**
     * Fetches all appointments associated with a specific customer ID
     * @param selectedCustomer
     * @return
     */
    public static ObservableList<Appointment> fetchAllAppointmentsByCustomerID(int selectedCustomer){
        ObservableList<Appointment> appList = null;
        try {
            appList = AppDAO.getAppByCustomerID(selectedCustomer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appList;
    }

    /**
     * Fetches all appointments of a specific type
     * @param selectedType
     * @return
     */
    public static ObservableList<Appointment> fetchAllAppointmentsByType(String selectedType){
        ObservableList<Appointment> appList = null;
        try {
            appList = AppDAO.getAppByType(selectedType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appList;
    }

    /**
     * Fetches all appointments for a specific month
     * @param selectedMonth
     * @return
     */
    public static ObservableList<Appointment> fetchAllAppointmentsByMonth(int selectedMonth){
        ObservableList<Appointment> appList = null;
        try {
            appList = AppDAO.getAppByMonth(selectedMonth);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appList;
    }

    /**
     * Fetches all appointments associated with a specific contact ID
     * @param contactID
     * @return
     */
    public static ObservableList<Appointment> fetchAllAppointmentsByContactID(int contactID){
        ObservableList<Appointment> appList = null;
        try {
            appList = AppDAO.getAppByContactID(contactID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appList;
    }

    /**
     * Fetches all customers associated with a specific country ID
     * @param countryID
     * @return
     */
    public static ObservableList<Customer> fetchAllCustomersByCountryID(int countryID){
        ObservableList<Customer> customerList = null;
        try {
            customerList = CustomerDAO.getCustomerByCountryID(countryID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Fetches all appointments
     * @return
     */
    public static ObservableList<Appointment> fetchAllAppointments(){
        ObservableList<Appointment> appList = null;
        try {
            appList = AppDAO.getAllApp();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appList;
    }

    /**
     * Fetches all customers
     * @return
     */
    public static ObservableList<Customer> fetchAllCustomers(){
        //Get customer data from DB
        ObservableList<Customer> customerList = null;
        try {
            customerList = CustomerDAO.getAllCustomer();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Fetches a division based on its division ID
     * @param divisionID
     * @return
     */
    public static Division fetchDivisionByDivisionID(int divisionID){
        Division resultDivision = null;
        try {
            resultDivision = DivisionDAO.getDivisionByID(divisionID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultDivision;
    }

    /**
     * Fetches division names associated with a specific country ID
     * @param countryID
     * @return
     */
    public static ObservableList<String> fetchDivisionNamesByCountryID(int countryID){
        //Fetch Division list according to the Country ID
        ObservableList<Division> divisionList = null;
        try {
            divisionList = DivisionDAO.getAllDivision(countryID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> divisionArrayList = FXCollections.observableArrayList();
        //Lambda Expression
        divisionList.forEach(division -> {
                    String name = division.getDivision();
                    String divisionString = name;
                    divisionArrayList.add(divisionString);
                }
        );
        return divisionArrayList;
    }

    /**
     * Fetches all country names using the CountryDAO class and returns them as an observable list of strings
     * @return
     */
    public static ObservableList<String> fetchAllCountryNames(){

        //Try fetching country list from Database
        ObservableList<Country> countryList = null;
        try {
            countryList = CountryDAO.getAllCountry();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> countryArraylist = FXCollections.observableArrayList();
        //Lambda Expression #1
        countryList.forEach(country -> {
                    String name = country.getCountry();
                    countryArraylist.add(name);
                }
        );
        return countryArraylist;

    }

    /**
     * Fetches a contact based on its contact ID
     * @param contactID
     * @return
     */
    public static Contact fetchContactByContactID(int contactID){
        Contact selectedContact = null;
        try {
            selectedContact = ContactDAO.getContactByID(contactID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return selectedContact;
    }

    /**
     * Fetches all contact names using the ContactDAO class and returns them as an observable list of strings
     * @return
     */
    public static ObservableList<String> fetchAllContactNames(){

        //Fetching Contact list from Database
        ObservableList<Contact> contactList = null;
        try {
            contactList = ContactDAO.getAllContact();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> contactArraylist = FXCollections.observableArrayList();
        //Lambda Expression #1
        contactList.forEach(contact -> {
                    String name = contact.getContactName();
                    contactArraylist.add(name);
                }
        );
        return contactArraylist;
    }

}
