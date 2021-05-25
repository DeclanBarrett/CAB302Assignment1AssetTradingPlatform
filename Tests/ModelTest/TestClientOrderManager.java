package ModelTest;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.NetworkObjects.UserInfo;
import Controllers.FrontEnd.User.OrderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TestClientOrderManager
{

    OrderManager orderManager;

    @BeforeEach
    public void ConstructClientOrderManager()
    {

        orderManager = new OrderManager();

    }

    @Test
    public void TestSetUser()
    {
        UserInfo userInfo = new UserInfo("Jack", AccountType.User, "Sales");
        orderManager.SetUser(userInfo);
    }

    @Test
    public void TestSetOrgUnit()
    {
        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        OrganisationalUnit unit = new OrganisationalUnit("Sales", 3000.50, organisationAssets);
        orderManager.SetOrgUnit(unit);
    }

    @Test
    public void TestCheckOrderNormal()
    {
        UserInfo userInfo = new UserInfo("Jack", AccountType.User, "Sales");

        Order order = null;//new Order(OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());

        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        OrganisationalUnit unit = new OrganisationalUnit("Sales", 3000.50, organisationAssets);

        orderManager.SetUser(userInfo);
        orderManager.SetOrgUnit(unit);
        orderManager.CheckOrderValidity(order);

        //assertEquals(, );
    }

}