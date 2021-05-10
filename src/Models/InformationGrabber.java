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
public class InformationGrabber {

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
     *
     * @param username
     * @return
     */
    public String getSalt(String username) {
        try
        {
            connection = DatabaseConnection.getInstance();
            getNonce = connection.prepareStatement(GET_NONCE);
            getNonce.setString(1, username);
            ResultSet rs = getNonce.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves password from database
     * @param username
     */
    public String getPassword(String username) {
        try
        {
            connection = DatabaseConnection.getInstance();
            getPassword = connection.prepareStatement(GET_PASSWORD);
            getPassword.setString(1, username);
            ResultSet rs = getPassword.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
