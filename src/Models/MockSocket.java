package Models;

import Controllers.Backend.*;
import Controllers.Backend.NetworkObjects.LoginToken;
import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.User;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Mock database for testing.
 */
public class MockSocket implements IDataSource {

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

        System.out.println("HELL");
    }

    public void OpenConnection() {
        databaseConnected = true;
    }

    public String RetrieveNonce(String username) {

        OpenConnection();

        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(username)) {
                return currentUser.GetSalt();
            }
        }
        CloseConnection();

        return null;
    }

    public LoginToken Login(String username, String password) {
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

    public void CreateUser(User newUser) {

    }

    public Controllers.Backend.NetworkObjects.User GetUser(String userName) {
        OpenConnection();
        for (User currentUser: userTable) {
            if (currentUser.GetUsername().equals(userName)) {
                return new Controllers.Backend.NetworkObjects.User(currentUser.GetUsername(), currentUser.GetAccountType(), currentUser.GetOrganisationalUnit());
            }
        }
        CloseConnection();
        return null;
    }

    public Order GetOrder(String organisationName, Date date) {
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

    public List<Order> GetOrderList() {
        OpenConnection();
        ArrayList<Order> orders = orderTable;
        CloseConnection();
        return orders;
    }

    public OrganisationalUnit GetOrganisationalUnit(String unitName) {
        OpenConnection();
        for (OrganisationalUnit organisationalUnit: organisationalUnitTable) {
            if (organisationalUnit.GetUnitName().equals(unitName)) {
                return organisationalUnit;
            }
        }
        CloseConnection();
        return null;
    }

    public void CloseConnection() {

        databaseConnected = false;
    }

    public class User implements Comparable<User>, Serializable {

        private String username;
        private String password;
        private AccountType accountType;
        private String organisationalType;
        private String salt;

        /**
         *
         * @param username Username of the user
         * @param password Password of the user
         * @param accountType Account type of the use
         * @param organisationalUnit Organisational Unit that the user belongs to
         */
        public User(String username, String password, AccountType accountType, String organisationalUnit, String salt) {
            this.username = username;
            this.password = password;
            this.accountType = accountType;
            this.organisationalType = organisationalUnit;
            this.salt = salt;
        }

        /**
         *
         * @return username of the User
         */
        public String GetUsername() {
            return username;
        }

        /**
         *
         * @return password of the User
         */
        public String GetPassword() { return password; }

        /**
         *
         * @return account type of the User
         */
        public AccountType GetAccountType() {
            return accountType;
        }

        /**
         *
         * @return organisational unit of the User
         */
        public String GetOrganisationalUnit() {
            return organisationalType;
        }

        public String GetSalt() {
            return salt;
        }


        /**
         * Compares this object with the specified object. Returns a
         * negative integer, zero, or a positive integer as this object is less than,
         * equal to, or greater than the specified object.
         *
         * @param other The other User object to compare against.
         * @return a negative integer, zero, or a positive integer as this object is
         *         less than, equal to, or greater than the specified object.
         * @throws ClassCastException if the specified object's type prevents it from
         *            being compared to this object.
         */
        @Override
        public int compareTo(User other) {
            return this.username.compareTo(other.username);
        }

        /**
         * Used to compare two obejcts to each other
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            // If the object is compared with itself then return true
            if (o == this) {
                return true;
            }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
            if (!(o instanceof User)) {
                return false;
            }

            // typecast o to Complex so that we can compare data members
            User u = (User) o;

            // Compare the data members and return accordingly
            return GetUsername().equals(u.GetUsername());
        }
    }
}
