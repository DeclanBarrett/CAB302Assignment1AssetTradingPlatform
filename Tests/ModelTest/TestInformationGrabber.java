package ModelTest;

import App_Start.SetupServer;
import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.OrderType;
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
        database.updateOrganisationAsset("Finance", "Casino Chips", 599);
        int assetQuantity = database.getOrganisationIndividualAsset("Finance", "Casino Chips");
        assertEquals(assetQuantity, 599);
    }
    @Test
    void TestGetNonce() {

        // Assert Equals
    }
    @Test
    void TestGetPassword() {

    }
    @Test
    void TestGetUser() {

    }
    @Test
    void TestGetUserInfo() {

    }
    @Test
    void TestGetAllUsers() {

        // test first/last in list
    }
    @Test
    void TestGetSalt() {

    }
    @Test
    void TestGetOrganisation() {

    }
    @Test
    void TestGetAllOrganisations() {

    }
    @Test
    void TestGetOrganisationOrders() {

    }
    @Test
    void TestGetBuyOrder() {

    }
    @Test
    void TestGetSellOrders() {

    }
    @Test
    void TestGetOrders() {

    }
    @Test
    void TestGetAssetTypes() {

    }
    @Test
    void TestGetTradeHistory() {

    }
    @Test
    void TestDeletePassword() {

    }
}
