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
