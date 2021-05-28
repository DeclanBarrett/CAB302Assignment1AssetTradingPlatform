package App_Start;

import Controllers.BackEnd.Socket.ClientHandle;
import Models.InformationGrabber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * SetupServer socket class.
 */
public class Server
{
    final private int PORT = 6066;
    final private int BACKLOG = 50;

    private static final int SOCKET_ACCEPT_TIMEOUT = 100;

    // Start and run the SetupServer
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
        InformationGrabber database = new InformationGrabber();
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);
        serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
        System.out.println("Starting Loop");

        while (true) {
            // Accept new Endpoint for single Client
            try {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandle(socket, database)).start();
            } catch (SocketTimeoutException timeout) {

            }
        }
    }
} // End of Class