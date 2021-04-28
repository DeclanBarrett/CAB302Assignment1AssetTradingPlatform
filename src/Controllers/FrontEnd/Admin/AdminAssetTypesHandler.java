package Controllers.FrontEnd.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles events in admin asset types tab.
 */
public class AdminAssetTypesHandler implements Initializable {

    @FXML
    TextField CreateAssetName;
    @FXML
    Label CreateAssetErrorText;
    @FXML
    Button CreateAssetSubmit;
    @FXML
    TableView CreateAssetTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Creates assets in database
     * @param CreateAsset Asset to be created
     */
    public void CreateAsset(ActionEvent CreateAsset) {
        System.out.println(CreateAsset.getSource());
    }

    /**
     * Updates asset types
     */
    private void UpdateAssetTypes() {

    }
}
