package Controllers.Utils;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
    public boolean CheckTwoStrings(String field1, String field2, Label errorText, String fieldType) {
        if (!field1.equals(field2)) {
            errorText.setTextFill(Color.RED);
            errorText.setText(fieldType + " DO NOT MATCH");
            return false;

        } else {
            errorText.setTextFill(Color.GREEN);
            errorText.setText(fieldType + " MATCH");
            return true;
        }
    }

}
