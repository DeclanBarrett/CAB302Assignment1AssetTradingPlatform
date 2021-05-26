package Controllers.Socket;

import java.io.*;
import java.net.*;


public class Server extends Thread
{
    private int port = 6066;
    private ServerSocket serverSock;
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;


    public Server()
    {
        // Create the Server
        try {
            serverSock = new ServerSocket(port);
            serverSock.setSoTimeout(10000);
        } catch (IOException i) {
            System.out.println("Server failed to Create :(");
            i.printStackTrace();
            System.exit(1);
        }
    }


    public void run()
    {
        // Run the Server
        while (true)
        {
            try {
                // Server Port
                System.out.println("Waiting for a Client on port: " + serverSock.getLocalPort());

                // Wait for Client
                server = serverSock.accept();

                // Client Connected
                in  = new DataInputStream (server.getInputStream() );
                out = new DataOutputStream(server.getOutputStream());
                System.out.println("Accepted!, clientAddr: " + server.getRemoteSocketAddress());

                // Read Msg & Respond to Client
                System.out.println(in.readUTF());
                out.writeUTF("Hello There Client! Nice to meet you");

                // Close the Connection to Client
                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket Timed out :(");
                break;
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Thread t = new Server();
        t.start();
    }
} // End of Class-
