package Controllers.Backend.NetworkObjects;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Defines organisational unit objects
 */
public class OrganisationalUnit implements Comparable<OrganisationalUnit>, Serializable {

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

    @Override
    public int compareTo(OrganisationalUnit o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof OrganisationalUnit)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        OrganisationalUnit u = (OrganisationalUnit) o;

        // Compare the data members and return accordingly
        return (this.GetUnitName()).equals(u.GetUnitName());
    }
}
