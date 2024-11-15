package models;
/**
 * Class for Division objects
 */
public class Division {

    private int id, countryId;
    private String division;

    public Division(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    public String getName() {
        return division;
    }

    public int getID() {
        return id;
    }
    
    public int getCountryID() {
        return countryId;
    }
}
