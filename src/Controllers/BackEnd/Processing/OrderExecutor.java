package Controllers.BackEnd.Processing;

import Controllers.BackEnd.NetworkObjects.Order;

import java.util.ArrayList;

/**
 * Updates order lists, generates new trades and executes orders
 */
public class OrderExecutor
{

    private ArrayList<Order> orderList;

    /**
     * Updates all orders, with new request
     *
     * @param order Contains order information
     */
    private void UpdateOrders(Order order)
    {

    }

    /**
     * Creates a trade by receiving the buy and sell information and linking the two
     *
     * @param buyOrder  contains information relating to the buy order
     * @param sellOrder contains information relating to the sell order
     */
    private void GenerateTrade(Order buyOrder, Order sellOrder)
    {

    }

    /**
     * Collates the order information and executes it with the server
     *
     * @param order Contains order information
     */
    public void ExecuteOrder(Order order)
    {

    }


}
