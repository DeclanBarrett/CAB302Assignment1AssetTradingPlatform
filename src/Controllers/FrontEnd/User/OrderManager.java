package Controllers.FrontEnd.User;

import Controllers.BackEnd.NetworkObjects.Trade;
import Controllers.FrontEnd.Login.LoginController;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates an order from UI data and attempts to send it to the SetupServer to execute
 */

// TODO Figure out why this is here
public class OrderManager {

    private List<Trade> trades;

    public OrderManager(List<Trade> trades) {
        this.trades = trades;
    }
    /**
     * Creates a notification
     * @param orderString - the string to display in the notification
     */
    private void generateTradeNotification(String orderString) {
        Notifications notificationsBuilder = Notifications.create()
                .title("CAB302 UNKNOWN COMPANY: ORGANISATION TRADE FULFILLED")
                .text(orderString)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notificationsBuilder.showConfirm();
    }

    /**
     * Checks through the new trades and if their are new ones then it sends a notification
     * @param newTrades - list of new trades
     */
    public void checkTrades(List<Trade> newTrades) {

        List<Trade> nonPersistingTrades = new ArrayList<>(newTrades);

        if (!trades.equals(nonPersistingTrades)) {
            nonPersistingTrades.removeAll(trades);

            for (Trade trade: nonPersistingTrades) {
                if (trade.getBuyerOrgName().equals(LoginController.GetUser().getOrganisationalUnit())) {
                    String tradeMessage = "Bought " + trade.getAssetQuantity() + " of the asset " + trade.getAssetName() + " at price of " + trade.getAssetPrice();
                    generateTradeNotification(tradeMessage);
                } else if (trade.getSellerOrgName().equals(LoginController.GetUser().getOrganisationalUnit())) {
                    String tradeMessage = "Sold " + trade.getAssetQuantity() + " of the asset " + trade.getAssetName() + " at price of " + trade.getAssetPrice();;
                    generateTradeNotification(tradeMessage);
                }
            }
        }
        trades = new ArrayList<>(newTrades);
    }
}