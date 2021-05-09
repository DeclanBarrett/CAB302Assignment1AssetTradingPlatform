package Controllers.Backend._Dump;


import java.util.Date;

/**
 * A data structure that stores all information for an Order
 */
public class Order {

    OrderType orderType;
    String assetType;
    int assetQuantity;
    double requestPrice;
    String organisationalUnitName;
    Date date;

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
        this.orderType = orderType;
        this.assetType = assetType;
        this.assetQuantity = assetQuantity;
        this.requestPrice = requestPrice;
        this.organisationalUnitName = organisationalUnit.GetUnitName();
        this.date = date;
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
        this.orderType = orderType;
        this.assetType = assetType;
        this.assetQuantity = assetQuantity;
        this.requestPrice = requestPrice;
        this.organisationalUnitName = organisationalName;
        this.date = date;
    }

    /**
     *
     * @return The type of order requested. Either Buy or Sell.
     */
    public OrderType GetOrderType() {
        return orderType;
    }

    /**
     *
     * @return Type of asset requested.
     */
    public String GetAssetType() {
        return assetType;
    }

    /**
     *
     * @return Quantity of asset requested
     */
    public int GetAssetQuantity() {
        return assetQuantity;
    }

    /**
     *
     * @return Price attached to order.
     */
    public double GetRequestPrice() {
        return requestPrice;
    }

    /**
     *
     * @return Organisational unit that has placed the Buy/Sell order.
     */
    public String GetOrganisationalUnit() {
        return organisationalUnitName;
    }

    /**
     *
     * @return Date on which the Buy/Sell order was placed.
     */
    public Date GetDate() {
        return date;
    }
}
