package models;
/**
 * Class for Division objects
 */
public class Division {

    private int id, countryId;
    private String division;
    /**
     * Constructor for Division objects
     * @param id The division ID
     * @param division The division name
     * @param countryId The country ID
     */
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
