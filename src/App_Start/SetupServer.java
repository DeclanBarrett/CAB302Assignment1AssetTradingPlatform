package App_Start;

import Models.DatabaseConnection;
import Models.DatabaseDummyValues;
import Models.DatabaseSchema;

import java.sql.Connection;

/**
 * Sets up database at its default state.
 */
public class SetupServer {
    public static void main(String[] args)
    {
       SetupServer server = new SetupServer();
       server.setsUpTheServer();
    }

    /**
     * Connects to the database and provides dummy values.
     */
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
