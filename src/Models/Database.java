package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database
{
    protected static Connection db;
    protected static Statement st;
    protected static ResultSet rs;

    public void getDbConnection()
    {
        db = DatabaseConnection.getInstance();
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
}
