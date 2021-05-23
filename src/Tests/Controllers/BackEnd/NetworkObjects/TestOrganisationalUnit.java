package Tests.Controllers.BackEnd.NetworkObjects;

import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrganisationalUnit {

    OrganisationalUnit organisationalUnit;

    @BeforeEach
    public void ConstructOrganisations(){

        HashMap<String, Integer> organisationAssets = new HashMap<>();
        organisationAssets.put("Paper", 50);
        organisationAssets.put("CPU hours", 600);
        organisationAssets.put("Pickles", 50);
        organisationAssets.put("Casino Chips", 50);
        organisationalUnit = new OrganisationalUnit("Sales", 3000.5, organisationAssets);
    }

    @Test
    public void TestGetUnitName() {
        String name = organisationalUnit.getUnitName();
        assertEquals("Sales", name);
    }

    @Test
    public void TestGetCredits() {
        double credits = organisationalUnit.getCredits();
        assertEquals(3000.5, credits);
    }

    @Test
    public void TestGetAllAssets() {
        HashMap<String, Integer> expectedAssets = new HashMap<>();
        expectedAssets.put("Paper", 50);
        expectedAssets.put("CPU hours", 600);
        expectedAssets.put("Pickles", 50);
        expectedAssets.put("Casino Chips", 50);
        HashMap<String, Integer> assets = organisationalUnit.GetAllAssets();
        assertEquals(expectedAssets, assets);
    }

    @Test
    public void TestGetQuantity() {
        int quantity = organisationalUnit.getQuantity("Casino Chips");
        assertEquals(50, quantity);
    }
}
