package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Client.observer.Observer;
import Client.observer.Subject;

public class TradingGUI extends JFrame implements Observer {

    //private static final long serialVersionUID = -8851746420097089647L;

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private static final int FONT_SIZE = 50;

    private JTabbedPane mainPane;



    private JPanel userTradePanel;
    private JPanel loginPanel;
    private JPanel resetPanel;
    private JPanel userOrganisationPanel;

    private JPanel serverTradePanel;
    private JPanel serverOrganisationPanel;
    private JPanel serverManageUserPanel;

    //Login
    private JPanel initialLoginPanel;

    //private JPanel centredLoginPanel;

    private JPanel loginButtonPanel;
    private JButton loginButton;
    /*
    private JButton redButton;

    private JButton blueButton;

    private JButton whiteButton;

    private JMenuItem redChoice;

    private JMenuItem blueChoice;

    private JMenuItem whiteChoice;

     */
    public TradingGUI() {
        super("Unknown Organisation's Trading Platform");
        initComponents();
    }

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



        /*
        redPanel = new JPanel();
        redPanel.setBackground(Color.LIGHT_GRAY);
        bluePanel = new JPanel();
        bluePanel.setBackground(Color.LIGHT_GRAY);
        whitePanel = new JPanel();
        whitePanel.setBackground(Color.LIGHT_GRAY);

        JPanel biggerPanel = new JPanel();
        biggerPanel.setLayout(new GridLayout(1, 3));
        biggerPanel.add(redPanel);
        biggerPanel.add(bluePanel);
        biggerPanel.add(whitePanel);
        add(biggerPanel, BorderLayout.CENTER);

        redButton = new JButton("Red");
        blueButton = new JButton("Blue");
        whiteButton = new JButton("White");
        redButton.setBackground(Color.RED);
        blueButton.setBackground(Color.BLUE);
        whiteButton.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(redButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(whiteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        redChoice = new JMenuItem("Red");
        blueChoice = new JMenuItem("Blue");
        whiteChoice = new JMenuItem("White");
        JMenu colourMenu = new JMenu("Add Colours");
        colourMenu.add(redChoice);
        colourMenu.add(blueChoice);
        colourMenu.add(whiteChoice);

         */

        /*
        JMenuBar bar = new JMenuBar();
        bar.add(colourMenu);
        setJMenuBar(bar);

         */
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    /*
    public JButton getBlueButton() {
        return blueButton;
    }

    public JMenuItem getBlueChoice() {
        return blueChoice;
    }

    public JButton getRedButton() {
        return redButton;
    }

    public JMenuItem getRedChoice() {
        return redChoice;
    }

    public JButton getWhiteButton() {
        return whiteButton;
    }

    public JMenuItem getWhiteChoice() {
        return whiteChoice;
    }
     */

    public void addActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
        //redButton.addActionListener(listener);
        //blueButton.addActionListener(listener);
        //whiteButton.addActionListener(listener);
        //redChoice.addActionListener(listener);
        //blueChoice.addActionListener(listener);
        //whiteChoice.addActionListener(listener);
    }

    @Override
    public void update(Subject s) {

        TradingModel model = (TradingModel) s;

        if (model.getLoggedInStatus()) {
            InitiateAfterLoginGUI(false);
        }
        //if (model.getRed()) {
        //    redPanel.setBackground(Color.RED);
        //} else {
        //    redPanel.setBackground(Color.LIGHT_GRAY);
        //}
        //if (model.getBlue()) {
        //    bluePanel.setBackground(Color.BLUE);
        //} else {
        //    bluePanel.setBackground(Color.LIGHT_GRAY);
        //}
        //if (model.getWhite()) {
        //    whitePanel.setBackground(Color.WHITE);
        //} else {
        //    whitePanel.setBackground(Color.LIGHT_GRAY);
        //}
//
    }

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

        int loginRows = 5;
        int loginColumns = 1;

        int loginBoxWidth = 200;
        int loginBoxHeight = 100;

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        //centredLoginPanel.setPreferredSize(new Dimension(loginBoxWidth, loginBoxHeight));

        JPanel centredLoginPanel = new JPanel();

        outerPanel.add(Box.createVerticalGlue());
        outerPanel.add(centredLoginPanel);
        outerPanel.add(Box.createVerticalGlue());

        centredLoginPanel.setMaximumSize(new Dimension(loginBoxWidth, loginBoxHeight));
        centredLoginPanel.setMinimumSize(new Dimension(loginBoxWidth, loginBoxHeight));

        centredLoginPanel.setLayout(new GridLayout(loginRows, loginColumns));

        JPanel titleLoginPanel = new JPanel();
        titleLoginPanel.add(new JLabel("LOGIN SCREEN"));
        titleLoginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centredLoginPanel.add(titleLoginPanel);

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
