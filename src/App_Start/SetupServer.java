package App_Start;

import Models.DatabaseConnection;
import Models.DatabaseDummyValues;
import Models.DatabaseSchema;

import java.sql.Connection;

/**
 *
 */
public class SetupServer {
    public static void main(String[] args)
    {
       SetupServer server = new SetupServer();
       server.setsUpTheServer();
    }

    public void setsUpTheServer() {
        try {
            Connection connection = DatabaseConnection.getInstance();
            DatabaseSchema schema = new DatabaseSchema(connection);
            DatabaseDummyValues dummy = new DatabaseDummyValues(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
