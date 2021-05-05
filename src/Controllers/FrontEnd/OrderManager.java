package Controllers.FrontEnd;

import Controllers.Backend.NetworkObjects.Order;
import Controllers.Backend.NetworkObjects.OrganisationalUnit;
import Controllers.Backend.NetworkObjects.User;

/**
 * Generates an order from UI data and attempts to send it to the Server to execute
 */

// TODO Figure out why this is here
public class OrderManager {

    private User loggedInUser;
    private OrganisationalUnit orgUnit;

    /**
     * Sends an Order to the server
     * @param order The Order object to be sent
     */
    private void SendOrder(Order order) {

    }

    /**
     * Gets organisational unit object associated with the current
     * @return
     */
    private OrganisationalUnit GetOrgUnit() {
        return null;
    }

    /**
     * Updates UI with new information
     */
    private void UpdateUI() {
        
    }

    /**
     * Set user as 'owner' of the order
     * @param user User attached to the order
     */
    public void SetUser(User user) {
        loggedInUser = user;
    }

    /**
     * Set organisaitonal unit as 'owner' of the Organisational Unit
     * @param unit Organisational unit attached to the order.
     */
    public void SetOrgUnit(OrganisationalUnit unit) {
        orgUnit = unit;
    }

    /**
     * Checks validity of the submitted order
     * @param order Order to be checked.
     */
    public void CheckOrderValidity(Order order) {
    }
}
