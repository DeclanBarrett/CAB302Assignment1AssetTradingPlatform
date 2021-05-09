package Models;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Finds information from the backend
 */
public class  InformationGrabber {

    //SQL queries for user, login and reset password

    private static final String INSERT_NEW_USER = "INSERT INTO user (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_PASSWORD = "UPDATE user SET Password=?, Salt=? WHERE UserName=?";

    private static final String GET_NONCE = "SELECT Salt FROM User WHERE UserName=?";

    private static final String GET_PASSWORD = "SELECT HashedPassword FROM User WHERE UserName=?";

    private static final String GET_USER = "SELECT * FROM user WHERE UserName=?";

    private PreparedStatement addUser;

    private PreparedStatement updatePassword;

    private PreparedStatement getNonce;

    private PreparedStatement getPassword;

    private PreparedStatement getUser;

    private Connection connection;



    /**
     * Inserts user into the database.
     * @param username - users username
     * @param orgUnit - organisational unit user belongs too
     * @param accType - Account type user has
     * @param hashedPW - hashed password attached to the user
     * @param salt - salt attached to users password
     */
    public void insertUser(String username, String orgUnit, String accType, String hashedPW, String salt)
    {
        try
        {
            connection = DatabaseConnection.getInstance();
            addUser = connection.prepareStatement(INSERT_NEW_USER);
            addUser.setString(1, username);
            addUser.setString(2, orgUnit);
            addUser.setString(3, accType);
            addUser.setString(4,hashedPW);
            addUser.setString(5, salt);

            if(addUser != null)
            {
                addUser.executeQuery();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Retrieves password from database
     * @param username
     */
    public void retrievePassword(String username)
    {
        ResultSet rs = null;
        try
        {
            // check for null;
            connection = DatabaseConnection.getInstance();
            getPassword = connection.prepareStatement(GET_PASSWORD);
            getPassword.setString(1, username);
            rs = getPassword.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Retrieve salt from database
     * @param username salt attached to this users password
     */
    public void retrieveSalt(String username)
    {
        try
        {
            connection = DatabaseConnection.getInstance();
            getNonce = connection.prepareStatement(GET_NONCE);
            getNonce.setString(1, username);


            if(getNonce != null)
            {
                ResultSet rs = getNonce.executeQuery();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Update users password
     * @param username - users username
     * @param password - users password
     * @param salt - salt attached to users password
     */
    public void updatePassword(String username, String password, String salt){
        try
        {
            updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
            updatePassword.setString(1, password);
            updatePassword.setString(2, salt);
            updatePassword.setString(3, username);

            if(updatePassword != null)
            {
                updatePassword.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets user from the database
     * @param username - username of requested user.
     */
    public void getUser(String username)
    {
        try
        {
            getUser = connection.prepareStatement(GET_USER);
            getUser.setString(1,username);

            if(getUser != null)
            {
                getUser.executeQuery();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    /**
     * Get the list of Orders
     * @return the list of orders
     */
    public List<Order> GetOrderList() {
        return null;
    }


    /**
     * Returns organisational unit
     * @return organisational unit
     */
    public OrganisationalUnit GetOrganisationalUnit() {
        return null;
    }
}
