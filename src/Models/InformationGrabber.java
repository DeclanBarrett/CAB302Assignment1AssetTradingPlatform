package Models;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.ServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Finds information from the backend
 */
public class  InformationGrabber {

    //PopulateDatabase queries

    private static final String INSERT_NEW_USER = "INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ASSET = "INSERT INTO Assets VALUES (?);";
    private static final String INSERT_ORGANISATION = "INSERT INTO OrganisationalUnit (OrganisationalUnitName, AmountCredits) VALUES (?, ?);";
    private static final String INSERT_ORDER = "INSERT INTO Orders (OrganisationalUnitName, PlaceDateMilSecs, AssetQuantity, AssetName, AssetPrice, OrderType) " +
                                               "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String INSERT_TRADE = "INSERT INTO Trade (BuyerOrgName, SellerOrgName, TradeDateMilSecs, AssetQuantity, AssetName, AssetPrice) " +
                                               "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String INSERT_ORGANISATION_ASSETS = "INSERT INTO orghasquantity (OrganisationalUnitName, AssetName, AssetQuantity) VALUES (?, ?, ?)";


    private static final String UPDATE_PASSWORD = "UPDATE Users SET HashedPassword=? WHERE UserName=?";
    private static final String UPDATE_USER_ACCOUNT_TYPE = "UPDATE Users SET AccountType = ? WHERE UserName = ?";
    private static final String UPDATE_USER_ORGANISATION = "UPDATE Users SET OrganisationalUnit = ? WHERE UserName = ?";
    private static final String UPDATE_ORGANISATION_ASSET = "UPDATE OrgHasQuantity SET AssetQuantity = ? WHERE OrganisationalUnitName = ? AND AssetName = ? ";
    private static final String UPDATE_ORGANISATION_CREDITS = "UPDATE OrganisationalUnit SET AmountCredits = ? WHERE OrganisationalUnitName = ?";
    private static final String UPDATE_ORDER = "UPDATE Orders SET AssetPrice = ? WHERE OrderID = ?";

    private static final String GET_NONCE = "SELECT Salt FROM Users WHERE UserName=?";
    private static final String GET_PASSWORD = "SELECT HashedPassword FROM Users WHERE UserName=?";
    private static final String GET_USER = "SELECT * FROM Users WHERE UserName=?";

    private static final String GET_ALL_USERS = "SELECT * FROM Users";
    private static final String GET_SALT = "SELECT Salt FROM Users WHERE UserName=?";
    private static final String GET_ORGANISATION = "SELECT * FROM OrganisationalUnit WHERE OrganisationalUnitName=?";
    private static final String GET_ALL_ORGANISATIONS = "SELECT * FROM OrganisationalUnit";
    private static final String GET_ORGANISATION_ASSETS = "SELECT * FROM OrgHasQuantity WHERE OrganisationalUnitName=?";
    private static final String GET_ORGANISATION_INDIVIDUAL_ASSETS = "SELECT AssetQuantity FROM OrgHasQuantity WHERE OrganisationalUnitName=? AND AssetName=? ";
    private static final String GET_ORGANISATION_ORDERS = "SELECT * FROM Orders WHERE OrganisationalUnitName=?";
    private static final String GET_ORGANISATION_BUY_ORDERS = "SELECT * FROM Orders WHERE OrganisationalUnitName=? AND OrderType='BUY'";
    private static final String GET_ORGANISATION_SELL_ORDERS = "SELECT * FROM Orders WHERE OrganisationalUnitName=? AND OrderType='SELL'";


    private static final String GET_BUY_ORDERS = "SELECT * FROM Orders WHERE OrderType='BUY'";
    private static final String GET_SELL_ORDERS = "SELECT * FROM Orders WHERE OrderType='SELL'";
    private static final String GET_ORDER_LIST = "SELECT * FROM Orders";
    private static final String GET_ALL_ORDERS = "SELECT * FROM Orders"; //fix to be diff from prev

    private static final String GET_ASSET_TYPES = "SELECT * FROM Assets";
    private static final String GET_TRADE_HISTORY = "SELECT * FROM Trade WHERE AssetName=?";
    private static final String GET_ALL_TRADE_HISTORY = "SELECT * FROM Trade";

