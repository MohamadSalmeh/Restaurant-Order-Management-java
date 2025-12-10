package changpic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantOrderManagementApp extends Component {

    // Generic ArrayLists
    ArrayList<clients> AllClientsList = getClientsDataFomeFile(path_clients);
    //static ArrayList<clients> AllClientsList = new ArrayList();

    ArrayList<meals> dailyMealsList = getMealsDataFomeFile(path_meals);
    // static ArrayList<meals> dailyMealsList = new ArrayList();

    ArrayList<meals> AllMealsList = new ArrayList();
    static final String path_clients = "clientsInfo.txt";
    static final String path_meals = "meals.txt";

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public RestaurantOrderManagementApp() {
        frame = new JFrame("Chill Restaurant ");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        createMainMenu();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    //القائمة الرئيسية
    private void createMainMenu() {
        BackgroundPanel menuPanel = new BackgroundPanel("img\\home_bg.jpeg"); // خلفية الواجهة
        menuPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // إعداد قيود التخطيط

        // إعداد العبارة
        JLabel welcomeLabel = new JLabel("Chill Resturant", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        welcomeLabel.setForeground(new Color(255, 188, 100));

        // إعداد قيود العبارة
        gbc.gridx = 0; // العمود
        gbc.gridy = 0; // الصف
        gbc.insets = new Insets(-100, 20, 20, 0); // الهوامش (أعلى، يسار، أسفل، يمين)
      gbc.anchor = GridBagConstraints.CENTER; // محاذاة في الوسط
        menuPanel.add(welcomeLabel, gbc); // إضافة العبارة إلى اللوحة

        // إعداد الأزرار
        JButton adminButton = new JButton("Login as Admin");
        JButton userButton = new JButton("Login as User");

        styleButton(adminButton);
        styleButton(userButton);

        adminButton.addActionListener(e -> showLoginDialog(true)); // زر الادمن
        userButton.addActionListener(e -> showUserOptions(AllClientsList)); // زر المستخدم

        // إعداد قيود الزر الإداري
        gbc.gridx = 0; // العمود
        gbc.gridy = 1; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(adminButton, gbc); // إضافة الزر الإداري

        // إعداد قيود الزر المستخدم
        gbc.gridx = 0; // العمود
        gbc.gridy = 2; // الصف
        menuPanel.add(userButton, gbc); // إضافة الزر المستخدم

//        JTabbedPane tabbedPane=new JTabbedPane();
//        tabbedPane.addTab("main",menuPanel);
//        tabbedPane.add("J",new JTextArea());
//        tabbedPane.setSelectedIndex(1);
//     tabbedPane.setToolTipTextAt(0,"Mainaaaa");
//        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        mainPanel.add(menuPanel, "MainMenu"); // إضافة جميع المحتويات على الفريم
        cardLayout.show(mainPanel, "MainMenu");

    }

    private void showUsersList(ArrayList<clients> AllClientsList) {
        usersFrame = new JFrame("Registered Users");
        usersFrame.setSize(400, 300);

        String[] columnNames = {"Username"};
        String[][] data = new String[AllClientsList.size()][1];

        for (int i = 0; i < AllClientsList.size(); i++) {
            clients client = AllClientsList.get(i);
            data[i][0] = client.getUserNameAccount();
        }

        JTable usersTable = new JTable(data, columnNames);

        usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = usersTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    // طلب إدخال ID الطلب من المستخدم
                    String input = JOptionPane.showInputDialog(usersFrame, "Enter Order ID:");
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int orderId = Integer.parseInt(input); // تحويل المدخل إلى int
                            viewOrderByItsID(AllClientsList.get(row), orderId); // تمرير ID الطلب إلى الدالة
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(usersFrame, "Please enter a valid Order ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(usersTable);
        usersFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        styleButton(closeButton);
        closeButton.addActionListener(e -> usersFrame.dispose());

        usersFrame.add(closeButton, BorderLayout.SOUTH);
        usersFrame.setLocationRelativeTo(frame);
        usersFrame.setVisible(true);
    }

    //عرض واجهة الادمن
    private void showAdminPanel() {
        JFrame adminPanel = new JFrame("Admin Panel");
        adminPanel.setSize(600, 400);

        // إنشاء كائن من BackgroundPanel مع مسار الصورة
        BackgroundPanel backgroundPanel = new BackgroundPanel("adminPic.png"); // استبدل المسار بالصورة الخاصة بك
        backgroundPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // إعداد قيود التخطيط

        // إعداد زر عرض المستخدمين
        JButton viewUsersButton = new JButton("View Users"); // زر عرض جميع المستخدمين
        styleButton(viewUsersButton);
        viewUsersButton.addActionListener(e -> showUsersList(AllClientsList)); // الضغط على الزر 

        // إعداد قيود زر عرض المستخدمين
        gbc.gridx = 0; // العمود
        gbc.gridy = 0; // الصف
        gbc.insets = new Insets(10, 10, 10, 10); // الهوامش
        gbc.fill = GridBagConstraints.HORIZONTAL; // ملء المساحة الأفقية
        backgroundPanel.add(viewUsersButton, gbc); // إضافة الزر إلى اللوحة

        // إعداد زر تغيير الوجبات
        JButton changeMealsButton = new JButton("Change Meals");
        styleButton(changeMealsButton);
        changeMealsButton.addActionListener(e -> randomMeals());

        // إعداد قيود زر تغيير الوجبات
        gbc.gridx = 0; // العمود
        gbc.gridy = 1; // الصف
        backgroundPanel.add(changeMealsButton, gbc); // إضافة الزر إلى اللوحة

        // إعداد زر الإغلاق
        JButton closeButton = new JButton("Close"); // زر إغلاق فريم الإدمن
        styleButton(closeButton);
        closeButton.addActionListener(e -> adminPanel.dispose());

        // إعداد قيود زر الإغلاق
        gbc.gridx = 0; // العمود
        gbc.gridy = 2; // الصف
        backgroundPanel.add(closeButton, gbc); // إضافة الزر إلى اللوحة

        adminPanel.add(backgroundPanel); // إضافة BackgroundPanel إلى JFrame

        // إعداد موقع الفريم
        adminPanel.setLocationRelativeTo(frame);
        adminPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // إغلاق الفريم عند الضغط على X
        adminPanel.setVisible(true);
    }

    //انشاء حساب مستخدم جديد
    private void showUserOptions(ArrayList<clients> AllClientsList) {
        int option = JOptionPane.showConfirmDialog(frame, "Would you like to create a new account?", "User Options", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            clients ob = new clients();
            String newUsername = JOptionPane.showInputDialog(frame, "Enter new username:");
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
            try {
                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                } else {
                    ob.setUserNameAccount(newUsername);
                    ob.setUserPasswordAccount(newPassword);
                    AllClientsList.add(ob);
                    saveClientsDataOnFile(AllClientsList, path_clients);
                    JOptionPane.showMessageDialog(frame, "Account created successfully.");
                }
            } catch (java.lang.NullPointerException ex) {
            }

        } else {
            showLoginDialog(false);
        }
    }

    //true تسجيل دخول ك ادمن 
    //false تسجيل دخول ك مستخدم
    private void showLoginDialog(boolean isAdmin) {
        JFrame loginFrame = new JFrame(isAdmin ? "Admin Login" : "User Login");
        loginFrame.setSize(400, 250);
        BackgroundPanel menuPanel = new BackgroundPanel("img\\offer-background.jpg"); // خلفية الواجهة
        menuPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // إعداد قيود التخطيط

        // إعداد العبارة
        // إعداد قيود العبارة
//        JPanel loginPanel = new JPanel();
//        loginPanel.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.GRAY);
        JTextField usernameField = new JTextField(20);//استقبال اسم المستخدم او اسم الادمن
        usernameLabel.setForeground(Color.GRAY);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your name")) {
                    usernameField.setText(""); // إزالة النص
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Enter your name"); // إعادة النص إذا كان الحقل فارغًا
                }
            }
        });
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.GRAY);
        JPasswordField passwordField = new JPasswordField(20);//استقبال كلمة سر المستخدم او كلمة سر المستحدم
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);

        gbc.gridx = 0; // العمود
        gbc.gridy = 1; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(usernameLabel, gbc); // إضافة الزر الإداري

        gbc.gridx = 1; // العمود
        gbc.gridy = 1; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(usernameField, gbc); // إضافة الزر الإداري

        gbc.gridx = 0; // العمود
        gbc.gridy = 2; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(passwordLabel, gbc); // إضافة الزر الإداري

        gbc.gridx = 1; // العمود
        gbc.gridy = 2; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(passwordField, gbc); // إضافة الزر الإداري

        gbc.gridx = 1; // العمود
        gbc.gridy = 3; // الصف
        gbc.insets = new Insets(10, 20, 10, 0); // الهوامش
        menuPanel.add(loginButton, gbc); // إضافة الزر الإداري

        loginButton.addActionListener(e -> {
            //تخزينهم داخل متغيرات
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isAdmin) {
                if ("admin".equals(username) && "admin123".equals(password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Welcome, Admin!");
                    loginFrame.dispose();
                    showAdminPanel();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid Admin Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (checkIfUserExist(AllClientsList, username, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Welcome, User!");
                    loginFrame.dispose();
                    showUserPanel(getClientObject(AllClientsList, username, password));
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid User Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginFrame.add(menuPanel);
        loginFrame.setLocationRelativeTo(frame);
        loginFrame.setVisible(true);
    }

    //واجهة المستخدم بعد تسجيل الدخول
    public void showUserPanel(clients object) {
        for (order i : object.getOrderClientList()) {
            System.out.println(i.toString());
        }
        order temp = new order();
        object.getOrderClientList().add(temp);
          = new JFrame("User Panel");
        userPanel.setSize(600, 400);
        userPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // إعداد قيود التخطيط

        // إعداد زر اختيار الوجبة
        JButton chooseMealButton = new JButton("Choose Meal");
        styleButton(chooseMealButton); // تنسيق الزر
        chooseMealButton.addActionListener(e -> chooseMeal(object)); // إضافة حدث الزر

        // إعداد قيود زر اختيار الوجبة
        gbc.gridx = 0; // العمود
        gbc.gridy = 0; // الصف
        gbc.insets = new Insets(10, 10, 10, 10); // الهوامش
        gbc.fill = GridBagConstraints.HORIZONTAL; // ملء المساحة الأفقية
        userPanel.add(chooseMealButton, gbc); // إضافة الزر إلى اللوحة

        // إعداد زر عرض الطلب
        JButton viewOrderButton = new JButton("View Order");
        styleButton(viewOrderButton); // تنسيق الزر
        viewOrderButton.addActionListener(e -> viewOrder(object)); // إضافة حدث الزر

        // إعداد قيود زر عرض الطلب
        gbc.gridx = 1; // العمود
        gbc.gridy = 0; // الصف
        userPanel.add(viewOrderButton, gbc); // إضافة الزر إلى اللوحة

        // إعداد زر تسجيل الخروج
        JButton closeButton = new JButton("Logout");
        styleButton(closeButton); // تنسيق الزر
        closeButton.addActionListener(e -> userPanel.dispose()); // إضافة حدث الزر

        // إعداد قيود زر تسجيل الخروج
        gbc.gridx = 0; // العمود
        gbc.gridy = 1; // الصف
        gbc.gridwidth = 2; // جعل الزر يمتد عبر عمودين
        userPanel.add(closeButton, gbc); // إضافة الزر إلى اللوحة

        // مركز وإظهار الإطار
        userPanel.setLocationRelativeTo(frame);
        userPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // إغلاق الإطار عند الضغط على X
        userPanel.setVisible(true);
    }

    public void creatAnewOreder() {
    }

    private void chooseMeal(clients object) {

        JFrame mealFrame = new JFrame("Choose a Meal");
        mealFrame.setSize(600, 400);
        mealFrame.setLayout(new BorderLayout(10, 10)); // Adding padding for better spacing

        JPanel mealPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Layout constraints

        int row = 0; // To keep track of the current row

        for (meals meal : dailyMealsList) {
            JPanel mealItemPanel = new JPanel(new GridBagLayout());
            mealItemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Optional borders

            // Image constraints
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5); // Margins
            gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

            JLabel imageLabel = new JLabel(new ImageIcon(meal.getImagePath()));
            imageLabel.setPreferredSize(new Dimension(100, 100)); // Set image size
            mealItemPanel.add(imageLabel, gbc);

            // Meal information
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel mealInfoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            mealInfoPanel.add(new JLabel("Name: " + meal.getMealName()));
            mealInfoPanel.add(new JLabel("Price: " + meal.getSinglMealPrice() + " $"));
            mealInfoPanel.add(new JLabel("Preparation Time: " + meal.getSingleMealPrepTime() + " mins"));

            mealItemPanel.add(mealInfoPanel, gbc);

            // Select button
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.NONE;

            JButton selectButton = new JButton("Select");
            styleSmallButton(selectButton);
            selectButton.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, " How many meals do you want frome " + meal.getMealName() + "?");

                try {
                    int mealCount = Integer.parseInt(input);
                    if (mealCount > 0) {
                        meal.setMealCount(mealCount);
                        object.getOrderClientList().getLast().getMealsClientList().add(meal);
                        refreshClientData(object);
                        saveClientsDataOnFile(AllClientsList, path_clients);
                        JOptionPane.showMessageDialog(frame, meal.getMealName() + " it has been added to your order by number : " + mealCount + " And the order number ID: " + order.getOrderIDCounter());
                    } else {
                        JOptionPane.showMessageDialog(frame, " please enter an active vlaue");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "please enter an active value !");
                }
            });

            mealItemPanel.add(selectButton, gbc);

            // Add meal item to meal panel
            gbc.gridx = 0;
            gbc.gridy = row++;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            mealPanel.add(mealItemPanel, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(mealPanel);
        mealFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        styleSmallButton(closeButton);
        closeButton.addActionListener(e -> {
            refreshClientData(object);
            mealFrame.dispose();
        });

        mealFrame.add(closeButton, BorderLayout.SOUTH);

        mealFrame.setLocationRelativeTo(frame);
        mealFrame.setVisible(true);
    }

    private void viewOrder(clients object) {
        JFrame orderFrame = new JFrame("Your Order");
        orderFrame.setSize(600, 400);
        orderFrame.setLayout(new BorderLayout(10, 10));

        // Create order list
        JList<meals> orderList = new JList<>(object.getOrderClientList().getLast().getMealsClientList().toArray(new meals[0]));
        JScrollPane scrollPane = new JScrollPane(orderList);
        orderFrame.add(scrollPane, BorderLayout.CENTER);

        // Create info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout

        // Add info fields
        infoPanel.add(new JLabel("Meal Type: "));
        JTextField mealTypeField = new JTextField();
        mealTypeField.setEditable(false);
        infoPanel.add(mealTypeField);

        infoPanel.add(new JLabel("Meals Number: "));
        JTextField mealCountField = new JTextField();
        mealCountField.setEditable(false);
        infoPanel.add(mealCountField);

        infoPanel.add(new JLabel("Single Price Meal: "));
        JTextField mealPriceField = new JTextField();
        mealPriceField.setEditable(false);
        infoPanel.add(mealPriceField);

        infoPanel.add(new JLabel("total Price Meal: "));
        JTextField totalMealPriceField = new JTextField();
        totalMealPriceField.setEditable(false);
        infoPanel.add(totalMealPriceField);

        infoPanel.add(new JLabel("Single Meal Preparation Time: "));
        JTextField singleMealPrepTimeField = new JTextField();
        singleMealPrepTimeField.setEditable(false);
        infoPanel.add(singleMealPrepTimeField);

        infoPanel.add(new JLabel("Total Preparation Time: "));
        JTextField totalPrepTimeField = new JTextField();
        totalPrepTimeField.setEditable(false);
        infoPanel.add(totalPrepTimeField);

        /*  infoPanel.add(new JLabel("Date: "));
        JTextField dateField = new JTextField();
        dateField.setEditable(false);
        infoPanel.add(dateField);
         */
        // Add info panel to the frame
        orderFrame.add(infoPanel, BorderLayout.NORTH);

        // Close button
        JButton closeButton = new JButton("Close");
        styleSmallButton(closeButton);
        closeButton.addActionListener(e -> orderFrame.dispose());

        orderFrame.add(closeButton, BorderLayout.SOUTH);

        // Update fields with appropriate data from the selected order item
        orderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                meals selectedMeal = orderList.getSelectedValue();
                if (selectedMeal != null) {
                    updateOrderDetails(selectedMeal, mealTypeField, mealCountField, mealPriceField, singleMealPrepTimeField, totalPrepTimeField, totalMealPriceField);
                }
            }
        });

        orderFrame.setLocationRelativeTo(frame);
        orderFrame.setVisible(true);
    }

    private void viewOrderByItsID(clients object, int ID) {
        JFrame orderFrame = new JFrame("Your Order");
        orderFrame.setSize(600, 400);
        orderFrame.setLayout(new BorderLayout(10, 10));

        // Create order list
        if (checkIfOrderExist(object, ID)) {
            JList<meals> orderList = new JList<>(getClientOrder(object, ID).getMealsClientList().toArray(new meals[0]));
            JScrollPane scrollPane = new JScrollPane(orderList);
            orderFrame.add(scrollPane, BorderLayout.CENTER);

            // Create info panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout

            // Add info fields
            infoPanel.add(new JLabel("Meal Type: "));
            JTextField mealTypeField = new JTextField();
            mealTypeField.setEditable(false);
            infoPanel.add(mealTypeField);

            infoPanel.add(new JLabel("Meals Number: "));
            JTextField mealCountField = new JTextField();
            mealCountField.setEditable(false);
            infoPanel.add(mealCountField);

            infoPanel.add(new JLabel("Single Price Meal: "));
            JTextField mealPriceField = new JTextField();
            mealPriceField.setEditable(false);
            infoPanel.add(mealPriceField);

            infoPanel.add(new JLabel("total Price Meal: "));
            JTextField totalMealPriceField = new JTextField();
            totalMealPriceField.setEditable(false);
            infoPanel.add(totalMealPriceField);

            infoPanel.add(new JLabel("Single Meal Preparation Time: "));
            JTextField singleMealPrepTimeField = new JTextField();
            singleMealPrepTimeField.setEditable(false);
            infoPanel.add(singleMealPrepTimeField);
            orderFrame.add(infoPanel, BorderLayout.NORTH);

            infoPanel.add(new JLabel("Total Preparation Time: "));
            JTextField totalPrepTimeField = new JTextField();
            totalPrepTimeField.setEditable(false);
            infoPanel.add(totalPrepTimeField);
            // Close button
            JButton closeButton = new JButton("Close");
            styleSmallButton(closeButton);
            closeButton.addActionListener(e -> orderFrame.dispose());

            orderFrame.add(closeButton, BorderLayout.SOUTH);

            // Update fields with appropriate data from the selected order item
            orderList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    meals selectedMeal = orderList.getSelectedValue();
                    if (selectedMeal != null) {
                        updateOrderDetails(selectedMeal, mealTypeField, mealCountField, mealPriceField, singleMealPrepTimeField, totalPrepTimeField, totalMealPriceField);
                    }
                }
            });

            orderFrame.setLocationRelativeTo(frame);
            orderFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(orderFrame, "Invalid order ID Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOrderDetails(meals selectedMeal, JTextField mealType, JTextField mealCount, JTextField mealPrice, JTextField singleMealPrepTime, JTextField totalPrepTime, JTextField totalMealPrice) {

        // Update info fields based on the selected meal
        mealType.setText(selectedMeal.getMealName());
        mealCount.setText(String.valueOf(selectedMeal.getMealCount()));
        mealPrice.setText(String.valueOf(selectedMeal.getSinglMealPrice()) + " $");
        totalMealPrice.setText(String.valueOf(selectedMeal.getSinglMealPrice() * selectedMeal.getMealCount()) + " $");
        singleMealPrepTime.setText(String.valueOf(selectedMeal.getSingleMealPrepTime()) + " mins");
        totalPrepTime.setText(String.valueOf(selectedMeal.getSingleMealPrepTime() * selectedMeal.getMealCount()) + " mins");
        //         date.setText(selectedMeal.getOrderDate()); // Assuming getDate() returns the date
    }

    private void styleSmallButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 30)); // Smaller button size
        button.setBackground(Color.decode("#4682B4")); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font size
    }

    public boolean checkIfUserExist(ArrayList<clients> clientList, String insertedUserName, String insertedUserPassword) {
        for (clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(insertedUserName) && i.getUserPasswordAccount().equals(insertedUserPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfOrderExist(clients object, int orderID) {
        for (order i : object.getOrderClientList()) {
            if (i.getOrderID() == orderID) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfMealExist(ArrayList<meals> ListMealForSearching, String mealName) {
        for (meals i : ListMealForSearching) {
            if (i.getMealName().equals(mealName)) {
                return true;
            }
        }
        return false;
    }

    public void refreshClientData(clients newClientsData) {
        for (clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(newClientsData.getUserNameAccount()) && i.getUserPasswordAccount().equals(newClientsData.getUserPasswordAccount())) {
                i = newClientsData;
            }
            saveClientsDataOnFile(AllClientsList, path_clients);
        }
    }

    public clients getClientObject(ArrayList<clients> clientList, String insertedUserName, String insertedUserPassword) {
        for (clients i : AllClientsList) {
            if (i.getUserNameAccount().equals(insertedUserName) && i.getUserPasswordAccount().equals(insertedUserPassword)) {
                return i;
            }
        }
        return null;
    }

    public order getClientOrder(clients object, int orderID) {
        for (order i : object.getOrderClientList()) {
            if (i.getOrderID() == orderID) {
                return i;
            }
        }
        return null;
    }

    public void randomMeals() {

    }

    public void addAnewMeal() {

    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(255, 188, 47));
        button.setForeground(new Color(56, 56, 72));
        button.setOpaque(true);
        button.setBorderPainted(false);//ازالة الborder
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    static class BackgroundPanel extends JPanel {

        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading image: " + imagePath);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    /////////////////////////////////////////////////////////////////BACK////////////////////////////////////////////////////////////////////////////////
    public static void saveClientsDataOnFile(ArrayList<clients> listOfallCustom, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File myFile = new File(path);
            fos = new FileOutputStream(myFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listOfallCustom);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void saveMealsDataOnFile(ArrayList<meals> listOfallCustom, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File myFile = new File(path);
            fos = new FileOutputStream(myFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listOfallCustom);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<clients> getClientsDataFomeFile(String path) {
        ArrayList<clients> myNewArrayList = new ArrayList();
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            File myFile = new File(path);
            fin = new FileInputStream(myFile);
            ois = new ObjectInputStream(fin);
            myNewArrayList = (ArrayList<clients>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myNewArrayList;
    }

    public static ArrayList<meals> getMealsDataFomeFile(String path) {
        ArrayList<meals> myNewArrayList = new ArrayList();
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            File myFile = new File(path);
            fin = new FileInputStream(myFile);
            ois = new ObjectInputStream(fin);
            myNewArrayList = (ArrayList<meals>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(RestaurantOrderManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myNewArrayList;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(RestaurantOrderManagementApp::new);
//
//        meals ob1 = new meals();
//        ob1.setMealName("shawarma");
//        ob1.setSinglMealPrice(50);
//        ob1.setSingleMealPrepTime(5);
//        ob1.setMealCount(1);
//        ob1.setImagePath("shawarma.png");
//        dailyMealsList.add(ob1);
//        saveMealsDataOnFile(dailyMealsList, path_meals);
//        meals ob2 = new meals();
//        ob2.setMealName("pizza");
//        ob2.setSinglMealPrice(30);
//        ob2.setSingleMealPrepTime(2);
//        ob2.setImagePath("pizza.png");
//        dailyMealsList.add(ob2);
//        saveMealsDataOnFile(dailyMealsList, path_meals);
//
//clients obj1=new clients();
//obj1.setUserNameAccount("memo");
//obj1.setUserPasswordAccount("6518475");
//AllClientsList.add(obj1);
//   saveClientsDataOnFile(AllClientsList, path_clients);
////

    }
}
