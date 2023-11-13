package li.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.helper.JDBCHelper;
import li.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database operations related to countries, including fetching country information
 */
public class CountryDAO {

    /**
     * Fetches a country based on a specific country ID
     * @param selectedCountryID
     * @return
     * @throws SQLException
     */
    public static Country getCountryByCountryID(int selectedCountryID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, selectedCountryID);
        ResultSet rs = ps.executeQuery();
        Country newCountry = null;
        if (rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            newCountry = new Country(countryID, country);
        }

        return newCountry;

    }


    /**
     * Fetches a country based on a specific country name
     * @param countryName
     * @return
     * @throws SQLException
     */
    public static Country getCountryByCountryName(String countryName) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.countries WHERE Country = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        Country newCountry = null;
        if (rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            newCountry = new Country(countryID, country);
        }

        return newCountry;

    }

    /**
     * Fetches all countries from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountry() throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.countries";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ObservableList<Country> list = FXCollections.observableArrayList();

        while (rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            Country newCountry = new Country(countryID, country);

            list.add(newCountry);

        }

        return list;

    }

}
