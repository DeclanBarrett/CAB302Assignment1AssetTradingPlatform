package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Assets
{
    public static void main(String[] args) throws SQLException
    {

        Connection connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cab302", "root", "HelloWorld11");


        connection.close();
    }
}
