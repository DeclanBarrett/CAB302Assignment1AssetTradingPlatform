package ModelTest;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.OrderType;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

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
        User user = new User("Ethan", "")
        database.insertUser();
        database.getUser("Ethan");
        assertEquals()
    }
    @Test
    void TestInsertAsset(){
        database.insertAsset("Paper");
        boolean contains = database.getAssetTypes().contains("Paper");
        assertTrue(contains);
    }

    @Test
    void TestInsertOrder() {
        // Sql order column doesnt match order object ???
        Order order = new Order(19, OrderType.BUY, "Paper", 50, 3.5, "Sales", new Date());
        database.insertOrder(order);
        boolean contains = database.getAllOrders().contains(order);
        assertTrue(contains);
    }
    @Test
    void TestInsertTrade() {
        Trade trade = new Trade(11111111, "Paper", 500, 9.00, "Sales", "Finance", new Date());
        database.insertTrade(trade);
        boolean contains = database.getTradeHistory("Paper").contains(trade);
        assertTrue(contains);
    }
    @Test
    void TestUpdatePassword() {
        database.updatePassword("Ethan", "Admin1");
    }
    @Test
    void TestUpdateUserAccountType() {

    }
    @Test
    void TestUpdateUserOrganisation() {

    }
    @Test
    void TestUpdateOrganisationAsset() {

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
