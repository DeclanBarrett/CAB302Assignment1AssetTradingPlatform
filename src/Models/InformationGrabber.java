package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;

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
    public void getPassword(String username)
    {
        ResultSet rs = null;
        try
        {
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
    public void getSalt(String username)
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
     * @return the User with login info included
     */
    public User getUser(String username)
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

        return null;
    }
    /**
     * Get the list of Orders
     * @return the list of orders
     */
    public List<Order> GetOrderList() {
        return null;
    }

    /**
     * Gets user info from the database
     * @param username - username of requested user.
     * @return the User with no login info included
     */
    public UserInfo getUserInfo(String username) {return null;}

    /**
     * Gets list of user info for all users from the database
     * @return the list of user information
     */
    public List<UserInfo> getAllUserInfo() {return null;}

    /**
     * Gets list of users for all users from the database
     * @return the list of users with login info
     */
    public List<User> getAllUsers() {return null;}

    /**
     * Inserts a new user into the database
     * (Warning - Users cannot be removed from the database)
     * (BUT they can have their Account Type be changed to inactive)
     * @param user - the user to be inserted (with login info)
     * @return a success message
     */
    public String insertUser(User user) {return null;}

    /**
     * Updates the account type of the user in the database
     * @param username - username of updated user.
     * @param accountType - account type to change the users account type to
     * @return a success message
     */
    public String updateUserAccountType(String username, AccountType accountType) {return null;}

    /**
     * Updates the organisation of the user in the database
     * @param username - username of the updated user.
     * @param organisationName - organisation to change the users organisation to
     * @return a success message
     */
    public String updateUserOrganisation(String username, String organisationName) {return null;}

    /**
     * Gets the organisational unit object in the database
     * @param orgName - organisation name of the organisation
     * @return the organisation unit object with all included information
     */
    public OrganisationalUnit getOrganisation(String orgName) {return null;}

    /**
     * Gets a list of all organisational units with info in the database
     * @return a list of organisationa unit objects
     */
    public List<OrganisationalUnit> getAllOrganisations() {return null;}

    /**
     * Insert a new organisation into the database
     * (Warning - Organisations cannot be removed from the database)
     * (Warning - All asset types must be valid asset types)
     * @param organisation - organisation with all information to be inserted
     * @return a success message
     */
    public String insertOrganisation(OrganisationalUnit organisation) {return null;}

    /**
     * Update the quantity of an asset in an organisation, either by inserting a new
     * asset or updating an existing asset in the database
     * @param organisationName - organisation to have its asset changed
     * @param assetType - the asset type to be added or updated
     * @param assetQuantity - quantity to set the asset to
     * @return a success message
     */
    public String updateOrganisationAsset(String organisationName, String assetType, int assetQuantity) {return null;}

    /**
     * Gets a list of all orders that an organisation currently has active in the database
     * @param orgName - the organisation to retrieve orders for
     * @return a list of orders both buy and sell
     */
    public List<Order> getOrganisationOrders(String orgName) {return null;}

    /**
     * Gets all buy orders that are currently active in the database
     * @return a list of all orders with the orderType BUY
     */
    public List<Order> getBuyOrders() {return null;}

    /**
     * Gets all sell orders that are currently active in the database
     * @return a list of all orders with the orderType SELL
     */
    public List<Order> getSellOrders() {return null;}

    /**
     * Gets all orders that are currently active in the database
     * @return a list of all orders, regardless of type
     */
    public List<Order> getAllOrders() {return null;}

    /**
     * Inserts a new order into the database
     * @param newOrder - the order with all information to be inserted
     * @return a success message
     */
    public String insertOrder(Order newOrder) {return null;}

    /**
     * Deletes an order in the database
     * @param OrderID - the orderID of the order to be deleted
     * @return a success message
     */
    public String deleteOrder(int OrderID) {return null;}

    /**
     * Gets a list of all the asset types that are in the database
     * @return a list of asset types
     */
    public List<String> getAssetTypes() {return null;}

    /**
     * Inserts a new asset type into the database, so that orders etc
     * cannot be placed with an invalid asset type
     * (Warning - asset types cannot be removed from the database)
     * @param assetName - asset to be added
     * @return a success message
     */
    public String insertAsset(String assetName) {return null;}

    /**
     * Inserts a new trade into the database
     * (Warning - a trade is a measure of history thus they cannot
     * be removed from the database)
     * @param trade - trade to be added
     * @return a success message
     */
    public String insertTrade(Trade trade) {return null;}

    /**
     * Gets a list of all previous trades that occurred for an asset type
     * @param AssetType - asset type of the trades
     * @return a list of trades
     */
    public List<Trade> getTradeHistory(String AssetType) {return null;}
}
