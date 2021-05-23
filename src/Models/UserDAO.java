package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure Static singleton that interacts with the database
 */
public class UserDAO
{
    private UserDAO() {}
    private static String[] columnNames = {"UserName", "OrganisationalUnit", "AccountType", "HashedPassword", "Salt"};
    private static final String getAll = "Select * from Users";
    private static Connection db;

   public static void updateDbConnection()
   {
       UserDAO.db = DatabaseConnection.getInstance();
   }
    public static void printAll()
    {
        try {

            ResultSet rs =  db.prepareStatement(getAll).executeQuery();
            while(rs.next())
                for (int i = 0; i < 5; i++)
                    System.out.print(rs.getString(columnNames[i]) + " : ");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

   // public static <T> List<T> getAll()
    public static List<User> getAll()
    {
        List<User> users = new ArrayList<>();

        try {
            ResultSet rs =  db.prepareStatement(getAll).executeQuery();
            while(rs.next())
            {
                User user = new User(
                        rs.getString(columnNames[0]),
                        rs.getString(columnNames[3]),
                        AccountType.valueOf(rs.getString(columnNames[2])),
                        rs.getString(columnNames[1]),
                        rs.getString(columnNames[4])
                );
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
}
