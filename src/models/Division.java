package models;

public class Division {

    private int id, country_id;
    private String division;

    public Division(int id, String division, int fk) {
        this.id = id;
        this.division = division;
        country_id = fk;
    }

    public String getName() {
        return division;
    }

    public int getID() {
        return id;
    }
}
