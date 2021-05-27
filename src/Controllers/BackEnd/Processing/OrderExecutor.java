package Controllers.BackEnd.Processing;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.NetworkObjects.Trade;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.ServerException;
import Models.InformationGrabber;

import java.sql.SQLException;
import java.util.*;

/**
 * Updates order lists, generates new trades and executes orders
 */
public class OrderExecutor
{
    InformationGrabber database;

    public OrderExecutor(InformationGrabber database) {
        this.database = database;
    }

    /**
     * Generates an order from a previous order and a new quantity, then add it to the database
     * @param order - the order
     * @param quantity - quantity to set in it
     */
    private void generateOrder(Order order, Integer quantity)
    {
        Order newOrder = new Order(-1,
                order.getOrderType(),
                order.getAssetType(),
                quantity,
                order.getRequestPrice(),
                order.getOrganisationalUnit(),
                new Date()
        );

        database.insertOrder(newOrder);
    }

    /**
     * Creates a trade by receiving the buy and sell information and linking the two
     *
     * @param buyOrder  contains information relating to the buy order
     * @param sellOrder contains information relating to the sell order
     */
    private void generateTrade(Order buyOrder, Order sellOrder, Integer quantity)
    {
        Trade newTrade = new Trade(-1,
                buyOrder.getAssetType(),
                quantity,
                sellOrder.getRequestPrice(),
                buyOrder.getOrganisationalUnit(),
                sellOrder.getOrganisationalUnit(),
                new Date());
        database.insertTrade(newTrade);
        System.out.println("TRADE OCCURRED:");
        System.out.println("BUYER: " + buyOrder.getOrganisationalUnit());
        System.out.println("SELLER: " + sellOrder.getOrganisationalUnit());
        System.out.println("ASSET: " + buyOrder.getAssetType());
        System.out.println("QUANTITY: " + quantity);
        System.out.println("PRICE: " + sellOrder.getRequestPrice());
        System.out.println("----------------------------------------");
    }

    /**
     * Collates the order information and executes it with the server
     * @param order Contains order information
     */
    public void executeOrder(Order order) throws ServerException
    {
        if (order.getOrderType() == OrderType.BUY) {
            executeBuyOrder(order);
        } else if (order.getOrderType() == OrderType.SELL) {
            executeSellOrder(order);
        }
    }

    /**
     * Checks if there is enough credits to make the order by comparing the total cost of all orders the organisation
     * has currently and the current order to the total credits the organisation has
     * @param buyOrganisationUnit
     * @param order
     * @return
     */
    private boolean checkEnoughCredits(OrganisationalUnit buyOrganisationUnit, Order order) {

        //Retrieve the current amount of credits that the buy organisation has
        Double credits = buyOrganisationUnit.getCredits();
        List<Order> buyOrganisationsBuyOrders = database.getOrganisationBuyOrders(buyOrganisationUnit.getUnitName());

        //Calculate the current total cost
        Double totalAttemptedCredits = order.getRequestPrice() * order.getAssetQuantity();

        //Calculate the total possible cost of the
        for (Order olderOrder: buyOrganisationsBuyOrders) {
            totalAttemptedCredits += olderOrder.getAssetQuantity() * olderOrder.getRequestPrice();
        }

        return credits >= totalAttemptedCredits;
    }

    /**
     * Check if there are enough assets in a target organisation to sustain an order and its previous orders
     * @param sellOrganisationUnit - the organisation to target
     * @param order - the order attempting to be made
     * @return - a boolean to say whether it does or not
     */
    private boolean checkEnoughAssets(OrganisationalUnit sellOrganisationUnit, Order order) {

        //Retrieve the current amount of credits that the buy organisation has
        Integer specificAssetQuantity = database.getOrganisationIndividualAsset(sellOrganisationUnit.getUnitName(), order.getAssetType());
        List<Order> sellOrganisationsSellOrders = database.getOrganisationSellOrders(sellOrganisationUnit.getUnitName());
        //Calculate the current total cost
        Integer totalAttemptedQuantity = order.getAssetQuantity();

        //Calculate the total possible cost of the
        for (Order olderOrder: sellOrganisationsSellOrders) {
            totalAttemptedQuantity += olderOrder.getAssetQuantity();
        }

        return specificAssetQuantity >= totalAttemptedQuantity;
    }

