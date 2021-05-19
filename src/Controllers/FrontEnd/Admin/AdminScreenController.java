package Controllers.FrontEnd.Admin;

import Controllers.FrontEnd.Subject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Admin screen handler initialisation
 */
public class AdminScreenController extends Subject implements Initializable {

    @FXML AdminEditUserTabController adminEditUserTabController;
    @FXML AdminAssetTypesTabController adminAssetTypesTabController;
    @FXML
    AdminCreateUserTabController adminCreateUserTabController;
    @FXML
    AdminCreateOrganisationTabController adminCreateOrganisationTabController;
    @FXML
    AdminEditOrganisationTabController adminEditOrganisationTabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        attachObserver(adminEditUserTabController);
        attachObserver(adminAssetTypesTabController);
        attachObserver(adminCreateUserTabController);
        attachObserver(adminCreateOrganisationTabController);
        attachObserver(adminEditOrganisationTabController);
        updatePage();
    }

    /**
     * Is a timer which updates the client side to check the server side and then trigger the updating of page information
     */
    private void updatePage() {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(5),
                        event -> {
                            System.out.println("Updating");
                            notifyObservers();
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }
}
