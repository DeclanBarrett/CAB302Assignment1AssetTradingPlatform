package Models;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.User;
import Controllers.Backend.OrganisationalUnit;

import java.util.Date;
import java.util.List;

public interface IDatabase {

    void OpenConnection();

    User GetUser(String userName);

    Order GetOrder(String organisationName, Date date); //May need to change to SQL date

    List<Order> GetOrderList();

    OrganisationalUnit GetOrganisationalUnit(String unitName);

    void CloseConnection();
}