    /**
     * Executes a buy order
     * @param buyOrder - the order to be executed
     * @throws ServerException - if something goes wrong with executing the order
     */
    private void executeBuyOrder(Order buyOrder) throws ServerException {
        OrganisationalUnit buyOrganisationUnit = database.getOrganisation(buyOrder.getOrganisationalUnit());

        if (checkEnoughCredits(buyOrganisationUnit, buyOrder)) {

            //Get the sell orders
            List<Order> orders = database.getSellOrders();
            List<Order> sortedSellOrder = new ArrayList<>();

            //filter them by the asset and the price, and sort it if there are any
            for (Order sellOrder: orders) {

                if (sellOrder.getAssetType().equals(buyOrder.getAssetType()) && sellOrder.getRequestPrice() <= buyOrder.getRequestPrice()) {
                    sortedSellOrder.add(sellOrder);
                }
            }

            Integer buyAssetQuantity = buyOrder.getAssetQuantity();

            //If there are no sell orders then dont continue
            if (sortedSellOrder.size() >= 0) {
                Collections.sort(sortedSellOrder);


                //Go through the orders, subtracting the quantity till their is none left
                for (int sellOrderCounter = 0; sellOrderCounter < sortedSellOrder.size(); sellOrderCounter++) {

                    Order currentSellOrder = sortedSellOrder.get(sellOrderCounter);

                    Integer sellOrderQuantity = currentSellOrder.getAssetQuantity();

                    //Sell order larger: take the buy order quantity from sell quantity: buy quantity is 0
                    //Sell order same: quantity left is 0 in orders
                    //Sell order smaller: take the sell order quantity from buy quantity: sell order quantity is 0
                    if (sellOrderQuantity >= buyAssetQuantity) {
                        sellOrderQuantity -= buyAssetQuantity;
                        updateBuyOrderServerInformation(sellOrderQuantity, buyAssetQuantity, currentSellOrder, buyOrder);
                        return;
                    } else if (sellOrderQuantity < buyAssetQuantity) {
                        buyAssetQuantity -= sellOrderQuantity;
                        updateBuyOrderServerInformation(0, sellOrderQuantity, currentSellOrder, buyOrder);
                    }
                }
            }

            //Add buy order with buyAssetQuantity to database
            generateOrder(buyOrder, buyAssetQuantity);


        } else {
            throw new ServerException("ORGANISATION TOTAL ORDER COST IS TOO HIGH");
        }
    }

    /**
     * Retrieves the required information and then modifies the existing sell order, removes the assets from the sell organisation,
     * Adds the assets to the buy organisation, adds the money to the sell organisation, take the money from the buy organisation
     * @param sellOrderQuantityLeft - the quantity of the sell order left
     * @param quantityToPurchase - the quantity being purchased
     * @param currentSellOrder - the sell order
     * @param buyOrder - the buy order
     */
    private void updateBuyOrderServerInformation(Integer sellOrderQuantityLeft, Integer quantityToPurchase, Order currentSellOrder, Order buyOrder) throws ServerException {
        try {
            database.beginTransaction();
            // Modify the sell order
            if (sellOrderQuantityLeft == 0) {
                database.deleteOrder(currentSellOrder.getOrderID());
            } else {
                database.updateOrder(currentSellOrder.getOrderID(), sellOrderQuantityLeft);
            }
            assetBuySellTransaction(quantityToPurchase, currentSellOrder, buyOrder);
            database.commitTransaction();
            return;
        } catch (SQLException e) {
            try {
                database.rollBackTransaction();
            } catch (SQLException r) {
                e.printStackTrace();
            }

        }
    }

    /**
     *
     * @param sellOrder
     * @throws ServerException
     */
    private void executeSellOrder(Order sellOrder) throws ServerException {
        OrganisationalUnit sellOrganisationUnit = database.getOrganisation(sellOrder.getOrganisationalUnit());

        if (checkEnoughAssets(sellOrganisationUnit, sellOrder)) {
            //Get the buy orders
            List<Order> orders = database.getBuyOrders();
            List<Order> sortedBuyOrders = new ArrayList<>();

            //filter them by the asset and the price, and sort it if there are any
            for (Order buyOrder: orders) {
                if (sellOrder.getAssetType().equals(buyOrder.getAssetType()) && sellOrder.getRequestPrice() <= buyOrder.getRequestPrice()) {
                    sortedBuyOrders.add(buyOrder);
                }
            }

            Integer sellAssetQuantity = sellOrder.getAssetQuantity();

            //If there are no sell orders then dont continue
            if (sortedBuyOrders.size() >= 0) {
                Collections.sort(sortedBuyOrders);

                //Go through the orders, subtracting the quantity till their is none left
                for (int buyOrderCounter = 0; buyOrderCounter < sortedBuyOrders.size(); buyOrderCounter++) {
                    Order currentBuyOrder = sortedBuyOrders.get(buyOrderCounter);

                    Integer buyOrderQuantity = currentBuyOrder.getAssetQuantity();

                    //Sell order larger: take the buy order quantity from sell quantity: buy quantity is 0
                    //Sell order same: quantity left is 0 in orders
                    //Sell order smaller: take the sell order quantity from buy quantity: sell order quantity is 0
                    if (sellAssetQuantity >= buyOrderQuantity) {
                        sellAssetQuantity -= buyOrderQuantity;
                        updateSellOrderServerInformation(0, buyOrderQuantity, sellOrder, currentBuyOrder);
                    } else if (sellAssetQuantity < buyOrderQuantity) {
                        buyOrderQuantity -= sellAssetQuantity;
                        updateSellOrderServerInformation(buyOrderQuantity, sellAssetQuantity, sellOrder, currentBuyOrder);
                        return;
                    }
                }
            }

            //Add buy order with buyAssetQuantity to database
            generateOrder(sellOrder, sellAssetQuantity);


        } else {
            throw new ServerException("ORGANISATION DOESNT HAVE REQUIRED ASSETS");
        }
    }



