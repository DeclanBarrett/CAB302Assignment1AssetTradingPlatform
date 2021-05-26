package Controllers.BackEnd.Socket;

import Controllers.BackEnd.RequestType;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;
import Models.InformationGrabber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClientHandle {

    private static final String HOSTNAME = "127.0.0.1";
    final private int PORT = 6066;
    final private int BACKLOG = 50;
    public static final String NETWORK_ERROR_MESSAGE = "Networking Error";

    private Socket clientSocket;
    private ServerSocket serverSocket;
    private Socket handleSocket;

    private ObjectOutputStream outputStreamClient;
    private ObjectInputStream inputStreamClient;

    private ObjectInputStream inputStreamServer;
    private ObjectOutputStream outputStreamServer;

    private ClientHandle handle;


    @BeforeEach
    public void ConstructClientServerInfo()
    {
        System.out.println("Constructing Client Socket");
        try {
            System.out.println("1");
            ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);

            System.out.println("2");
            clientSocket = new Socket(HOSTNAME, PORT);

            System.out.println("3");
            Socket handleSocket = serverSocket.accept();

            InformationGrabber database = new InformationGrabber();
            System.out.println("4");
            new Thread(new ClientHandle(handleSocket, database)).start();
            outputStreamClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStreamClient = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("5");


            System.out.println("6");
            outputStreamServer = new ObjectOutputStream(handleSocket.getOutputStream());
            inputStreamServer = new ObjectInputStream(handleSocket.getInputStream());

            InformationGrabber grabber = new InformationGrabber();
            handle = new ClientHandle(handleSocket, grabber);

        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Client End...");
    }

    @Test
    public void GetSalt() throws Exception {
        outputStreamClient.writeObject(RequestType.RequestSalt);
        outputStreamClient.writeObject("User 1");

        handle.handleCommand(inputStreamServer, outputStreamServer, RequestType.RequestSalt);

        assertEquals(inputStreamClient.readObject(), "WHAT");

        //Get the return type

    }
/*
    @Test
    public String AttemptLogin(String username, String password) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to handle login
            System.out.println("Attempting Login");
            outputStream.writeObject(RequestType.RequestLogin);

            //Tell the server the username and hashed password
            outputStream.writeObject(username);
            outputStream.writeObject(password);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();
            System.out.println("Response is: " + response.toString());
            //Either get a token or send the error code
            if (RequestType.SendLoginToken.equals(response)) {
                System.out.println("Get the Login Token");
                String jwt = (String) inputStream.readObject();
                System.out.println(jwt);
                return jwt;
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String AttemptResetPassword(String token, String username, String newPassword) throws AuthenticationException, ServerException {
        try {
            System.out.println("Attempting Reset Password");
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestResetPassword);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(username);
            outputStream.writeObject(newPassword);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public UserInfo GetUser(String token, String username) throws AuthenticationException, ServerException {
        try {
            System.out.println("Request User Info");
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUserInfo);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(username);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();
            System.out.println("Oopsie");
            //Either get some information or send the error code
            if (response == RequestType.SendUserInfo) {
                System.out.println("Send User Info");
                return (UserInfo) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public OrganisationalUnit GetOrganisation(String token, String orgName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisation);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(orgName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrganisation) {
                return (OrganisationalUnit) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetOrganisationOrders(String token, String orgName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisationOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(orgName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                return (List<Order>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetAllOrders(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAllOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                return (List<Order>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetBuyOrders(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestBuyOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                return (List<Order>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetSellOrders(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestSellOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                return (List<Order>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetOrganisationBuyOrders(String token, String organisationName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisationOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(organisationName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                List<Order> orders = (List<Order>) inputStream.readObject();
                List<Order> buyOrders = new ArrayList<>();
                for (Order order: orders) {
                    if (order.getOrderType().equals(OrderType.BUY)) {
                        buyOrders.add(order);
                    }
                }
                return buyOrders;
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Order> GetOrganisationSellOrders(String token, String organisationName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisationOrders);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(organisationName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendOrders) {
                List<Order> orders = (List<Order>) inputStream.readObject();
                List<Order> sellOrders = new ArrayList<>();
                for (Order order: orders) {
                    if (order.getOrderType().equals(OrderType.SELL)) {
                        sellOrders.add(order);
                    }
                }
                return sellOrders;
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String AddOrder(String token, Order newOrder) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAddOrder);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(newOrder);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String RemoveOrder(String token, int orderID) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestRemoveOrder);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(orderID);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<String> GetAssetTypes(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAssetTypes);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendAssetTypes) {
                return (List<String>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<Trade> GetTradeHistory(String token, String AssetType) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestTradeHistory);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(AssetType);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendTradeHistory) {
                return (List<Trade>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String AddUser(String token, User user) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAddUser);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(user);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<UserInfo> GetAllUsers(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAllUsers);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendAllUsers) {
                return (List<UserInfo>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String UpdateUserPassword(String token, String username, String hashedPassword, String salt) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUpdateUserPassword);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(username);
            outputStream.writeObject(hashedPassword);
            outputStream.writeObject(salt);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String UpdateUserAccountType(String token, String username, AccountType accountType) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUpdateUserAccountType);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(username);
            outputStream.writeObject(accountType);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String UpdateUserOrganisation(String token, String username, String organisationName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUpdateUserOrganisation);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(username);
            outputStream.writeObject(organisationName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String AddAsset(String token, String assetName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAddAsset);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(assetName);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String AddOrganisation(String token, OrganisationalUnit organisation) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAddOrganisation);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(organisation);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public List<OrganisationalUnit> GetAllOrganisations(String token) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestAllOrganisations);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendAllOrganisations) {
                return (List<OrganisationalUnit>) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String UpdateOrganisationAsset(String token, String organisationName, String AssetType, int AssetQuantity) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUpdateOrganisationAsset);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(organisationName);
            outputStream.writeObject(AssetType);
            outputStream.writeObject(AssetQuantity);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Test
    public String UpdateOrganisationCredit(String token, String organisationName, int creditAmount) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestUpdateOrganisationCredit);

            //Tell the server the information
            outputStream.writeObject(token);
            outputStream.writeObject(organisationName);
            outputStream.writeObject(creditAmount);
            outputStream.flush();

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();

            //Either get some information or send the error code
            if (response == RequestType.SendSuccessMessage) {
                return (String) inputStream.readObject();
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }
*/
    /**
     * Handles the generic errors that can be expected from the server
     * @param response - the response from the server
     * @throws ServerException - if something goes wrong on the server for processing
     * @throws AuthenticationException - if something goes wrong with authenticating the user
     * @throws Exception - if something else goes wrong, like an IO issue

    /*
    private void errorHandling(RequestType response) throws ServerException, AuthenticationException, Exception {
        if (response == RequestType.SendErrorCode) {
            throw new ServerException((String) inputStream.readObject());
        } else if (response == RequestType.SendAuthenticationError) {
            throw new AuthenticationException((String) inputStream.readObject());
        } else {
            throw new Exception("Something has gone wrong");
        }
    }
    */
}
