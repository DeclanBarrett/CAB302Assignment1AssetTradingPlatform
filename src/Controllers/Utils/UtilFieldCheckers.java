package Controllers.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UtilFieldCheckers {
    public boolean CheckTwoStrings(TextField field1, TextField field2, Label errorText, String fieldType) {
        if (!field1.getText().equals(field2.getText())) {
            errorText.setText(fieldType + " DO NOT MATCH");
            return false;

        } else {
            errorText.setText(fieldType + " MATCH");
            return true;
        }
    }

}
