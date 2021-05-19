package Controllers.FrontEnd.Admin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Admin screen handler initialisation
 */
public class AdminScreenHandler implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePage();
    }

    private void updatePage() {
        long endTime = 2000;
        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            final long diff = endTime - System.currentTimeMillis();
                            System.out.println("Updating");
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }
}
