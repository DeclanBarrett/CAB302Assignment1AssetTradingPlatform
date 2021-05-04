package Models;

import Controllers.Backend.*;
import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.User;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Mock database for testing.
 */
public class MockSocket {

    private static boolean databaseConnected;

    private static ArrayList<User> userTable = new ArrayList<>();
    private static ArrayList<OrganisationalUnit> organisationalUnitTable = new ArrayList<>();
    private static HashMap<String, Integer> organisationAssets = new HashMap<>();
    private static ArrayList<Order> orderTable = new ArrayList<>();

    /**
     * TODO TURN INTO REAL DATABASE
     * Populating the mock database with values
     */
    public MockSocket() {
        databaseConnected = false;

        userTable.add(new User("User 1", "qwerty", AccountType.User, "Sales"));
        userTable.add(new User("User 2", "qwert", AccountType.User, "Sales"));
        userTable.add(new User("User 3", "1234", AccountType.User, "Finance"));
        userTable.add(new User("User 4", "1234", AccountType.User, "Finance"));
        userTable.add(new User("User 5", "password", AccountType.User, "Finance"));
        userTable.add(new User("User 6", "password", AccountType.User, "Research"));
        userTable.add(new User("User 7", "password", AccountType.User, "Research"));
        userTable.add(new User("User 8", "password", AccountType.User, "Research"));
        userTable.add(new User("User 9", "password", AccountType.User, "Research"));

        userTable.add(new User("Declan Testing", "qwerty", AccountType.SystemAdmin, "Admin"));
        userTable.add(new User("Aiden Testing", "qwerty", AccountType.SystemAdmin, "Admin"));
        userTable.add(new User("Brad Testing", "qwerty", AccountType.SystemAdmin, "Admin"));
        userTable.add(new User("Ethan Testing", "qwerty", AccountType.SystemAdmin, "Admin"));

        userTable.add(new User("User 10", "123", AccountType.UnitLeader, "Sales"));
        userTable.add(new User("User 11", "qwerty", AccountType.UnitLeader, "Finance"));
        userTable.add(new User("User 12", "qwerty", AccountType.UnitLeader, "Research"));
        userTable.add(new User("User 13", "qwerty", AccountType.UnitLeader, "Research"));


        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        organisationalUnitTable.add(new OrganisationalUnit("Sales", 3000.50, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Finance", 100, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Research", 90, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Admin", 0, organisationAssets));

        System.out.println("HELL");
    }

    public static void OpenConnection() {
        databaseConnected = true;
    }

    public static String RetrieveNonce(String username) {

        OpenConnection();

        for (User currentUser: userTable) {
            System.out.println(currentUser.GetUsername() + " + " + username);
            if (currentUser.GetUsername().equals(username)) {
                return "5";
            }
        }
        CloseConnection();

        return null;
    }

    public static LoginToken Login(String username, String password) {
        OpenConnection();
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(username) && currentUser.GetPassword().equals(password)) {
                Date actualDate = new Date();

                actualDate.toInstant().plus(Duration.ofHours(2));

                LoginToken login = new LoginToken(currentUser.GetUsername(), new Date());

                CloseConnection();
                return login;
            }
        }

        return null;
    }

    public static void CreateUser(User newUser) {

    }

    public static User GetUser(String userName) {
        OpenConnection();
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(userName)) {
                return currentUser;
            }
        }
        CloseConnection();
        return null;
    }

    public static Order GetOrder(String organisationName, Date date) {
        OpenConnection();
        for (Order currentOrder: orderTable) {
            if (currentOrder.GetOrganisationalUnit().equals(organisationName) &&
                currentOrder.GetDate().equals(date)) {
                return currentOrder;
            }
        }
        CloseConnection();
        return null;
    }

    public static List<Order> GetOrderList() {
        OpenConnection();
        ArrayList<Order> orders = orderTable;
        CloseConnection();
        return orders;
    }

    public static OrganisationalUnit GetOrganisationalUnit(String unitName) {
        OpenConnection();
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.GetUnitName().equals(unitName)) {
                return organisationalUnit;
            }
        }
        CloseConnection();
        return null;
    }

    public static void CloseConnection() {

        databaseConnected = false;
    }
}
