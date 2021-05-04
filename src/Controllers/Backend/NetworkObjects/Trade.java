package Controllers.Backend.NetworkObjects;

import java.io.Serializable;
import java.util.Date;

public class Trade implements Serializable, Comparable<Trade> {

    Integer TradeID;
    String AssetName;
    Integer AssetQuantity;
    Float AssetPrice;
    String BuyerOrgName;
    String SellerOrgName;
    Date TradeDateMilSecs;

    public Trade(Integer tradeID, String assetName, Integer assetQuantity, Float assetPrice, String buyerOrgName, String sellerOrgName, Date tradeDateMilSecs) {
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

    public Float getAssetPrice() {
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
}
