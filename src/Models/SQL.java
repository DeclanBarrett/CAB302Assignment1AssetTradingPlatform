package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SQL {
    // Table and Database Creation
    public static final String CREATE_DATABASE =
            "CREATE DATABASE trading_platform;";
    public static final String CREATE_TABLE_OrgHasQuantity =
            "CREATE TABLE IF NOT EXISTS OrgHasQuantity (" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)" +
                    ");";
    public static final String CREATE_TABLE_OrganisationalUnit =
            "CREATE TABLE IF NOT EXISTS  OrganisationalUnit(" +
                    "OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY," +
                    "AmountCredits INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE_Users =
            "CREATE TABLE IF NOT EXISTS  Users(" +
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
    public static final String CREATE_TABLE_Assets =
            "CREATE TABLE IF NOT EXISTS  Assets(" +
                    "AssetName VARCHAR(60) NOT NULL PRIMARY KEY" +
                    ");";
    public static final String CREATE_TABLE_Trade =
            "CREATE TABLE IF NOT EXISTS  Trade(" +
                    "TradeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "BuyerOrgName VARCHAR(60) NOT NULL," +
                    "SellerOrgName VARCHAR(60) NOT NULL," +
                    "TradeDateMilSecs INT NOT NULL," +
                    "AssetQuantity INT NOT NULL," +
                    "AssetName VARCHAR(60) NOT NULL," +
                    "FOREIGN KEY(BuyerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)," +
                    "FOREIGN KEY(SellerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)" +
                    ");";


    // Connection and Statements
    private Connection connection;


    // This function currently 'works' for the users tables
    public SQL(Connection connection) {
        this.connection = connection;
        CreateTables();
    }

    private void CreateTables() {
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}