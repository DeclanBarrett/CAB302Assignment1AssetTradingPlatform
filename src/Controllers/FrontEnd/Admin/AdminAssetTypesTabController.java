package Controllers.FrontEnd.Admin;

import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles events in admin asset types tab.
 */
public class AdminAssetTypesTabController implements Initializable, Observer {

    @FXML
    TextField CreateAssetName;
    @FXML
    Label CreateAssetErrorText;
    @FXML
    Button CreateAssetSubmit;
    @FXML
    ListView<String> CreateAssetTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateAssetTypes();
    }

    /**
     * Creates assets in database
     * @param CreateAsset Asset to be created
     */
    public void CreateAsset(ActionEvent CreateAsset) {

        String clientResponse = "";

        //Attempt to contact the server with a new asset to be created
        try {

            if (CreateAssetName.getText().equals(null) || CreateAssetName.getText().equals("")) {
                throw new NullPointerException("INFORMATION MISSING");
            }

            clientResponse = ClientSocket.getInstance().AddAsset(LoginController.GetToken(), CreateAssetName.getText());
            CreateAssetErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            CreateAssetErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        CreateAssetErrorText.setText(clientResponse);

        UpdateAssetTypes();
    }

    /**
     * Updates asset types
     */
    private void UpdateAssetTypes() {

        List<String> types = new ArrayList<>();

        String clientResponse = CreateAssetErrorText.getText();

        //Attempt to contact the server to retrieve all the assets
        try {
            types = ClientSocket.getInstance().GetAssetTypes(LoginController.GetToken());
            CreateAssetErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            CreateAssetErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        CreateAssetTable.getItems().setAll(types);

        CreateAssetErrorText.setText(clientResponse);
    }


    @Override
    public void update(Subject s) {
        UpdateAssetTypes();
    }
}
