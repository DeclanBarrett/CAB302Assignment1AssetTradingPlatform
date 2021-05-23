package Controllers.BackEnd.Socket;

import Controllers.BackEnd.*;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;

import java.time.Duration;
import java.util.*;

/**
 * Mock database for testing.
 */
public class MockSocket implements IDataSource {


    private boolean databaseConnected;

    private ArrayList<User> userTable = new ArrayList<>();
    private ArrayList<OrganisationalUnit> organisationalUnitTable = new ArrayList<>();
    private HashMap<String, Integer> organisationAssets = new HashMap<>();
    private ArrayList<Order> orderTable = new ArrayList<>();
    private ArrayList<String> assetTypesTable = new ArrayList<>();
    private ArrayList<Trade> tradesTable = new ArrayList<>();

    /**
     * TODO finish implementing IDataSource
     * Populating the mock database with values
     */
    protected MockSocket() {
        databaseConnected = false;

        userTable.add(new User("User 1", "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345")); //qwerty
        userTable.add(new User("User 2", "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345"));
        userTable.add(new User("User 3", "8d421e892a47dff539f46142eb09e56b", AccountType.User, "Finance", "123456")); //1234
        userTable.add(new User("User 4", "b26b843656e6834822b83179b4297620", AccountType.User, "Finance", "123457"));
        userTable.add(new User("User 5", "c3a4b61825259a74c26d49daa3e89312", AccountType.User, "Finance", "123458")); //password
        userTable.add(new User("User 6", "2ec7484fa99bbaa7bdebe544f1f52f61", AccountType.User, "Research", "123459"));
        userTable.add(new User("User 7", "ccab59bc481b2105a4dbdf3d30a66248", AccountType.User, "Research", "123450"));
        userTable.add(new User("User 8", "aa3cae505478da19d13efa65bc8c71b3", AccountType.User, "Research", "123451"));
        userTable.add(new User("User 9", "43bf88d863f230f328c15ccf61d9d89d", AccountType.User, "Research", "123452"));

        userTable.add(new User("Declan Testing", "802b492fc1d1fe592090399c1ca3b56a", AccountType.SystemAdmin, "Admin", "12346")); //qwerty
        userTable.add(new User("Aiden Testing", "086e1b7e1c12ba37cd473670b3a15214", AccountType.SystemAdmin, "Admin", "123456"));
        userTable.add(new User("Brad Testing", "086e1b7e1c12ba37cd473670b3a15214", AccountType.SystemAdmin, "Admin", "123456"));
        userTable.add(new User("Ethan Testing", "086e1b7e1c12ba37cd473670b3a15214", AccountType.SystemAdmin, "Admin", "123456"));

        userTable.add(new User("User 10", "579d9ec9d0c3d687aaa91289ac2854e4", AccountType.UnitLeader, "Sales", "123456")); //123
        userTable.add(new User("User 11", "086e1b7e1c12ba37cd473670b3a15214", AccountType.UnitLeader, "Finance", "123456")); //qwerty
        userTable.add(new User("User 12", "086e1b7e1c12ba37cd473670b3a15214", AccountType.UnitLeader, "Research", "123456"));
        userTable.add(new User("User 13", "086e1b7e1c12ba37cd473670b3a15214", AccountType.UnitLeader, "Research", "123456"));

        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);

        organisationalUnitTable.add(new OrganisationalUnit("Sales", 3000.50, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Finance", 100, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Research", 90, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Admin", 0, organisationAssets));

        assetTypesTable.add("Paper");
        assetTypesTable.add("CPU hours");
        assetTypesTable.add("Pickles");
        assetTypesTable.add("Casino Chips");

        orderTable.add(new Order(123456, OrderType.BUY, "Paper", 3, 3, "Research", new Date()));
        orderTable.add(new Order(123457, OrderType.SELL, "CPU hours", 3, 3000, "Research", new Date()));
        orderTable.add(new Order(123458, OrderType.BUY, "Pickles", 3, 3000, "Sales", new Date()));
        orderTable.add(new Order(123459, OrderType.SELL, "Casino Chips", 3, 3, "Sales", new Date()));

        Date date = new Date();

        tradesTable.add(new Trade(123456, "Paper", 5, 3.0, "Research", "Sales", new Date(date.getTime() + 1)));
        tradesTable.add(new Trade(123457, "Paper", 4, 3.2, "Research", "Sales", new Date(date.getTime() + 2)));
        tradesTable.add(new Trade(123458, "Paper", 6, 4.3, "Research", "Sales", new Date(date.getTime() + 3)));
        tradesTable.add(new Trade(123459, "Paper", 2, 4.5, "Research", "Sales", new Date(date.getTime() + 4)));
        tradesTable.add(new Trade(123410, "Paper", 4, 3.1, "Research", "Sales", new Date(date.getTime() + 5)));
        tradesTable.add(new Trade(123412, "Paper", 5, 5.0, "Research", "Sales", new Date(date.getTime() + 6)));
        tradesTable.add(new Trade(123413, "Paper", 1, 1.0, "Research", "Sales", new Date(date.getTime() + 7)));

        System.out.println("HELL");
    }

    private static class MockSocketHolder {
        private final static MockSocket INSTANCE = new MockSocket();
    }

    public static MockSocket getInstance() {
        return MockSocketHolder.INSTANCE;
    }

    @Override
    public String GetSalt(String username) throws ServerException{
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                return currentUser.getSalt();
            }
        }

