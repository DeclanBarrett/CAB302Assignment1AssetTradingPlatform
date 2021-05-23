package Models;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * File is Redundant
 */
public class SQL {

//    // Populate functions (Currently adding user and organisational unit)
//    public void AddUser(String username, String orgUnit, String accType, String hashedPW, String salt) {
//        try {
//            AddUser.setString(1, username);
//            AddUser.setString(2, orgUnit);
//            AddUser.setString(3, accType);
//            AddUser.setString(4, hashedPW);
//            AddUser.setString(5, salt);
//
//            if (AddUser != null) {
//                AddUser.executeQuery();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//    public void AddOrganisationalUnit(String orgUnitName, Integer amountCredits) {
//        try {
//            addOrganisationalUnit.setString(1, orgUnitName);
//            addOrganisationalUnit.setInt(2, amountCredits);
//
//            if (addOrganisationalUnit != null) {
//                addOrganisationalUnit.executeQuery();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}