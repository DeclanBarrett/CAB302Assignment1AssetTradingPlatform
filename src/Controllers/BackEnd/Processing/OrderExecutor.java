package Controllers.BackEnd.Processing;

import Controllers.BackEnd.NetworkObjects.Order;
import Controllers.BackEnd.NetworkObjects.OrganisationalUnit;
import Controllers.BackEnd.NetworkObjects.Trade;
import Controllers.BackEnd.OrderType;
import Controllers.Exceptions.ServerException;
import Models.InformationGrabber;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Updates order lists, generates new trades and executes orders
 */
public class OrderExecutor
{
    InformationGrabber database;

    public OrderExecutor(InformationGrabber database) {
        this.database = database;
    }

    private ArrayList<Order> orderList;

    /**
     * Updates all orders, with new request
     *
     */
    private void UpdateOrders()
    {

    }

    /**
     * Creates a trade by receiving the buy and sell information and linking the two
     *
     * @param buyOrder  contains information relating to the buy order
     * @param sellOrder contains information relating to the sell order
     */
    private void GenerateTrade(Order buyOrder, Order sellOrder, Integer quantity)
    {
        Trade newTrade = new Trade(-1,
                buyOrder.getAssetType(),
                quantity,
                sellOrder.getRequestPrice(),
                buyOrder.getOrganisationalUnit(),
                sellOrder.getOrganisationalUnit(),
                new Date());
        database.insertTrade(newTrade);
    }

    /**
     * Collates the order information and executes it with the server
     *
     * @param order Contains order information
     */
    public void ExecuteOrder(Order order) throws ServerException
    {
        //Get the orders organisation


        if (order.getOrderType() == OrderType.BUY) {
            executeBuyOrder(order);


        } else if (order.getOrderType() == OrderType.SELL) {

        }
        //Order is buy, check if the organisation has credits
            //Get all sell orders
            //Filter by the asset type
            //If length 0 return



        //Order is sell, check if the organisation has the assets


    }

    /**
     * Checks if there is enough credits to make the order by comparing the total cost of all orders the organisation
     * has currently and the current order to the total credits the organisation has
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
                    if (sellOrderQuantity > buyAssetQuantity) {
                        sellOrderQuantity -= buyAssetQuantity;
                        ProcessBuyOrderLargerSeller(sellOrderQuantity, buyAssetQuantity, currentSellOrder, buyOrder);
                        return;
                    } else if (sellOrderQuantity == buyAssetQuantity) {
                        sellOrderQuantity = 0;
                        // Remove assets from sell organisation
                        // Add assets to buy order organisation
                        // Add trade to trade history
                        // Delete the sell order
                        return;
                    } else if (sellOrderQuantity < buyAssetQuantity) {
                        // Delete the sell order
                        // Remove assets from sell organisation
                        // Add assets to buy organisation
                        // Add trade to trade history
                    }
                }
            }

            //Add buy order with buyAssetQuantity to database



        } else {
            throw new ServerException("ORGANISATION TOTAL ORDER COST IS TOO HIGH");
        }
    }

    /**
     * Retrieves the required information and then modifies the existing sell order, removes the assets from the sell organisation,
     * Adds the assets to the buy organisation, adds the money to the sell organisation, take the money from the buy organisation
     * @param sellOrderQuantity
     * @param buyAssetQuantity
     * @param currentSellOrder
     * @param buyOrder
     */
    private void ProcessBuyOrderLargerSeller(Integer sellOrderQuantity, Integer buyAssetQuantity, Order currentSellOrder, Order buyOrder) {

        //Retrieve the selling organisation information
        OrganisationalUnit sellingOrganisation = database.getOrganisation(currentSellOrder.getOrganisationalUnit());
        Integer sellingOrganisationQuantity = database.getOrganisationIndividualAsset(currentSellOrder.getOrganisationalUnit(), currentSellOrder.getAssetType());
        Double sellingOrganisationCredits = sellingOrganisation.getCredits();

        //Retrieve the buying organisation information
        OrganisationalUnit buyingOrganisation = database.getOrganisation(currentSellOrder.getOrganisationalUnit());
        Integer buyingOrganisationQuantity = database.getOrganisationIndividualAsset(currentSellOrder.getOrganisationalUnit(), currentSellOrder.getAssetType());
        Double buyingOrganisationCredits = buyingOrganisation.getCredits();

        if (sellingOrganisationQuantity == null) {
            sellingOrganisationQuantity = 0;
        }
        try {
            database.beginTransaction();
            // Modify the sell order
            database.updateOrder(currentSellOrder.getOrderID(), sellOrderQuantity);

            // Remove assets from sell organisation
            sellingOrganisationQuantity -= buyAssetQuantity;
            database.updateOrganisationAsset(currentSellOrder.getOrganisationalUnit(), currentSellOrder.getAssetType(), sellingOrganisationQuantity);

            // Add assets to buy order organisation
            buyingOrganisationQuantity += buyAssetQuantity;
            database.updateOrganisationAsset(buyOrder.getOrganisationalUnit(), buyOrder.getAssetType(), buyingOrganisationQuantity);

            // Add money to sell organisation
            sellingOrganisationCredits += buyAssetQuantity * currentSellOrder.getRequestPrice();
            database.updateOrganisationCredits(currentSellOrder.getOrganisationalUnit(), sellingOrganisationCredits);

            // Remove money from buy organisation
            buyingOrganisationCredits -= buyAssetQuantity * currentSellOrder.getRequestPrice();
            database.updateOrganisationCredits(buyOrder.getOrganisationalUnit(), buyingOrganisationCredits);

            // Add trade to trade history
            GenerateTrade(buyOrder, currentSellOrder, buyAssetQuantity);
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


}
