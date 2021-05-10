package App_Start;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientStart extends Application
{
    public static void main(String[] args)
    {
//        ClientSocket clientSocket = new ClientSocket();
//        clientSocket.startClient();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Unnamed Client Organisation Trading System");

        Parent root;

        // Admin
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/Admin/AdminScreen.fxml"));
        // User
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/User/UserScreen.fxml"));
        // Login
        root = FXMLLoader.load(getClass().getResource("/Views/Login/Login.fxml"));
        // Reset
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/PasswordReset.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    } }
