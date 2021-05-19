package App_Start;

import Models.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerStart
{
    public static void main(String[] args) throws SQLException
    {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trading_platform", "root", "HelloWorld11");
        SQL sql = new SQL(connection);
        sql.populateDatabase();
        connection.close();


    }
}
