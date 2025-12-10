package changpic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RestaurantOrderManagementApp extends Component {

    //admin info :
    //admin name : admin
    //admin password : admin123
    Restaurant admin = new Restaurant();

    // Generic ArrayLists
    ArrayList<Clients> AllClientsList = serializableFile.getClientsDataFomeFile(PATH_CLIENTS);
    //  static ArrayList<Clients> AllClientsList = new ArrayList();

    ArrayList<Meals> dailyMealsList = serializableFile.getMealsFataFomeFile(PATH_MEALS);
    //static ArrayList<Meals> dailyMealsList = new ArrayList();

    // ArrayList<Meals> AllMealsList = new ArrayList();
    static final String PATH_CLIENTS = "clientsInfo.txt";
    static final String PATH_MEALS = "meals.txt";
    protected static JProgressBar progressBar = new JProgressBar();
    protected static JFrame usersFrame;
    protected static JFrame frameLo;
    protected static JFrame adminPanel;
    protected static JFrame userPanel;
    protected static JFrame frame;
    protected CardLayout cardLayout;
    protected JPanel mainPanel;
    static JComboBox comboBox;
    protected static BackgroundPanel icon = new BackgroundPanel();

    public RestaurantOrderManagementApp() {
        UIManager.put("Button.background", new Color(255, 188, 47));
        UIManager.put("Button.foreground", new Color(56, 56, 72));

        frame = new JFrame("Chill Restaurant ");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        frame.setResizable(false);
//        ImageIcon logo = new ImageIcon("logo.jpg");
//        frame.setIconImage(logo.getImage());
        icon.setIconFrame(frame, "img\\IconProject2.jpg");

        createMainMenu();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createMainMenu() {
        BackgroundPanel menuPanel = new BackgroundPanel("img\\home_bg.jpeg");
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Chill Resturant", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        welcomeLabel.setForeground(new Color(255, 188, 100));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-100, 20, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(welcomeLabel, gbc);

        JButton adminButton = new JButton("Login as Admin");
        JButton userButton = new JButton("Login as User");

        Extension.styleButton(adminButton);
        Extension.styleButton(userButton);

        adminButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            frame.setVisible(false);
            Clients.showLoginDialog(true, AllClientsList, dailyMealsList, admin);
        }); // زر الادمن
        userButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            frame.setVisible(false);
            //UserOptions.showUserOptions(AllClientsList, dailyMealsList, admin);
            Clients.showUserOptions(AllClientsList, dailyMealsList, admin);
        }); // زر المستخدم

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(adminButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(userButton, gbc);
        mainPanel.add(menuPanel, "MainMenu");
        cardLayout.show(mainPanel, "MainMenu");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantOrderManagementApp::new);
   //     MusicPlayer2 player = new MusicPlayer2("chillmusic");
    }
}
