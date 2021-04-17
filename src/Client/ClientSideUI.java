package Client;

import javax.swing.*;
import java.awt.*;
/**
 * Create GUI and display it.
 */
public class ClientSideUI {

    public void createGUI()
    {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame clientSideUI = new JFrame ("Client Side UI");
        clientSideUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add label
        JLabel label = new JLabel("Hello Java Swing World");
        clientSideUI.getContentPane().add(label);
        clientSideUI.add(label);

        clientSideUI.setPreferredSize(new Dimension(300, 100));
        clientSideUI.setLocation(new Point(200,200));
        clientSideUI.pack();
        clientSideUI.setVisible(true);

    }
}