    private static final String DELETE_ORDER = "DELETE FROM Orders WHERE OrderID = ?";


    // Prepared statements for all previous queries + Connection
    private PreparedStatement addUser;
    private PreparedStatement addAsset;
    private PreparedStatement addOrganisation;
    private PreparedStatement addOrder;
    private PreparedStatement addTrade;
    private PreparedStatement addOrganisationAsset;

    private PreparedStatement updatePassword;
    private PreparedStatement updateUserAccountType;
    private PreparedStatement updateUserOrganisation;
    private PreparedStatement updateOrganisationAsset;
    private PreparedStatement updateOrganisationCredits;
    private PreparedStatement updateOrder;

    private PreparedStatement getNonce;
    private PreparedStatement getPassword;
    private PreparedStatement getUser;
    private PreparedStatement getAllUsers;
    private PreparedStatement getSalt;
    private PreparedStatement getOrganisation;
    private PreparedStatement getOrganisationAssets;
    private PreparedStatement getOrganisationIndividualAssets;
    private PreparedStatement getAllOrganisations;
    private PreparedStatement getOrganisationOrders;
    private PreparedStatement getBuyOrders;
    private PreparedStatement getSellOrders;
    private PreparedStatement getOrderList;
    private PreparedStatement getAllOrders;
    private PreparedStatement getAssetTypes;
    private PreparedStatement getTradeHistory;

    private PreparedStatement getOrganisationBuyOrders;
    private PreparedStatement getOrganisationSellOrders;

    private PreparedStatement deleteOrder;

    private Connection connection;

