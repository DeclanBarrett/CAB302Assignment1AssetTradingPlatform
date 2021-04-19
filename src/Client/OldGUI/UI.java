package Client.OldGUI;

import Client.OldGUI.TradingController;
import Client.OldGUI.TradingGUI;
import Client.OldGUI.TradingModel;

import javax.swing.*;

public class UI {

    public static void main(String[] args) {
        // create an instance of the model
        TradingModel model = new TradingModel();

        // register the model with the GUI
        TradingGUI gui = new TradingGUI();

        // set up the controller
        TradingController controller = new TradingController(model, gui);

        // make the GUI visible
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        //gui.pack();
    }


}
