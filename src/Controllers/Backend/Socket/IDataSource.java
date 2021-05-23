package Controllers.Backend.Socket;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;
import Controllers.Exceptions.LoginException;

import java.util.List;

/**
 * Interface for data source.
 */
public interface IDataSource
{

    /**
     * Gets the salt for a user from the server
     *
     * @param username - username for the user that's to be retrieved
     * @return a string that is the salt
     */
    String GetSalt(String username);

    /**
     * Attempt to login to the server so that the user can be authenticated
     * and the user can then have a token to prove authentication
     *
     * @param username - username of user to login
     * @param password - the client side hashed password
     * @return a login token that can be stored client side and given to the server
     * whenever the client wants to communicate while logged in
     */
    LoginToken AttemptLogin(String username, String password);

    /**
     * Attempts to reset the password of the user on the server
     *
     * @param newPassword - the new password of the user which is to replace the old password
     * @return a success message, or failure message
     */
    String AttemptResetPassword(LoginToken token, String username, String newPassword) throws LoginException;

    /**
     * Gets the user information (without login details) from the server
     *
     * @param token    - token to be used for authentication
     * @param username - the user to gather the information on
     * @return a user info object with the users information in it
     */
    UserInfo GetUser(LoginToken token, String username);

    /**
     * Gets an organisation from the server
     *
     * @param token   - token to be used for authentication
     * @param orgName - organisation name for the organisation to gather
     * @return an organisation unit object with organisation information within it
     */
    OrganisationalUnit GetOrganisation(LoginToken token, String orgName);

    /**
     * Gets all orders for an organisation from the server
     *
     * @param token   - token to be used for authentication
     * @param orgName - organisation which orders will be gathered
     * @return a list of orders that have been placed by an organisation
     */
    List<Order> GetOrganisationOrders(LoginToken token, String orgName);

    /**
     * Get all orders from the server
     *
     * @param token - token to be used for authentication
     * @return List of all orders
     */
    List<Order> GetAllOrders(LoginToken token);

    /**
     * Get buy orders from the server
     *
     * @param token - token to be used for authentication
     * @return List of all orders
     */
    List<Order> GetBuyOrders(LoginToken token);

    /**
     * Get sell orders from the server
     *
     * @param token - token to be used for authentication
     * @return List of all orders
     */
    List<Order> GetSellOrders(LoginToken token);

    /**
     * Get buy orders from the server for an organisation
     *
     * @param token            - token to be used for authentication
     * @param organisationName - organisation to get buy orders
     * @return List of all orders
     */
    List<Order> GetOrganisationBuyOrders(LoginToken token, String organisationName);

    /**
     * Get sell orders from the server for an organisation
     *
     * @param token            - token to be used for authentication
     * @param organisationName - organisation to get buy orders
     * @return List of all orders
     */
    List<Order> GetOrganisationSellOrders(LoginToken token, String organisationName);

    /**
     * Attempts to add an order to the server, with it either being executed,
     * stored for later execution or rejected since it would put the organisation
     * at risk of going into negative credits
     *
     * @param token    - token to be used for authentication
     * @param newOrder - order to attempt to be added and executed
     * @return a success or failure message
     */
    String AddOrder(LoginToken token, Order newOrder);

    /**
     * Attempts to remove an order from the server
     *
     * @param token   - token to be used for authentication
     * @param orderID - orderID of the order to be removed
     * @return a success or failure message
     */
    String RemoveOrder(LoginToken token, int orderID);

    /**
     * Gets the asset types which are currently allowed from the server
     *
     * @param token - token to be used for authentication
     * @return a list of asset types
     */
    List<String> GetAssetTypes(LoginToken token);

    /**
     * Gets the history of all trades that have occurred for a type of asset
     * from the server
     *
     * @param token     - token to be used for authentication
     * @param AssetType - the type of asset that the history is retrieved for
     * @return a list of trades for a type of asset
     */
    List<Trade> GetTradeHistory(LoginToken token, String AssetType);

    /**
     * Attempts to add a new user to the server
     *
     * @param token - token to be used for authentication
     * @param user  - user object with all information to be added to the database
     * @return a success or failure message
     */
    String AddUser(LoginToken token, User user);

    /**
     * Gets all the user info from the server
     *
     * @param token - token to be used for authentication
     * @return a list of user information in user info objects
     */
    List<UserInfo> GetAllUsers(LoginToken token);

    /**
     * Change a users password on the server
     *
     * @param token          - token to be used for authentication
     * @param username       - username for user to have their password changed
     * @param hashedPassword - password to change to
     * @param salt           - client side salt to store with it
     * @return a success or failure message
     */
    String UpdateUserPassword(LoginToken token, String username, String hashedPassword, String salt);

    /**
     * Change a users account type on the server
     *
     * @param token       - token to be used for authentication
     * @param username    - username for user to have their account type changed
     * @param accountType - account type to change to
     * @return a success or failure message
     */
    String UpdateUserAccountType(LoginToken token, String username, AccountType accountType);

    /**
     * Change a users organisation they are part of on the server
     *
     * @param token            - token to be used for authentication
     * @param username         - username for user to have their organisation changed
     * @param organisationName - organisation to change to
     * @return a success or failure message
     */
    String UpdateUserOrganisation(LoginToken token, String username, String organisationName);

    /**
     * Attempt to add an asset that is allowed to the server which
     * doesn't actually add any instances it just allows for the asset type
     * to be used
     *
     * @param token     - token to be used for authentication
     * @param assetName - asset to be added
     * @return a success or failure message
     */
    String AddAsset(LoginToken token, String assetName);

    /**
     * Attempt to add a new organisation to the server
     *
     * @param token        - token to be used for authentication
     * @param organisation - organisation object with all information regarding organisation
     * @return a success or failure message
     */
    String AddOrganisation(LoginToken token, OrganisationalUnit organisation);

    /**
     * Gets all organisations with information from the server
     *
     * @param token - token to be used for authentication
     * @return a list of all organisations with internal information from the server
     */
    List<OrganisationalUnit> GetAllOrganisations(LoginToken token);

    /**
     * Change an organisations asset and quantity on the server
     *
     * @param token            - token to be used for authentication
     * @param organisationName - organisation to have their assets edited
     * @param AssetType        - asset to add or edit
     * @param AssetQuantity    - quantity to set the asset to
     * @return a success or failure message
     */
    String UpdateOrganisationAsset(LoginToken token, String organisationName, String AssetType, int AssetQuantity);

    /**
     * Change an organisations credit
     *
     * @param token            - token to be used for authentication
     * @param organisationName - organisation to have their credit edited
     * @param creditAmount     - credit amount to change to
     * @return a success or failure message
     */
    String UpdateOrganisationCredit(LoginToken token, String organisationName, int creditAmount);

}
