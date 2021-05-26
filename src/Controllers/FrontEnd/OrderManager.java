package Controllers.FrontEnd;

import Controllers.Backend.Order;
import Controllers.Backend.OrganisationalUnit;

/**
 * Generates an order from UI data and attempts to send it to the Server to execute
 */
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

    private void UpdateUI() {
        
    }

    public void SetUser(User user) {
        loggedInUser = user;
    }

    public void SetOrgUnit(OrganisationalUnit unit) {
        orgUnit = unit;
    }

    public void CheckOrderValidity(Order order) {
    }
}
