package App_Start;


import Controllers.FrontEnd.ClientSocket;

public class ClientStart
{
    public static void main(String[] args)
    {
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.startClient();
    }
}
