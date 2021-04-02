package Shared;

import java.util.Date;

public class Order {
    public Order(OrderType orderType, String assetType, int assetQuantity, double requestPrice, OrganisationalUnit organisationalUnit, Date date) {
    }

    public OrderType OrderType() {
        return null;
    }

    public String GetAssetType() {
        return null;
    }

    public int GetAssetQuantity() {
        return 0;
    }

    public float GetRequestPrice() {
        return 0;
    }

    public String GetOrganisationalUnit() {
        return null;
    }

    public Date GetDate() {
        return null;
    }
}
