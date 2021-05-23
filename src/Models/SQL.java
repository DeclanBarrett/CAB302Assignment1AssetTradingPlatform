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

/**
 * Creates and initialises PopulateDatabase database. Features all table creation String statements
 */
public class SQL {
    // Table and Database Creation
    public static final String CREATE_DATABASE =
            "CREATE DATABASE IF NOT EXISTS trading_platform;";







    // Population of database via PopulateDatabase Queries (based off Mock Socket data)



    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_SALES = "INSERT INTO OrganisationalUnit VALUES ('Sales', 3000.50)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE = "INSERT INTO OrganisationalUnit VALUES ('Finance', 100)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH = "INSERT INTO OrganisationalUnit VALUES ('Research', 90)";
    private static final String INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN = "INSERT INTO OrganisationalUnit VALUES ('Admin', 0)";

    private static final String INSERT_NEW_TRADE = "INSERT INTO Trade VALUES (1, 'Sales', 'Finance', 1, 10, 'Paper', 200);"; // Fixed, not sure about Data tho?
    private static final String INSERT_NEW_ASSET = "INSERT INTO Assets VALUES ('Paper')";

    // Connection and Statements
    private Connection connection;
    private PreparedStatement AddUser;
    private PreparedStatement AddTrade;
    private PreparedStatement AddAsset;
    private PreparedStatement addOrganisationalUnit;

    // This function currently 'works' for the users tables
    public SQL(Connection connection) {
        this.connection = connection;
        CreateTables();
    }

    private void CreateTables() {}



    // Create and Populate prepare statements in here
    public void populateDatabase() {
       // connection = DatabaseConnection.getInstance();
        try {
            Statement createUserTableStatement = connection.createStatement();
            Statement createOrgHasQuantityTableStatement = connection.createStatement();
            Statement createOrganisationalUnitTableStatement = connection.createStatement();
            Statement createOrdersTableStatement = connection.createStatement();
            Statement createAssetsTableStatement = connection.createStatement();
            Statement createTradeTableStatement = connection.createStatement();

           /* createUserTableStatement.execute(CREATE_TABLE_Users);
            createOrgHasQuantityTableStatement.execute(CREATE_TABLE_OrgHasQuantity);
            createOrganisationalUnitTableStatement.execute(CREATE_TABLE_OrganisationalUnit);
            createOrdersTableStatement.execute(CREATE_TABLE_Orders);
            createAssetsTableStatement.execute(CREATE_TABLE_Assets);
            createTradeTableStatement.execute(CREATE_TABLE_Trade);*/

            Statement st = connection.createStatement();
            //st.execute(CREATE_DATABASE);

            st.execute(CREATE_TABLE_OrganisationalUnit);
            st.execute(CREATE_TABLE_Assets);
            st.execute(CREATE_TABLE_Users);
            st.execute(CREATE_TABLE_OrgHasQuantity);
            st.execute(CREATE_TABLE_Trade);
            st.execute(CREATE_TABLE_Orders);


//            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_SALES);
//            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE);
//            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH);
//            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN);
//            addOrganisationalUnit.execute(INSERT_NEW_ORGANISATIONAL_UNIT_SALES);
//            addOrganisationalUnit.execute(INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE);
//            addOrganisationalUnit.execute(INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH);
//            addOrganisationalUnit.execute(INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN);
//
//            AddUser = connection.prepareStatement(INSERT_NEW_USER1);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER2);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER3);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER4);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER5);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER6);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER7);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER8);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER9);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER10);
//
//            AddUser.execute(INSERT_NEW_USER1);
//            AddUser.execute(INSERT_NEW_USER2);
//            AddUser.execute(INSERT_NEW_USER3);
//            AddUser.execute(INSERT_NEW_USER4);
//            AddUser.execute(INSERT_NEW_USER5);
//            AddUser.execute(INSERT_NEW_USER6);
//            AddUser.execute(INSERT_NEW_USER7);
//            AddUser.execute(INSERT_NEW_USER8);
//            AddUser.execute(INSERT_NEW_USER9);
//            AddUser.execute(INSERT_NEW_USER10);
//
//            AddAsset = connection.prepareStatement(INSERT_NEW_ASSET);
//            AddTrade = connection.prepareStatement(INSERT_NEW_TRADE);
//            AddTrade.execute();
//            AddAsset.execute();


            AddUser = connection.prepareStatement(INSERT_NEW_USER11);
            AddUser = connection.prepareStatement(INSERT_NEW_USER12);
            AddUser = connection.prepareStatement(INSERT_NEW_USER13);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER_DECLAN);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER_AIDEN);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER_BRAD);
//            AddUser = connection.prepareStatement(INSERT_NEW_USER_ETHAN);
            AddUser.execute(INSERT_NEW_USER_DECLAN);
            AddUser.execute(INSERT_NEW_USER_BRAD);
            AddUser.execute(INSERT_NEW_USER_ETHAN);
            AddUser.execute(INSERT_NEW_USER_AIDEN);
            AddUser.execute(INSERT_NEW_USER11);
            AddUser.execute(INSERT_NEW_USER12);
            AddUser.execute(INSERT_NEW_USER13);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_SALES);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_FINANCE);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_RESEARCH);
            addOrganisationalUnit = connection.prepareStatement(INSERT_NEW_ORGANISATIONAL_UNIT_ADMIN);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // Populate functions (Currently adding user and organisational unit)
    public void AddUser(String username, String orgUnit, String accType, String hashedPW, String salt) {
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
    public void AddOrganisationalUnit(String orgUnitName, Integer amountCredits) {
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