package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Client.observer.Observer;
import Client.observer.Subject;

public class TradingGUI extends JFrame implements Observer {

    //private static final long serialVersionUID = -8851746420097089647L;

    //Global GUI variables
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private static final int FONT_SIZE = 50;

    //The tabbed pane that all logged in panels are placed in
    private JTabbedPane mainPane;

    //Non-Admin Panels
    private JPanel userTradePanel;
    private JPanel loginPanel;
    private JPanel resetPanel;
    private JPanel userOrganisationPanel;

    //Admin Panels
    private JPanel serverTradePanel;
    private JPanel serverOrganisationPanel;
    private JPanel serverManageUserPanel;

    //Login
    private JPanel initialLoginPanel;

    //private JPanel centredLoginPanel;

    private JPanel loginButtonPanel;
    private JButton loginButton;

    /**
     * Creates the JFrame GUI
     */
    public TradingGUI() {
        super("Unknown Organisation's Trading Platform");
        initComponents();
    }

    /**
     * Initital components (login screen)
     */
    private void initComponents() {

        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        initialLoginPanel = new JPanel();
        initialLoginPanel.setLayout(new BorderLayout());


        //initialLoginPanel.setLayout(new BorderLayout());
        JLabel applicationTitle = new JLabel("Unknown Organisation's Trading Platform");
        applicationTitle.setFont(new Font("Arial",Font.BOLD,FONT_SIZE));
        initialLoginPanel.add(applicationTitle, BorderLayout.NORTH);
        layoutCentredLogin(initialLoginPanel);

        getContentPane().add(initialLoginPanel);

    }

    /**
     * Gets Login Button JButton
     * @return The JButton
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Adds a listener to the login button
     * @param listener Listener
     */
    public void addActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    @Override
    public void update(Subject s) {

        TradingModel model = (TradingModel) s;

        if (model.getLoggedInStatus()) {
            InitiateAfterLoginGUI(model.getLoggedInStatus());
        }
    }

    /**
     * Change from login screen to server/non-server
     * @param isUser
     */
    private void InitiateAfterLoginGUI(boolean isUser) {

        mainPane = new JTabbedPane();
        getContentPane().remove(initialLoginPanel);
        getContentPane().add(mainPane);

        if (isUser) {
            layoutClientGUI();
        } else {
            layoutServerGUI();
        }

    }

    /**
     * Initiates the tab bar for Server Admins
     */
    private void layoutServerGUI() {
        serverTradePanel = new JPanel();
        serverOrganisationPanel = new JPanel();
        serverManageUserPanel = new JPanel();

        mainPane.add("Organisations", serverOrganisationPanel);
        mainPane.add("Manage Users", serverManageUserPanel);
        mainPane.add("Trading", serverTradePanel);
        mainPane.add("Login", loginPanel);
    }

    /**
     * Initiates the tab bar for Non-Server Admins
     */
    private void layoutClientGUI() {
        userTradePanel = new JPanel();

        loginPanel = new JPanel();

        resetPanel = new JPanel();

        userOrganisationPanel = new JPanel();

        /*User Panels*/
        mainPane.add("Organisation", userOrganisationPanel);
        mainPane.add("Trade", userTradePanel);
        mainPane.add("Login", loginPanel);

    }

    /**
     * Adds the login screen to the panel given
     * @param panel the panel to place it upon
     */
    private void layoutCentredLogin(JPanel panel) {

        //rows and columns of table formating the login form
        int loginRows = 5;
        int loginColumns = 1;

        //The login box width and height
        int loginBoxWidth = 200;
        int loginBoxHeight = 100;

        //Create an outer panel which will have the login box inside it
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));

        //Create the inner login box
        JPanel centredLoginPanel = new JPanel();

        //Put spacing around the box
        outerPanel.add(Box.createVerticalGlue());
        outerPanel.add(centredLoginPanel);
        outerPanel.add(Box.createVerticalGlue());

        //Set the size of the box - and make it unchanging
        centredLoginPanel.setMaximumSize(new Dimension(loginBoxWidth, loginBoxHeight));
        centredLoginPanel.setMinimumSize(new Dimension(loginBoxWidth, loginBoxHeight));

        //Set the layout to be a standard grid
        centredLoginPanel.setLayout(new GridLayout(loginRows, loginColumns));

        //Add the "Login" title
        JPanel titleLoginPanel = new JPanel();
        titleLoginPanel.add(new JLabel("LOGIN SCREEN"));
        titleLoginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centredLoginPanel.add(titleLoginPanel);

        //
        loginButtonPanel = new JPanel();
        loginButton = new JButton("LOGIN");
        loginButtonPanel.add(loginButton);
        loginButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centredLoginPanel.add(loginButtonPanel);

        centredLoginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centredLoginPanel.setSize(new Dimension(loginBoxWidth, loginBoxHeight));


        outerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(outerPanel, BorderLayout.CENTER);


    }
}
