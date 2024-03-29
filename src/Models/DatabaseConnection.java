package Models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection, allows creation and grants global access to the singleton instance
 * of url set.
 */
public class DatabaseConnection
{
    private static Connection instance = null;

    private DatabaseConnection()
    {
        Properties props = new Properties();
        FileInputStream in = null;
        try
        {
            in = new FileInputStream("src/Models/db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException | FileNotFoundException sqle)
        {
            System.err.println(sqle);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance()
    {
        if (instance == null)
        {
            new DatabaseConnection();
        }
        return instance;
    }

}