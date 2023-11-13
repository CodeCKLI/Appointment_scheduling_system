package li.model;

/**
 * Class Country provides a structure for appointment objects. Getters and Setters methods included
 */
public class Country {

    private int countryID;
    private String country;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }
}
