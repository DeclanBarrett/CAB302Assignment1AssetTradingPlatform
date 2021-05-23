package Controllers.BackEnd.Socket;

import Controllers.BackEnd.NetworkObjects.BradsPacket;

import java.net.*;
import java.io.*;

/**
 * Client Socket intiialisation.
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

            // Communication with server
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in  = new ObjectInputStream(client.getInputStream());

            for (int i = 0; i < 10; i++) {
                if (in.available() > 0) {
                    System.out.println(in.readObject());
                }
                out.writeObject(new BradsPacket());
            }
            Thread.sleep(500);
            // Close Connections
            out.close();
            client.close();
        } catch (IOException | ClassNotFoundException | InterruptedException i){
            i.printStackTrace();
        }

        System.out.println("Client End...");
    }
} // End of Class
