package Client.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Server extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
//        primaryStage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });

//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        Parent root;

        // Admin
        //root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
        // User
        //root = FXMLLoader.load(getClass().getResource("UserScreen.fxml"));
        // Login
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        // Reset
        //root = FXMLLoader.load(getClass().getResource("PasswordReset.fxml"));


        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
