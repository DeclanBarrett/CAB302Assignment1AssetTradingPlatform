package Controllers.FrontEnd.User;

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
 * Handles main user screen tab
 */
public class UserScreenController extends Subject implements Initializable {

    @FXML UserBuyTabController userBuyTabController;
    @FXML UserOrganisationTabController userOrganisationTabController;
    @FXML UserRemoveTabController userRemoveTabController;
    @FXML UserSellTabController userSellTabController;
    @FXML UserUserTabController userUserTabController;

    /**
     * Initialisers User Screen Handler.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attachObserver(userBuyTabController);
        attachObserver(userOrganisationTabController);
        attachObserver(userRemoveTabController);
        attachObserver(userSellTabController);
        attachObserver(userUserTabController);
        updatePage();
    }

    /**
     * Is a timer which updates the client side to check the server side and then trigger the updating of page information
     */
    private void updatePage() {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(10),
                        event -> {
                            notifyObservers();
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }
}
