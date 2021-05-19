package Controllers.Backend;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Brad Kent
 * @author n10632999@qut.edu.au
 * @version 1.0
 * @since 0.1
 */
public class Server
{
    final private int PORT = 6066;
    final private int BACKLOG = 50;

    public void startServer()
    {
        // Create ENDPOINT
        ServerSocket serverSocket;

        try{
            serverSocket = new ServerSocket(PORT, BACKLOG);

            System.out.println("Starting Loop");

            while (true)
                new Thread(new ClientHandle(serverSocket.accept())).start();

        }catch(IOException i ){
            System.out.println("Server Socket not Assigning :(");
        }

    }
} // End of Class