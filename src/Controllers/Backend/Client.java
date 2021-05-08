package Controllers.Backend;

import java.net.*;
import java.io.*;


public class Client
{

    public static void main(String[] args)
    {
        String serverName = "localhost";
        int port = 6066;
        DataInputStream in;
        DataOutputStream out;

        try {
            // Connect to Server
            Socket client = new Socket(serverName, port);
            System.out.println("Connected: " + client.getRemoteSocketAddress());

            // Send to Server
            out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("Hello From the client");

            // Received from Server
            in = new DataInputStream(client.getInputStream());
            System.out.println(in.readUTF());

            // Close the Connection
            client.close();

        } catch (IOException i){
            i.printStackTrace();
        }

        System.out.println("Client End...");

    }
} // End of Class
