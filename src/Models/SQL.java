package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates and initialises SQL database. Features all table creation String statements
 */
public class SQL
{
    // Table and Database Creation
    public static final String CREATE_DATABASE =
            "CREATE DATABASE IF NOT EXISTS trading_platform;";
    public static final String CREATE_TABLE_OrgHasQuantity =
            "CREATE TABLE IF NOT EXISTS OrgHasQuantity (" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_OrganisationalUnit =
            "CREATE TABLE IF NOT EXISTS OrganisationalUnit(" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AmountCredits INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE_Users =
            "CREATE TABLE IF NOT EXISTS Users(" +
                    "UserName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "OrganisationalUnit VARCHAR(60) NOT NULL," +
                    "AccountType ENUM('User', 'UnitLeader', 'SystemAdmin') NOT NULL," +
                    "HashedPassword VARCHAR(60) NOT NULL," +
                    "Salt VARCHAR(60) NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnit) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";
    public static final String CREATE_TABLE_Orders =
            "CREATE TABLE IF NOT EXISTS Order(" +
                    "OrderID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL," +
                    "PlaceDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "OrderType ENUM('BUY', 'SELL') NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_Assets =
            "CREATE TABLE IF NOT EXISTS Assets(" +
                    "AssetName VARCHAR(60) NOT NULL PRIMARY KEY" +
                    ");";
    public static final String CREATE_TABLE_Trade =
            "CREATE TABLE IF NOT EXISTS Trade(" +
                    "TradeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "BuyerOrgName VARCHAR(60) NOT NULL," +
                    "SellerOrgName VARCHAR(60) NOT NULL," +
                    "TradeDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "FOREIGN KEY(BuyerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(SellerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";

    // Population of database via SQL Queries (based off Mock Socket data)
    private static final String INSERT_NEW_USER1 = "INSERT INTO Users VALUES ('User 1', 'Sales', 'User', " +
            "'b717415eb5e699e4989ef3e2c4e9cbf7', '12345')";
    private static final String INSERT_NEW_USER2 = "INSERT INTO Users VALUES ('User 2', 'Sales', 'User', " +
            "'b717415eb5e699e4989ef3e2c4e9cbf7', '12345')";
    private static final String INSERT_NEW_USER3 = "INSERT INTO Users VALUES ('User 3', 'Finance', 'User', " +
            "'8d421e892a47dff539f46142eb09e56b', '123456')";
    private static final String INSERT_NEW_USER4 = "INSERT INTO Users VALUES ('User 4', 'Finance', 'User', " +
            "'b26b843656e6834822b83179b4297620', '123457')";
    private static final String INSERT_NEW_USER5 = "INSERT INTO Users VALUES ('User 5', 'Finance', 'User', " +
            "'c3a4b61825259a74c26d49daa3e89312', '123458')";
    private static final String INSERT_NEW_USER6 = "INSERT INTO Users VALUES ('User 6', 'Research', 'User', " +
            "'2ec7484fa99bbaa7bdebe544f1f52f61', '123459')";
    private static final String INSERT_NEW_USER7 = "INSERT INTO Users VALUES ('User 7', 'Research', 'User', " +
            "'ccab59bc481b2105a4dbdf3d30a66248', '123450')";
    private static final String INSERT_NEW_USER8 = "INSERT INTO Users VALUES ('User 8', 'Research', 'User', " +
            "'aa3cae505478da19d13efa65bc8c71b3', '123451')";
    private static final String INSERT_NEW_USER9 = "INSERT INTO Users VALUES ('User 9', 'Research', 'User', " +
            "'43bf88d863f230f328c15ccf61d9d89d', '123452')";

    private static final String INSERT_NEW_USER_DECLAN = "INSERT INTO Users VALUES ('Declan Testing', 'Admin', 'SystemAdmin', " +
            "'802b492fc1d1fe592090399c1ca3b56a', '12346')";
    private static final String INSERT_NEW_USER_AIDEN = "INSERT INTO Users VALUES ('Aiden Testing', 'Admin', 'SystemAdmin', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123456')";
    private static final String INSERT_NEW_USER_BRAD = "INSERT INTO Users VALUES ('Brad Testing', 'Admin', 'SystemAdmin', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123456')";
    private static final String INSERT_NEW_USER_ETHAN = "INSERT INTO Users VALUES ('Ethan Testing', 'Admin', 'SystemAdmin', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123456')";

    private static final String INSERT_NEW_USER10 = "INSERT INTO Users VALUES ('User 10', 'Sales', 'UnitLeader', " +
            "'579d9ec9d0c3d687aaa91289ac2854e4', '123452')";
    private static final String INSERT_NEW_USER11 = "INSERT INTO Users VALUES ('User 11', 'Finance', 'UnitLeader', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123452')";
    private static final String INSERT_NEW_USER12 = "INSERT INTO Users VALUES ('User 12', 'Research', 'UnitLeader', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123452')";
    private static final String INSERT_NEW_USER13 = "INSERT INTO Users VALUES ('User 13', 'Research', 'UnitLeader', " +
            "'086e1b7e1c12ba37cd473670b3a15214', '123452')";

    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_SALES = "INSERT INTO OrganisationalUnit VALUES ('Sales', 3000.50)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE = "INSERT INTO OrganisationalUnit VALUES ('Finance', 100)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH = "INSERT INTO OrganisationalUnit VALUES ('Research', 90)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN = "INSERT INTO OrganisationalUnit VALUES ('Admin', 0)";


    // Connection and Statements
    private Connection connection;
    private PreparedStatement AddUser;
    private PreparedStatement addOrganisationalUnit;

    // This function currently 'works' for the users tables
    public SQL(Connection connection)
    {
        this.connection = connection;
    }


    // Create and Populate prepare statements in here
    public void populateDatabase()
    {
        //connection = DatabaseConnection.getInstance(); //TODO: This will cause an exception if not Connection is passed to Constructor!
        try {
            Statement createUserTableStatement = connection.createStatement();
            Statement createOrgHasQuantityTableStatement = connection.createStatement();
            Statement createOrganisationalUnitTableStatement = connection.createStatement();
            Statement createOrdersTableStatement = connection.createStatement();
            Statement createAssetsTableStatement = connection.createStatement();
            Statement createTradeTableStatement = connection.createStatement();

            createUserTableStatement.execute(CREATE_TABLE_Users);
            createOrgHasQuantityTableStatement.execute(CREATE_TABLE_OrgHasQuantity);
            createOrganisationalUnitTableStatement.execute(CREATE_TABLE_OrganisationalUnit);
            createOrdersTableStatement.execute(CREATE_TABLE_Orders);
            createAssetsTableStatement.execute(CREATE_TABLE_Assets);
            createTradeTableStatement.execute(CREATE_TABLE_Trade);

            Statement st = connection.createStatement();
            st.execute(CREATE_DATABASE);

            st.execute(CREATE_TABLE_Users);
            st.execute(CREATE_TABLE_OrgHasQuantity);
            st.execute(CREATE_TABLE_Orders);
            st.execute(CREATE_TABLE_OrganisationalUnit);
            st.execute(CREATE_TABLE_Assets);
            st.execute(CREATE_TABLE_Trade);

            AddUser = connection.prepareStatement(INSERT_NEW_USER1);
            AddUser = connection.prepareStatement(INSERT_NEW_USER2);
            AddUser = connection.prepareStatement(INSERT_NEW_USER3);
            AddUser = connection.prepareStatement(INSERT_NEW_USER4);
            AddUser = connection.prepareStatement(INSERT_NEW_USER5);
            AddUser = connection.prepareStatement(INSERT_NEW_USER6);
            AddUser = connection.prepareStatement(INSERT_NEW_USER7);
            AddUser = connection.prepareStatement(INSERT_NEW_USER8);
            AddUser = connection.prepareStatement(INSERT_NEW_USER9);
            AddUser = connection.prepareStatement(INSERT_NEW_USER10);
            AddUser = connection.prepareStatement(INSERT_NEW_USER11);
            AddUser = connection.prepareStatement(INSERT_NEW_USER12);
            AddUser = connection.prepareStatement(INSERT_NEW_USER13);
            AddUser = connection.prepareStatement(INSERT_NEW_USER_DECLAN);
            AddUser = connection.prepareStatement(INSERT_NEW_USER_AIDEN);
            AddUser = connection.prepareStatement(INSERT_NEW_USER_BRAD);
            AddUser = connection.prepareStatement(INSERT_NEW_USER_ETHAN);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_SALES);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Populate functions (Currently adding user and organisational unit)
    public void AddUser(String username, String orgUnit, String accType, String hashedPW, String salt)
    {
        try {
            AddUser.setString(1, username);
            AddUser.setString(2, orgUnit);
            AddUser.setString(3, accType);
            AddUser.setString(4, hashedPW);
            AddUser.setString(5, salt);

            if (AddUser != null) {
                AddUser.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void AddOrganisationalUnit(String orgUnitName, Integer amountCredits)
    {
        try {
            addOrganisationalUnit.setString(1, orgUnitName);
            addOrganisationalUnit.setInt(2, amountCredits);

            if (addOrganisationalUnit != null) {
                addOrganisationalUnit.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}