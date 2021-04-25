package Controllers.Backend;

import java.util.HashMap;

/**
 * Contains information
 */
public class OrganisationalUnit {

    String name;
    double credits;
    HashMap<String, Integer> organisationAssets;

    /**
     *
     * @param name Name of organisational unit.
     * @param credits Amount of credits the organisational unit owns.
     * @param organisationAssets Asset type and Asset quantity hashmap pair.
     */
    public OrganisationalUnit(String name, double credits, HashMap<String, Integer> organisationAssets) {
        this.name = name;
        this.credits = credits;
        this.organisationAssets = organisationAssets;
    }

    /**
     *
     * @return the name of the unit.
     */
    public String GetUnitName() {
        return name;
    }

    /**
     *
     * @return Amount of credits owned by the unit.
     */
    public double GetCredits() {
        return credits;
    }

    /**
     *
     * @param assetName requests quantity based on parameter value.
     * @return quantity of asset requested.
     */
    public int GetQuantity(String assetName) {
        return organisationAssets.get(assetName);
    }

    /**
     *
     * @return Organisational assets.
     */
    public HashMap<String, Integer> GetAllAssets() {
        return organisationAssets;
    }
}
