package models;
/**
 * Class for Countries
 */
public class Country {

    private int id;
    private String country;

    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    public int getID() {
        return id;
    }

    public String getCountry() {
        return country;
    }
}
