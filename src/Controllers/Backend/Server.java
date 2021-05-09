package Controllers.Backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    public void startServer() throws IOException, ClassNotFoundException
    {
        // Create ENDPOINT
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);

        System.out.println("Starting Loop");

        while (true) {
            // Accept new Endpoint for single Client
            Socket socket = serverSocket.accept();
            System.out.println("Client Connected" + socket.getRemoteSocketAddress());

            // Get client MSG
            System.out.println(socket.getInputStream().available());
            //TODO: Put socket.getInputStream().available in a while for threads!
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Received: " + in.readObject());
            // BufferedInputStream

            // End of Communication
            //in.close();
            System.out.println("End Server");
            socket.close();
            //serverSocket.close();
        }
    }
} // End of Class