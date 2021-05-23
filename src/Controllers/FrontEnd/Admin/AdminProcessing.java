package Controllers.FrontEnd.Admin;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.User;
import Controllers.BackEnd.Socket.MockSocket;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Utils.UtilFieldCheckers;
import Controllers.Utils.UtilLoginSecurity;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminProcessing {

    public AdminProcessing() {

    }

    /**
     * Creates a new user and handles the security requirements for it
     * @param accountType - account type for the new user
     * @param username - username for user
     * @param password - password to be hashed and sent
     * @param organisationalUnit - the organisation unit of the user
     * @return - a success message
     * @throws Exception - generic error to catch if anything goes wrong
     */
    public String createUser(String accountType, String username, String password, String organisationalUnit) throws Exception {

        //Check fields
        UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(accountType, username, password, organisationalUnit)));

        //Uses the login security utility to generate a salt
        UtilLoginSecurity utilLoginSecurity = new UtilLoginSecurity();
        String salt = utilLoginSecurity.generateSalt();

        //Attempt to parse the account type
        AccountType type = UtilFieldCheckers.checkAccountType(accountType);

        //Create a new user with a new password with the salt passed through it as well
        User newUser = new User(username,
                utilLoginSecurity.hashPassword(password, salt),
                type,
                organisationalUnit,
                salt);

        return MockSocket.getInstance().AddUser(LoginController.GetToken(), newUser);
    }

    /**
     * Handles the editing of a user
     * @param username - username of the user
     * @param password - password of the user
     * @return - as success message
     * @throws Exception generic to throw if something goes wrong
     */
    public String editUserPassword(String username, String password) throws Exception {

        UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(username, password)));

        //Uses the login security utility to generate a salt
        UtilLoginSecurity utilLoginSecurity = new UtilLoginSecurity();
        String salt = utilLoginSecurity.generateSalt();

        return MockSocket.getInstance().UpdateUserPassword(LoginController.GetToken(),
                username,
                utilLoginSecurity.hashPassword(password, salt),
                salt);
    }

}
