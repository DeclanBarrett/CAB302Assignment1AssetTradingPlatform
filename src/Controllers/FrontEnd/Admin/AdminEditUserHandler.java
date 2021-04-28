package Controllers.FrontEnd.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
