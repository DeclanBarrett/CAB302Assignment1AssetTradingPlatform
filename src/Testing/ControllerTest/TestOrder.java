package Testing.ControllerTest;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrder {

    Order order;

    @BeforeEach
    public void ConstructOrder(){
        order = new Order(OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());
    }

    @Test
    public void TestOrderType() {
        OrderType orderType = order.GetOrderType();
        assertEquals(OrderType.BUY, orderType);
    }

    @Test
    public void TestAssetType() {
        String assetType = order.GetAssetType();
        assertEquals("Paper", assetType);
    }

    @Test
    public void TestAssetQuantity() {
        int quantity = order.GetAssetQuantity();
        assertEquals(50, quantity);
    }

    @Test
    public void TestRequestPrice() {
        double price = order.GetRequestPrice();
        assertEquals(3.5, price);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = order.GetOrganisationalUnit();
        assertEquals("Sales", unit);
    }

    @Test
    public void TestGetDate() {
        Date date = order.GetDate();
        assertEquals(new Date(), date);
    }

}
