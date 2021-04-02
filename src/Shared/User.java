package Shared;

/**
 * User data object that allows the interaction of a single users account information
 */
public class User {

    private String username;
    private String password;
    private AccountType accountType;
    private String organisationalType;

    public User(String username, String password, AccountType accountType, String organisationalUnit) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.organisationalType = organisationalUnit;
    }

    public String GetUsername() {
        return username;
    }

    public String GetPassword() {
        return password;
    }

    public AccountType GetAccountType() {
        return accountType;
    }

    public String GetOrganisationalUnit() {
        return organisationalType;
    }
}