    /**
     * Inserts user into the database.
     * @param username - users username
     * @param orgUnit - organisational unit user belongs too
     * @param accType - Account type user has
     * @param hashedPW - hashed password attached to the user
     * @param salt - salt attached to users password
     */
    public void insertUser(String username, String orgUnit, AccountType accType, String hashedPW, String salt)
    {
        try
        {
            connection = DatabaseConnection.getInstance();
            addUser = connection.prepareStatement(INSERT_NEW_USER);
            addUser.setString(1, username);
            addUser.setString(2, orgUnit);
            addUser.setString(3, accType.toString());
            addUser.setString(4, hashedPW);
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
     * Inserts a new asset type into the database, so that orders etc
     * cannot be placed with an invalid asset type
     * (Warning - asset types cannot be removed from the database)
     * @param assetName - asset to be added
     * @return a success message
     */
    public String insertAsset(String assetName) {
        try
        {
            connection = DatabaseConnection.getInstance();
            addAsset = connection.prepareStatement(INSERT_ASSET);
            addAsset.setString(1, assetName);


            if(addAsset != null)
            {
                addAsset.executeQuery();
                return "Successfully insert asset";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Insert a new organisation into the database
     * (Warning - Organisations cannot be removed from the database)
     * (Warning - All asset types must be valid asset types)
     * @param organisation - organisation with all information to be inserted
     * @return a success message
     */
    public String insertOrganisation(OrganisationalUnit organisation) {
        try
        {
            connection = DatabaseConnection.getInstance();
            addOrganisation = connection.prepareStatement(INSERT_ORGANISATION);
            addOrganisation.setString(1, organisation.getUnitName());
            addOrganisation.setDouble(2, organisation.getCredits());

            if(addOrganisation != null)
            {
                addOrganisation.executeQuery();
                return "Successfully insert organisation";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a new order into the database
     * @param newOrder - the order with all information to be inserted
     * @return a success message
     */
    public String insertOrder(Order newOrder) {
        try {
            connection = DatabaseConnection.getInstance();
            addOrder = connection.prepareStatement(INSERT_ORDER);
            addOrder.setString(1, newOrder.getOrganisationalUnit());
            addOrder.setLong(2, new Date().getTime());
            addOrder.setInt(3, newOrder.getAssetQuantity());
            addOrder.setString(4, newOrder.getAssetType());
            addOrder.setDouble(5, newOrder.getRequestPrice());
            addOrder.setString(6, newOrder.getOrderType().toString());

            if (addOrder != null) {
                addOrder.executeQuery();
                return "Successfully inserted order";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a new trade into the database
     * (Warning - a trade is a measure of history thus they cannot
     * be removed from the database)
     * @param trade - trade to be added
     * @return a success message
     */
    public String insertTrade(Trade trade) {
        try {
            connection = DatabaseConnection.getInstance();
            addTrade = connection.prepareStatement(INSERT_TRADE);
            addTrade.setString(1, trade.getBuyerOrgName());
            addTrade.setString(2, trade.getSellerOrgName());
            addTrade.setLong(3, trade.getTradeDateMilSecs().getTime());
            addTrade.setInt(4, trade.getAssetQuantity());
            addTrade.setString(5, trade.getAssetName());
            addTrade.setDouble(6, trade.getAssetPrice());

            if (addTrade != null) {
                addTrade.executeQuery();
                return "Successfully inserted trade";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * Update users password
     * @param username - users username
     * @param password - users password
     */
    public void updatePassword(String username, String password){
        try
        {
            connection = DatabaseConnection.getInstance();
            updatePassword = connection.prepareStatement(UPDATE_PASSWORD);
            updatePassword.setString(1, password);
            updatePassword.setString(2, username);

            if(updatePassword != null)
            {
                updatePassword.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Updates the account type of the user in the database
     * @param username - username of updated user.
     * @param accountType - account type to change the users account type to
     * @return a success message
     */
    public String updateUserAccountType(String username, AccountType accountType) {
        try
        {
            connection = DatabaseConnection.getInstance();
            updateUserAccountType = connection.prepareStatement(UPDATE_USER_ACCOUNT_TYPE);
            updateUserAccountType.setString(1, accountType.toString());
            updateUserAccountType.setString(2, username);

            if(updateUserAccountType != null)
            {
                updateUserAccountType.executeQuery();
                return "Successfully updated user account type";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * Updates the order to change its asset quantity
     * @param orderID - order ID to identify it by
     * @param assetQuantity - the asset quantity to change it to
     * @return a success message
     */
    public String updateOrder(Integer orderID, Integer assetQuantity) {
        try
        {
            connection = DatabaseConnection.getInstance();
            updateOrder = connection.prepareStatement(UPDATE_ORDER);
            updateOrder.setInt(1, assetQuantity);
            updateOrder.setInt(2, orderID);

            if(updateOrder != null)
            {
                updateOrder.executeQuery();
                return "Successfully updated order";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the organisation of the user in the database
     * @param username - username of the updated user.
     * @param organisationName - organisation to change the users organisation to
     * @return a success message
     */
    public String updateUserOrganisation(String username, String organisationName) {
        try
        {
            connection = DatabaseConnection.getInstance();
            updateUserOrganisation = connection.prepareStatement(UPDATE_USER_ORGANISATION);
            updateUserOrganisation.setString(1, organisationName);
            updateUserOrganisation.setString(2, username);

            if(updateUserOrganisation != null)
            {
                updateUserOrganisation.executeQuery();
                return "Successfully updated user organisation";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Update the quantity of an asset in an organisation, either by inserting a new
     * asset or updating an existing asset in the database
     * @param organisationName - organisation to have its asset changed
     * @param assetType - the asset type to be added or updated
     * @param assetQuantity - quantity to set the asset to
     * @return a success message
     */
    public String updateOrganisationAsset(String organisationName, String assetType, int assetQuantity) {
        try
        {
            connection = DatabaseConnection.getInstance();
            if (getOrganisationIndividualAsset(organisationName, assetType) == null) {
                addOrganisationAsset = connection.prepareStatement(INSERT_ORGANISATION_ASSETS);
                addOrganisationAsset.setString(1, organisationName);
                addOrganisationAsset.setString(2, assetType);
                addOrganisationAsset.setInt(3, assetQuantity);
                addOrganisationAsset.execute();
            } else {
                updateOrganisationAsset = connection.prepareStatement(UPDATE_ORGANISATION_ASSET);
                updateOrganisationAsset.setInt(1, assetQuantity);
                updateOrganisationAsset.setString(2, organisationName);
                updateOrganisationAsset.setString(3, assetType);
                updateOrganisationAsset.execute();
            }

            return "Successfully updated organisation asset";
        } catch (SQLException | ServerException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves password from database
     * @param username username to retrieve password for
     * @return the hashed password
     */
    public String getPassword(String username)
    {
        String result = null;
        try
        {
            connection = DatabaseConnection.getInstance();
            getPassword = connection.prepareStatement(GET_PASSWORD);
            getPassword.setString(1, username);

            if(getPassword != null)
            {
                ResultSet rs = getPassword.executeQuery();
                rs.next();
                result = rs.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Gets user from the database
     * @param username - username of requested user.
     * @return the User with login info included
     */
    public User getUser(String username)
    {
        ResultSet rs = null;
        User user = new User();

        try
        {
            connection = DatabaseConnection.getInstance();
            getUser = connection.prepareStatement(GET_USER);
            getUser.setString(1,username);

            if(getUser != null)
            {
                rs = getUser.executeQuery();
                rs.next();
                user = new User (
                        rs.getString("UserName"),
                        rs.getString("HashedPassword"),
                        AccountType.valueOf(rs.getString("AccountType")),
                        rs.getString("OrganisationalUnit"),
                        rs.getString("Salt")
                );
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Gets list of users for all users from the database
     * @return the list of users with login info
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getAllUsers = connection.prepareStatement(GET_ALL_USERS);

            if(getAllUsers != null)
            {
                ResultSet rs = getAllUsers.executeQuery();
                while (rs.next()) {
                     User user = new User(
                            rs.getString("UserName"),
                            rs.getString("HashedPassword"),
                             AccountType.valueOf(rs.getString("AccountType")),
                            rs.getString("OrganisationalUnit"),
                            rs.getString("Salt")
                    );

                    users.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    /**
     * Gets user info from the database
     * @param username - username of requested user.
     * @return the User with no login info included
     */
    public UserInfo getUserInfo(String username)  {
        UserInfo user;
        try
        {
            connection = DatabaseConnection.getInstance();
            getUser = connection.prepareStatement(GET_USER);
            getUser.setString(1,username);

            if(getUser != null)
            {
                ResultSet rs = getUser.executeQuery();
                rs.next();
                user = new UserInfo(rs.getString("UserName"),
                        AccountType.valueOf(rs.getString("AccountType")),
                        rs.getString("OrganisationalUnit"));
                return user;
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Gets list of user info for all users from the database
     * @return the list of user information
     */
    public List<UserInfo> getAllUserInfo() {
        List<UserInfo> users = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getAllUsers = connection.prepareStatement(GET_ALL_USERS);

            if(getAllUsers != null)
            {
                ResultSet rs = getAllUsers.executeQuery();
                while (rs.next()) {
                    UserInfo user = new UserInfo(rs.getString("UserName"),
                            AccountType.valueOf(rs.getString("AccountType")),
                            rs.getString("OrganisationalUnit"));

                    users.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    /**
     * Retrieve salt from database
     * @param username salt attached to this users password
     */
    public String getSalt(String username) throws ServerException {
        String result = null;
        try
        {
            connection = DatabaseConnection.getInstance();
            getSalt= connection.prepareStatement(GET_SALT);
            getSalt.setString(1, username);

            if(getSalt != null)
            {
                ResultSet rs = getSalt.executeQuery();
                rs.next();
                result = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServerException("No salt found.");

        }

        return result;
    }

    /**
     * Gets the organisation quantity of an asset type
     * @param orgName
     * @param assetType
     * @return
     */
    public Integer getOrganisationIndividualAsset(String orgName, String assetType) throws ServerException {
        try {
            connection = DatabaseConnection.getInstance();

            getOrganisationIndividualAssets = connection.prepareStatement(GET_ORGANISATION_INDIVIDUAL_ASSETS);
            getOrganisationIndividualAssets.setString(1, orgName);
            getOrganisationIndividualAssets.setString(2, assetType);


            if (getOrganisationIndividualAssets != null) {
                ResultSet rs = getOrganisationIndividualAssets.executeQuery();
                rs.next();
                return rs.getInt("AssetQuantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerException("Get organisational assets not found.");
        }

        return null;
    }

    /**
     * Gets the organisational unit object in the database
     * @param orgName - organisation name of the organisation
     * @return the organisation unit object with all included information
     */
    public OrganisationalUnit getOrganisation(String orgName) {
        OrganisationalUnit organisationalunit;
        try
        {
            connection = DatabaseConnection.getInstance();

            getOrganisationAssets = connection.prepareStatement(GET_ORGANISATION_ASSETS);
            getOrganisationAssets.setString(1, orgName);

            HashMap<String, Integer> orgAssets = new HashMap<>();

            if(getOrganisationAssets != null)
            {
                ResultSet rs = getOrganisationAssets.executeQuery();
                while (rs.next()) {
                    orgAssets.put(rs.getString("AssetName"), rs.getInt("AssetQuantity"));
                }
            }


            getOrganisation = connection.prepareStatement(GET_ORGANISATION);
            getOrganisation.setString(1, orgName);

            if(getOrganisation != null)
            {
                ResultSet rs = getOrganisation.executeQuery();
                rs.next();
                organisationalunit = new OrganisationalUnit(
                        orgName,
                        rs.getInt("amountCredits"),
                        orgAssets
                );
                return organisationalunit;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a list of all organisational units with info in the database
     * @return a list of organisational unit objects
     */
    public List<OrganisationalUnit> getAllOrganisations() {
        List<OrganisationalUnit> organisationalUnits = new ArrayList<>();

        try
        {
            connection = DatabaseConnection.getInstance();

            getAllOrganisations = connection.prepareStatement(GET_ALL_ORGANISATIONS);

            if(getAllOrganisations != null)
            {
                ResultSet rs = getAllOrganisations.executeQuery();
                while (rs.next()) {
                    getOrganisationAssets = connection.prepareStatement(GET_ORGANISATION_ASSETS);
                    String orgName = rs.getString("OrganisationalUnitName");
                    getOrganisationAssets.setString(1, orgName);

                    HashMap<String, Integer> orgAssets = new HashMap<>();

                    if(getOrganisationAssets != null)
                    {
                        ResultSet assetsRs = getOrganisationAssets.executeQuery();
                        while (assetsRs.next()) {
                            orgAssets.put(assetsRs.getString("AssetName"), assetsRs.getInt("AssetQuantity"));
                        }
                    }


                    organisationalUnits.add(new OrganisationalUnit(
                            orgName,
                            rs.getInt("amountCredits"),
                            orgAssets
                    ));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return organisationalUnits;
    }

    /**
     * Gets a list of all orders that an organisation currently has active in the database
     * @param orgName - the organisation to retrieve orders for
     * @return a list of orders both buy and sell
     */
    public List<Order> getOrganisationOrders(String orgName) {
        List<Order> orgOrders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getOrganisationOrders = connection.prepareStatement(GET_ORGANISATION_ORDERS);
            getOrganisationOrders.setString(1,orgName);
            if(getOrganisationOrders != null)
            {
                ResultSet rs = getOrganisationOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    orgOrders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orgOrders;
    }

    /**
     * Gets all buy orders that are currently active in the database
     * @return a list of all orders with the orderType BUY
     */
    public List<Order> getBuyOrders() {
        List<Order> buyOrders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getBuyOrders = connection.prepareStatement(GET_BUY_ORDERS);

            if(getBuyOrders != null)
            {
                ResultSet rs = getBuyOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    buyOrders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyOrders;
    }

    /**
     * Gets all sell orders that are currently active in the database
     * @return a list of all orders with the orderType SELL
     */
    public List<Order> getSellOrders() {
        List<Order> sellOrders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getSellOrders= connection.prepareStatement(GET_SELL_ORDERS);

            if(getSellOrders != null)
            {
                ResultSet rs = getSellOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    sellOrders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sellOrders;
    }

    /**
     * Gets all of an organisations sell orders that are currently active in the database
     * @return a list of all orders with the orderType SELL
     */
    public List<Order> getOrganisationSellOrders(String organisationName) {
        List<Order> sellOrders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getOrganisationSellOrders = connection.prepareStatement(GET_ORGANISATION_SELL_ORDERS);
            getOrganisationSellOrders.setString(1, organisationName);

            if(getOrganisationSellOrders != null)
            {
                ResultSet rs = getOrganisationSellOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    sellOrders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sellOrders;
    }

    /**
     * Gets all of an organisations buy orders that are currently active in the database
     * @return a list of all orders with the orderType SELL
     */
    public List<Order> getOrganisationBuyOrders(String organisationName) {
        List<Order> buyOrders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getOrganisationBuyOrders = connection.prepareStatement(GET_ORGANISATION_BUY_ORDERS);
            getOrganisationBuyOrders.setString(1, organisationName);

            if(getOrganisationBuyOrders != null)
            {
                ResultSet rs = getOrganisationBuyOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    buyOrders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyOrders;
    }

    /**
     * Get the list of Orders
     * @return the list of orders
     */
    public List<Order> getOrderList() {
        List<Order> orders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getOrderList = connection.prepareStatement(GET_ORDER_LIST);

            if(getOrderList != null)
            {
                ResultSet rs = getOrderList.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    orders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    /**
     * Gets all orders that are currently active in the database
     * @return a list of all orders, regardless of type
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getAllOrders = connection.prepareStatement(GET_ALL_ORDERS);

            if(getAllOrders != null)
            {
                ResultSet rs = getAllOrders.executeQuery();
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("OrderID"),
                            OrderType.valueOf(rs.getString("OrderType")),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("OrganisationalUnitName"),
                            new Date(rs.getLong("PlaceDateMilSecs"))
                    );

                    orders.add(order);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }



    /**
     * Gets a list of all the asset types that are in the database
     * @return a list of asset types
     */
    public List<String> getAssetTypes() {
        List<String> assetTypes = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getAssetTypes = connection.prepareStatement(GET_ASSET_TYPES);

            if(getAssetTypes != null)
            {
                ResultSet rs = getAssetTypes.executeQuery();
                while (rs.next()) {
                    assetTypes.add(rs.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assetTypes;
    }


    /**
     * Gets a list of all previous trades that occurred for an asset type
     * @param AssetName - asset that was traded
     * @return a list of trades
     */
    public List<Trade> getTradeHistory(String AssetName) {
        List<Trade> trades = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getTradeHistory = connection.prepareStatement(GET_TRADE_HISTORY);
            getTradeHistory.setString(1, AssetName);

            if (getTradeHistory != null)
            {
                ResultSet rs = getTradeHistory.executeQuery();
                while (rs.next()) {
                    Trade trade = new Trade(
                            rs.getInt("TradeID"),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("BuyerOrgName"),
                            rs.getString("SellerOrgName"),
                            new Date(rs.getLong("TradeDateMilSecs"))

                    );

                    trades.add(trade);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trades;
    }

    /**
     * Gets a list of all previous trades that occurred
     * @return a list of trades
     */
    public List<Trade> getAllTradeHistory() {
        List<Trade> trades = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getInstance();
            getTradeHistory = connection.prepareStatement(GET_ALL_TRADE_HISTORY);

            if(getTradeHistory != null)
            {
                ResultSet rs = getTradeHistory.executeQuery();
                while (rs.next()) {
                    Trade trade = new Trade(
                            rs.getInt("TradeID"),
                            rs.getString("AssetName"),
                            rs.getInt("AssetQuantity"),
                            rs.getDouble("AssetPrice"),
                            rs.getString("BuyerOrgName"),
                            rs.getString("SellerOrgName"),
                            new Date(rs.getLong("TradeDateMilSecs"))

                    );

                    trades.add(trade);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trades;
    }

    /**
     * Deletes an order in the database
     * @param OrderID - the orderID of the order to be deleted
     * @return a success message
     */
    public String deleteOrder(int OrderID) {
        try
        {
            connection = DatabaseConnection.getInstance();
            deleteOrder = connection.prepareStatement(DELETE_ORDER);
            deleteOrder.setInt(1, OrderID);

            if(deleteOrder != null)
            {
                deleteOrder.executeUpdate();
            }
            return "Successfully Deleted";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the amount of credits held by an organisational unit.
     * @param organisationName - the organisational units name
     * @param creditAmount - the amount of credits the organisation will possess after change.
     */
    public void updateOrganisationCredits(String organisationName, double creditAmount)
    {
        try
        {
            updateOrganisationCredits = connection.prepareStatement(UPDATE_ORGANISATION_CREDITS);
            updateOrganisationCredits.setString(2,organisationName);
            updateOrganisationCredits.setDouble(1, creditAmount);

            if(updateOrganisationCredits != null)
            {
                updateOrganisationCredits.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollBackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
