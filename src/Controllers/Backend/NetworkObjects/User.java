package Controllers.Backend.NetworkObjects;

import Controllers.Backend.AccountType;
import Controllers.Socket.MockSocket;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {

    private String username;
    private String password;
    private AccountType accountType;
    private String organisationalType;
    private String salt;

    /**
     * @param username           Username of the user
     * @param password           Password of the user
     * @param accountType        Account type of the use
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
     * @return username of the User
     */
    public String GetUsername() {
        return username;
    }

    /**
     * @return password of the User
     */
    public String GetPassword() {
        return password;
    }

    /**
     * @return account type of the User
     */
    public AccountType GetAccountType() {
        return accountType;
    }

    /**
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
     * less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it from
     *                            being compared to this object.
     */
    @Override
    public int compareTo(User other) {
        return this.username.compareTo(other.username);
    }

    /**
     * Used to compare two obejcts to each other
     *
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