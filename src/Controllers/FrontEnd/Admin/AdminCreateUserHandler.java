package Controllers.FrontEnd.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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

    private boolean passwordsMatch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Creates new user
     * @param CreateUser User to be created
     */
    public void CreateUser(ActionEvent CreateUser) {

        /*
        User newUser = new User()
        MockSocket.
        System.out.println(CreateUser.getSource());

         */
    }

    private void UpdateUserInfoTable() {

    }
}