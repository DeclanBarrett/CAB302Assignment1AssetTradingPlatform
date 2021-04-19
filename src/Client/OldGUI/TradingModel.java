package Client.OldGUI;
import Client.OldGUI.observer.Subject;

public class TradingModel extends Subject{

    private boolean loggedIn;

    public TradingModel() {
        loggedIn = false;
    }

    public void Login() {
        loggedIn = true;
        notifyObservers();
    }

    public boolean getLoggedInStatus() {return loggedIn; }

}
