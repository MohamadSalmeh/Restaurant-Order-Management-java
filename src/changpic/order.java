package changpic;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import static changpic.RestaurantOrderManagementApp.*;
import javax.swing.SwingUtilities;

public class order implements Serializable {

    private ArrayList<Meals> MealsClientList = new ArrayList<>();
    private float totalOrderPrice = 0;
    private int totalOrderPrepTime = 0;
    private static LocalDateTime orderDate = LocalDateTime.now();
    private String typeOfOrder;
    private static int orderIDCounter = 1;
    private final int orderID;
    private String orderStatus;

    static {
        loadOrderID();
    }

    public order() {
        this.orderID = orderIDCounter++;
        saveOrderID();
        calculateOrderDetails();
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private void calculateOrderDetails() {
        for (Meals meal : MealsClientList) {
            totalOrderPrice += meal.getSinglMealPrice();
            totalOrderPrepTime += meal.getSingleMealPrepTime();
        }
    }

    public ArrayList<Meals> getMealsClientList() {
        return MealsClientList;
    }

    public void setMealsClientList(ArrayList<Meals> MealsClientList) {
        this.MealsClientList = MealsClientList;
        calculateOrderDetails();
    }

    public float getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(float totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public int getTotalOrderPrepTime() {
        return totalOrderPrepTime;
    }

    public static LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public  void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public int getOrderID() {
        return orderID;
    }

    public static int getOrderIDCounter() {
        return orderIDCounter - 1;
    }

    public static void loadOrderID() {
        try (BufferedReader reader = new BufferedReader(new FileReader("orderID.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                orderIDCounter = Integer.parseInt(line);
            } else {
                orderIDCounter = 1;
            }
        } catch (IOException e) {
            orderIDCounter = 1;
        }
    }

    public static void saveOrderID() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orderID.txt"))) {
            writer.write(String.valueOf(orderIDCounter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fill(boolean isCenal) {
        int count = 0;
        progressBar.setString("Preparing..");
        while (count <= 100 && isCenal) {
            try {
                Thread.sleep(100);
                progressBar.setValue(count);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
        progressBar.setString("Delivered");
    }

    public static void viewOrderByItsID(Clients object, int ID, boolean withIdOrNot) {
        JFrame orderFrame = new JFrame("Your Order");
        orderFrame.setSize(600, 400);
        orderFrame.setLayout(new BorderLayout(10, 10));
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        orderFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("path_to_icon.png")); //icon
        icon.setIconFrame(orderFrame, "img\\IconProject.jpg");
        ArrayList<Meals> mealsList;
        if (withIdOrNot) {
            if (checkIfOrderExist(object, ID)) {
                mealsList = Clients.getClientOrder(object, ID).getMealsClientList();
            } else {
                JOptionPane.showMessageDialog(orderFrame, "Invalid order ID Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                orderFrame.dispose();
                return;
            }
        } else {
            mealsList = object.getOrderClientList().getLast().getMealsClientList();
        }

        JList<Meals> orderList = new JList<>(mealsList.toArray(new Meals[0]));
        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Meals List"));
        orderFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Meal Details"));

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

        infoPanel.add(new JLabel("Total Price Meal: "));
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

        orderFrame.add(infoPanel, BorderLayout.NORTH);

        JButton ButtonClose = new JButton("Close");
        Extension.styleButton(ButtonClose);
        ButtonClose.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            if (withIdOrNot) {
                orderFrame.setVisible(false);
                // adminPanel.setVisible(true);
            } else {
                orderFrame.setVisible(false);
                userPanel.setVisible(true);
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(0, 2, 10, 10));
//        getOrderDate()
        JTextField orderField = new JTextField(20);
        orderField.setText("Number Order :" + String.valueOf(order.getOrderIDCounter()));
        orderField.setFont(new Font("Arial", Font.BOLD, 20));
        orderField.setEditable(false);
        JPanel PanelSmall = new JPanel(new GridLayout(2, 0));
        PanelSmall.add(orderField);
        JTextField dataField = new JTextField(20);
        dataField.setEditable(false);
        dataField.setText("Data order :" + String.valueOf(order.getOrderDate().toLocalDate().now()));
        dataField.setFont(new Font("Arial", Font.BOLD, 20));
        PanelSmall.add(dataField);
        southPanel.add(PanelSmall);
        southPanel.add(ButtonClose);

        orderFrame.add(southPanel, BorderLayout.SOUTH);

        orderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Meals selectedMeal = orderList.getSelectedValue();
                if (selectedMeal != null) {
                    updateOrderDetails(selectedMeal, mealTypeField, mealCountField,
                            mealPriceField, singleMealPrepTimeField, totalPrepTimeField, totalMealPriceField);
                }
            }
        });

        orderFrame.setLocationRelativeTo(null);
        orderFrame.setVisible(true);
    }

    private static void updateOrderDetails(Meals selectedMeal, JTextField mealType, JTextField mealCount,
            JTextField mealPrice, JTextField singleMealPrepTime, JTextField totalPrepTime, JTextField totalMealPrice) {

        mealType.setText(selectedMeal.getMealName());
        mealCount.setText(String.valueOf(selectedMeal.getMealCount()));
        mealPrice.setText(String.valueOf(selectedMeal.getSinglMealPrice()) + " $");
        totalMealPrice.setText(String.valueOf(selectedMeal.getSinglMealPrice() * selectedMeal.getMealCount()) + " $");
        singleMealPrepTime.setText(String.valueOf(selectedMeal.getSingleMealPrepTime()) + " mins");
        totalPrepTime.setText(String.valueOf(selectedMeal.getSingleMealPrepTime() * selectedMeal.getMealCount()) + " mins");
    }

    public static boolean checkIfOrderExist(Clients object, int orderID) {
        for (order i : object.getOrderClientList()) {
            if (i.getOrderID() == orderID) {
                return true;
            }
        }
        return false;
    }

}
