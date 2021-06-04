package Models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base database values to allow for database testing.
 */
public class DatabaseDummyValues
{
    //=========== User Dummy Data =============//
    private static String[] userInputData = {
            "INSERT INTO Users VALUES ('User 1' , 'Sales'   , 'User'      , 'b717415eb5e699e4989ef3e2c4e9cbf7', '12345')",
            "INSERT INTO Users VALUES ('User 2' , 'Sales'   , 'User'      , 'b717415eb5e699e4989ef3e2c4e9cbf7', '12345')",
            "INSERT INTO Users VALUES ('User 3' , 'Finance' , 'User'      , '8d421e892a47dff539f46142eb09e56b', '123456')",
            "INSERT INTO Users VALUES ('User 4' , 'Finance' , 'User'      , 'b26b843656e6834822b83179b4297620', '123457')",
            "INSERT INTO Users VALUES ('User 5' , 'Finance' , 'User'      , 'c3a4b61825259a74c26d49daa3e89312', '123458')",
            "INSERT INTO Users VALUES ('User 6' , 'Research', 'User'      , '2ec7484fa99bbaa7bdebe544f1f52f61', '123459')",
            "INSERT INTO Users VALUES ('User 7' , 'Research', 'User'      , 'ccab59bc481b2105a4dbdf3d30a66248', '123450')",
            "INSERT INTO Users VALUES ('User 8' , 'Research', 'User'      , 'aa3cae505478da19d13efa65bc8c71b3', '123451')",
            "INSERT INTO Users VALUES ('User 9' , 'Research', 'User'      , '43bf88d863f230f328c15ccf61d9d89d', '123452')",
            "INSERT INTO Users VALUES ('User 10', 'Sales'   , 'UnitLeader', '579d9ec9d0c3d687aaa91289ac2854e4', '123452')",
            "INSERT INTO Users VALUES ('User 11', 'Finance' , 'UnitLeader', '086e1b7e1c12ba37cd473670b3a15214', '123452')",
            "INSERT INTO Users VALUES ('User 12', 'Research', 'UnitLeader', '086e1b7e1c12ba37cd473670b3a15214', '123452')",
            "INSERT INTO Users VALUES ('User 13', 'Research', 'UnitLeader', '086e1b7e1c12ba37cd473670b3a15214', '123452')"
    };
    private static String[] theBoyz = {
            "INSERT INTO Users VALUES ('Declan Testing', 'Admin', 'SystemAdmin', '802b492fc1d1fe592090399c1ca3b56a', '123456')",
            "INSERT INTO Users VALUES ('CAB302Demo' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')",
            "INSERT INTO Users VALUES ('Brad Testing ' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')",
            "INSERT INTO Users VALUES ('Ethan Testing' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')"
    };

    //=========== Organisational Unit Dummy Data =============//
    private static final String[] orgData = {
            "INSERT INTO OrganisationalUnit VALUES ('Sales'   , 10000)",
            "INSERT INTO OrganisationalUnit VALUES ('Finance' , 10000)",
            "INSERT INTO OrganisationalUnit VALUES ('Research', 10000)",
            "INSERT INTO OrganisationalUnit VALUES ('Admin'   , 10000)"
    };

    //=========== Asset Dummy Data =============//
    private static final String[] assetData = {
            "INSERT INTO Assets VALUES ('Paper')",
            "INSERT INTO Assets VALUES ('Juicy Fruit')",
            "INSERT INTO Assets VALUES ('RTX 2070TI')",
            "INSERT INTO Assets VALUES ('RTX 3090TI')",
            "INSERT INTO Assets VALUES ('CPU hours')",
            "INSERT INTO Assets VALUES ('Pickles')",
            "INSERT INTO Assets VALUES ('Casino Chips')"
    };

    //=========== Org Asset  Dummy Data =============//
    private static final String[] orgAssetData = {
            "INSERT INTO orghasquantity VALUES ('Sales', 'Paper', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'CPU hours', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'Pickles', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'RTX 3090TI', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'Casino Chips', 600)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Paper', 600)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'CPU hours', 600)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Pickles', 600)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'RTX 3090TI', 600)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Casino Chips', 600)",
            "INSERT INTO orghasquantity VALUES ('Research', 'Paper', 600)",
            "INSERT INTO orghasquantity VALUES ('Research', 'CPU hours', 600)",
            "INSERT INTO orghasquantity VALUES ('Research', 'Pickles', 600)",
            "INSERT INTO orghasquantity VALUES ('Research', 'RTX 3090TI', 600)",
            "INSERT INTO orghasquantity VALUES ('Research', 'Casino Chips', 600)",
    };

