package App_Start;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.User;
import Models.DatabaseConnection;
import Models.InformationGrabber;
import Models.SQL;
import Models.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Server {
    public static void main(String[] args) throws SQLException
    {
        UserDAO.updateDbConnection();
        List<User> a = UserDAO.getAll();
        a.forEach((n) -> { System.out.println(n);});
        DatabaseConnection.closeDbConnection();
    }
}
