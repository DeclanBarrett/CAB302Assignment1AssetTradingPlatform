package Controllers.FrontEnd.Admin;

import Controllers.BackEnd.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles events in admin Create User tab.
 */
public class AdminCreateUserTabController implements Initializable, Observer {

    @FXML
    TextField CreateUserUsername;
    @FXML
    PasswordField CreateUserPassword;
    @FXML
    PasswordField CreateUserPassword2;
    @FXML
    TextField CreateUserAccountType;
    @FXML
    TextField CreateUserOrgUnit;
    @FXML
    Label CreateUserErrorText;
    @FXML
    Button CreateUserSubmitButton;
    @FXML
    TableView CreateUserTable;

    @FXML
    TableColumn<UserInfo, String> CreateUserNameColumn;
    @FXML
    TableColumn<UserInfo, String> CreateUserAccountTypeColumn;
    @FXML
    TableColumn<UserInfo, String> CreateUserOrgUnitColumn;


    private boolean passwordsMatch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        CreateUserAccountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        CreateUserOrgUnitColumn.setCellValueFactory(new PropertyValueFactory<>("organisationalUnit"));
        UpdateUserInfoTable();
        CreateUserErrorText.setText("");
    }

    /**
     * Creates new user
     * @param CreateUser User to be created
     */
    public void CreateUser(ActionEvent CreateUser) {

        String clientResponse = "";

        try {
            AdminProcessing adminProcessing = new AdminProcessing();
            clientResponse = adminProcessing.createUser(CreateUserAccountType.getText(),
                    CreateUserUsername.getText(),
                    CreateUserPassword.getText(),
                    CreateUserOrgUnit.getText());

            CreateUserErrorText.setTextFill(Color.GREEN);
            UpdateUserInfoTable();
        } catch (Exception e) {
            CreateUserErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        CreateUserErrorText.setText(clientResponse);

    }

    /**
     * Updates user info table.
     */
    private void UpdateUserInfoTable() {

        String clientResponse = CreateUserErrorText.getText();

        List<UserInfo> users = new ArrayList<>();

        try {
            users = ClientSocket.getInstance().GetAllUsers(LoginController.GetToken());
        } catch (Exception e) {
            CreateUserErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        CreateUserErrorText.setText(clientResponse);
        CreateUserTable.getItems().setAll(users);
    }

    @Override
    public void update(Subject s) {
        UpdateUserInfoTable();
    }
}
