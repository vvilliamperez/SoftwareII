package models;
/**
 * Class for Countries
 */
public class Country {

    private final int id;
    private final String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}
