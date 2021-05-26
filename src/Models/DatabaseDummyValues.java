package Models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            "INSERT INTO Users VALUES ('Aiden Testing' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')",
            "INSERT INTO Users VALUES ('Brad Testing ' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')",
            "INSERT INTO Users VALUES ('Ethan Testing' , 'Admin', 'SystemAdmin', '086e1b7e1c12ba37cd473670b3a15214', '123456')"
    };

    //=========== Organisational Unit Dummy Data =============//
    private static final String[] orgData = {
            "INSERT INTO OrganisationalUnit VALUES ('Sales'   , 3000.50)",
            "INSERT INTO OrganisationalUnit VALUES ('Finance' , 100)",
            "INSERT INTO OrganisationalUnit VALUES ('Research', 90)",
            "INSERT INTO OrganisationalUnit VALUES ('Admin'   , 0)"
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
            "INSERT INTO orghasquantity VALUES ('Sales', 'Paper', 6000)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'CPU hours', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'Pickles', 600)",
            "INSERT INTO orghasquantity VALUES ('Sales', 'RTX 3090TI', 60000)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Paper', 60)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Pickles', 9000)",
            "INSERT INTO orghasquantity VALUES ('Finance', 'Casino Chips', 690)",
            "INSERT INTO orghasquantity VALUES ('Research', 'Paper', 6)",
            "INSERT INTO orghasquantity VALUES ('Research', 'CPU hours', 9)",
    };

    //=========== Trade Dummy Data =============//
    private static final String[] tradeData = {
            "INSERT INTO Trade VALUES (1, 'Sales', 'Finance', 1, 10, 'Paper', 6.00)",
            "INSERT INTO Trade VALUES (2, 'Sales', 'Finance', 2, 10, 'Paper', 7.00)",
            "INSERT INTO Trade VALUES (3, 'Sales', 'Finance', 3, 10, 'Paper', 9.00)",
            "INSERT INTO Trade VALUES (4, 'Sales', 'Finance', 4, 10, 'Paper', 10.00)",
            "INSERT INTO Trade VALUES (5, 'Sales', 'Finance', 5, 10, 'Paper', 11.00)",
            "INSERT INTO Trade VALUES (6, 'Sales', 'Finance', 6, 10, 'Paper', 12.00)",
            "INSERT INTO Trade VALUES (7, 'Sales', 'Finance', 7, 10, 'Paper', 13.00)",
            "INSERT INTO Trade VALUES (8, 'Sales', 'Finance', 6, 133, 'CPU hours', 6.00)",
            "INSERT INTO Trade VALUES (9, 'Sales', 'Finance', 7, 101, 'CPU hours', 10.00)",
            "INSERT INTO Trade VALUES (10, 'Sales', 'Finance', 1, 102, 'CPU hours', 15.00)",
            "INSERT INTO Trade VALUES (11, 'Sales', 'Finance', 3, 100, 'CPU hours', 16.00)",
            "INSERT INTO Trade VALUES (12, 'Sales', 'Finance', 4, 15, 'CPU hours', 17.00)",
            "INSERT INTO Trade VALUES (13, 'Sales', 'Finance', 5, 13, 'CPU hours', 18.00)",
            "INSERT INTO Trade VALUES (14, 'Sales', 'Finance', 2, 12, 'CPU hours', 19.00)",
            "INSERT INTO Trade VALUES (15, 'Sales', 'Finance', 64, 6, 'CPU hours', 20.00)",
            "INSERT INTO Trade VALUES (16, 'Sales', 'Finance', 600, 10, 'CPU hours', 21.00)",
            "INSERT INTO Trade VALUES (17, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 1.00)",
            "INSERT INTO Trade VALUES (18, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 2.00)",
            "INSERT INTO Trade VALUES (19, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 3.00)",
            "INSERT INTO Trade VALUES (20, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 4.00)",
            "INSERT INTO Trade VALUES (21, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 5.00)",
            "INSERT INTO Trade VALUES (22, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 6.00)",
            "INSERT INTO Trade VALUES (23, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 7.00)",
            "INSERT INTO Trade VALUES (24, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 8.00)",
            "INSERT INTO Trade VALUES (25, 'Finance', 'Research', 6, 10, 'RTX 2070TI', 9.00)",
            "INSERT INTO Trade VALUES (26, 'Sales', 'Finance', 1000, 9, 'Casino Chips', 100.00)",
            "INSERT INTO Trade VALUES (27, 'Sales', 'Finance', 3000, 10, 'Casino Chips', 104.00)",
            "INSERT INTO Trade VALUES (28, 'Research', 'Finance', 2000, 1, 'Casino Chips', 105.00)"
    };

    //=========== Trade Dummy Data =============//
    private static final String[] orderData = {
            "INSERT INTO Orders VALUES (1,  'Finance',  2, 1,    'Paper',        1,    'BUY')",
            "INSERT INTO Orders VALUES (2,  'Finance',  3, 2,    'Paper',        2,    'SELL')",
            "INSERT INTO Orders VALUES (15, 'Finance',  4, 64,   'CPU hours',    64,   'BUY')",
            "INSERT INTO Orders VALUES (16, 'Finance',  5, 600,  'CPU hours',    600,  'SELL')",
            "INSERT INTO Orders VALUES (25, 'Research', 6, 6,    'RTX 2070TI',   6,    'BUY')",
            "INSERT INTO Orders VALUES (26, 'Finance',  7, 1000, 'Casino Chips', 1000, 'BUY')",
            "INSERT INTO Orders VALUES (27, 'Finance',  8, 3000, 'Casino Chips', 3000, 'BUY')",
            "INSERT INTO Orders VALUES (28, 'Finance',  9, 2000, 'Casino Chips', 2000, 'BUY')"
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
