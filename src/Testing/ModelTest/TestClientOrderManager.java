package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.Order;
import Controllers.Backend.OrderType;
import Controllers.Backend.OrganisationalUnit;
import Controllers.Backend.User;
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
        User user = new User("Jack", "qwerty", AccountType.User, "Sales");
        orderManager.SetUser(user);
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
        User user = new User("Jack", "qwerty", AccountType.User, "Sales");

        Order order = new Order(OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());

        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        OrganisationalUnit unit = new OrganisationalUnit("Sales", 3000.50, organisationAssets);

        orderManager.SetUser(user);
        orderManager.SetOrgUnit(unit);
        orderManager.CheckOrderValidity(order);

        //assertEquals(, );
    }

}
