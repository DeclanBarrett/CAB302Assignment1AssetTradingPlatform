package Models;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.User;

import java.util.Date;
import java.util.List;

public interface IDataSource {

    void OpenConnection();

    public String RetrieveNonce(String username);

    User GetUser(String userName);

    Order GetOrder(String organisationName, Date date); //May need to change to SQL date

    List<Order> GetOrderList();

    OrganisationalUnit GetOrganisationalUnit(String unitName);

    void CloseConnection();
}
