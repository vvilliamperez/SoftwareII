package models;
import DAO.UserDaoImpl;
import utils.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

/*
    Session Class

    Holds all data related to a login session
    Handles login/logout, manipulating current calendar, and locale
 */
public class Session {
    private final Constants strings = new Constants();
    private User currentUser;
    private ResourceBundle lStrings;

    public Session(){
        setLocale();
    }


    private void setLocale(){
        System.out.println("Setting Language");
        System.out.println(Locale.getDefault());
        lStrings = ResourceBundle.getBundle("localization/rb");
    }

    public String getString(String key){
        //Passthrough function for less verbosity
        //Gets strings from resource bundle
        return lStrings.getString(key);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    //User Login/Logout
    public void logUserIn(String user, String pass) throws Exception {
        currentUser = UserDaoImpl.getUser(user);
        if (currentUser == null || !currentUser.passwordVerified(pass)){
            currentUser = null;
            //TODO: translation for each language
            throw new Exception("Bad Username / Password");
        }
    }

    public boolean logUserOut() {
        currentUser = null;
        return true;
    }





}
