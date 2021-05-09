package Models;

import Controllers.Backend._Dump.Order;
import Controllers.Backend._Dump.OrganisationalUnit;
import Controllers.Backend._Dump.User;

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
