package li.model;

/**
 * Class Division provides a structure for appointment objects. Getters and Setters methods included
 */
public class Division {

    private int divisionID;
    private String division;
    private int countyID;

    public Division(int divisionID, String division, int countyID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countyID = countyID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountyID() {
        return countyID;
    }

    public void setCountyID(int countyID) {
        this.countyID = countyID;
    }
}
