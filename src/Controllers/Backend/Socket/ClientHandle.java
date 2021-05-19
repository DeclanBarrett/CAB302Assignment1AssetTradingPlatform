package Controllers.Backend.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Brad Kent
 * @author n10632999@qut.edu.au
 * @version 1.2
 * @since 0.3
 */
public class ClientHandle implements Runnable
{
    static int id = 0;
    final Socket connection;
    private int thisId;

    public ClientHandle(Socket connection)
    {
        thisId = id++;
        System.out.println("New Obj");
        this.connection = connection;
    }

    @Override
    public void run()
    {
        System.out.println("Starting new Thread " + thisId);
        int i = 0;
        System.out.println(connection.toString());

        //TODO: Put socket.getInputStream().available in a while for threads!
        //TODO: BufferedInputStream

        try {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            while(!connection.isClosed()) {
            //while(in.read() != -1) {
                if (connection.getInputStream().available() > 0) {
                    System.out.println("Received: " + in.readObject() + " : " + i++);
                } else{
                    System.out.println("Just Waiting for: " + thisId + "...");
                    Thread.sleep(500);
                }
            }
            System.out.println("Loop-----");
            in.close();
            connection.close();
        } catch (SocketException g) {
            System.out.println("Socket is fucked");
            System.exit(1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("IO or  Class");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Interrupted");
        } finally {
            System.out.println("Exit");
        }

    }
}
