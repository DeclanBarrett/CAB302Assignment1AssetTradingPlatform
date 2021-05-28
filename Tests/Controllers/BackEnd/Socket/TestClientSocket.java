package Controllers.BackEnd.Socket;

import App_Start.Server;
import App_Start.SetupServer;
import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.NetworkObjects.User;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestClientSocket{

    static String token;
    static String token2;

    //TODO: ADD WHITEBOX TESTING

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

            token = ClientSocket.getInstance().AttemptLogin("User 1", "b717415eb5e699e4989ef3e2c4e9cbf7");
            token2 = ClientSocket.getInstance().AttemptLogin("User 3", "8d421e892a47dff539f46142eb09e56b");
        } catch (Exception e) {
            System.out.println("Someones poisoned the waterhole");
        }
    }

    @Test
    public void GetSalt() throws ServerException {
        ClientSocket.getInstance().GetSalt("User 1");

    }
    @Test
    public void GetSaltException() {
        ServerException exception = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetSalt("testytest");
        });
    }
    @Test
    public void AttemptLogin() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AttemptLogin("User 1", "b717415eb5e699e4989ef3e2c4e9cbf7");
    }

    @Test
    public void GetUser() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetUser(token, "User 1");
    }

    @Test
    public void GetOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisation(token, "Sales");
    }

    @Test
    public void GetOrganisationOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationOrders(token, "Sales");
    }

    @Test
    public void GetAllOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllOrders(token);
    }

    @Test
    public void GetBuyOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetBuyOrders(token);
    }

    @Test
    public void GetSellOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetSellOrders(token);
    }

    @Test
    public void GetOrganisationBuyOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationBuyOrders(token, "Sales");
    }

    @Test
    public void GetOrganisationSellOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationSellOrders(token, "Sales");
    }

    @Test
    public void AddOrderBuyPaper() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 50, 50, "Sales", null));
    }

    @Test
    public void AddOrderBuyTooHighPrice() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 5000, 99999, "Sales", null));
    }

    @Test
    public void AddOrderBuyTooLowPrice() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 50, 1, "Sales", null));
    }

    @Test
    public void AddOrderBuySmallQuantity() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 1, 50, "Sales", null));
    }

    @Test
    public void AddOrderSellPaper() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50, 50, "Sales", null));
    }

    @Test
    public void AddOrderSellTooHighPrice() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50000, 99999, "Sales", null));
    }

    @Test
    public void AddOrderSellTooLowPrice() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50, 1, "Sales", null));
    }

    @Test
    public void AddOrderSellSmallQuantity() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 1, 50, "Sales", null));
    }

    @Test
    public void RemoveOrder() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().RemoveOrder(token, 123456);
    }

    @Test
    public void GetAssetTypes() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAssetTypes(token);
    }

    @Test
    public void GetTradeHistory() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetTradeHistory(token, "Paper");
    }

    @Test
    public void AddUser() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddUser(token, new User("Jack Ma",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345"));
    }

    @Test
    public void GetAllUsers() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllUsers(token);
    }

    @Test
    public void UpdateUserPassword() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserPassword(token, "User 10", "b717415eb5e699e4989ef3e2c4e9cbf7", "123456");
    }

    @Test
    public void UpdateUserAccountType() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserAccountType(token, "User 2", AccountType.SystemAdmin);
    }

    @Test
    public void UpdateUserOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserOrganisation(token, "User 2", "Finance");
    }

    @Test
    public void AddAsset() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddAsset(token, "Jelly Beans");
    }

    @Test
    public void AddOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrganisation(token, new OrganisationalUnit("Jumble", 3000, null));
    }

    @Test
    public void GetAllOrganisations() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllOrganisations(token);
    }

    @Test
    public void UpdateOrganisationAsset() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateOrganisationAsset(token, "Sales", "Paper", 50);
    }

    @Test
    public void UpdateOrganisationCredit() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateOrganisationCredit(token, "Sales", 1);
    }

    @Test
    public void AttemptResetPassword() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AttemptResetPassword(token2, "User 3", "086e1b7e1c12ba37cd473670b3a15214");
    }
}
