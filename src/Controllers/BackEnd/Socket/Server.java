package Controllers.BackEnd.Socket;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server socket class.
 */
public class Server
{
    final private int PORT = 6066;
    final private int BACKLOG = 50;

    // Start and run the Server
    public static void main(String[] args)
    {
        Server server = new Server();
        try {
            server.startServer();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    /**
     * Method to start the server.
     * @throws IOException - Throws file in out exception
     * @throws ClassNotFoundException - Throws exception if  a class is not found.
     */
    public void startServer() throws IOException, ClassNotFoundException
    {
        // Create ENDPOINT
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);

        System.out.println("Starting Loop");

        while (true) {
            // Accept new Endpoint for single Client
            new Thread(new ClientHandle(serverSocket.accept())).start();
        }
    }
} // End of Class