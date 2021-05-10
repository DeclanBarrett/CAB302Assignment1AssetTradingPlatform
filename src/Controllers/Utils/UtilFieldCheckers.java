package Controllers.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Utility for checking various elements of program.
 */
public class UtilFieldCheckers {
    /**
     * Checks if two strings match
     * @param field1
     * @param field2
     * @param errorText
     * @param fieldType
     * @return - true if strings match and false if strings do not match.
     */
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
