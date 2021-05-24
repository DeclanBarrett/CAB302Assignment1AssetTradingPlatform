package Controllers.FrontEnd.Admin;

import Controllers.BackEnd.AccountType;
import Controllers.BackEnd.NetworkObjects.UserInfo;
import Controllers.FrontEnd.Login.LoginController;
import Controllers.BackEnd.Socket.ClientSocket;
import Controllers.FrontEnd.Observer;
import Controllers.FrontEnd.Subject;
import Controllers.Utils.UtilFieldCheckers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        String clientResponse = "";

        try {
            clientResponse = UtilFieldCheckers.checkTwoStrings(EditUserPassword.toString(), EditUserPassword2.toString(), "PASSWORDS");
            AdminProcessing processing = new AdminProcessing();
            clientResponse = processing.editUserPassword(EditUserUsername.getText(), EditUserPassword.toString());
            EditUserErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            clientResponse = e.getMessage();
            EditUserErrorText.setTextFill(Color.RED);
        }

        EditUserErrorText.setText(clientResponse);


    }

    /**
     * Edits the users account type by attempting to contact the server
     * @param EditAccountType
     */
    public void EditAccountType(ActionEvent EditAccountType) {

        String clientResponse = "";

        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(EditUserAccountType.getText(), EditUserUsername.getText())));

            AccountType accountType = UtilFieldCheckers.checkAccountType(EditUserAccountType.getText());

            clientResponse = ClientSocket.getInstance().UpdateUserAccountType(LoginController.GetToken(),
                    EditUserUsername.getText(),
                    accountType);
            EditUserErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            EditUserErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        EditUserErrorText.setText(clientResponse);

    }

    /**
     * Edits the user organisation by attempting to contact the server
     * @param EditUserOrg
     */
    public void EditUserOrg(ActionEvent EditUserOrg) {

        String clientResponse = "";

        try {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList(EditUserOrgUnit.getText(), EditUserUsername.getText())));

            clientResponse = ClientSocket.getInstance().UpdateUserOrganisation(LoginController.GetToken(),
                    EditUserUsername.getText(),
                    EditUserOrgUnit.getText());
            EditUserErrorText.setTextFill(Color.GREEN);
        } catch (Exception e) {
            EditUserErrorText.setTextFill(Color.RED);
            clientResponse = e.getMessage();
        }

        EditUserErrorText.setText(clientResponse);
    }

    /**
     * Sets all the user information in the tables
     */
    private void UpdateUserInfoTable() {
        List<UserInfo> users = new ArrayList<>();

        try {
            users = ClientSocket.getInstance().GetAllUsers(LoginController.GetToken());
        } catch (Exception e) {
            EditUserErrorText.setTextFill(Color.RED);
            EditUserErrorText.setText(e.getMessage());
        }

        EditUserTable.getItems().setAll(users);
    }

    @Override
    public void update(Subject s) {
        UpdateUserInfoTable();
    }
}