        return null;
    }

    @Override
    public LoginToken AttemptLogin(String username, String password) throws AuthenticationException, ServerException {

        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username) && currentUser.getPassword().equals(password)) {
                Date actualDate = new Date();

                actualDate.toInstant().plus(Duration.ofHours(2));

                LoginToken login = new LoginToken(currentUser.getUsername(), new Date());

                return login;
            }
        }
        return null;
    }

    @Override
    public String AttemptResetPassword(LoginToken token, String username, String newPassword) throws AuthenticationException, ServerException {
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                User updatedUser = new User(currentUser.getUsername(), newPassword, currentUser.getAccountType(), currentUser.getOrganisationalUnit(), currentUser.getSalt());
                userTable.remove(currentUser);
                userTable.add(updatedUser);
                return "Success";
            }
        }
        throw new AuthenticationException("USERNAME OR PASSWORD IS INCORRECT");
    }

    @Override
    public UserInfo GetUser(LoginToken token, String username) throws AuthenticationException, ServerException {
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                System.out.println(currentUser.getOrganisationalUnit());
                return new UserInfo(currentUser.getUsername(), currentUser.getAccountType(), currentUser.getOrganisationalUnit());
            }
        }
        return null;
    }

    @Override
    public OrganisationalUnit GetOrganisation(LoginToken token, String orgName) throws AuthenticationException, ServerException {
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.getUnitName().equals(orgName)) {
                return organisationalUnit;
            }
        }
        return null;
    }

    @Override
    public List<Order> GetOrganisationOrders(LoginToken token, String orgName) throws AuthenticationException, ServerException {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order: orderTable) {
            if (order.getOrganisationalUnit().equals(orgName))
                orders.add(order);
        }
        return orders;
    }

    @Override
    public List<Order> GetAllOrders(LoginToken token) throws AuthenticationException, ServerException {
        ArrayList<Order> orders = orderTable;
        return orders;
    }

    @Override
    public List<Order> GetBuyOrders(LoginToken token) throws AuthenticationException, ServerException {
        ArrayList<Order> buyOrders = new ArrayList<>();
        for (Order order: orderTable) {
            if (order.getOrderType().equals(OrderType.BUY)) {
                buyOrders.add(order);
            }
        }
        return buyOrders;
    }

    @Override
    public List<Order> GetSellOrders(LoginToken token) throws AuthenticationException, ServerException {
        ArrayList<Order> sellOrders = new ArrayList<>();
        for (Order order: orderTable) {
            if (order.getOrderType().equals(OrderType.SELL)) {
                sellOrders.add(order);
            }
        }
        return sellOrders;
    }

    @Override
    public List<Order> GetOrganisationBuyOrders(LoginToken token, String organisationName) throws AuthenticationException, ServerException {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order: orderTable) {
            if (order.getOrganisationalUnit().equals(organisationName))
                orders.add(order);
        }

        ArrayList<Order> buyOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.getOrderType().equals(OrderType.BUY)) {
                buyOrders.add(order);
            }
        }
        return buyOrders;
    }

    @Override
    public List<Order> GetOrganisationSellOrders(LoginToken token, String organisationName) throws AuthenticationException, ServerException {

        ArrayList<Order> orders = new ArrayList<>();
        for (Order order: orderTable) {
            if (order.getOrganisationalUnit().equals(organisationName))
            orders.add(order);
        }

        ArrayList<Order> sellOrders = new ArrayList<>();
        for (Order order: orders) {
            if (order.getOrderType().equals(OrderType.SELL)) {
                sellOrders.add(order);
            }
        }
        return sellOrders;
    }

    @Override
    public String AddOrder(LoginToken token, Order newOrder) throws AuthenticationException, ServerException {
        Random rand = new Random();
        orderTable.add(new Order(rand.nextInt(),
                newOrder.getOrderType(),
                newOrder.getAssetType(),
                newOrder.getAssetQuantity(),
                newOrder.getRequestPrice(),
                newOrder.getOrganisationalUnit(),
                new Date()));
        return "Success";
    }

    @Override
    public String RemoveOrder(LoginToken token, int OrderID) throws AuthenticationException, ServerException {
        orderTable.removeIf(order -> order.getOrderID() == OrderID);
        return "Success";
    }

    @Override
    public List<String> GetAssetTypes(LoginToken token) throws AuthenticationException, ServerException {
        return assetTypesTable;
    }

    @Override
    public List<Trade> GetTradeHistory(LoginToken token, String AssetType) throws AuthenticationException, ServerException {
        List<Trade> trades = new ArrayList<>();

        for (Trade trade: tradesTable) {
            if (trade.getAssetName().equals(AssetType)) {
                trades.add(trade);
            }
        }

        return trades;
    }

    @Override
    public String AddUser(LoginToken token, User user) throws AuthenticationException, ServerException {
        userTable.add(user);
        return "Success";
    }

    @Override
    public List<UserInfo> GetAllUsers(LoginToken token) throws AuthenticationException, ServerException {
        List<UserInfo> infoTable = new ArrayList<>();
        for (User user : userTable) {
            infoTable.add(new UserInfo(user.getUsername(), user.getAccountType(), user.getOrganisationalUnit()));
        }
        return infoTable;
    }

    @Override
    public String UpdateUserPassword(LoginToken token, String username, String hashedPassword, String salt) throws AuthenticationException, ServerException {
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                User updatedUser = new User(currentUser.getUsername(), hashedPassword, currentUser.getAccountType(), currentUser.getOrganisationalUnit(), salt);
                userTable.remove(currentUser);
                userTable.add(updatedUser);
                return "Success";
            }
        }
        return "mp";
    }

    @Override
    public String UpdateUserAccountType(LoginToken token, String username, AccountType accountType) throws AuthenticationException, ServerException {
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                User updatedUser = new User(currentUser.getUsername(), currentUser.getPassword(), accountType, currentUser.getOrganisationalUnit(), currentUser.getSalt());
                userTable.remove(currentUser);
                userTable.add(updatedUser);
                return "Success";
            }
        }
        return "BAD";//throw new AuthenticationException("USERNAME OR PASSWORD IS INCORRECT");
    }

    @Override
    public String UpdateUserOrganisation(LoginToken token, String username, String organisationName) throws AuthenticationException, ServerException {
        for (User currentUser: userTable) {
            if (currentUser.getUsername().equals(username)) {
                User updatedUser = new User(currentUser.getUsername(), currentUser.getPassword(), currentUser.getAccountType(), organisationName, currentUser.getSalt());
                userTable.remove(currentUser);
                userTable.add(updatedUser);
                return "Success";
            }
        }
        return "BAD";//throw new AuthenticationException("USERNAME OR PASSWORD IS INCORRECT");
    }

    @Override
    public String AddAsset(LoginToken token, String assetName) throws AuthenticationException, ServerException {
        assetTypesTable.add(assetName);
        return "Success";
    }

    @Override
    public String AddOrganisation(LoginToken token, OrganisationalUnit organisation) throws AuthenticationException, ServerException {

        organisationalUnitTable.add(organisation);
        return "Success";
    }

    @Override
    public List<OrganisationalUnit> GetAllOrganisations(LoginToken token) throws AuthenticationException, ServerException {

        return organisationalUnitTable;
    }

    @Override
    public String UpdateOrganisationAsset(LoginToken token, String organisationName, String AssetType, int AssetQuantity) throws AuthenticationException, ServerException {
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.getUnitName().equals(organisationName)) {

                HashMap<String, Integer> assets = organisationalUnit.GetAllAssets();

                if (assetTypesTable.contains(AssetType)) {
                    assets.put(AssetType, AssetQuantity);
                } else {
                    return "BAD";
                }

                OrganisationalUnit updatedOrg = new OrganisationalUnit(organisationalUnit.getUnitName(), organisationalUnit.getCredits(), assets);
                organisationalUnitTable.remove(organisationalUnit);
                organisationalUnitTable.add(updatedOrg);
                return "Success";
            }
        }
        return "BAD";//throw new AuthenticationException("USERNAME OR PASSWORD IS INCORRECT");
    }

    @Override
    public String UpdateOrganisationCredit(LoginToken token, String organisationName, int creditAmount) throws AuthenticationException, ServerException {
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.getUnitName().equals(organisationName)) {
                OrganisationalUnit updatedOrg = new OrganisationalUnit(organisationalUnit.getUnitName(), creditAmount, organisationalUnit.GetAllAssets());
                organisationalUnitTable.remove(organisationalUnit);
                organisationalUnitTable.add(updatedOrg);
                return "Success";
            }
        }
        return "BAD";//throw new AuthenticationException("USERNAME OR PASSWORD IS INCORRECT");
    }


}
