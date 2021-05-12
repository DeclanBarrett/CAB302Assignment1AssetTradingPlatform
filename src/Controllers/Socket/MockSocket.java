package Controllers.Socket;

import Controllers.Backend.*;
import Controllers.Backend.NetworkObjects.*;
import Models.IDataSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

        assetTypesTable.add("Sales");
        assetTypesTable.add("Finance");
        assetTypesTable.add("Research");

        System.out.println("HELL");
    }

    private static class MockSocketHolder {
        private final static MockSocket INSTANCE = new MockSocket();
    }

    public static MockSocket getInstance() {
        return MockSocketHolder.INSTANCE;
    }

    @Override
    public String GetSalt(String username) {
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(username)) {
                return currentUser.GetSalt();
            }
        }

        return null;
    }

    @Override
    public LoginToken AttemptLogin(String username, String password) {
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(username) && currentUser.GetPassword().equals(password)) {
                Date actualDate = new Date();

                actualDate.toInstant().plus(Duration.ofHours(2));

                LoginToken login = new LoginToken(currentUser.GetUsername(), new Date());

                return login;
            }
        }
        return null;
    }

    @Override
    public String AttemptResetPassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public UserInfo GetUser(LoginToken token, String username) {
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(username)) {
                return new UserInfo(currentUser.GetUsername(), currentUser.GetAccountType(), currentUser.GetOrganisationalUnit());
            }
        }
        return null;
    }

    @Override
    public OrganisationalUnit GetOrganisation(LoginToken token, String orgName) {
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.GetUnitName().equals(orgName)) {
                return organisationalUnit;
            }
        }
        return null;
    }

    @Override
    public List<Order> GetOrganisationOrders(LoginToken token, String orgName) {
        return orderTable;
    }

    @Override
    public List<Order> GetAllOrders(LoginToken token) {
        ArrayList<Order> orders = orderTable;
        return orders;
    }

    @Override
    public String AddOrder(LoginToken token, Order newOrder) {
        return "Success";
    }

    @Override
    public String RemoveOrder(LoginToken token, int OrderID) {
        return "Success";
    }

    @Override
    public List<String> GetAssetTypes(LoginToken token) {
        return assetTypesTable;
    }

    @Override
    public List<Trade> GetTradeHistory(LoginToken token, String AssetType) {
        return tradesTable;
    }

    @Override
    public String AddUser(LoginToken token, User user) {
        return "Success";
    }

    @Override
    public List<UserInfo> GetAllUsers(LoginToken token) {
        List<UserInfo> infoTable = new ArrayList<>();
        for (User user : userTable) {
            infoTable.add(new UserInfo(user.GetUsername(), user.GetAccountType(), user.GetOrganisationalUnit()));
        }
        return infoTable;
    }

    @Override
    public String UpdateUserPassword(LoginToken token, String username, String hashedPassword, String salt) {
        return "success";
    }

    @Override
    public String UpdateUserAccountType(LoginToken token, String username, AccountType accountType) {
        return "Success";
    }

    @Override
    public String UpdateUserOrganisation(LoginToken token, String username, String organisationName) {
        return "Success";
    }

    @Override
    public String AddAsset(LoginToken token, String assetName) {
        return "Success";
    }

    @Override
    public String AddOrganisation(LoginToken token, OrganisationalUnit organisation) {
        return "Success";
    }

    @Override
    public List<OrganisationalUnit> GetAllOrganisations(LoginToken token) {

        return organisationalUnitTable;
    }

    @Override
    public String UpdateOrganisationAsset(LoginToken token, String organisationName, String AssetType, int AssetQuantity) {
        return null;
    }





}
