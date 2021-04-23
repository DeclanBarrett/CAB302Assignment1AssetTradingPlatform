package Server;

import Shared.Order;
import Shared.OrganisationalUnit;
import Shared.Trade;
import Shared.User;

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
