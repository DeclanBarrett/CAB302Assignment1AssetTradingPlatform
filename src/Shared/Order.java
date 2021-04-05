package Shared;

import java.util.Date;

/**
 * A data structure that stores all information for an Order
 */
public class Order {

    /**
     * Generates an Order using the OrganisationalUnit object
     * @param orderType Buy or Sell order
     * @param assetType Type of asset the order is for (must be an asset that can be bought)
     * @param assetQuantity The amount of an asset to be either bought or sold
     * @param requestPrice The price per asset. If it is a BUY order, this is the maximum price the item will be bought
     *                     for. If it is a SELL order, this is the EXACT price that the item will be sold for.
     * @param organisationalUnit The OrganisationalUnit object for the Organisation the Order is made on behalf of
     * @param date The Date in milliseconds when the Order was placed
     */
    public Order(OrderType orderType, String assetType, int assetQuantity, double requestPrice, OrganisationalUnit organisationalUnit, Date date) {
    }

    /**
     * Generates an Order using the Organisational Units name
     * @param orderType Buy or Sell order
     * @param assetType Type of asset the order is for (must be an asset that can be bought)
     * @param assetQuantity The amount of an asset to be either bought or sold
     * @param requestPrice The price per asset. If it is a BUY order, this is the maximum price the item will be bought
     *                     for. If it is a SELL order, this is the EXACT price that the item will be sold for.
     * @param organisationalName The OrganisationalUnit name for the Organisation the Order is made on behalf of
     * @param date The Date in milliseconds when the Order was placed
     */
    public Order(OrderType orderType, String assetType, int assetQuantity, double requestPrice, String organisationalName, Date date) {
    }

    /**
     *
     * @return The
     */
    public OrderType GetOrderType() {
        return null;
    }

    /**
     *
     * @return
     */
    public String GetAssetType() {
        return null;
    }

    /**
     *
     * @return
     */
    public int GetAssetQuantity() {
        return 0;
    }

    /**
     *
     * @return
     */
    public float GetRequestPrice() {
        return 0;
    }

    /**
     *
     * @return
     */
    public String GetOrganisationalUnit() {
        return null;
    }

    /**
     *
     * @return
     */
    public Date GetDate() {
        return null;
    }
}
