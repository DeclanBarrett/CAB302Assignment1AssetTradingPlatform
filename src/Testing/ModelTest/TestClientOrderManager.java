package Testing.ModelTest;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.Backend.OrderType;
import Controllers.FrontEnd.OrderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClientOrderManager {

    OrderManager orderManager;

    @BeforeEach
    public void ConstructClientOrderManager(){

        orderManager = new OrderManager();

    }

    @Test
    public void TestSetUser() {
        UserInfo userInfo = new UserInfo("Jack", AccountType.User, "Sales");
        orderManager.SetUser(userInfo);
    }

    @Test
    public void TestSetOrgUnit() {
        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        OrganisationalUnit unit = new OrganisationalUnit("Sales", 3000.50, organisationAssets);
        orderManager.SetOrgUnit(unit);
    }

    @Test
    public void TestCheckOrderNormal() {
        UserInfo userInfo = new UserInfo("Jack", AccountType.User, "Sales");

        Order order = new Order(OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());

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
