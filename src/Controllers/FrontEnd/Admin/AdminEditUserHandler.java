package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.UserInfo;
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
 * Handles events in admin edit user tab.
 */
public class AdminEditUserHandler implements Initializable {

    @FXML
    TextField EditUserUsername;
    @FXML
    PasswordField EditUserPassword;
    @FXML
    PasswordField EditUserPassword2;
    @FXML
    Button EditUserPasswordSubmit;
    @FXML
    TextField EditUserAccountType;
    @FXML
    Button EditUserAccountTypeSubmit;
    @FXML
    TextField EditUserOrgUnit;
    @FXML
    Button EditUserOrgUnitSubmit;
    @FXML
    Label EditUserErrorText;
    @FXML
    TableView EditUserTable;

    @FXML
    TableColumn<UserInfo, String> EditUserNameColumn;
    @FXML
    TableColumn<UserInfo, String> EditUserAccountTypeColumn;
    @FXML
    TableColumn<UserInfo, String> EditUserOrgUnitColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EditUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        EditUserAccountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        EditUserOrgUnitColumn.setCellValueFactory(new PropertyValueFactory<>("organisationalUnit"));
        UpdateUserInfoTable();
    }

    public void EditUserPassword(ActionEvent EditUserPassword) {
        System.out.println(EditUserPassword.getSource());
    }
    public void EditAccountType(ActionEvent EditAccountType) {
        System.out.println(EditAccountType.getSource());
    }
    public void EditUserOrg(ActionEvent EditUserOrg) {
        System.out.println(EditUserOrg.getSource());
    }

    private void UpdateUserInfoTable() {
        List<UserInfo> users = MockSocket.getInstance().GetAllUsers(LoginController.GetToken());
        EditUserTable.getItems().setAll(users);
    }
}
