package Controllers.FrontEnd.Admin;

import Controllers.Backend.AccountType;
import Controllers.Backend.NetworkObjects.User;
import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilLoginSecurity;
import Controllers.FrontEnd.Login.LoginController;
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

        try {
            UtilLoginSecurity utilLoginSecurity = new UtilLoginSecurity();
            String salt = utilLoginSecurity.generateSalt();

            AccountType type = null;

            try {
                type = AccountType.valueOf(CreateUserAccountType.getText());
            } catch (IllegalArgumentException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("ACCOUNT TYPES ARE:");
                AccountType accountTypes[] = AccountType.values();
                for (AccountType accountType: accountTypes) {
                    sb.append(" " + accountType.toString());
                }
                CreateUserErrorText.setText(sb.toString());
                return;
            }

            //Create a new user with a new password with the salt passed through it as well
            User newUser = new User(CreateUserUsername.getText(),
                    utilLoginSecurity.hashPassword(CreateUserPassword.getText(), salt),
                    type,
                    CreateUserOrgUnit.getText(),
                    salt);

            String success = MockSocket.getInstance().AddUser(LoginController.GetToken(), newUser);
            CreateUserErrorText.setText(success);
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

    @Override
    public void update(Subject s) {
        UpdateUserInfoTable();
    }
}
