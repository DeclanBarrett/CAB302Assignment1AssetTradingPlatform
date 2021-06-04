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
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the Client Socket class
 */
public class TestClientSocket{

    static String token;
    static String token2;
    static String token0;

    private OutputStream exceptionThrowingOutputStream;
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
/**
    @BeforeEach
    public void SetupIOException() throws Exception {
        exceptionThrowingOutputStream = new OutputStream() {
            @Override
            public void write(byte[] b) throws IOException{
                throw new IOException();
            }
            @Override
            public void write(int b){}

            try
            {
                ClientSocket.
            }
        };
    }
 */

    @Test
    public void GetSalt() throws ServerException {
        ClientSocket.getInstance().GetSalt("User 1");

    }
    @Test
    public void GetSaltServerException() {
        ServerException exception = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetSalt("testytest");
        });
    }

    @Test
    public void AttemptLogin() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AttemptLogin("User 1", "b717415eb5e699e4989ef3e2c4e9cbf7");
    }

    @Test
    public void AttemptLoginAuthenticationException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().AttemptLogin("BadUsername", "BadPassword");
        });
    }

    @Test
    public void GetUser() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetUser(token, "User 1");
    }

    @Test
    public void GetUserServerException()
    {
        ServerException exception = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetUser("Bad Token", "BadUsername");
        });
    }

    @Test
    public void GetOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisation(token, "Sales");
    }

    @Test
    public void GetOrganisationAuthenticationException() {
        AuthenticationException authException = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().GetOrganisation("Bad Token", "Sales");
        });
    }


    @Test
    public void GetOrganisationOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationOrders(token, "Sales");
    }

    @Test
    public void GetOrganisationalException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetOrganisationOrders("BadToken", "Sales");
        });
    }


    @Test
    public void GetAllOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllOrders(token);
    }

    @Test
    public void GetAllException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetAllOrders("BadToken");
        });
    }

    @Test
    public void GetBuyOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetBuyOrders(token);
    }

    @Test
    public void GetBuyException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetBuyOrders("BadToken");
        });
    }

    @Test
    public void GetSellOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetSellOrders(token);
    }

    @Test
    public void GetSellOrdersException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetSellOrders("BadToken");
        });
    }

    @Test
    public void GetOrganisationBuyOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationBuyOrders(token, "Sales");
    }

    @Test
    public void GetOrgBuyOrdersException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetOrganisationBuyOrders("BadToken", "Sales");
        });
    }

    @Test
    public void GetOrganisationSellOrders() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetOrganisationSellOrders(token, "Sales");
    }

    @Test
    public void GetOrganisationSellException() {
        ServerException serverException = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetOrganisationSellOrders("BadToken", "Sales");
        });
    }

    @Test
    public void AddOrderBuyPaper() throws AuthenticationException, ServerException {
        try
        {
            ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 50, 50, "Sales", null));
        }
        catch (ServerException e)
        {
            System.out.println("Order price too high.");
        }
    }

    @Test
    public void AddOrderBuyTooHighPrice() throws AuthenticationException, ServerException {
        try
        {
            ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.BUY, "Paper", 5000, 99999, "Sales", null));
        }
        catch (ServerException e)
        {
            System.out.println("Order price too high.");
        }
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
        try
        {
            ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50, 50, "Sales", null));
        }
        catch (ServerException e)
        {
            System.out.println("Organisation does not have required assets to add order");
        }
    }

    @Test
    public void AddOrderSellTooHighPrice() throws AuthenticationException, ServerException {
        try {
            ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50000, 99999, "Sales", null));
        } catch (ServerException e) {
            System.out.println("Order price too high.");
        }
    }


    @Test
    public void AddOrderSellTooLowPrice() throws AuthenticationException, ServerException {
        try
        {
            ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 50, 1,"Sales", null));
        }
        catch (ServerException e)
        {
            System.out.println("Order price too low.");
        }

    }

    @Test
    public void AddOrderSellSmallQuantity() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrder(token, new Order(-1, OrderType.SELL, "Paper", 1, 50, "Sales", null));
    }

    @Test
    public void AddOrderAuthException(){
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().AddOrder("Bad Token", new Order(-1, OrderType.BUY, "Paper", 50, 50, "Sales", null));
        });
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
    public void GetAssetTypesException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().GetAssetTypes("Bad Token");
        });
    }

    @Test
    public void GetTradeHistory() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetTradeHistory(token, "Paper");
    }

    @Test
    public void GetTradeHistoryAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().GetTradeHistory("Bad Token", "Paper");
        });
    }

    @Test
    public void GetAllTradeHistory() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllTradeHistory(token);
    }

    @Test
    public void GetAllTradeHistoryAuthException() {
        ServerException exception = assertThrows(ServerException.class, () -> {
            ClientSocket.getInstance().GetAllTradeHistory("Bad Token");
        });
    }

    @Test
    public void AddUser() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddUser(token, new User("Jack Ma",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345"));
    }
    @Test
    public void AddUserAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().AddUser("Bad Token", new User("Jack Ma",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345"));
        });
    }

    @Test
    public void GetAllUsers() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllUsers(token);
    }

    @Test
    public void GetAllUsersAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().AddUser("Bad Token", new User("Jack Ma",  "b717415eb5e699e4989ef3e2c4e9cbf7", AccountType.User, "Sales", "12345"));
        });
    }

    @Test
    public void UpdateUserPassword() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserPassword(token, "User 10", "b717415eb5e699e4989ef3e2c4e9cbf7");
    }

    @Test
    public void UpdateUserPasswordAuth() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->{
            ClientSocket.getInstance().UpdateUserPassword("Bad Token", "User 10", "b717415eb5e699e4989ef3e2c4e9cbf7");
        });
    }

    @Test
    public void UpdateUserAccountType() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserAccountType(token, "User 2", AccountType.SystemAdmin);
    }

    @Test
    public void UpdateUserAccountTypeAuth() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->{
            ClientSocket.getInstance().UpdateUserAccountType("Bad Token", "User 2", AccountType.SystemAdmin);
        });
    }

    @Test
    public void UpdateUserOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateUserOrganisation(token, "User 2", "Finance");
    }

    @Test
    public void UpdateUserOrganisationAuth() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->{
            ClientSocket.getInstance().UpdateUserAccountType("Bad Token", "User 2", AccountType.SystemAdmin);
        });
    }

    @Test
    public void AddAsset() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddAsset(token, "Jelly Beans");
    }

    @Test
    public void AddAssetAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->{
            ClientSocket.getInstance().AddAsset("Bad Token", "Jelly Beans");
        });
    }

    @Test
    public void AddOrganisation() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AddOrganisation(token, new OrganisationalUnit("Jumble", 3000, null));
    }

    @Test
    public void AddOrgAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().AddOrganisation("Bad Token", new OrganisationalUnit("Jumble", 3000, null));
        });
    }

    @Test
    public void GetAllOrganisations() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().GetAllOrganisations(token);
    }

    @Test
    public void GetAllOrgsAuthentication() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().GetAllOrganisations("Bad Token");
        });
    }

    @Test
    public void UpdateOrganisationAsset() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateOrganisationAsset(token, "Sales", "Paper", 50);
    }

    @Test
    public void UpdateOrgAssetsAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().UpdateOrganisationAsset("Bad Token", "Sales", "Paper", 50);
        });
    }

    @Test
    public void UpdateOrganisationCredit() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().UpdateOrganisationCredit(token, "Sales", 1);
    }

    @Test
    public void UpdateOrgCreditAuthException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            ClientSocket.getInstance().UpdateOrganisationCredit("Bad Token", "Sales", 1);
        });
    }

    @Test
    public void AttemptResetPassword() throws AuthenticationException, ServerException {
        ClientSocket.getInstance().AttemptResetPassword(token2, "User 3", "086e1b7e1c12ba37cd473670b3a15214");
    }

    @Test
    public void AttemptResetPasswordAuthException() {
        AuthenticationException authExcept = assertThrows(AuthenticationException.class, () -> {
            String token0 = null;
            ClientSocket.getInstance().AttemptResetPassword(token0, "User400", "086e1b7e1c12ba37cd473670b3a15214");
        });

    }

}
