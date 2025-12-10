package changpic;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderViewUI {

    public static void viewOrder(order myOrder) {
        JFrame orderFrame = new JFrame("Your Order");
        orderFrame.setResizable(false);
        orderFrame.setSize(600, 700);
        orderFrame.setLayout(new BorderLayout(10, 10));
        orderFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        orderFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("path_to_icon.png"));
        orderFrame.setLocation(700, -5);
        Font font = new Font("Arial", Font.PLAIN, 14);

        ArrayList<Meals> mealsList;
        order order1;
        order1 = myOrder;
        mealsList = order1.getMealsClientList();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        orderFrame.add(scrollPane, BorderLayout.CENTER);

        mainPanel.setBackground(new Color(240, 240, 240));

        for (Meals meal : mealsList) {
            JPanel mealPanel = new JPanel();
            mealPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            mealPanel.setLayout(new BorderLayout(10, 10));
            Dimension preferredSize = new Dimension(580, 150);
            mealPanel.setPreferredSize(preferredSize);
            mealPanel.setMaximumSize(preferredSize);
            mealPanel.setMinimumSize(preferredSize);

            BackgroundPanel photoPanel = new BackgroundPanel(meal.getImagePath());
            photoPanel.setPreferredSize(new Dimension(150, 150));
            mealPanel.add(photoPanel, BorderLayout.WEST);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(4, 1));
            infoPanel.setBackground(new Color(240, 240, 240));

            JLabel nameLabel = new JLabel("Meal Name: " + meal.getMealName());
            JLabel countLabel = new JLabel("Number of Meals: " + meal.getMealCount());
            JLabel priceLabel = new JLabel("Price per Meal: " + meal.getSinglMealPrice() + " $");
            JLabel prepTimeLabel = new JLabel("Preparation Time: " + meal.getSingleMealPrepTime() + " mins");

            nameLabel.setFont(font);
            countLabel.setFont(font);
            priceLabel.setFont(font);
            prepTimeLabel.setFont(font);

            infoPanel.add(nameLabel);
            infoPanel.add(countLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(prepTimeLabel);

            mealPanel.add(infoPanel, BorderLayout.CENTER);
            mainPanel.add(mealPanel);
        }

        JPanel orderDetailsPanel = new JPanel(new GridLayout(6, 1));
        orderDetailsPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        orderDetailsPanel.setPreferredSize(new Dimension(580, 150));
        orderDetailsPanel.setMaximumSize(new Dimension(580, 150));
        orderDetailsPanel.setMinimumSize(new Dimension(580, 150));
        orderDetailsPanel.setBackground(new Color(220, 220, 220));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = order1.getOrderDate().format(formatter);

        JLabel orderNumberLabel = new JLabel("Order Number: " + order1.getOrderIDCounter());
        JLabel orderDateLabel = new JLabel("Order Date: " + formattedDateTime);
        JLabel orderStatusLabel = new JLabel("Order Status: " + order1.getOrderStatus());
        JLabel orderTypeLabel = new JLabel("Order Type: " + order1.getTypeOfOrder());
        JLabel totalPriceLabel = new JLabel("Total Order Price: " + calculateTotalPrice(mealsList) + " $");
        JLabel totalPrepTimeLabel = new JLabel("Total Preparation Time: " + calculateTotalPrepTime(mealsList) + " mins");

        orderNumberLabel.setFont(font);
        orderDateLabel.setFont(font);
        orderStatusLabel.setFont(font);
        orderTypeLabel.setFont(font);
        totalPriceLabel.setFont(font);
        totalPrepTimeLabel.setFont(font);

        orderDetailsPanel.add(orderNumberLabel);
        orderDetailsPanel.add(orderDateLabel);
        orderDetailsPanel.add(orderStatusLabel);
        orderDetailsPanel.add(orderTypeLabel);
        orderDetailsPanel.add(totalPriceLabel);
        orderDetailsPanel.add(totalPrepTimeLabel);

        orderFrame.add(orderDetailsPanel, BorderLayout.NORTH);

        JButton closeButton = new JButton("Close");
        Extension.styleButton(closeButton);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            orderFrame.dispose();
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        buttonPanel.setBackground(new Color(240, 240, 240));

        orderFrame.add(buttonPanel, BorderLayout.SOUTH);

    //    orderFrame.setLocationRelativeTo(null);

        orderFrame.setVisible(true);
    }

    private static double calculateTotalPrice(ArrayList<Meals> mealsList) {
        double totalPrice = 0.0;
        for (Meals meal : mealsList) {
            totalPrice += meal.getSinglMealPrice() * meal.getMealCount();
        }
        return totalPrice;
    }

    private static int calculateTotalPrepTime(ArrayList<Meals> mealsList) {
        int totalPrepTime = 0;
        for (Meals meal : mealsList) {
            totalPrepTime += meal.getSingleMealPrepTime() * meal.getMealCount();
        }
        return totalPrepTime;
    }
}
