package Client.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TradingController {

    private TradingModel model;

    private TradingGUI view;

    private ActionListener colourListener;

    public TradingController(TradingModel model, TradingGUI gui) {
        this.model = model;
        this.view = gui;
        gui.addActionListener(new LoginListener());
        model.attachObserver(gui);
    }

    private class ColourListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Component source = (Component) e.getSource();

            /*
            if (source == view.getRedButton() || source == view.getRedChoice()) {
                model.changeRed();
            } else if (source == view.getBlueButton()
                    || source == view.getBlueChoice()) {
                model.changeBlue();
            } else if (source == view.getWhiteButton()
                    || source == view.getWhiteChoice()) {
                model.changeWhite();
            }

             */

        }
    }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Component source = (Component) e.getSource();

            if (source == view.getLoginButton()) {
                model.Login();
            }

            /*
            if (source == view.getRedButton() || source == view.getRedChoice()) {
                model.changeRed();
            } else if (source == view.getBlueButton()
                    || source == view.getBlueChoice()) {
                model.changeBlue();
            } else if (source == view.getWhiteButton()
                    || source == view.getWhiteChoice()) {
                model.changeWhite();
            }

             */

        }
    }
}
