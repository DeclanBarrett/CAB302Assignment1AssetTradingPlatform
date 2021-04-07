package Shared;

/**
 * User data object that allows the interaction of a single users account information
 */
public class User {

    private String username;
    private String password;
    private AccountType accountType;
    private String organisationalType;

    /**
     *
     * @param username Username of the user
     * @param password Password of the user
     * @param accountType Account type of the use
     * @param organisationalUnit Organisational Unit that the user belongs to
     */
    public User(String username, String password, AccountType accountType, String organisationalUnit) {
        this.username = username;
        this.password = password;
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
    public String GetPassword() {
        return password;
    }

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
}
