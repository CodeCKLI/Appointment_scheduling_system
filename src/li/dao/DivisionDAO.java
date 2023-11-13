package li.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import li.helper.JDBCHelper;
import li.model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database operations related to divisions, including fetching division information based on various conditions
 */
public class DivisionDAO {

    /**
     * Fetches a division's ID based on its name
     * @param divisionName
     * @return
     * @throws SQLException
     */
    public static Division getDivisionIDByDivisionName(String divisionName) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        Division newDivision = null;
        if (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countyID = rs.getInt("Country_ID");

            newDivision = new Division(divisionID, division, countyID);
        }
        return newDivision;
    }

    /**
     * Fetches all divisions associated with a specific country ID
     * @param countryID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Division> getAllDivision(int countryID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.first_level_divisions where Country_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countyID = rs.getInt("Country_ID");

            Division newDivision = new Division(divisionID, division, countyID);

            list.add(newDivision);

        }

        return list;
    }

    /**
     * Fetches a division's information based on its ID
     * @param divID
     * @return
     * @throws SQLException
     */
    public static Division getDivisionByID(int divID) throws SQLException {
        String sqlQuery = "SELECT * FROM client_schedule.first_level_divisions Where Division_ID = ?";
        PreparedStatement ps = JDBCHelper.connection.prepareStatement(sqlQuery);
        ps.setInt(1, divID);
        ResultSet rs = ps.executeQuery();
        Division newDivision = null;
        if (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countyID = rs.getInt("Country_ID");

            newDivision = new Division(divisionID, division, countyID);
        }
        return newDivision;
    }
}
