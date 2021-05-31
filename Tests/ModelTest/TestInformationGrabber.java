package ModelTest;

import App_Start.SetupServer;
import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.ServerException;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInformationGrabber
{
    static InformationGrabber database;
    @BeforeAll
    public static void ConstructDatabaseConnection() {
        database = new InformationGrabber();
    }

    @BeforeEach
    public void CleanDatabase() {
        SetupServer setupServer = new SetupServer();
        setupServer.setsUpTheServer();
    }

    @Test
    void TestInsertOrganisation() {
        HashMap<String, Integer> hm1 = new HashMap<>(10);
        OrganisationalUnit organisationalUnit = new OrganisationalUnit("Admin", 0, hm1);
        database.insertOrganisation(organisationalUnit);

        assertEquals(database.getOrganisation("Admin"), organisationalUnit);
    }
    @Test
    void TestInsertUser(){
        User user = new User("Ethan", "xx", AccountType.SystemAdmin, "Admin", "12345");
        database.insertUser("Ethan", "Admin", AccountType.SystemAdmin, "xx", "12345");
        assertEquals(user, database.getUser("Ethan"));
    }
    @Test
    void TestInsertAsset(){
        database.insertAsset("Paper");
        boolean contains = database.getAssetTypes().contains("Paper");
        assertTrue(contains);
    }

    @Test
    void TestInsertOrder() {
        Order order = new Order(19, OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());
        database.insertOrder(order);
        List<Order> orders = database.getAllOrders();
        for (Order neworder:orders){
            if (neworder.getAssetQuantity() == 50 && neworder.getRequestPrice() == 3.5) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }
    @Test
    void TestInsertTrade() {
        Trade trade = new Trade(37, "Paper", 500, 9.00, "Sales", "Finance", new Date());
        database.insertTrade(trade);
        List<Trade> trades = database.getTradeHistory("Paper");
        for (Trade newtrade:trades){
            if (newtrade.getAssetQuantity() == 500 && newtrade.getAssetPrice() == 9.00) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }
    @Test
    void TestUpdatePassword() {
        database.updatePassword("Ethan Testing", "Admin");
        assertEquals(database.getPassword("Ethan Testing"), "Admin");
    }
    @Test
    void TestUpdateUserAccountType() {
        database.updateUserAccountType("Ethan Testing", AccountType.User);
        UserInfo user = database.getUserInfo("Ethan Testing");
        assertEquals(user.getAccountType(), AccountType.User);
    }
    @Test
    void TestUpdateUserOrganisation() {
        database.updateUserOrganisation("Ethan Testing", "Admin");
        UserInfo user = database.getUserInfo("Ethan Testing");
        assertEquals(user.getOrganisationalUnit(), "Admin");
    }
    @Test
    void TestUpdateOrganisationAsset() {
        try {
            database.updateOrganisationAsset("Finance", "Casino Chips", 599);
            int assetQuantity = database.getOrganisationIndividualAsset("Finance", "Casino Chips");
            assertEquals(assetQuantity, 599);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestGetNonce() {
        //database.getNonce()
        // Assert Equals
    }
    @Test
    void TestGetPassword() {
        assertEquals(database.getPassword("Ethan Testing"), "086e1b7e1c12ba37cd473670b3a15214");
    }
    @Test
    void TestGetUser() {
        User user = new User("Ethan Testing", "qwerty", AccountType.SystemAdmin, "Admin", "123456");

        assertEquals(database.getUser("Ethan Testing"), user);
    }
    @Test
    void TestGetUserInfo() {
        UserInfo user = new UserInfo("Ethan Testing", AccountType.SystemAdmin, "Admin");

        assertEquals(database.getUserInfo("Ethan Testing"), user);
    }
    @Test
    void TestGetAllUsers() {
        User userFirst = new User("Aiden Testing", "qwerty", AccountType.SystemAdmin, "Admin", "123456");
        User userLast = new User("User 9", "qwerty", AccountType.User, "Research", "123452");
        List<User> users = database.getAllUsers();
        assertEquals(users.get(0), userFirst);
        assertEquals(users.get(16), userLast);
    }
    @Test
    void TestGetSalt() {
        try {
            assertEquals(database.getSalt("Ethan Testing"), "123456");
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestGetOrganisation() {
        HashMap<String, Integer> hm1 = new HashMap<>(10);
        OrganisationalUnit finance = new OrganisationalUnit("Finance", 10000, hm1);
        assertEquals(database.getOrganisation("Finance"), finance);
    }
    @Test
    void TestGetAllOrganisations() {
        HashMap<String, Integer> hm1 = new HashMap<>(10);
        OrganisationalUnit admin = new OrganisationalUnit("Admin", 10000, hm1);
        OrganisationalUnit sales = new OrganisationalUnit("Sales", 10000, hm1);
        List <OrganisationalUnit> orgUnits = database.getAllOrganisations();
        assertEquals(orgUnits.get(0), admin);
        assertEquals(orgUnits.get(3), sales);
    }
    @Test
    void TestGetOrganisationOrders() {
        List <Order> orders = database.getOrganisationOrders("Finance");
        Order firstOrder = new Order(1, OrderType.SELL, "Paper", 10, 100, "Finance", new Date());
        Order lastOrder = new Order(6, OrderType.BUY, "Casino Chips", 30, 100, "Finance", new Date());
        assertEquals(orders.get(0), firstOrder);
        assertEquals(orders.get(5), lastOrder);
    }
    @Test
    void TestGetBuyOrders() {
        List <Order> buyOrders = database.getBuyOrders();
        Order firstOrder = new Order(4, OrderType.BUY, "Casino Chips", 20, 100, "Finance", new Date());
        Order lastOrder = new Order(18, OrderType.BUY, "Casino Chips", 30, 100, "Research", new Date());
        assertEquals(buyOrders.get(0), firstOrder);
        assertEquals(buyOrders.get(8), lastOrder);
    }
    @Test
    void TestGetSellOrders() {
        List <Order> sellOrders = database.getSellOrders();
        Order firstOrder = new Order(1, OrderType.SELL, "Paper", 10, 100, "Finance", new Date());
        Order lastOrder = new Order(15, OrderType.SELL, "CPU hours", 10, 100, "Research", new Date());
        assertEquals(sellOrders.get(0), firstOrder);
        assertEquals(sellOrders.get(8), lastOrder);
    }
    @Test
    void TestGetOrders() {
        List <Order> allOrders = database.getAllOrders();
        Order lastOrder = new Order(18, OrderType.BUY, "Casino Chips", 30, 100, "Research", new Date());
        Order firstOrder = new Order(1, OrderType.SELL, "Paper", 10, 100, "Finance", new Date());
        assertEquals(allOrders.get(0), firstOrder);
        assertEquals(allOrders.get(17), lastOrder);
    }
    @Test
    void TestGetAssetTypes() {
        List<String> assetTypes = database.getAssetTypes();
        assertEquals(assetTypes.get(0), "Casino Chips");
        assertEquals(assetTypes.get(6), "RTX 3090TI");
    }
    @Test
    void TestGetTradeHistory() {
        List<Trade> trades = database.getTradeHistory("Paper");
        Trade firstTrade = new Trade(1, "Paper", 10, 6.0, "Sales", "Finance", new Date());
        Trade secondTrade = new Trade(7, "Paper", 10, 13.0, "Sales", "Finance", new Date());
        assertEquals(trades.get(0), firstTrade);
        assertEquals(trades.get(6), secondTrade);
    }
    @Test
    void TestDeleteOrder() {
        database.deleteOrder(1);
        Order secondOrder = new Order(2, OrderType.SELL, "Paper", 10, 100, "Finance", new Date());
        List <Order> orders = database.getOrderList();
        assertEquals(orders.get(0), secondOrder);
    }
}