    //=========== Trade Dummy Data =============//
    private static final String[] tradeData = {
            "INSERT INTO Trade VALUES (1, 'Sales', 'Finance',  1622119729015, 10, 'Paper', 6.00)",
            "INSERT INTO Trade VALUES (2, 'Sales', 'Finance',  1622119729016, 10, 'Paper', 7.00)",
            "INSERT INTO Trade VALUES (3, 'Sales', 'Finance',  1622119729017, 10, 'Paper', 9.00)",
            "INSERT INTO Trade VALUES (4, 'Sales', 'Finance',  1622119729018, 10, 'Paper', 10.00)",
            "INSERT INTO Trade VALUES (5, 'Sales', 'Finance',  1622119729019, 10, 'Paper', 11.00)",
            "INSERT INTO Trade VALUES (6, 'Sales', 'Finance',  1622119729020, 10, 'Paper', 12.00)",
            "INSERT INTO Trade VALUES (7, 'Sales', 'Finance',  1622119729021, 10, 'Paper', 13.00)",
            "INSERT INTO Trade VALUES (8, 'Sales', 'Finance',  1622119729022, 133, 'CPU hours', 6.00)",
            "INSERT INTO Trade VALUES (9, 'Sales', 'Finance',  1622119729023, 101, 'CPU hours', 10.00)",
            "INSERT INTO Trade VALUES (10, 'Sales', 'Finance', 1622119729024, 102, 'CPU hours', 15.00)",
            "INSERT INTO Trade VALUES (11, 'Sales', 'Finance', 1622119729025, 100, 'CPU hours', 16.00)",
            "INSERT INTO Trade VALUES (12, 'Sales', 'Finance', 1622119729026, 15, 'CPU hours', 17.00)",
            "INSERT INTO Trade VALUES (13, 'Sales', 'Finance', 1622119729027, 13, 'CPU hours', 18.00)",
            "INSERT INTO Trade VALUES (14, 'Sales', 'Finance', 1622119729028, 12, 'CPU hours', 19.00)",
            "INSERT INTO Trade VALUES (15, 'Sales', 'Finance', 1622119729029, 6, 'CPU hours', 20.00)",
            "INSERT INTO Trade VALUES (16, 'Sales', 'Finance', 1622119729030, 10, 'CPU hours', 21.00)",
            "INSERT INTO Trade VALUES (17, 'Finance', 'Research', 1622119729031, 10, 'RTX 2070TI', 1.00)",
            "INSERT INTO Trade VALUES (18, 'Finance', 'Research', 1622119729032, 10, 'RTX 2070TI', 2.00)",
            "INSERT INTO Trade VALUES (19, 'Finance', 'Research', 1622119729033, 10, 'RTX 2070TI', 3.00)",
            "INSERT INTO Trade VALUES (20, 'Finance', 'Research', 1622119729034, 10, 'RTX 2070TI', 4.00)",
            "INSERT INTO Trade VALUES (21, 'Finance', 'Research', 1622119729035, 10, 'RTX 2070TI', 5.00)",
            "INSERT INTO Trade VALUES (22, 'Finance', 'Research', 1622119729036, 10, 'RTX 2070TI', 6.00)",
            "INSERT INTO Trade VALUES (23, 'Finance', 'Research', 1622119729037, 10, 'RTX 2070TI', 7.00)",
            "INSERT INTO Trade VALUES (24, 'Finance', 'Research', 1622119729038, 10, 'RTX 2070TI', 8.00)",
            "INSERT INTO Trade VALUES (25, 'Finance', 'Research', 1622119729039, 10, 'RTX 2070TI', 9.00)",
            "INSERT INTO Trade VALUES (26, 'Sales', 'Finance', 1622119729040, 9, 'Casino Chips', 100.00)",
            "INSERT INTO Trade VALUES (27, 'Sales', 'Finance', 1622119729041, 10, 'Casino Chips', 104.00)",
            "INSERT INTO Trade VALUES (28, 'Research', 'Finance', 1622119729042, 1, 'Casino Chips', 105.00)"
    };

    //=========== Trade Dummy Data =============//
    private static final String[] orderData = {
            "INSERT INTO Orders VALUES (1,  'Finance',   1, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (2,  'Finance',   3, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (3,  'Finance',   4, 10, 'CPU hours',    100, 'SELL')",
            "INSERT INTO Orders VALUES (4,  'Finance',   7, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (5,  'Finance',   8, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (6,  'Finance',   9, 30, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (7,  'Sales',     1, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (8,  'Sales',     3, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (9,  'Sales',     4, 10, 'CPU hours',    100, 'SELL')",
            "INSERT INTO Orders VALUES (10, 'Sales',     7, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (11, 'Sales',     8, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (12, 'Sales',     9, 30, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (13, 'Research',  1, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (14, 'Research',  3, 10, 'Paper',        100, 'SELL')",
            "INSERT INTO Orders VALUES (15, 'Research',  4, 10, 'CPU hours',    100, 'SELL')",
            "INSERT INTO Orders VALUES (16, 'Research',  7, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (17, 'Research',  8, 20, 'Casino Chips', 100, 'BUY')",
            "INSERT INTO Orders VALUES (18, 'Research',  9, 30, 'Casino Chips', 100, 'BUY')",
    };

    //=========== Trade Delete Data =============//
    private static final String[] deleteData = {
            "DELETE FROM orders",
            "DELETE FROM trade",
            "DELETE FROM users",
            "DELETE FROM orghasquantity",
            "DELETE FROM organisationalunit",
            "DELETE FROM assets"
    };


    private Connection connection;
    private Statement st;

    public DatabaseDummyValues(Connection connection)
    {
        this.connection = connection;
        execute(deleteData);
        execute(assetData);
        execute(orgData);
        execute(userInputData);
        execute(theBoyz);
        execute(tradeData);
        execute(orderData);
        execute(orgAssetData);
    }

    private void execute(String[] inserts)
    {
        for(String insert : inserts){
            try{
                st = connection.createStatement();
                st.execute(insert);
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }



}
