package changpic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import static changpic.RestaurantOrderManagementApp.*;
import java.time.LocalDateTime;
import javax.swing.SwingUtilities;

public class Meals extends order implements Serializable {

    private String mealName;
    private float singlMealPrice;
    private int SingleMealPrepTime;
    private int mealCount = 0;
    private String imagePath;
    private int RequestedMeal;
    private String mealDetails;

    public Meals() {
        super.setTotalOrderPrice(singlMealPrice);
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getSinglMealPrice() {
        return singlMealPrice;
    }

    public void setSinglMealPrice(float singlMealPrice) {
        this.singlMealPrice = singlMealPrice;
    }

    public int getSingleMealPrepTime() {
        return SingleMealPrepTime;
    }

    public void setSingleMealPrepTime(int SingleMealPrepTime) {
        this.SingleMealPrepTime = SingleMealPrepTime;
    }

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRequestedMeal() {
        return RequestedMeal;
    }

    public void setRequestedMeal(int RequestedMeal) {
        this.RequestedMeal += RequestedMeal;
    }

    public String getMealDetails() {
        return mealDetails;
    }

    public void setMealDetails(String mealDetails) {
        this.mealDetails = mealDetails;
    }

    @Override
    public String toString() {
        return " Meal Name : " + mealName + "";
    }

    public static void removeAmeal(ArrayList<Meals> dailyMealsList1) {
        String meal_remov_name = JOptionPane.showInputDialog(null, "enter meal name that you want to remove it from your menu:",
                "remove meal", JOptionPane.QUESTION_MESSAGE);

        if (meal_remov_name != null && !meal_remov_name.trim().isEmpty()) {

            boolean removed = dailyMealsList1.removeIf(i -> i.getMealName().equals(meal_remov_name.trim()));
            if (removed) {
                JOptionPane.showMessageDialog(null, meal_remov_name + " has been successfully removed from the menu ",
                        "successfully", JOptionPane.INFORMATION_MESSAGE);
                serializableFile.saveMealsDataOnFile(dailyMealsList1, PATH_MEALS);
            } else {
                JOptionPane.showMessageDialog(null, "the meal to be deleted was not found :( " + meal_remov_name, "error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "the operation was cancelled successfully.", "warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean checkIfMealExist(ArrayList<Meals> dailyMealsList, String mealName) {
        for (Meals i : dailyMealsList) {
            if (i.getMealName().equals(mealName)) {
                return true;
            }
        }
        return false;
    }

    public static Meals getMealObject(ArrayList<Meals> dailyMealsList, String mealName) {
        for (Meals i : dailyMealsList) {
            if (i.getMealName().equals(mealName)) {
                return i;
            }
        }
        return null;
    }

    public static void refreshMealData(ArrayList<Meals> dailyMealsList) {
        boolean checkIfDataHasBeenRefreshed = false;
        Meals meal = null;
        String mealNameForRefresh = JOptionPane.showInputDialog(null, "Enter the meal name that you want to refresh:", "Refresh Meal", JOptionPane.QUESTION_MESSAGE);

        if (mealNameForRefresh != null && !mealNameForRefresh.trim().isEmpty()) {
            if (checkIfMealExist(dailyMealsList, mealNameForRefresh)) {
                meal = getMealObject(dailyMealsList, mealNameForRefresh);

                String newMealName = JOptionPane.showInputDialog(null, "Enter the new meal name (leave blank to keep current):");
                if (newMealName != null && !newMealName.trim().isEmpty()) {
                    meal.setMealName(newMealName);
                    checkIfDataHasBeenRefreshed = true;
                }

                String newMealPrice = JOptionPane.showInputDialog(null, "Enter the new meal price (leave blank to keep current):");
                if (newMealPrice != null && !newMealPrice.trim().isEmpty()) {
                    try {
                        meal.setSinglMealPrice(Float.parseFloat(newMealPrice));
                        checkIfDataHasBeenRefreshed = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                String newMealPrepTime = JOptionPane.showInputDialog(null, "Enter the new meal preparation time (leave blank to keep current):");
                if (newMealPrepTime != null && !newMealPrepTime.trim().isEmpty()) {
                    try {
                        meal.setSingleMealPrepTime(Integer.parseInt(newMealPrepTime));
                        checkIfDataHasBeenRefreshed = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid preparation time format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "The meal '" + mealNameForRefresh + "' was not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid meal name!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (checkIfDataHasBeenRefreshed) {
            refreshMealsData(dailyMealsList, meal);
            JOptionPane.showMessageDialog(null, "The meal information has been changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void refreshMealsData(ArrayList<Meals> dailyMealsList, Meals newMealsData) {
        for (Meals i : dailyMealsList) {
            if (i.getMealName().equalsIgnoreCase(newMealsData.getMealName())) {
                i = newMealsData;
                serializableFile.saveMealsDataOnFile(dailyMealsList, PATH_MEALS);
                return;
            }
        }
    }

    public static void showDetailsMeal(Meals meal) {
        JFrame frameDetails = new JFrame("Meal Details");
        frameDetails.setSize(350, 250);
        frameDetails.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDetails.setLocationRelativeTo(null);
        frameDetails.setResizable(false);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel descriptionLabel = new JLabel("<html>Description: " + meal.getMealDetails() + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
        descriptionLabel.setForeground(new Color(0, 0, 0));

        panel.add(descriptionLabel, gbc);

        frameDetails.add(panel);
        frameDetails.setVisible(true);
    }

    public static void chooseMeal(Clients object, ArrayList<Meals> dailyMealsList, ArrayList<Clients> AllClientsList, Restaurant admin) {
        order tempObject = new order();
        object.getOrderClientList().add(tempObject);

        object.getOrderClientList().getLast().setOrderDate(LocalDateTime.now());
        dailyMealsList = serializableFile.getMealsFataFomeFile(PATH_MEALS);
        JFrame mealFrame = new JFrame("Choose a Meal");
        mealFrame.setResizable(false);
        mealFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mealFrame.setSize(600, 700);
        mealFrame.setLayout(new BorderLayout(10, 10));
        mealFrame.setLocation(100, -5);
        String[] arrayType = {"Private", "In Restaurant", "Delivery"};
        comboBox = new JComboBox(arrayType);
        object.getOrderClientList().getLast().setTypeOfOrder((String) "Private");

        comboBox.addActionListener(e -> {
            object.getOrderClientList().getLast().setTypeOfOrder((String) comboBox.getSelectedItem());

        });

        icon.setIconFrame(mealFrame, "img\\IconProject2.jpg");
        JPanel mealPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int row = 0;

        for (Meals meal : dailyMealsList) {
            JPanel mealItemPanel = new JPanel(new GridBagLayout());
            mealItemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel imageLabel = new JLabel(new ImageIcon(meal.getImagePath()));
            imageLabel.setPreferredSize(new Dimension(160, 130));
            mealItemPanel.add(imageLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel mealInfoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            mealInfoPanel.add(new JLabel("Name: " + meal.getMealName()));
            mealInfoPanel.add(new JLabel("Price: " + meal.getSinglMealPrice() + " $"));
            mealInfoPanel.add(new JLabel("Preparation Time: " + meal.getSingleMealPrepTime() + " mins"));

            mealItemPanel.add(mealInfoPanel, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.NONE;

            JButton selectButton = new JButton("Select");
            Extension.styleSmallButton(selectButton);
            JPanel panelButton = new JPanel(new GridLayout(2, 1, 5, 5));
            panelButton.add(selectButton);
            JButton buttonDetails = new JButton("Details");
            Extension.styleSmallButton(buttonDetails);
            panelButton.add(buttonDetails);
            buttonDetails.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
                Meals.showDetailsMeal(meal);
            });

            selectButton.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
                meal.setRequestedMeal(1);
                String input = JOptionPane.showInputDialog(frame, " How many Meals do you want frome " + meal.getMealName() + "?");

                try {
                    int mealCount = Integer.parseInt(input);
                    if (mealCount > 0) {
                        if (Clients.checkIfClientHasThisMealAlready(object.getOrderClientList().getLast().getMealsClientList(), meal.getMealName())) {
                            int temp = Clients.getMealObjectFormClientList(object.getOrderClientList().getLast().getMealsClientList(), meal.getMealName()).getMealCount();
                            Clients.getMealObjectFormClientList(object.getOrderClientList().getLast().getMealsClientList(), meal.getMealName()).setMealCount(temp + mealCount);
                        } else {
                            meal.setMealCount(mealCount);
                            object.getOrderClientList().getLast().getMealsClientList().add(meal);
                        }

                        JOptionPane.showMessageDialog(frame, meal.getMealName() + " it has been added to your order by number : " + mealCount
                                + " And the order number ID: " + order.getOrderIDCounter());
                    } else {
                        JOptionPane.showMessageDialog(frame, " please enter an active vlaue");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "please enter an active value !");
                }
            });

            mealItemPanel.add(panelButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            mealPanel.add(mealItemPanel, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(mealPanel);
        mealFrame.add(scrollPane, BorderLayout.CENTER);

        JButton payButton = new JButton("pay here !");
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(150, 30));
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setBackground(Color.BLACK);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.BLUE);
        Extension.styleSmallButton(payButton);
        JPanel panelPay = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPay.add(progressBar, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelPay.add(payButton, gbc);

        JButton buttonCanel = new JButton("Cancel");
        Extension.styleSmallButton(buttonCanel);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panelPay.add(buttonCanel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;

        panelPay.add(comboBox, gbc);
        buttonCanel.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            object.getOrderClientList().removeLast();
            object.getOrderClientList().getLast().setOrderStatus("Cancel");
            JOptionPane.showMessageDialog(null, "Success Cancel Order!");
            mealFrame.setVisible(false);
            userPanel.setVisible(true);
        });
        payButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            double totalAmount = 0;
            for (Meals meal : object.getOrderClientList().getLast().getMealsClientList()) {
                totalAmount += meal.getSinglMealPrice() * meal.getMealCount();
            }

            String discountCode = JOptionPane.showInputDialog(mealFrame, "Your total amount is: " + totalAmount + " $. Please enter the discount code (if any):");

            double discountAmount = 0;
            if (discountCode != null && !discountCode.trim().isEmpty()) {
                if (discountCode.equals("DISCOUNT10%")) {
                    discountAmount = totalAmount * 0.10;
                    JOptionPane.showMessageDialog(mealFrame, "Discount applied: " + discountAmount + "$");
                } else if (discountCode.equals("PRODISCOUNT20%")) {
                    discountAmount = totalAmount * 0.20;
                    JOptionPane.showMessageDialog(mealFrame, "Discount applied: " + discountAmount + "$");
                } else {
                    JOptionPane.showMessageDialog(mealFrame, "Invalid discount code.");
                }
            }
            String paymentInput;
            double finalAmount = totalAmount - discountAmount;
            if (object.getOrderClientList().getLast().getTypeOfOrder().equals("Delivery") || object.getOrderClientList().getLast().getTypeOfOrder().equals("In Restaurant")) {
                paymentInput = JOptionPane.showInputDialog(mealFrame, "Your final amount after discount is: " + finalAmount + " $ + 1 $ Services. Please enter the payment amount:");
                finalAmount += 1;
            } else {
                paymentInput = JOptionPane.showInputDialog(mealFrame, "Your final amount after discount is: " + finalAmount + " $. Please enter the payment amount:");
            }
            double paymentAmount = 0;
            try {
                paymentAmount = Double.parseDouble(paymentInput);
            } catch (java.lang.NullPointerException ex) {
                JOptionPane.showMessageDialog(mealFrame, "the operation was canceled succssfully.");
            } catch (java.lang.NumberFormatException ex) {
            }

            try {
                if (paymentAmount >= finalAmount && paymentAmount != 0) {
                    SwingUtilities.invokeLater(() -> new MusicPlayer("cashSound"));
                    JOptionPane.showMessageDialog(mealFrame, "Thank you for your payment! Your order has been placed.");
                    admin.setDailyFinancialRetuns(finalAmount);
                    admin.setNumberOfDailyRequested(1);
                    object.setNumberOfVisits(1);
                    Clients.refreshClientData(AllClientsList, object);
                    threadSleep threadSleep = new threadSleep();
                    Thread thread = new Thread(threadSleep);
                    thread.start();
                    object.getOrderClientList().getLast().setOrderStatus("Delivered");
                    serializableFile.saveClientsDataOnFile(AllClientsList, PATH_CLIENTS);
                    OrderViewUI.viewOrder(object.getOrderClientList().getLast());
                } else {
                    JOptionPane.showMessageDialog(mealFrame, "Insufficient payment. Please try again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mealFrame, "Please enter a valid payment amount!");
            } catch (NullPointerException ex) {
            }
        });

        mealFrame.add(panelPay, BorderLayout.NORTH);

        JButton closeButton = new JButton("Close");
        Extension.styleButton(closeButton);
        closeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            mealFrame.setVisible(false);
            userPanel.setVisible(true);
        });
        mealFrame.add(closeButton, BorderLayout.SOUTH);
        //   mealFrame.setLocationRelativeTo(null);
        mealFrame.setVisible(true);

    }

}
