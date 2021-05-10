package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;

import java.util.List;

public interface IDataSource {

    String GetSalt(String username);
    LoginToken AttemptLogin(String username, String password);

    String AttemptResetPassword(String oldPassword, String newPassword);

    User GetUser(LoginToken token, String username);
    OrganisationalUnit GetOrganisation(LoginToken token, String orgName);
    List<Order> GetOrganisationOrders(LoginToken token, String orgName);
    List<Order> GetAllOrders(LoginToken token);
    String AddOrder(LoginToken token, Order newOrder);
    String RemoveOrder(LoginToken token, int OrderID);
    List<String> GetAssetTypes(LoginToken token);
    List<Trade> GetTradeHistory(LoginToken token, String AssetType);

    String AddUser(LoginToken token, User user);
    List<UserInfo> GetAllUsers(LoginToken token);
    String UpdateUserPassword(LoginToken token, String username, String hashedPassword, String salt);
    String UpdateUserAccountType(LoginToken token, String username, AccountType accountType);
    String UpdateUserOrganisation(LoginToken token, String username, String organisationName);
    String AddAsset(LoginToken token, String assetName);
    String AddOrganisation(LoginToken token, OrganisationalUnit organisation);
    List<OrganisationalUnit> GetAllOrganisations(LoginToken token);
    String UpdateOrganisationAsset(LoginToken token, String AssetType, int AssetQuantity);

}
