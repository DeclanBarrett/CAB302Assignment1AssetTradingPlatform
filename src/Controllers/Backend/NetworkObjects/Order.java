package Controllers.Backend.NetworkObjects;


import Controllers.Backend.OrderType;

import java.io.Serializable;
import java.util.Date;

/**
 * A data structure that stores all information for an Order
 */
public class Order implements Comparable<Order>, Serializable  {

    int orderID;
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
    public Order(int orderID, OrderType orderType, String assetType, int assetQuantity, double requestPrice, OrganisationalUnit organisationalUnit, Date date) {
        this.orderID = orderID;
        this.orderType = orderType;
        this.assetType = assetType;
        this.assetQuantity = assetQuantity;
        this.requestPrice = requestPrice;
        this.organisationalUnitName = organisationalUnit.getUnitName();
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
    public Order(int orderID, OrderType orderType, String assetType, int assetQuantity, double requestPrice, String organisationalName, Date date) {
        this.orderID = orderID;
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
    public int getOrderID() {
        return orderID;
    }

    /**
     *
     * @return The type of order requested. Either Buy or Sell.
     */
    public OrderType getOrderType() {
        return orderType;
    }

    /**
     *
     * @return Type of asset requested.
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     *
     * @return Quantity of asset requested
     */
    public int getAssetQuantity() {
        return assetQuantity;
    }

    /**
     *
     * @return Price attached to order.
     */
    public double getRequestPrice() {
        return requestPrice;
    }

    /**
     *
     * @return Organisational unit that has placed the Buy/Sell order.
     */
    public String getOrganisationalUnit() {
        return organisationalUnitName;
    }

    /**
     *
     * @return Date on which the Buy/Sell order was placed.
     */
    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Order o) {
        return ((Integer) orderID).compareTo(o.orderID);
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Order)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Order u = (Order) o;

        // Compare the data members and return accordingly
        return ((Integer) getOrderID()).equals(u.getOrderID());
    }
}
