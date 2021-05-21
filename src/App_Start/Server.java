package App_Start;

import Models.DatabaseConnection;
import Models.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance();
        SQL sql = new SQL(connection);
        sql.populateDatabase();
        connection.close();
    }
}
