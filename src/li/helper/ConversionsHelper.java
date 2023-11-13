package li.helper;

import li.dao.ContactDAO;
import li.dao.CountryDAO;
import li.dao.DivisionDAO;
import li.model.Contact;
import li.model.Country;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Contains various methods for converting and transforming data
 */
public class ConversionsHelper {

    /**
     * Converts a given LocalDateTime to Eastern Time (ET) by adjusting the time based on the time zone offsets
     * @param LocalTime
     * @return
     */
    public static LocalDateTime LocalTimeToET(LocalDateTime LocalTime){
        LocalDateTime newTime = null;

        ZoneId desiredTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime currentTimeInDesiredTimeZone = ZonedDateTime.now(desiredTimeZone);
        ZoneOffset offsetToUTC = currentTimeInDesiredTimeZone.getOffset();

        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentTimeInSystemTimeZone = ZonedDateTime.now(systemTimeZone);
        ZoneOffset systemOffsetToUTC = currentTimeInSystemTimeZone.getOffset();

        int comparedSecond = offsetToUTC.compareTo(systemOffsetToUTC);

        if (comparedSecond > 0 | comparedSecond < 0){
            newTime = LocalTime.plusSeconds(comparedSecond);
        } else {
            newTime = LocalTime;
        }
        return newTime;
    }

    /**
     * Converts a given LocalDateTime to Coordinated Universal Time (UTC) by adjusting the time based on the time zone offsets
     * @param LocalTime
     * @return
     */
    public static LocalDateTime LocalTimeToUTC(LocalDateTime LocalTime){
        LocalDateTime newTime = null;

        ZoneId desiredTimeZone = ZoneId.of("GMT");
        ZonedDateTime currentTimeInDesiredTimeZone = ZonedDateTime.now(desiredTimeZone);
        ZoneOffset offsetToUTC = currentTimeInDesiredTimeZone.getOffset();

        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentTimeInSystemTimeZone = ZonedDateTime.now(systemTimeZone);
        ZoneOffset systemOffsetToUTC = currentTimeInSystemTimeZone.getOffset();

        int comparedSecond = offsetToUTC.compareTo(systemOffsetToUTC);

        if (comparedSecond > 0 | comparedSecond < 0){
            newTime = LocalTime.minusSeconds(comparedSecond);
        }else {
            newTime = LocalTime;
        }
        return newTime;
    }

    /**
     * Converts a given UTC LocalDateTime to the local system's time by adjusting the time based on the time zone offsets
     * @param UTCTime
     * @return
     */
    public static LocalDateTime UTCtoLocalTime(LocalDateTime UTCTime){

        LocalDateTime newTime = null;

        ZoneId desiredTimeZone = ZoneId.of("GMT");
        ZonedDateTime currentTimeInDesiredTimeZone = ZonedDateTime.now(desiredTimeZone);
        ZoneOffset offsetToUTC = currentTimeInDesiredTimeZone.getOffset();

        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentTimeInSystemTimeZone = ZonedDateTime.now(systemTimeZone);
        ZoneOffset systemOffsetToUTC = currentTimeInSystemTimeZone.getOffset();

        int comparedSecond = offsetToUTC.compareTo(systemOffsetToUTC);

        if (comparedSecond > 0 | comparedSecond < 0){
            newTime = UTCTime.plusSeconds(comparedSecond);
        } else {
            newTime = UTCTime;
        }

        return newTime;
    }

    /**
     * Converts a country ID to its corresponding country name
     * @param countryID
     * @return
     * @throws SQLException
     */
    public static String countryIDToCountryName (int countryID) throws SQLException {

        Country result = CountryDAO.getCountryByCountryID(countryID);

        return result.getCountry();
    }

    /**
     * Converts a country name to its corresponding country ID
     * @param countryName
     * @return
     * @throws SQLException
     */
    public static int countryNameToCountryID (String countryName) throws SQLException {

        Country result = CountryDAO.getCountryByCountryName(countryName);

        return result.getCountryID();
    }

    /**
     * Converts a division ID to its corresponding country ID
     * @param divID
     * @return
     * @throws SQLException
     */
    public static int divisionIDToCountryID(int divID) throws SQLException {
        return DivisionDAO.getDivisionByID(divID).getCountyID();
    }

    /**
     * Converts a contact name to its corresponding contact ID
     * @param contact
     * @return
     * @throws SQLException
     */
    public static int contactNameToID(String contact) throws SQLException {
        Contact result = ContactDAO.getContactByName(contact);
        return result.getContactID();
    }

    /**
     * Converts a month to its corresponding integer value
     * @param month
     * @return
     */
    public static int monthsToInt(String month){

        if (month.equals("Jan")){
            return 1;
        }
        if (month.equals("Feb")){
            return 2;
        }
        if (month.equals("Mar")){
            return 3;
        }
        if (month.equals("Apr")){
            return 4;
        }
        if (month.equals("May")){
            return 5;
        }
        if (month.equals("Jun")){
            return 6;
        }
        if (month.equals("Jul")){
            return 7;
        }
        if (month.equals("Aug")){
            return 8;
        }
        if (month.equals("Sep")){
            return 9;
        }
        if (month.equals("Oct")){
            return 10;
        }
        if (month.equals("Nov")){
            return 11;
        }
        if (month.equals("Dec")){
            return 12;
        }

        return 0;
    }

}
