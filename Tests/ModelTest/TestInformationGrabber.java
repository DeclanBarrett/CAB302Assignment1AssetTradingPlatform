package ModelTest;

import Models.DatabaseConnection;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInformationGrabber
{
    InformationGrabber a;
    @BeforeEach
    void newInformationGrabber()
    {
        a = new InformationGrabber();
    }

    @Test
    void TestInsertOrganisation() throws SQLException {
        //a.insertOrganisation("");

    }
    @Test
    void TestInsertUser(){
        a.insertUser("Ethan", "Admin", "SystemAdmin", "xx", "12345");

        // Will return an error if it doesnt work, so if no error -> pass
    }
    @Test
    void TestInsertAsset(){
        a.insertAsset("Paper");

    }

    @Test
    void TestInsertOrder() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestInsertTrade() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestUpdatePassword() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestUpdateUserAccountType() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestUpdateUserOrganisation() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestUpdateOrganisationAsset() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetNonce() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
        // Assert Equals
    }
    @Test
    void TestGetPassword() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetUser() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetUserInfo() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetAllUsers() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
        // test first/last in list
    }
    @Test
    void TestGetSalt() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetOrganisation() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetAllOrganisations() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetOrganisationOrders() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetBuyOrder() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetSellOrders() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetOrders() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetAssetTypes() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestGetTradeHistory() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
    @Test
    void TestDeletePassword() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Statement st = connection.createStatement();
        st.execute("INSERT INTO Users (UserName, OrganisationalUnit, AccountType, HashedPassword, Salt) VALUES ('Ethan', '', ?, ?, ?);");
        connection.close();
    }
}
