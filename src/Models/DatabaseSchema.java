package Models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchema
{
    private static Connection db;
    private static Statement st;

    private static final String CREATE_TABLE_OrganisationalUnit =
            "CREATE TABLE IF NOT EXISTS OrganisationalUnit(" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AmountCredits INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE_Assets =
            "CREATE TABLE IF NOT EXISTS Assets(" +
                    "AssetName VARCHAR(60) NOT NULL PRIMARY KEY" +
                    ");";
    private static final String CREATE_TABLE_OrgHasQuantity =
            "CREATE TABLE IF NOT EXISTS OrgHasQuantity (" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
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
            "CREATE TABLE IF NOT EXISTS Orders(" +
                    "OrderID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL," +
                    "PlaceDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "OrderType ENUM('BUY', 'SELL') NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_Trade =
            "CREATE TABLE IF NOT EXISTS Trade(" +
                    "TradeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "BuyerOrgName VARCHAR(60) NOT NULL," +
                    "SellerOrgName VARCHAR(60) NOT NULL," +
                    "TradeDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "AssetPrice FLOAT NOT NULL," +
                    "FOREIGN KEY(BuyerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(SellerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";


    private DatabaseSchema() {}

    public void getDbConnection()
    {
        DatabaseSchema.db = DatabaseConnection.getInstance();
        if (db != null)
        {
            try{
                st = db.createStatement();
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }

    public static void createAssetTable() throws SQLException
    {
        st.execute(CREATE_TABLE_Assets);
    }
    public static void createOrgUnitTable() throws SQLException
    {
        st.execute(CREATE_TABLE_OrganisationalUnit)
    }
    public static void createOrgHasTable() throws SQLException
    {
        st.execute(CREATE_TABLE_OrgHasQuantity);
    }
    public static void createUserTable() throws SQLException
    {
        st.execute(CREATE_TABLE_Users);
    }
    public static void createTradeTable() throws SQLException
    {
        st.execute(CREATE_TABLE_Trade);
    }
    public static void createOrderTable() throws SQLException
    {
        st.execute(CREATE_TABLE_Orders);
    }

    public static void createAllTables()
    {
        try
        {
            createOrgUnitTable();
            createAssetTable();
            createUserTable();
            createTradeTable();
            createOrderTable();
            createOrgHasTable();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
