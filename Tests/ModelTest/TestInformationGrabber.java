package ModelTest;

import Models.DatabaseConnection;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestInformationGrabber
{
    InformationGrabber a;
    @BeforeEach
    void newInformationGrabber()
    {
        a = new InformationGrabber();
    }

    @Test
    void TestInsertUser(){
        a.insertUser("Declan", "Admin", "SystemAdmin", "xx", "12345");
        // Will return an error if it doesnt work, so if no error = pass
    }

    @Test
    void TestInsertAsset(){
        a.insertAsset("Paper");
    }

    @Test
    void TestInsertOrganisation() {
        //a.insertOrganisation("Sales", 3000.50);
    }

    @Test
    void TestInsertOrder() {
        //a.insertOrder(123456, "BUY", "Paper", 3, 3, "Research");
    }
    @Test
    void TestInsertTrade(){
        //a.insertTrade(12345, "Paper", 5, 3.0, "Research", "Sales", new Date());
    }
    @Test
    void TestUpdatePassword() {
        a.updatePassword("Ethan", "xx", "123456");
    }
    @Test
    void TestUpdateUserAccountType() {
        //a.updateUserAccountType("Ethan", "User");
    }
    @Test
    void TestUpdateUserOrganisation() {
        //a.updateUserAccountType("Ethan", "User");
    }
    @Test
    void TestUpdateOrganisationAsset() {
        //a.updateUserAccountType("Ethan", "User");
    }
    @Test
    void TestGetNonce() { // what is thiiiiiis
        String nonce = a.getSalt("Ethan");
        assertEquals(nonce, "xx");
    }
    @Test
    void TestGetPassword() {
        String password = a.getPassword("Ethan");
        assertEquals(password, "xx");
    }
    @Test
    void TestGetUser() {
        a.getUser("Ethan");

    }

    @Test
    void TestGetAllUsers() {
        // test first/last in list
    }

    @Test
    void TestGetUserInfo() {

    }

    @Test
    void TestGetAllUserInfo() {
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
// test first/last in list
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
    void TestGetOrderList() {

    }

    @Test
    void TestGetAllOrders() {
        // test first/last in list
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
