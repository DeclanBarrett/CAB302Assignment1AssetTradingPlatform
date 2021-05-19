package App_Start;

import Controllers.FrontEnd.ClientSocket;

public class ClientStart
{
    public static void main(String[] args)
    {


        for(int i = 0; i < 1; i++){
            new Thread(
                new Runnable(){
                    @Override
                    public void run()
                     {
                         new ClientSocket().startClient();
                     }
            }).start();
        }
    }
}
