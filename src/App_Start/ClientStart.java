package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClientStart extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialise GUI screen, make visible
     * @param primaryStage Handles creation of main GUI screen
     * @throws Exception To stop GUI breaking
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        primaryStage.setTitle("Unnamed Client Organisation Trading System");

        Parent root;

        // Admin
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/Admin/AdminScreen.fxml"));
        // User
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/User/UserScreen.fxml"));
        // Login
        root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/login.fxml"));
        // Reset
        //root = FXMLLoader.load(getClass().getResource("/Client/FXMLPages/PasswordReset.fxml"));


        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
