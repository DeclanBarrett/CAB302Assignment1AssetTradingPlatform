package Models;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.UserInfo;

import java.util.List;

public interface IDataSource {


    void OpenConnection();

    /**
     * Gets a stored nonce for a user
     * @param username the users username that uniquely identifies them
     * @return the nonce that is stored for the user
     */
    String RetrieveNonce(String username);

    /**
     * Retrieves a users information, not including the password
     * @param userName the users username that uniquely identifies them
     * @return the User object that contains stored information
     */
    UserInfo GetUser(String userName);

    /*

     */
    void CreateUser(UserInfo newUserInfo);

    Order GetOrder(); //May need to change to SQL date

    List<Order> GetOrderList();

    OrganisationalUnit GetOrganisationalUnit(String unitName);

    void CloseConnection();
}
