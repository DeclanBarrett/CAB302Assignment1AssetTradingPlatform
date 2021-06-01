package Controllers.FrontEnd.User;

import App_Start.Server;
import App_Start.SetupServer;
import Controllers.BackEnd.NetworkObjects.Trade;
import Controllers.FrontEnd.Login.LoginController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the Client Order Manager class
 */
public class TestClientOrderManager
{

    OrderManager orderManager;
    static String token;


    @BeforeAll
    public static void ServerInstance() {
        SetupServer setup = new SetupServer();
        setup.setsUpTheServer();
        Server server = new Server();
        try {
            new Thread(() -> {
                try {
                    server.startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

            LoginController loginController = new LoginController();
            loginController.AttemptLogin("User 1", "qwerty");
        } catch (Exception e) {
            System.out.println("Someones poisoned the waterhole");
        }
    }

    @BeforeEach
    public void ConstructClientOrderManager() throws Exception
    {
        //List<Order> allOrders = ClientSocket.getInstance().GetOrganisationOrders(LoginController.GetToken(), LoginController.GetUser().getOrganisationalUnit());
        List<Trade> allTrades = new ArrayList<>();
        allTrades.add(new Trade(123456, "Paper", 5, 3.0, "Research", "Sales", new Date((new Date()).getTime() + 1)));
        allTrades.add(new Trade(123457, "Paper", 4, 3.2, "Research", "Sales", new Date((new Date()).getTime() + 2)));
        allTrades.add(new Trade(123458, "Paper", 6, 4.3, "Research", "Sales", new Date((new Date()).getTime() + 3)));
        allTrades.add(new Trade(123459, "Paper", 2, 4.5, "Research", "Sales", new Date((new Date()).getTime() + 4)));
        allTrades.add(new Trade(123410, "Paper", 4, 3.1, "Research", "Sales", new Date((new Date()).getTime() + 5)));
        allTrades.add(new Trade(123412, "Paper", 5, 5.0, "Research", "Sales", new Date((new Date()).getTime() + 6)));
        allTrades.add(new Trade(123413, "Paper", 1, 1.0, "Research", "Sales", new Date((new Date()).getTime() + 7)));

        orderManager = new OrderManager(allTrades);

    }

    @Test
    public void TestNewTrade()
    {
        List<Trade> newTrades = new ArrayList<>();
        newTrades.add(new Trade(123456, "Paper", 5, 3.0, "Research", "Sales", new Date((new Date()).getTime() + 1)));
        newTrades.add(new Trade(123457, "Paper", 4, 3.2, "Research", "Sales", new Date((new Date()).getTime() + 2)));
        newTrades.add(new Trade(123458, "Paper", 6, 4.3, "Research", "Sales", new Date((new Date()).getTime() + 3)));
        newTrades.add(new Trade(123459, "Paper", 2, 4.5, "Research", "Sales", new Date((new Date()).getTime() + 4)));
        newTrades.add(new Trade(123410, "Paper", 4, 3.1, "Research", "Sales", new Date((new Date()).getTime() + 5)));
        newTrades.add(new Trade(123412, "Paper", 5, 5.0, "Research", "Sales", new Date((new Date()).getTime() + 6)));
        newTrades.add(new Trade(123413, "Paper", 1, 1.0, "Research", "Sales", new Date((new Date()).getTime() + 7)));
        newTrades.add(new Trade(123414, "Paper", 1, 1.0, "Research", "Sales", new Date((new Date()).getTime() + 7)));

        Exception exception = assertThrows(Exception.class, () -> {
            orderManager.checkTrades(newTrades);
        });

    }

    @Test
    public void TestNewBuyTrade()
    {
        List<Trade> newTrades = new ArrayList<>();
        newTrades.add(new Trade(123456, "Paper", 5, 3.0, "Research", "Sales", new Date((new Date()).getTime() + 1)));
        newTrades.add(new Trade(123457, "Paper", 4, 3.2, "Research", "Sales", new Date((new Date()).getTime() + 2)));
        newTrades.add(new Trade(123458, "Paper", 6, 4.3, "Research", "Sales", new Date((new Date()).getTime() + 3)));
        newTrades.add(new Trade(123459, "Paper", 2, 4.5, "Research", "Sales", new Date((new Date()).getTime() + 4)));
        newTrades.add(new Trade(123410, "Paper", 4, 3.1, "Research", "Sales", new Date((new Date()).getTime() + 5)));
        newTrades.add(new Trade(123412, "Paper", 5, 5.0, "Research", "Sales", new Date((new Date()).getTime() + 6)));
        newTrades.add(new Trade(123413, "Paper", 1, 1.0, "Research", "Sales", new Date((new Date()).getTime() + 7)));
        newTrades.add(new Trade(123414, "Paper", 1, 1.0, "Sales", "Research", new Date((new Date()).getTime() + 7)));

        Exception exception = assertThrows(Exception.class, () -> {
            orderManager.checkTrades(newTrades);
        });

    }

    @Test
    public void TestNoNewTrade()
    {
        List<Trade> newTrades = new ArrayList<>();
        newTrades.add(new Trade(123456, "Paper", 5, 3.0, "Research", "Sales", new Date((new Date()).getTime() + 1)));
        newTrades.add(new Trade(123457, "Paper", 4, 3.2, "Research", "Sales", new Date((new Date()).getTime() + 2)));
        newTrades.add(new Trade(123458, "Paper", 6, 4.3, "Research", "Sales", new Date((new Date()).getTime() + 3)));
        newTrades.add(new Trade(123459, "Paper", 2, 4.5, "Research", "Sales", new Date((new Date()).getTime() + 4)));
        newTrades.add(new Trade(123410, "Paper", 4, 3.1, "Research", "Sales", new Date((new Date()).getTime() + 5)));
        newTrades.add(new Trade(123412, "Paper", 5, 5.0, "Research", "Sales", new Date((new Date()).getTime() + 6)));
        newTrades.add(new Trade(123413, "Paper", 1, 1.0, "Research", "Sales", new Date((new Date()).getTime() + 7)));

        orderManager.checkTrades(newTrades);
    }


}