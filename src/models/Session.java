package models;
import DAO.UserDaoImpl;
import utils.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for Session object
 * holds the logged in user as well as the currently editing appointment or customer
 * holds the resource bundle for localization
 * provides a methods to retrieve localized strings
 */
public class Session {
    private final Constants strings = new Constants();
    private User currentUser;
    private Customer currentCustomer;
    private Appointment currentAppointment;
    private ResourceBundle localeStrings;

    public Session(){
        setLocale();
    }


    private void setLocale(){
        System.out.println("Setting Language");
        System.out.println(Locale.getDefault());
        localeStrings = ResourceBundle.getBundle("localization/rb");
    }

    public String getString(String key){
        //Passthrough function for less verbosity
        //Gets strings from resource bundle
        return localeStrings.getString(key);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    //User Login/Logout
    public void logUserIn(String user, String pass) throws Exception {
        currentUser = UserDaoImpl.getUserByUserName(user);
        if (currentUser == null || !currentUser.passwordVerified(pass)){
            currentUser = null;
            //TODO: translation for each language
            throw new Exception(getString("authErrorMessage"));
        }
    }

    public boolean logUserOut() {
        currentUser = null;
        return true;
    }


    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Appointment getCurrentAppointment() {
        return currentAppointment;
    }

    public void setCurrentAppointment(Appointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
