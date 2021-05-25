package Controllers.BackEnd.Socket;

import Controllers.BackEnd.NetworkObjects.BradsPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Brad Kent
 * @author n10632999@qut.edu.au
 * @version 1.0
 * @since 0.1
 */
public class ClientSocket
{

    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 10000;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private static class ClientSocketHolder {
        private final static ClientSocket INSTANCE = new ClientSocket();
    }
    public void ClientSocket()
    {
        String serverName = "127.0.0.1";
        int port = 6066;

        try {
            // Connect to Server
            Socket client = new Socket(serverName, port);
            System.out.println("Client Connected: " + client.getRemoteSocketAddress());
            System.out.println("Streams");
            // Communication with server
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in  = new ObjectInputStream(client.getInputStream());
            System.out.println("Looping");

            out.writeObject(new BradsPacket());
            System.out.println(in.readObject());


            Thread.sleep(500);
            // Close Connections
            out.close();
            client.close();
        } catch (IOException | InterruptedException | ClassNotFoundException i){
            i.printStackTrace();
        }

        System.out.println("Client End...");
    }
} // End of Class
