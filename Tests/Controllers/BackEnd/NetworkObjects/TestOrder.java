package Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrder {

    Order order;

    @BeforeEach
    public void ConstructOrder(){
        order = new Order(123456, OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());
    }

    @Test
    public void TestOrderConstruction() {
        order = new Order(123456, OrderType.BUY, "Paper", 50, 3.5, new OrganisationalUnit("Sales", 0, null), new Date());
    }
    
    @Test
    public void TestOrderID() {
        Integer orderID = order.getOrderID();
        assertEquals(123456, orderID);
    }

    @Test
    public void TestOrderType() {
        OrderType orderType = order.getOrderType();
        assertEquals(OrderType.BUY, orderType);
    }

    @Test
    public void TestAssetType() {
        String assetType = order.getAssetType();
        assertEquals("Paper", assetType);
    }

    @Test
    public void TestAssetQuantity() {
        int quantity = order.getAssetQuantity();
        assertEquals(50, quantity);
    }

    @Test
    public void TestRequestPrice() {
        double price = order.getRequestPrice();
        assertEquals(3.5, price);
    }

    @Test
    public void TestGetOrganisationalUnit() {
        String unit = order.getOrganisationalUnit();
        assertEquals("Sales", unit);
    }

    @Test
    public void TestGetDate() {
        Date date = order.getDate();
        assertEquals(new Date(), date);
    }

    @Test
    public void TestOrderEquals() {
        assertTrue(order.equals(order));
    }

    @Test
    public void TestOrderDoesntEquals() {
        assertFalse(order.equals(new Order(123457, OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date())));
    }

    @Test
    public void TestOrderClassDoesntEquals() {
        assertFalse(order.equals(new Object()));
    }

    @Test
    public void TestCompareOrder() {
        assertTrue(0 == order.compareTo(order));
    }

    @Test
    public void TestCompareMismatchedOrder() {
        assertFalse(1 == order.compareTo(order));
    }
}
