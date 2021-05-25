package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.AccountType;

import java.io.Serializable;

/**
 * Serializable user objects constructor
 */
public class User implements Comparable<User>, Serializable {

    private String username;
    private String password;
    private AccountType accountType;
    private String organisationalType;
    private String salt;


    /**
     * No args constructor
     */
    public User()
    {

    }
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
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of user.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * @return password of the User
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets user password
     * @param password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * @return account type of the User
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets user account type
     * @param accountType
     */
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    /**
     * @return organisational unit of the User
     */
    public String getOrganisationalUnit() {
        return organisationalType;
    }

    /**
     * Sets users organisational Unit
     * @return
     */
    public void setOrganisationalType(String organisationalType) { this.organisationalType = organisationalType; }


    /**
     * gets users salt.
     * @return
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets user salt.
     * @param salt
     */
    public void setSalt(String salt) { this.salt = salt; }


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
        return getUsername().equals(u.getUsername());
    }
}