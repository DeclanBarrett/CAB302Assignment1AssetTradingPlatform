package Controllers.FrontEnd.Admin;

import Controllers.Backend.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.Backend.Socket.MockSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
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
public class AdminEditUserTabController implements Initializable, Observer {

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

    /**
     * Edits the users password by attempting to contact the server
     * @param EditUserPassword
     */
    public void EditUserPassword(ActionEvent EditUserPassword) {

        System.out.println(EditUserPassword.getSource());
        //MockSocket.getInstance().UpdateUserPassword();
    }

    /**
     * Edits the users account type by attempting to contact the server
     * @param EditAccountType
     */
    public void EditAccountType(ActionEvent EditAccountType) {
        System.out.println(EditAccountType.getSource());
        //MockSocket.getInstance().UpdateUserAccountType();
    }

    /**
     * Edits the user organisation by attempting to contact the server
     * @param EditUserOrg
     */
    public void EditUserOrg(ActionEvent EditUserOrg) {
        System.out.println(EditUserOrg.getSource());
        //MockSocket.getInstance().UpdateUserOrganisation();
    }

    private void UpdateUserInfoTable() {
        List<UserInfo> users = MockSocket.getInstance().GetAllUsers(LoginController.GetToken());
        EditUserTable.getItems().setAll(users);
    }

    @Override
    public void update(Subject s) {
        UpdateUserInfoTable();
    }
}
