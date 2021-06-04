package Controllers.Utils;

import Controllers.BackEnd.AccountType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Util Field Checkers class
 */
public class TestUtilFieldCheckers {

    @Test
    public void TestCheckTwoStrings() {
        String response = UtilFieldCheckers.checkTwoStrings("James", "James", "NAMES");
        assertEquals("NAMES MATCH", response);
    }

    @Test
    public void TestCheckTwoBadStrings() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            UtilFieldCheckers.checkTwoStrings("James", "John", "NAMES");
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("NAMES DO NOT MATCH"));
    }

    @Test
    public void TestCheckNoMissingValues() {
        UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList("James and the Peach")));
    }

    @Test
    public void TestCheckMissingValuesNull() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList("James", null)));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("INFORMATION MISSING"));
    }

    @Test
    public void TestCheckMissingValuesEmpty() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            UtilFieldCheckers.checkMissingValues(new ArrayList<>(Arrays.asList("James", "")));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("INFORMATION MISSING"));
    }

    @Test
    public void TestAccountType() {
        AccountType type = UtilFieldCheckers.checkAccountType("User");

        assertEquals(type, AccountType.User);
    }

    @Test
    public void TestBadAccountType() {
        assertThrows(IllegalArgumentException.class, () -> {
            UtilFieldCheckers.checkAccountType("USER");
        });
    }
}
