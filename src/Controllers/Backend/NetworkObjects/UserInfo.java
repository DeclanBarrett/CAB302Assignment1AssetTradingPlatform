package Controllers.Backend.NetworkObjects;

import Controllers.Backend.AccountType;

import java.io.Serializable;

/**
 * User data object that allows the interaction of a single users account information
 */
public class UserInfo implements Comparable<UserInfo>, Serializable {

    private String username;
    //private String password;
    private AccountType accountType;
    private String organisationalType;

    /**
     *
     * @param username Username of the user
     * //@param password Password of the user
     * @param accountType Account type of the use
     * @param organisationalUnit Organisational Unit that the user belongs to
     */
    public UserInfo(String username, AccountType accountType, String organisationalUnit) {
        this.username = username;
        //this.password = password;
        this.accountType = accountType;
        this.organisationalType = organisationalUnit;
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
    //public String GetPassword() { return password; }

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
    public int compareTo(UserInfo other) {
        return this.username.compareTo(other.username);
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof UserInfo)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        UserInfo u = (UserInfo) o;

        // Compare the data members and return accordingly
        return GetUsername().equals(u.GetUsername());
    }
}
