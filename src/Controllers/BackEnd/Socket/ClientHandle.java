package Controllers.BackEnd.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;


import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.NetworkObjects.User;
import Controllers.BackEnd.Processing.JWTHandler;
import Controllers.BackEnd.Processing.LoginChecker;
import Controllers.BackEnd.RequestType;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;
import Models.DatabaseConnection;
import Models.InformationGrabber;
import com.mysql.cj.log.Log;

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
    private InformationGrabber dbRequest;
    private ClientSocket clSocket;

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

    public void handleCommand(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, RequestType command)  {
        switch(command)
        {
            case RequestSalt: {
                try
                {
                    //client provides username
                    final String username = (String) inputStream.readObject();

                    synchronized (dbRequest) {
                        try
                        {
                            String salt = dbRequest.getSalt(username);

                            outputStream.writeObject(RequestType.SendSalt);
                            outputStream.writeObject(salt);
                            System.out.println(String.format("Gathered '%s' from database", salt));

                        } catch (Exception e) {
                            e.printStackTrace();
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("Salt not found.");
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            break;

            // Not too sure the protocol with these login tokens, to complete later.
            case RequestLogin: {

                try
                {
                    final String username = (String) inputStream.readObject();
                    final String password = (String) inputStream.readObject();
                    LoginChecker checkLogin = new LoginChecker(dbRequest);
                    synchronized (dbRequest)
                    {
                        try
                        {
                            String token = checkLogin.compareLogin(username, password);
                            outputStream.writeObject(RequestType.SendLoginToken);
                            outputStream.writeObject(token);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("Login failed");
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Will also hold off on this one until meeting
                // Issue mainly revolves around the Update password in information grabber
                // Need to fully understand these login/JWT tokens first.
            case RequestResetPassword: {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    String password = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        try
                        {
                            handle.verifyToken(token);
                            dbRequest.updatePassword(username, password);
                            outputStream.writeObject(RequestType.SendSuccessMessage);
                            outputStream.writeObject("Reset password successful");

                        } catch (Exception e) {
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("Reset password failed");
                            e.printStackTrace();
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            case RequestUserInfo: {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        try
                        {
                            handle.verifyToken(token);
                            User user = dbRequest.getUser(username);
                            outputStream.writeObject(RequestType.SendUserInfo);
                            outputStream.writeObject(user);

                        } catch (Exception e) {
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("User Info request failed");
                            e.printStackTrace();
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            case RequestOrganisation: {
                try
                {
                    // initialise JWT handler
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String orgName = (String) inputStream.readObject();

                    //Synchronise to keep thread safe.
                    synchronized (dbRequest)
                    {
                        try
                        {
                            handle.verifyToken(token);
                            OrganisationalUnit orgUnit = dbRequest.getOrganisation(orgName);
                            outputStream.writeObject(RequestType.SendOrganisation);
                            outputStream.writeObject(orgUnit);

                        } catch (Exception e) {
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("Organisation info request failed.");
                            e.printStackTrace();
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            case RequestOrganisationOrders: {
                try
                {
                    // initialise JWT handler
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String orgName = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        try
                        {
                            handle.verifyToken(token);
                            List<Order> orders = dbRequest.getOrganisationOrders(orgName);
                            outputStream.writeObject(RequestType.SendOrders);
                            outputStream.writeObject(orders);
                        }
                        catch (Exception e) {
                            outputStream.writeObject(RequestType.SendErrorCode);
                            outputStream.writeObject("Organisation order request failed.");
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
