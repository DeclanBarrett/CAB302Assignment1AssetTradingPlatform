package Client;

import Shared.AccountType;
import Shared.OrganisationalUnit;
import Shared.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClientOrderManager {

    ClientOrderManager orderManager;

    @BeforeEach
    public void ConstructClientOrderManager(){

        orderManager = new ClientOrderManager();

    }

    @Test
    public void TestCheckOrderNormal() {
        Shared.User user = new Shared.User("Jack", "qwerty", AccountType.User, "Sales");


        Shared.Order order = new Shared.Order(Shared.OrderType.BUY, "Paper", 50, 3.5, new Shared.OrganisationalUnit(), new Date());
        Shared.OrganisationalUnit unit = new Shared.OrganisationalUnit();

        orderManager.SetUser(user);
        orderManager.SetOrgUnit(unit);

        orderManager.CheckOrderValidity(order);

        //assertEquals(, );
    }

}
