package Client;

import Shared.Order;
import Shared.OrganisationalUnit;
import Shared.User;

public class ClientOrderManager {

    private User loggedInUser;
    private OrganisationalUnit orgUnit;

    private void SendOrder(Order order) {

    }

    private OrganisationalUnit GatherOrgUnit() {
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
