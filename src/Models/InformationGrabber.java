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
            connection = DatabaseConnection.getInstance();
            getPassword = connection.prepareStatement(GET_PASSWORD);
            getPassword.setString(1, username);
            ResultSet rs = getPassword.executeQuery();

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
    public String updatePassword(String username, String password, String salt) {return null;}
    public User getUser(String username) {return null;}
    public UserInfo getUserInfo(String username) {return null;}
    public List<UserInfo> getAllUserInfo() {return null;}
    public List<User> getAllUsers() {return null;}
    public String createUser(User user) {return null;}
    public String updateUserAccountType(String username, AccountType accountType) {return null;}
    public String updateUserOrganisation(String username, String organisaitonName) {return null;}
    public OrganisationalUnit getOrganisation(String orgName) {return null;}
    public List<OrganisationalUnit> getAllOrganisations() {return null;}
    public String createOrganisation(OrganisationalUnit organisation) {return null;}
    public String updateOrganisationAsset(String assetType, int assetQuantity) {return null;}
    public List<Order> getOrganisationOrders(String orgName) {return null;}
    public List<Order> getBuyOrders() {return null;}
    public List<Order> getSellOrders() {return null;}
    public List<Order>  getAllOrders() {return null;}
    public String createOrder(Order newOrder) {return null;}
    public String deleteOrder(int OrderID) {return null;}
    public List<String> getAssetTypes() {return null;}
    public String createAsset(String assetName) {return null;}
    public String createTrade(Trade trade) {return null;}
    public List<Trade> getTradeHistory(String AssetType) {return null;}
}
