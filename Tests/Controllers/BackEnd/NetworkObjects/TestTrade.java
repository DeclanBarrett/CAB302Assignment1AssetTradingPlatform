package Controllers.BackEnd.NetworkObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the trade class
 */
public class TestTrade {

    Trade trade;

    @BeforeEach
    public void ConstructTrade(){
        trade = new Trade(11111111, "Paper", 500, 9.00, "Sales", "Finance", new Date());
    }

    @Test
    public void TestGetTrade() {
        assertEquals(11111111, trade.getTradeID());
    }

    @Test
    public void TestGetAssetName() {
        assertEquals("Paper", trade.getAssetName());
    }

    @Test
    public void TestGetAssetQuantity() {
        assertEquals(500, trade.getAssetQuantity());
    }

    @Test
    public void TestGetPrice() {
        assertEquals(9.00f, trade.getAssetPrice());
    }

    @Test
    public void TestGetBuyerOrg() {
        assertEquals("Sales", trade.getBuyerOrgName());
    }

    @Test
    public void TestGetSellerOrg() {
        assertEquals("Finance", trade.getSellerOrgName());
    }

    @Test
    public void TestGetDate() {
        assertEquals(new Date(), trade.getTradeDateMilSecs());
    }

    @Test
    public void TestIfEqual() {
        assertTrue(trade.equals(trade));
    }

    @Test
    public void TestEqualWithIncorrectTrade() {
        assertFalse(trade.equals(new Trade(69, "Juice", 1, 1.0, "Rick", "Morty", new Date())));
    }

    @Test
    public void TestEqualWithIncorrectClass() {
        assertFalse(trade.equals(new Date()));
    }

    @Test
    public void TestCompareOrg() {
        assertTrue(0 == trade.compareTo(trade));
    }
}
