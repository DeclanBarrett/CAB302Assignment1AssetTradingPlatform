package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SQL {
    // Table and Database Creation
    public static final String CREATE_DATABASE =
            "CREATE DATABASE trading_platform;";
    public static final String CREATE_TABLE_OrgHasQuantity =
            "CREATE TABLE OrgHasQuantity (" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_OrganisationalUnit =
            "CREATE TABLE OrganisationalUnit(" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AmountCredits INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE_Users =
            "CREATE TABLE Users(" +
                    "UserName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "OrganisationalUnit VARCHAR(60) NOT NULL," +
                    "AccountType ENUM('User', 'UnitLeader', 'SystemAdmin') NOT NULL," +
                    "HashedPassword VARCHAR(60) NOT NULL," +
                    "Salt VARCHAR(60) NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnit) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";
    public static final String CREATE_TABLE_Order1 =
            "CREATE TABLE Order1(" +
                    "OrderID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL," +
                    "PlaceDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "OrderType ENUM('BUY', 'SELL') NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_Assets =
            "CREATE TABLE Assets(" +
                    "AssetName VARCHAR(60) NOT NULL PRIMARY KEY" +
                    ");";
    public static final String CREATE_TABLE_Trade =
            "CREATE TABLE Trade(" +
                    "TradeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "BuyerOrgName VARCHAR(60) NOT NULL," +
                    "SellerOrgName VARCHAR(60) NOT NULL," +
                    "TradeDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "FOREIGN KEY(BuyerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(SellerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";

    // SQL Queries
    private static final String INSERT_NEW_USER = "INSERT INTO user (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ASSET = "INSERT INTO Assets VALUES ('?');";
    private static final String INSERT_ORGANISATION = "INSERT INTO OrganisationalUnit VALUES ('?', '?');";
    private static final String INSERT_ORDER = "INSERT INTO Order1 (OrganisationalUnitName, PlaceDateMilSecs, AssetQuantity, AssetName, OrderType) " +
                                                "VALUES ('?', '?', '?', '?', '?');";

    private static final String GET_USER = "SELECT * FROM Users WHERE UserName=?";
    private static final String GET_ALL_USERS = "SELECT * FROM Users";
    private static final String GET_SALT = "SELECT Salt FROM Users WHERE UserName=?";
    private static final String GET_ORGANISATION = "SELECT * FROM OrganisationUnit WHERE OrganisationalUnitName=?";
    private static final String GET_ALL_ORGANISATIONS = "SELECT * FROM OrganisationUnit";
    private static final String GET_ORGANISATION_ORDERS = "SELECT * FROM Order WHERE OrganisationalUnitName=?";
    private static final String GET_ORDERS = "SELECT * FROM Order1";
    private static final String GET_ASSET_TYPES = "SELECT * FROM Assets"; // is this correct?
    private static final String GET_TRADE_HISTORY = "SELECT * FROM Trade"; // is this correct?

    private static final String DELETE_ORDER = "DELETE FROM Order1 WHERE OrderID = ?";

    private static final String UPDATE_USER_PASSWORD = "UPDATE Users SET HashedPassword = ?, Salt = ? WHERE UserName = ?";
    private static final String UPDATE_USER_ACCOUNT_TYPE = "UPDATE Users SET AccountType = ? WHERE UserName = ?";
    private static final String UPDATE_USER_ORGANISATION = "UPDATE Users SET OrganisationalUnit = ? WHERE UserName = ?";
    private static final String UPDATE_ORGANISATION_ASSET = "UPDATE OrgHasQuantity SET AssetName = ?, AssetQuantity = ? " +
                                                            "WHERE OrganisationalUnitName = ?";


    // Connection and Statements
    private Connection connection;

    private PreparedStatement AddUser;
    private PreparedStatement AddAsset;
    private PreparedStatement AddOrganisation;
    private PreparedStatement AddOrder;

    private PreparedStatement GetUser;
    private PreparedStatement GetAllUsers;
    private PreparedStatement GetSalt;
    private PreparedStatement GetOrganisation;
    private PreparedStatement GetAllOrganisations;
    private PreparedStatement GetOrganisationOrders;
    private PreparedStatement GetOrders;
    private PreparedStatement GetAssetTypes;
    private PreparedStatement GetTradeHistory;

    private PreparedStatement DeleteOrder;

    private PreparedStatement UpdateUserPassword;
    private PreparedStatement UpdateUserAccountType;
    private PreparedStatement UpdateUserOrganisation;
    private PreparedStatement UpdateOrganisationAsset;

    // This function currently 'works' for the users tables
    public SQL() {
        connection = DatabaseConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_Users);
            AddUser = connection.prepareStatement(INSERT_NEW_USER);
            GetUser = connection.prepareStatement(GET_USER);
            GetAllUsers = connection.prepareStatement(GET_ALL_USERS);
            GetSalt = connection.prepareStatement(GET_SALT);
            UpdateUserPassword = connection.prepareStatement(UPDATE_USER_PASSWORD);
            UpdateUserAccountType = connection.prepareStatement(UPDATE_USER_ACCOUNT_TYPE);
            UpdateUserOrganisation = connection.prepareStatement(UPDATE_USER_ORGANISATION);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void AddUser(String username, String orgUnit, String accType, String hashedPW, String salt)
    {
        try
        {
            connection = DatabaseConnection.getInstance();
            AddUser = connection.prepareStatement(INSERT_NEW_USER);
            AddUser.setString(1, username);
            AddUser.setString(2, orgUnit);
            AddUser.setString(3, accType);
            AddUser.setString(4,hashedPW);
            AddUser.setString(5, salt);

            if(AddUser != null)
            {
                AddUser.executeQuery();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserInfo GetUser(LoginToken token, String username) {
        UserInfo u = new UserInfo();
        ResultSet rs = null;
        try {
            GetUser.setString(1, username);
            rs = GetUser.executeQuery();
            rs.next();
            u.setUserName(rs.getString("username"));
            u.setOrganisationalUnit(rs.getString("organisationalunit"));
            u.setAccountType(rs.getString("accounttype"));
            u.setHashedPassword(rs.getString("hashedpassword"));
            u.setSalt(rs.getString("salt"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }


// Methods to be created
    public String GetSalt(String username) {
        return null;
    }

    public LoginToken AttemptLogin(String username, String password) {
        return null;
    }

    public String AttemptResetPassword(String oldPassword, String newPassword) {
        return null;
    }

    public OrganisationalUnit GetOrganisation(LoginToken token, String orgName) {
        return null;
    }

    public List<Order> GetOrganisationOrders(LoginToken token, String orgName) {
        return null;
    }


    public List<Order> GetAllOrders(LoginToken token) {
        return null;
    }


    public String AddOrder(LoginToken token, Order newOrder) {
        return null;
    }


    public String RemoveOrder(LoginToken token, int orderID) {
        return null;
    }


    public List<String> GetAssetTypes(LoginToken token) {
        return null;
    }


    public List<Trade> GetTradeHistory(LoginToken token, String AssetType) {
        return null;
    }


    public List<UserInfo> GetAllUsers(LoginToken token) {
        return null;
    }

    public String UpdateUserPassword(LoginToken token, String username, String hashedPassword, String salt) {
        return null;
    }


    public String UpdateUserAccountType(LoginToken token, String username, AccountType accountType) {
        return null;
    }


    public String UpdateUserOrganisation(LoginToken token, String username, String organisationName) {
        return null;
    }


    public String AddAsset(LoginToken token, String assetName) {
        return null;
    }


    public String AddOrganisation(LoginToken token, OrganisationalUnit organisation) {
        return null;
    }

    public List<OrganisationalUnit> GetAllOrganisations(LoginToken token) {
        return null;
    }

    public String UpdateOrganisationAsset(LoginToken token, String organisationName, String AssetType, int AssetQuantity) {
        return null;
    }
}
