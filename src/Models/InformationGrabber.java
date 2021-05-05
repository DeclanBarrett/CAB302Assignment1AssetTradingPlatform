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

    public void retrievePassword(String username)
    {
        try
        {
            connection = DatabaseConnection.getInstance();
            getPassword = connection.prepareStatement(GET_PASSWORD);
            getPassword.setString(1, username);
            ResultSet rs = getPassword.executeQuery();
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
