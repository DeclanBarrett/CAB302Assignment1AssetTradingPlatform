package Shared;

import java.util.HashMap;

public class OrganisationalUnit {

    String name;
    double credits;
    HashMap<String, Integer> organisationAssets;

    public OrganisationalUnit(String name, double credits, HashMap<String, Integer> organisationAssets) {
        this.name = name;
        this.credits = credits;
        this.organisationAssets = organisationAssets;
    }

    public String GetUnitName() {
        return name;
    }

    public double GetCredits() {
        return credits;
    }

    public int GetQuantity(String assetName) {
        return organisationAssets.get(assetName);
    }

    public HashMap<String, Integer> GetAllAssets() {
        return organisationAssets;
    }
}
