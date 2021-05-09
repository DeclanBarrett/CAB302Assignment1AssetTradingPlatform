package Controllers.FrontEnd;

import Controllers.BradsPacket;

import java.net.*;
import java.io.*;

public class ClientSocket
{
    public void startClient()
    {
        String serverName = "127.0.0.1";
        int port = 6066;

        try {
            // Connect to Server
            Socket client = new Socket(serverName, port);
            System.out.println("Client Connected: " + client.getRemoteSocketAddress());

            // Write to Sever
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(new BradsPacket());

            // Send to Server
            out.close();
            client.close();

        } catch (IOException i){
            i.printStackTrace();
        }

        System.out.println("Client End...");
    }
} // End of Class
