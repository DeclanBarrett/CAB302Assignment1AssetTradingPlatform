package Client.EventHandlers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void CreateUser(ActionEvent CreateUser) {
        System.out.println(CreateUser.getSource());
    }

    private void UpdateUserInfoTable() {

    }
}
