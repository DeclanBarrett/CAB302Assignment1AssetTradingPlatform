package Controllers.FrontEnd.Admin;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.User;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.FrontEnd.ClientSecurity;
import Controllers.FrontEnd.LoginController;
import Controllers.Backend.Socket.MockSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles events in admin Create User tab.
 */
public class AdminCreateUserHandler implements Initializable {

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
    }

    /**
     * Creates new user
     * @param CreateUser User to be created
     */
    public void CreateUser(ActionEvent CreateUser) {

        try {
            ClientSecurity clientSecurity = new ClientSecurity();
            String salt = clientSecurity.generateSalt();
            User newUser = new User(CreateUserUsername.getText(), clientSecurity.hashPassword(CreateUserPassword.getText(),
                    salt), AccountType.valueOf(CreateUserAccountType.getText()), CreateUserOrgUnit.getText(), salt);
            MockSocket.getInstance().AddUser(LoginController.GetToken(), newUser);
            UpdateUserInfoTable();
        } catch (Exception e) {
            CreateUserErrorText.setText(e.getMessage());
        }

        System.out.println(CreateUser.getSource());


    }

    private void UpdateUserInfoTable() {
        List<UserInfo> users = MockSocket.getInstance().GetAllUsers(LoginController.GetToken());
        CreateUserTable.getItems().setAll(users);
    }
}
