package Controllers.BackEnd.Socket;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.*;
import Controllers.BackEnd.OrderType;
import Controllers.BackEnd.RequestType;
import Controllers.Exceptions.AuthenticationException;
import Controllers.Exceptions.ServerException;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Client Socket initialisation.
 */
public class ClientSocket implements IDataSource
{

    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 6066;
    public static final String NETWORK_ERROR_MESSAGE = "Networking Error";

    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private static class ClientSocketHolder {
        private final static ClientSocket INSTANCE = new ClientSocket();
    }

    public static ClientSocket getInstance() {
        return ClientSocket.ClientSocketHolder.INSTANCE;
    }

    protected ClientSocket()
    {
        System.out.println("Constructing Client Socket");
        try {
            // Connect to TestServer
            clientSocket = new Socket(HOSTNAME, PORT);
            System.out.println("Client Connected: " + clientSocket.getRemoteSocketAddress());

            // Communication with server
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream  = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e){
            //e.printStackTrace();
        }

        System.out.println("Client End...");
    }

    @Override
    public String GetSalt(String username) throws ServerException {
        try {
            System.out.println("Request Salt");
            //Tell the server we need it to pass the salt
            outputStream.writeObject(RequestType.RequestSalt);
            System.out.println("REQUEST TYPE: SALT");
            //Tell the server the username
            outputStream.writeObject(username);
            System.out.println("Wrote the username");
            outputStream.flush();
            System.out.println("Flush");

            //Get the return type
            RequestType response = (RequestType) inputStream.readObject();
            System.out.println("Retrieved: " + response.toString());

            //Either get a salt or send the error code
            if (response == RequestType.SendSalt ) {
                return (String) inputStream.readObject();
            } else if (response == RequestType.SendErrorCode) {
                throw new ServerException((String) inputStream.readObject());
            }

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Order> GetOrganisationBuyOrders(String token, String organisationName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisationOrders);

            //Tell the server the information
            outputStream.writeObject(token);
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
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<Order> GetOrganisationSellOrders(String token, String organisationName) throws AuthenticationException, ServerException {
        try {
            //Tell the server we need it to perform a request type function
            outputStream.writeObject(RequestType.RequestOrganisationOrders);

            //Tell the server the information
            outputStream.writeObject(token);
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
            }

            errorHandling(response);

        } catch (ServerException e) {
            throw new ServerException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e) {
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
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
            throw new ServerException(NETWORK_ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Handles the generic errors that can be expected from the server
     * @param response - the response from the server
     * @throws ServerException - if something goes wrong on the server for processing
     * @throws AuthenticationException - if something goes wrong with authenticating the user
     * @throws Exception - if something else goes wrong, like an IO issue
     */
    private void errorHandling(RequestType response) throws ServerException, AuthenticationException, Exception {
        if (response == RequestType.SendErrorCode) {
            throw new ServerException((String) inputStream.readObject());
        } else if (response == RequestType.SendAuthenticationError) {
            throw new AuthenticationException((String) inputStream.readObject());
        } else {
            throw new Exception();
        }
    }


}