    /**
     * Retrieves the required information and then modifies the existing sell order, removes the assets from the sell organisation,
     * Adds the assets to the buy organisation, adds the money to the sell organisation, take the money from the buy organisation
     * @param buyOrderQuantityLeft
     * @param quantityToPurchase
     * @param sellOrder
     * @param currentBuyOrder
     */
    private void updateSellOrderServerInformation(Integer buyOrderQuantityLeft, Integer quantityToPurchase, Order sellOrder, Order currentBuyOrder) throws ServerException {


        try {
            database.beginTransaction();
            // Modify the sell order
            if (buyOrderQuantityLeft == 0) {
                database.deleteOrder(currentBuyOrder.getOrderID());
            } else {
                database.updateOrder(currentBuyOrder.getOrderID(), buyOrderQuantityLeft);
            }

            assetBuySellTransaction(quantityToPurchase, sellOrder, currentBuyOrder);
            database.commitTransaction();
            return;
        } catch (SQLException e) {
            try {
                database.rollBackTransaction();
            } catch (SQLException r) {
                e.printStackTrace();
            }

        }
    }

    /**
     *
     * @param quantityToPurchase
     * @param sellOrder
     * @param buyOrder
     * @throws SQLException
     */
    private void assetBuySellTransaction(Integer quantityToPurchase, Order sellOrder, Order buyOrder) throws SQLException, ServerException {

        //Gather the information from the database now
        OrganisationalUnit sellingOrganisation = database.getOrganisation(sellOrder.getOrganisationalUnit());
        Integer sellingOrganisationQuantity = database.getOrganisationIndividualAsset(sellOrder.getOrganisationalUnit(), sellOrder.getAssetType());
        Double sellingOrganisationCredits = sellingOrganisation.getCredits();

        //Even if the organisation doesnt have a quantity, state that its 0
        if (sellingOrganisationQuantity == null) {
            sellingOrganisationQuantity = 0;
        }

        if (sellingOrganisationQuantity < quantityToPurchase) {
            database.deleteOrder(sellOrder.getOrderID());
            throw new ServerException("Admins Have Edited Assets");
        }

        // Remove assets from sell organisation
        sellingOrganisationQuantity -= quantityToPurchase;
        database.updateOrganisationAsset(sellOrder.getOrganisationalUnit(),sellOrder.getAssetType(), sellingOrganisationQuantity);

        // Add money to sell organisation
        sellingOrganisationCredits += quantityToPurchase * sellOrder.getRequestPrice();
        database.updateOrganisationCredits(sellOrder.getOrganisationalUnit(), sellingOrganisationCredits);

        //Gather the information from the database now, since it could produce race condition if placed before
        OrganisationalUnit buyingOrganisation = database.getOrganisation(buyOrder.getOrganisationalUnit());
        Integer buyingOrganisationQuantity = database.getOrganisationIndividualAsset(buyOrder.getOrganisationalUnit(), buyOrder.getAssetType());
        Double buyingOrganisationCredits = buyingOrganisation.getCredits();

        //Even if the organisation doesnt have a quantity, state that its 0
        if (buyingOrganisationQuantity == null) {
            buyingOrganisationQuantity = 0;
        }

        // Add assets to buy order organisation
        buyingOrganisationQuantity += quantityToPurchase;
        database.updateOrganisationAsset(buyOrder.getOrganisationalUnit(), buyOrder.getAssetType(), buyingOrganisationQuantity);
        // Remove money from buy organisation
        buyingOrganisationCredits -= quantityToPurchase * sellOrder.getRequestPrice();
        database.updateOrganisationCredits(buyOrder.getOrganisationalUnit(), buyingOrganisationCredits);

        // Add trade to trade history
        generateTrade(buyOrder, sellOrder, quantityToPurchase);
    }

}
