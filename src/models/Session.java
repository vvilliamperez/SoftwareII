package models;
import DAO.UserDaoImpl;
import utils.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * Constructor for Session
     * Sets the locale
     */
    public Session(){
        setLocale();
    }

    /**
     * Sets the locale and resource bundle
     */
    private void setLocale(){
        System.out.println("Setting Language");
        System.out.println(Locale.getDefault());
        localeStrings = ResourceBundle.getBundle("localization/rb");
    }

    /**
     * Gets a string from the resource bundle
     * @param key The key for the string
     * @return The string
     */
    public String getString(String key){
        //Passthrough function for less verbosity
        //Gets strings from resource bundle
        return localeStrings.getString(key);
    }


    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs in a user
     * @param user The username
     * @param pass The password
     * @throws Exception If the user is not found or the password is incorrect
     */
    public void logUserIn(String user, String pass) throws Exception {
        currentUser = UserDaoImpl.getUserByUserName(user);
        if (currentUser == null || !currentUser.passwordVerified(pass)){
            currentUser = null;
            logLoginAttempt(user, pass, false);
            throw new Exception(getString("authErrorMessage"));

        }
        else {
            logLoginAttempt(user, "********", true);
        }
    }

    /**
     * Logs a login attempt to a file
     * @param user The username
     * @param pass The password
     * @param success Whether the login was successful
     */
    private void logLoginAttempt(String user, String pass, boolean success) {
        // Log the login attempt to a login_activity.txt file
        // Format: [timestamp] [username] [success]
        String fileName = "login_activity.txt"; // The file to log to
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter); // Get the current timestamp
        String status = success ? "SUCCESS" : "FAILURE"; // Convert success to a readable status

        // Construct the log entry
        String logEntry = String.format("[%s] [Username: %s] [Password: %s] [Status: %s]%n", timestamp, user, pass, status);

        try (FileWriter fileWriter = new FileWriter(fileName, true)) { // Open in append mode
            fileWriter.write(logEntry); // Write the log entry to the file
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
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
