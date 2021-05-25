package Controllers.Utils;

import Controllers.BackEnd.AccountType;

import java.util.ArrayList;

/**
 * Utility for checking various elements of program.
 */
public class UtilFieldCheckers {
    /**
     * Checks if two strings match
     * @param field1
     * @param field2
     * @param fieldType
     * @return - true if strings match and false if strings do not match.
     */
    public static String checkTwoStrings(String field1, String field2, String fieldType) {
        if (!field1.equals(field2)) {
            throw new IllegalArgumentException(fieldType + " DO NOT MATCH");
        } else {
            return fieldType + " MATCH";
        }
    }

    /**
     * Checks the values to see if they are null or an empty string and throws an error if they are
     * @param values - list of values to be checked
     * @throws NullPointerException - throws if the objects are null
     */
    public static void checkMissingValues(ArrayList<String> values) throws NullPointerException {
        for (String string: values) {
            if (string == null ||
                    string.equals("")) {
                throw new NullPointerException("INFORMATION MISSING");
            }
        }
    }

    /**
     * Checks to see if the string given to it is an account type
     * @param type - the string to check
     * @return - the account type
     * @throws IllegalArgumentException - throws with an appropriate message if the string isnt an account type
     */
    public static AccountType checkAccountType(String type) throws IllegalArgumentException {
        try {
            return AccountType.valueOf(type);
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("ACCOUNT TYPES ARE:");
            AccountType accountTypes[] = AccountType.values();
            for (AccountType accountEnumType: accountTypes) {
                sb.append(" " + accountEnumType.toString());
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }
}


