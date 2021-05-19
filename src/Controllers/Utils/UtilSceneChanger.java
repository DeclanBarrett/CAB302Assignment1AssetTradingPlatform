package Controllers.Utils;

import App_Start.ClientStart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UtilSceneChanger {

    public static final String LOGIN_FXML_FILE_PATH = "/Views/Login/Login.fxml";
    public static final String RESET_FXML_FILE_PATH = "/Views/FXMLPages/PasswordReset.fxml";
    public static final String ADMIN_FXML_FILE_PATH = "/Views/Admin/AdminScreen.fxml";
    public static final String USER_FXML_FILE_PATH = "/Views/User/UserScreen.fxml";

    public UtilSceneChanger() {
    }

    private static class SceneChangerHolder {

        private final static UtilSceneChanger INSTANCE = new UtilSceneChanger();
    }

    /**
     * Get an instance of the scene changer in singleton format
     * @return instance
     */
    public static UtilSceneChanger getInstance() {

        return UtilSceneChanger.SceneChangerHolder.INSTANCE;
    }

    /**
     * Scenes that can be changed to
     */
    public enum SceneType {
        LOGIN,
        RESET,
        ADMIN,
        USER
    }

    /**
     * A utility to allow the changing of scene to a scene type
     * @param sceneType - type to change to
     */
    public void ChangeToScene(SceneType sceneType) {
        try {
            Parent root;

            switch(sceneType) {
                case LOGIN:
                    root = FXMLLoader.load(getClass().getResource(LOGIN_FXML_FILE_PATH));
                    break;
                case RESET:
                    root = FXMLLoader.load(getClass().getResource(RESET_FXML_FILE_PATH));
                    break;
                case ADMIN:
                    root = FXMLLoader.load(getClass().getResource(ADMIN_FXML_FILE_PATH));
                    break;
                case USER:
                    root = FXMLLoader.load(getClass().getResource(USER_FXML_FILE_PATH));
                    break;
                default:
                    root = FXMLLoader.load(getClass().getResource(LOGIN_FXML_FILE_PATH));
            }

            ClientStart.getStage().setScene(new Scene(root));
            ClientStart.getStage().show();
        } catch (Exception e) {
            System.out.println("Failed to load FXML");
        }
    }

}
