package ProjectConstructor;

import Shared.Order;
import Shared.OrganisationalUnit;
import Shared.Trade;

import java.util.List;

public interface IDatabase {

    List<Order> GetOrderList();

    Trade GetSuccessfulTrade();

    OrganisationalUnit GetOrganisationalUnit();
}
