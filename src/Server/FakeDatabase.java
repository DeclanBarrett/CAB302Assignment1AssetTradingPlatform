package Server;

import Shared.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FakeDatabase implements IDatabase {

    private boolean databaseConnected;

    private ArrayList<User> userTable;
    private ArrayList<OrganisationalUnit> organisationalUnitTable;
    private ArrayList<Order> orderTable;

    /**
     * TODO TURN INTO REAL DATABASE
     * Populating the mock database with values
     */
    public FakeDatabase() {
        databaseConnected = false;

        userTable = new ArrayList<>();
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

        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        organisationalUnitTable.add(new OrganisationalUnit("Sales", 3000.50, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Finance", 100, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Research", 90, organisationAssets));
        organisationalUnitTable.add(new OrganisationalUnit("Admin", 0, organisationAssets));
    }

    @Override
    public void OpenConnection() {
        databaseConnected = true;
    }

    @Override
    public User GetUser(String userName) {
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(userName)) {
                return currentUser;
            }
        }
        return null;
    }

    @Override
    public Order GetOrder(String organisationName, Date date) {
        for (Order currentOrder: orderTable) {
            if (currentOrder.GetOrganisationalUnit().equals(organisationName) &&
            currentOrder.GetDate().equals(date)) {
                return currentOrder;
            }
        }
        return null;
    }

    @Override
    public List<Order> GetOrderList() {
        return orderTable;
    }

    @Override
    public OrganisationalUnit GetOrganisationalUnit(String unitName) {
        return null;
    }

    @Override
    public void CloseConnection() {
        databaseConnected = false;
    }
}
