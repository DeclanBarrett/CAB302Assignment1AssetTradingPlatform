package Controllers.BackEnd.NetworkObjects;

import java.io.Serializable;
import java.util.Date;

/**
 * Serializable trade objects.
 */
public class Trade implements Serializable, Comparable<Trade> {

    Integer TradeID;
    String AssetName;
    Integer AssetQuantity;
    Double AssetPrice;
    String BuyerOrgName;
    String SellerOrgName;
    Date TradeDateMilSecs;

    /**
     * Trade constructor
     * @param tradeID - Identification number of the trade.
     * @param assetName - name of the asset being traded.
     * @param assetQuantity - quantity of the asset being traded
     * @param assetPrice - price of the asset being traded
     * @param buyerOrgName - name of the buying organisational unit
     * @param sellerOrgName - name of the selling organisational unit.
     * @param tradeDateMilSecs - the trade date in milliseconds
     */
    public Trade(Integer tradeID, String assetName, Integer assetQuantity, Double assetPrice, String buyerOrgName, String sellerOrgName, Date tradeDateMilSecs) {
        TradeID = tradeID;
        AssetName = assetName;
        AssetQuantity = assetQuantity;
        AssetPrice = assetPrice;
        BuyerOrgName = buyerOrgName;
        SellerOrgName = sellerOrgName;
        TradeDateMilSecs = tradeDateMilSecs;
    }

    @Override
    public int compareTo(Trade o) {
        return 0;
    }


    public Integer getTradeID() {
        return TradeID;
    }

    public String getAssetName() {
        return AssetName;
    }

    public Integer getAssetQuantity() {
        return AssetQuantity;
    }

    public Double getAssetPrice() {
        return AssetPrice;
    }

    public String getBuyerOrgName() {
        return BuyerOrgName;
    }

    public String getSellerOrgName() {
        return SellerOrgName;
    }

    public Date getTradeDateMilSecs() {
        return TradeDateMilSecs;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Trade)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Trade u = (Trade) o;

        // Compare the data members and return accordingly
        return this.getTradeID().equals(u.getTradeID());
    }
}
