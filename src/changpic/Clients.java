package changpic;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static changpic.RestaurantOrderManagementApp.*;
import javax.swing.SwingUtilities;
//import java.util.Scanner;
//import javax.swing.DefaultListModel;

public class Clients implements Serializable {

    private String userNameAccount;
    private String userPasswordAccount;
    private ArrayList<order> orderClientList = new ArrayList();
    private int numberOfVisits = 0;

    public String getUserNameAccount() {
        return userNameAccount;
    }

    public void setUserNameAccount(String userNameAccount) {
        this.userNameAccount = userNameAccount;
    }

    public String getUserPasswordAccount() {
        return userPasswordAccount;
    }

    public void setUserPasswordAccount(String userPasswordAccount) {
        this.userPasswordAccount = userPasswordAccount;
    }

    public ArrayList<order> getOrderClientList() {
        return orderClientList;
    }

    public void setOrderClientList(ArrayList<order> orderClientList) {
        this.orderClientList = orderClientList;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits += numberOfVisits;
    }

    @Override
    public String toString() {
        return "clients{" + "userNameAccount=" + userNameAccount + ", userPasswordAccount=" + userPasswordAccount + ", orderClientList=" + orderClientList + '}';
    }

    public static boolean checkIfThisUserNameIsAlreadyExistOrNot(ArrayList<Clients> MyClientList, String newUserName) {
        for (Clients i : MyClientList) {
            if (newUserName.equals(i.getUserNameAccount())) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIfPasswordIsValid(String password) {
        if (password.trim().length() < 8) {
            return false;
        }
        return true;
    }

    //انشاء حساب مستخدم جديد
    public static void showUserOptions(ArrayList<Clients> AllClientsList, ArrayList<Meals> dailyMealsList, Restaurant admin) {
        int option = JOptionPane.showConfirmDialog(frame, "Would you like to create a new account?", "User Options", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            Clients ob = new Clients();
            String newUsername = JOptionPane.showInputDialog(frame, "Enter new username:");
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
            try {
                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                } else {
                    if (checkIfThisUserNameIsAlreadyExistOrNot(serializableFile.getClientsDataFomeFile(PATH_CLIENTS), newUsername)) {
                        if (checkIfPasswordIsValid(newPassword)) {
                            ob.setUserNameAccount(newUsername);
                            ob.setUserPasswordAccount(newPassword);
                            AllClientsList.add(ob);
                            serializableFile.saveClientsDataOnFile(AllClientsList, PATH_CLIENTS);
                            JOptionPane.showMessageDialog(frame, "Account created successfully.");
                            Clients.showUserPanel(ob, dailyMealsList, AllClientsList, admin);
                        } else {
                            JOptionPane.showMessageDialog(frame, "password must be at least 8 characters.");
                            frame.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "The username  you entered is already has been used please try another one.");
                        frame.setVisible(true);
                    }
                }
            } catch (java.lang.NullPointerException ex) {
                JOptionPane.showMessageDialog(frame, "the operation was cancelled successfully.");
                frame.setVisible(true);
            }

        } else {
            showLoginDialog(false, AllClientsList, dailyMealsList, admin);
        }
    }

    public static void showLoginDialog(boolean isAdmin, ArrayList<Clients> AllClientsList, ArrayList<Meals> dailyMealsList, Restaurant admin) {
        JFrame loginFrame = new JFrame(isAdmin ? "Admin Login" : "User Login");
        loginFrame.setResizable(false);
        loginFrame.setSize(550, 300);
        BackgroundPanel menuPanel = new BackgroundPanel("img\\offer-background.jpg");
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        icon.setIconFrame(loginFrame, "img\\iconLogin.jpg");
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.GRAY);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        JTextField usernameField = new JTextField(22);
        usernameLabel.setForeground(Color.GRAY);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your name")) {
                    usernameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Enter your name");
                }
            }
        });
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.GRAY);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JPasswordField passwordField = new JPasswordField(22);

        JButton loginButton = new JButton("Login");
        Extension.styleButton(loginButton);
        JButton buttonClose = new JButton("Close");
        Extension.styleButton(buttonClose);

        buttonClose.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            frame.setVisible(true);
            loginFrame.setVisible(false);
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 20, 10, 0);
        menuPanel.add(buttonClose, gbc);
        loginButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isAdmin) {
                if ("admin".equals(username) && "admin123".equals(password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Welcome, Admin!");
                    loginFrame.dispose();
                    Admin.showAdminPanel(AllClientsList, dailyMealsList, admin);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid Admin Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (Clients.checkIfUserExist(AllClientsList, username, password)) {
                    loginFrame.dispose();
                    Clients temp = Clients.getClientObject(AllClientsList, username, password);
                    JOptionPane.showMessageDialog(loginFrame, "Welcome, " + temp.getUserNameAccount() + "!");
                    Clients.showUserPanel(temp, dailyMealsList, AllClientsList, admin);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid User Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginFrame.add(menuPanel);
        loginFrame.setLocationRelativeTo(frame);
        loginFrame.setVisible(true);
    }

    public static void refreshClientData(ArrayList<Clients> AllClientsList, Clients newClientsData) {
        for (Clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(newClientsData.getUserNameAccount()) && i.getUserPasswordAccount().equals(newClientsData.getUserPasswordAccount())) {
                i = newClientsData;
            }
            serializableFile.saveClientsDataOnFile(AllClientsList, PATH_CLIENTS);
            return;
        }
    }

    public static Clients getClientObject(ArrayList<Clients> AllClientsList, String insertedUserName, String insertedUserPassword) {
        for (Clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(insertedUserName) && i.getUserPasswordAccount().equals(insertedUserPassword)) {
                return i;
            }
        }
        return null;
    }

    public static order getClientOrder(Clients object, int orderID) {
        for (order i : object.getOrderClientList()) {
            if (i.getOrderID() == orderID) {
                return i;
            }
        }
        return null;
    }

    public static boolean checkIfUserExist(ArrayList<Clients> AllClientsList, String insertedUserName, String insertedUserPassword) {
        for (Clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(insertedUserName) && i.getUserPasswordAccount().equals(insertedUserPassword)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfClientHasThisMealAlready(ArrayList<Meals> ArrayListForSearching, String mealName) {
        for (Meals i : ArrayListForSearching) {
            if (i.getMealName().equals(mealName)) {
                return true;
            }
        }
        return false;
    }

    public static Meals getMealObjectFormClientList(ArrayList<Meals> ArrayListForSearching, String mealName) {
        for (Meals i : ArrayListForSearching) {
            if (i.getMealName().equals(mealName)) {
                return i;
            }
        }
        return null;
    }

    public static void creatAnewOreder(Clients object) {
        order temp = new order();
        object.getOrderClientList().add(temp);
        JOptionPane.showMessageDialog(null, "new order has been creatred successfully !");
    }

    //واجهة المستخدم بعد تسجيل الدخول
    public static void showUserPanel(Clients object, ArrayList<Meals> dailyMealsList, ArrayList<Clients> AllClientsList, Restaurant admin) {
        userPanel = new JFrame("User Panel");
        userPanel.setSize(600, 400);
        userPanel.setResizable(false);
        userPanel.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        BackgroundPanel backgroundPanelUser = new BackgroundPanel("img\\back2.jpg");
        backgroundPanelUser.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        icon.setIconFrame(userPanel, "img\\IconProject2.jpg");
        JButton chooseMealButton = new JButton("Choose Meal");
        Extension.styleButton(chooseMealButton);
        chooseMealButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            Meals.chooseMeal(object, dailyMealsList, AllClientsList, admin);
            userPanel.setVisible(false);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanelUser.add(chooseMealButton, gbc);

        JButton viewOrderButton = new JButton("View Order");
        Extension.styleButton(viewOrderButton);
        viewOrderButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            try {
                OrderViewUI.viewOrder(object.getOrderClientList().getLast());

                //  order.viewOrderByItsID(object, 0, false);
            } catch (java.util.NoSuchElementException ex) {
                JOptionPane.showMessageDialog(null, " there is no order to disply");
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        backgroundPanelUser.add(viewOrderButton, gbc);

        JButton creatAnewOrderButton = new JButton("new order");
        Extension.styleButton(creatAnewOrderButton);
        creatAnewOrderButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            Clients.creatAnewOreder(object);
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        backgroundPanelUser.add(creatAnewOrderButton, gbc);

        JButton getAdicountCodeButton = new JButton("Get Discount Code");
        Extension.styleButton(getAdicountCodeButton);
        getAdicountCodeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            if (object.getNumberOfVisits() >= 5) {
                JOptionPane.showMessageDialog(null, "congratulatoins ,here is a 10% discount code on the entire value\n of any order you place from our Chill Restaurant [DISCOUNT10%] ", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (object.getNumberOfVisits() >= 10) {
                JOptionPane.showMessageDialog(null, "congratulatoins ,here is a 10% discount code on the entire value\n of any order you place from our Chill Restaurant [PRODISCOUNT20%] ", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Sorry, there are no discount codes avaliable at the moment .\nTry again later.", "oops", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.gridwidth = 2;
        backgroundPanelUser.add(getAdicountCodeButton, gbc);

        JButton closeButton = new JButton("Logout");
        Extension.styleButton(closeButton);
        closeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            userPanel.setVisible(false);
            frame.setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        //gbc.gridwidth = 2;
        backgroundPanelUser.add(closeButton, gbc);

        JButton removeAccountButton = new JButton("Remove My Account");
        Extension.styleButton(removeAccountButton);
        removeAccountButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            createAndShowRemoveFrame(AllClientsList, object);

        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        //gbc.gridwidth = 2;
        backgroundPanelUser.add(removeAccountButton, gbc);

        userPanel.add(backgroundPanelUser);
        userPanel.setLocationRelativeTo(frame);
        userPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userPanel.setVisible(true);
    }

    public static void removeMyAccount(ArrayList<Clients> AllClientsList, Clients object) {
        AllClientsList.remove(object);
        serializableFile.saveClientsDataOnFile(AllClientsList, PATH_CLIENTS);
        userPanel.setVisible(false);
        frame.setVisible(true);
    }

    private static void createAndShowRemoveFrame(ArrayList<Clients> AllClientsList, Clients currentClient) {
        JFrame frame = new JFrame("Remove My Account");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        JTextField passwordField = new JTextField(15);
        frame.add(new JLabel("Enter your password to confirm:"));
        frame.add(passwordField);

        JButton confirmButton = new JButton("Confirm");
        frame.add(confirmButton);

        confirmButton.addActionListener(e -> {
            String password = passwordField.getText();
            handleRemoveAccount(password, AllClientsList, currentClient);
            frame.setVisible(false);

        });

        frame.setVisible(true);
    }

    private static void handleRemoveAccount(String password, ArrayList<Clients> AllClientsList, Clients currentClient) {
        if (confirmPassword(password, currentClient)) {
            removeMyAccount(AllClientsList, currentClient);
            JOptionPane.showMessageDialog(null, "The account is removed.");
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
        }
    }

    private static boolean confirmPassword(String password, Clients currentClient) {
        return currentClient.getUserPasswordAccount().equals(password);
    }

}
