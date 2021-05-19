package App_Start;

import Controllers.Utils.UtilSceneChanger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creates st age for the UI, contains basic stage information
 */
public class ClientStart extends Application
{
    private static Stage primaryStage;

    public static Stage getStage() {
        return primaryStage;
    }

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
        this.primaryStage = primaryStage;
        UtilSceneChanger.getInstance().ChangeToScene(UtilSceneChanger.SceneType.LOGIN);
        this.primaryStage.setTitle("Unnamed Client Organisation Trading System");

    }
}
