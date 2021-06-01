package Controllers.BackEnd.Processing;

import App_Start.SetupServer;
import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.ServerException;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Tests the Order Executor class
 */
public class TestOrderExecutor {

    static InformationGrabber database;
    static OrderExecutor orderExecutor;

    @BeforeAll
    public static void ConstructDatabaseConnection() {
        database = new InformationGrabber();
        orderExecutor = new OrderExecutor(database);

    }

    @BeforeEach
    public void ResetDatabase() {
        SetupServer setupServer = new SetupServer();
        setupServer.setsUpTheServer();
    }

    @Test
    public void TestCheckEnoughAssets() throws ServerException {
        ServerException exception = assertThrows(ServerException.class, () -> {
            Order order = new Order(-1, OrderType.SELL, "Casino Chips", 6009, 1000, "Finance", null);
            orderExecutor.executeOrder(order);
        });

        assertEquals(exception.getMessage(), "ORGANISATION DOESNT HAVE REQUIRED ASSETS");
    }

    @Test
    public void TestExecuteTooLargeSellOrder() throws ServerException {
        ServerException exception = assertThrows(ServerException.class, () -> {
            Order order = new Order(-1, OrderType.SELL, "Paper", 6009, 99, "Sales", null);
            orderExecutor.executeOrder(order);
        });

        assertEquals(exception.getMessage(), "ORGANISATION DOESNT HAVE REQUIRED ASSETS");
    }

    @Test
    public void TestExecuteTooLargeBuyOrder() {

        ServerException exception = assertThrows(ServerException.class, () -> {
            Order order = new Order(-1, OrderType.BUY, "Paper", 6000, 99, "Sales", null);
            orderExecutor.executeOrder(order);
        });

        assertEquals(exception.getMessage(), "ORGANISATION TOTAL ORDER COST IS TOO HIGH");
    }

    @Test
    public void TestCheckEnoughCredits() {

        ServerException exception = assertThrows(ServerException.class, () -> {
            Order order = new Order(-1, OrderType.BUY, "CPU hours", 6000, 99, "Finance", null);
            orderExecutor.executeOrder(order);
        });

        assertEquals(exception.getMessage(), "ORGANISATION TOTAL ORDER COST IS TOO HIGH");
    }

    @Test
    public void TestIfBuyHasDesiredEffect() throws ServerException {
        Double expectedCredit = database.getOrganisation("Admin").getCredits();
        Integer expectedAssetQuantity = 0;
        expectedCredit -= 100;
        expectedAssetQuantity += 1;
        Order order = new Order(-1, OrderType.BUY, "CPU hours", 1, 150, "Admin", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Admin").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Admin", "CPU hours");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }

    @Test
    public void TestIfBuyLargeQuantity() throws ServerException {
        Double expectedCredit = database.getOrganisation("Admin").getCredits();
        Integer expectedAssetQuantity = 0;
        expectedCredit -= 3000;
        expectedAssetQuantity += 30;
        Order order = new Order(-1, OrderType.BUY, "CPU hours", 100, 100, "Admin", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Admin").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Admin", "CPU hours");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }


    @Test
    public void TestIfSellHasDesiredEffect() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        Integer expectedAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        expectedCredit += 50;
        expectedAssetQuantity -= 1;
        Order order = new Order(-1, OrderType.SELL, "Casino Chips", 1, 50, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }

    @Test
    public void TestIfSellLowPrice() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        Integer expectedAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        expectedCredit += 70;
        expectedAssetQuantity -= 70;
        Order order = new Order(-1, OrderType.SELL, "Casino Chips", 100, 1, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }

    @Test
    public void TestIfSellLotsLowPrice() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        Integer expectedAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        expectedCredit += 140;
        expectedAssetQuantity -= 140;
        Order order = new Order(-1, OrderType.SELL, "Casino Chips", 500, 1, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }

    @Test
    public void TestIfSellRegularPrice() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        Integer expectedAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        expectedCredit += 0;
        expectedAssetQuantity -= 0;
        Order order = new Order(-1, OrderType.SELL, "Casino Chips", 5, 200, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        Integer actualAssetQuantity = database.getOrganisationIndividualAsset("Sales", "Casino Chips");
        assertEquals(expectedCredit, retrievedCredit);
        assertEquals(expectedAssetQuantity, actualAssetQuantity);
    }
}
