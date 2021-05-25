package App_Start;

import Controllers.Backend.Server;

import java.sql.SQLException;

public class ServerStart
{
    //
    public static void main(String[] args) throws SQLException
    {
//        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trading_platform", "root", "HelloWorld11");
//        SQL sql = new SQL(connection);
//        sql.populateDatabase();
//        connection.close();

//        final String NAME, PASSWORD, ACCOUNT_TYPE, ORG_UNIT, SALT;
//        NAME = "Name"; PASSWORD = "PASSWORD"; ACCOUNT_TYPE = "Account_Type"; ORG_UNIT = "Organisational_Unit"; SALT = "Salt";
//
//        User user = new User("UserName", "Password", null, "OrgStuff", "MeFeelingSalty");
//        JSONObject bradsPacket = new JSONObject();
//        JSONObject us = new JSONObject();
//        us.put(NAME, user.getUsername());
//        us.put(PASSWORD, user.getPassword());
//        us.put(ACCOUNT_TYPE, " OBJECT :)");
//        us.put(ORG_UNIT, user.getOrganisationalUnit());
//        us.put(SALT, user.getSalt());
//
//        bradsPacket.put("User", us);
//        bradsPacket.put("JSONToken", "129741327986413269132");
//        bradsPacket.put("REQ", "UPDATE");
//        System.out.println(bradsPacket);

        new Server().startServer();
    }
}