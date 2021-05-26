package Models;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure Static singleton that interacts with the database
 */
public class UserDAO extends Database
{
    private UserDAO() {}
    private static String[] columnNames = {"UserName", "OrganisationalUnit", "AccountType", "HashedPassword", "Salt"};
    private static final String getAll = "Select * from Users";

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

//    // Populate functions (Currently adding user and organisational unit)
//    public static void AddUser(User user) {
//        try {
//            db.prepareStatement()setString(1, user.getUsername());
//            AddUser.setString(2, user.getOrganisationalUnit());
//            AddUser.setString(3, user.getAccountType());
//            AddUser.setString(4, user.getPassword());
//            AddUser.setString(5, user.getSalt());
//
//            if (AddUser != null) {
//                AddUser.executeQuery();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}
