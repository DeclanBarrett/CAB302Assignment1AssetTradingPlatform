package Models;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseDummyValues extends Database
{
    private DatabaseDummyValues() {}

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
            "INSERT INTO Assets VALUES ('Paper')"
    };

    //=========== Trade Dummy Data =============//
    private static final String[] tradeData = {
            "INSERT INTO Trade VALUES (1, 'Sales', 'Finance', 1, 10, 'Paper', 200);"
    };


    public static void insertUsers()
    {
        insert(userInputData);
    }

    public static void insertTheBoyz()
    {
        insert(theBoyz);
    }

    public static void insertOrganisationalUnits()
    {
        insert(orgData);
    }

    public static void insertAssetData()
    {
        insert(assetData);
    }

    public static void insertTradeData()
    {
        insert(tradeData);
    }


    private static void insert(String[] inserts)
    {
        for(String insert : inserts){
            try{
                st.execute(insert);
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }

}
