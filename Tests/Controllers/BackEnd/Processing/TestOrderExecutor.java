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
    public void TestExecuteStandardSellOrder() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        expectedCredit += 1;
        Order order = new Order(-1, OrderType.SELL, "Paper", 1, 1, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        assertEquals(expectedCredit, retrievedCredit);
    }

    @Test
    public void TestExecuteStandardBuyOrder() throws ServerException {
        Double expectedCredit = database.getOrganisation("Sales").getCredits();
        expectedCredit -= 4;
        Order order = new Order(-1, OrderType.BUY, "Paper", 2, 2, "Sales", null);
        orderExecutor.executeOrder(order);
        Double retrievedCredit = database.getOrganisation("Sales").getCredits();
        assertEquals(expectedCredit, retrievedCredit);
    }
}
