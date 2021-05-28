package ModelTest;

import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class TestInformationGrabber
{
    InformationGrabber a;
    @BeforeEach
    void newInformationGrabber()
    {
        a = new InformationGrabber();
    }

    @Test
    void TestInsertOrganisation() {
        //a.insertOrganisation("");

    }
    @Test
    void TestInsertUser(){
        a.insertUser("Ethan1", "Admin", "SystemAdmin", "xx", "12345");

        // Will return an error if it doesnt work, so if no error -> pass
    }
    @Test
    void TestInsertAsset(){
        a.insertAsset("Paper");

    }

    @Test
    void TestInsertOrder() {
    }
    @Test
    void TestInsertTrade() {

    }
    @Test
    void TestUpdatePassword() {

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
