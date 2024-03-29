package Controllers.BackEnd.Socket;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.Processing.JWTHandler;
import Controllers.BackEnd.Processing.LoginChecker;
import Controllers.BackEnd.Processing.OrderExecutor;
import Controllers.BackEnd.RequestType;
import Controllers.Exceptions.AuthenticationException;
import Models.InformationGrabber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Handles information sent by the client on the server side.
 */
public class ClientHandle implements Runnable
{
    static int id = 0;
    final Socket connection;
    private int thisId;
    private InformationGrabber dbRequest;


    public ClientHandle(Socket connection, InformationGrabber grabber)
    {
        dbRequest = grabber;
        thisId = id++;
        System.out.println("New Obj");
        this.connection = connection;
    }

    /**
     * Runs a new thread when a new connection comes in
     */
    @Override
    public void run()
    {
        System.out.println("Starting new Thread " + thisId);
        int i = 0;
        System.out.println(connection.toString());

        try {
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            while(!connection.isClosed()) {
                try {
                    // read the command, this tells us what to send the client back
                    final RequestType command = (RequestType) in.readObject();
                    handleCommand(in, out, command);
                } catch (SocketTimeoutException e) {
                    continue;
                }
            }
            System.out.println("Loop-----");
            in.close();
            connection.close();
        } catch (SocketException g) {
            System.out.println("Socket is not working");
            System.exit(1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("IO or  Class");
        } finally {
            System.out.println("Exit");
        }

    }

    /**
     * Handle commands recieved from the client socket.
     * @param inputStream - information from the client socket
     * @param outputStream - information to send to the client socket
     * @param command - the request type sent by the client socket.
     */
    public void handleCommand(ObjectInputStream inputStream, ObjectOutputStream outputStream, RequestType command) throws IOException {
        System.out.println("NEW HANDLE COMMAND: " + command.toString());
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
                            outputStream.writeObject("USERNAME OR PASSWORD INCORRECT");
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

            case RequestLogin: {
                try
                {
                    final String username = (String) inputStream.readObject();
                    final String password = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        LoginChecker checkLogin = new LoginChecker(dbRequest);
                        String token = checkLogin.compareLogin(username, password);
                        outputStream.writeObject(RequestType.SendLoginToken);
                        System.out.println("Send the login token");
                        outputStream.writeObject(token);
                        System.out.println("Finished sending the token");
                    }
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Login could not be authenticated");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

            case RequestResetPassword: {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    final String token = (String) inputStream.readObject();
                    final String username = (String) inputStream.readObject();
                    final String password = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.updatePassword(username, password);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Reset password successful");
                    }
                } catch(AuthenticationException a)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Login could not be authenticated for password reset");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Reset password failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestUserInfo: {
                try {
                    System.out.println("Request User Info");
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();

                    synchronized (dbRequest) {

                        handle.verifyToken(token);

                        UserInfo user = dbRequest.getUserInfo(username);
                        outputStream.writeObject(RequestType.SendUserInfo);
                        outputStream.writeObject(user);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("User Info request failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            break;

            case RequestOrganisation: {
                try
                {
                    System.out.println("Request Organisation");
                    // initialise JWT handler
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String orgName = (String) inputStream.readObject();

                    //Synchronise to keep thread safe.
                    synchronized (dbRequest) {

                        handle.verifyToken(token);
                        OrganisationalUnit orgUnit = dbRequest.getOrganisation(orgName);
                        outputStream.writeObject(RequestType.SendOrganisation);
                        outputStream.writeObject(orgUnit);
                    }

                }catch(AuthenticationException f)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Organisation info request failed.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestOrganisationOrders: {
                try
                {
                    // initialise JWT handler
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String orgName = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<Order> orders = dbRequest.getOrganisationOrders(orgName);
                        outputStream.writeObject(RequestType.SendOrders);
                        outputStream.writeObject(orders);
                        System.out.println("Why");
                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Organisation order request failed.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAllOrders: {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<Order> orders = dbRequest.getAllOrders();
                        outputStream.writeObject((RequestType.SendOrders));
                        outputStream.writeObject(orders);
                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Get all orders request failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestBuyOrders:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest) {
                        handle.verifyToken(token);
                        List<Order> orders = dbRequest.getBuyOrders();
                        outputStream.writeObject(RequestType.SendOrders);
                        outputStream.writeObject(orders);
                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Get all buy orders request failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestSellOrders:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<Order> orders = dbRequest.getSellOrders();
                        outputStream.writeObject(RequestType.SendOrders);
                        outputStream.writeObject(orders);

                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Get all sell orders request failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAddOrder: {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    Order order = (Order)  inputStream.readObject();

                    synchronized (dbRequest)
                    {

                        handle.verifyToken(token);
                        OrderExecutor executor = new OrderExecutor(dbRequest);
                        executor.executeOrder(order);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Order added");
                    }
                }catch (AuthenticationException e)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Could not be authenticated");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Add order request failed");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestRemoveOrder:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    int orderID = (Integer) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.deleteOrder(orderID);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Order deleted");
                    }
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            break;

            case RequestAssetTypes:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<String> assetTypes = dbRequest.getAssetTypes();
                        outputStream.writeObject(RequestType.SendAssetTypes);
                        outputStream.writeObject(assetTypes);
                    }
                } catch (AuthenticationException f)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Could not authenticate");
                }

                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to get asset types.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestTradeHistory: {
                try {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String assetType = (String) inputStream.readObject();

                    synchronized (dbRequest) {

                        handle.verifyToken(token);
                        List<Trade> tradeHistory = dbRequest.getTradeHistory(assetType);
                        outputStream.writeObject(RequestType.SendTradeHistory);
                        outputStream.writeObject(tradeHistory);
                    }
                }catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Could not authenticate.");
                }

                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to get trade history.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAllTradeHistory: {
                try {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest) {

                        handle.verifyToken(token);
                        List<Trade> tradeHistory = dbRequest.getAllTradeHistory();
                        outputStream.writeObject(RequestType.SendTradeHistory);
                        outputStream.writeObject(tradeHistory);
                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to get trade history.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAddUser:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    User user = (User) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        if (null == dbRequest.getOrganisation(user.getOrganisationalUnit())) {
                            throw new Exception();
                        }
                        dbRequest.insertUser(user.getUsername(), user.getOrganisationalUnit(), user.getAccountType(),
                                             user.getPassword(), user.getSalt());
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("User was successfully added to the database.");
                    }
                }catch(AuthenticationException exception){
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Could not authenticate.");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to add user.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAllUsers:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<UserInfo> userInfo = dbRequest.getAllUserInfo();
                        outputStream.writeObject(RequestType.SendAllUsers);
                        outputStream.writeObject(userInfo);
                    }
                } catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("User request failed.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestUpdateUserPassword:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    String hashedPassword = (String) inputStream.readObject();

                    // leaving this in temporarily so it doesnt mess up the read objects later down the track.
                    //String salt = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {

                        handle.verifyToken(token);
                        dbRequest.updatePassword(username, hashedPassword);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Users password was updated successfully.");

                    }
                } catch(AuthenticationException f)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to update user's password.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestUpdateUserAccountType:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    AccountType accountType = (AccountType) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.updateUserAccountType(username, accountType);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Users account type was updated successfully.");
                    }
                } catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e) {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to update user account type.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestUpdateUserOrganisation:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    String organisationName = (String)  inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.updateUserOrganisation(username, organisationName);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Users organisational unit was updated successfully.");
                    }
                } catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        outputStream.writeObject("Failed to update user's organisational unit.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAddAsset:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String assetName = (String)  inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.insertAsset(assetName);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Successfully added new asset to the database.");
                    }
                } catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        outputStream.writeObject("Successfully added new asset type");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAddOrganisation:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    OrganisationalUnit OrgUnit = (OrganisationalUnit) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.insertOrganisation(OrgUnit);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Successfully added new organisational unit to the database.");
                    }
                } catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to add new organisational unit.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestAllOrganisations:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        List<OrganisationalUnit> OrgUnitList = dbRequest.getAllOrganisations();
                        outputStream.writeObject(RequestType.SendAllOrganisations);
                        outputStream.writeObject(OrgUnitList);
                    }
                } catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("All organisational units are returned.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;

            case RequestUpdateOrganisationAsset:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String organisationName = (String) inputStream.readObject();
                    String assetType = (String) inputStream.readObject();
                    int assetQuantity = (Integer) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.updateOrganisationAsset(organisationName,assetType,assetQuantity);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Successfully added new organisational unit to the database.");
                    }
                }
                catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to update user's organisational unit.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            break;

            case RequestUpdateOrganisationCredit:
            {
                try
                {
                    JWTHandler handle = new JWTHandler();
                    String token = (String) inputStream.readObject();
                    String orgName = (String) inputStream.readObject();
                    int creditAmount = (Integer) inputStream.readObject();

                    synchronized (dbRequest)
                    {
                        handle.verifyToken(token);
                        dbRequest.updateOrganisationCredits(orgName, creditAmount);
                        outputStream.writeObject(RequestType.SendSuccessMessage);
                        outputStream.writeObject("Successfully added new credit amount to organisational unit");
                    }
                }catch(AuthenticationException exception)
                {
                    outputStream.writeObject(RequestType.SendAuthenticationError);
                    outputStream.writeObject("Authentication failed");
                }
                catch (Exception e)
                {
                    try {
                        outputStream.writeObject(RequestType.SendErrorCode);
                        outputStream.writeObject("Failed to update user's organisational unit.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}
