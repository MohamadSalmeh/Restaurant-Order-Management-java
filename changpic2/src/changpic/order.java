package changpic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.*;

public class order implements Serializable {

    private ArrayList<meals> mealsClientList = new ArrayList<>();
    private float totalOrderPrice = 0;
    private int totalOrderPrepTime = 0;
    private LocalDateTime orderDate = LocalDateTime.now();
    private String typeOfOrder;
    private static int orderIDCounter = 1;
    private final int orderID;

    static {
        loadOrderID();
    }

    public order() {
        this.orderID = orderIDCounter++;
        saveOrderID();
        calculateOrderDetails();
    }

    private void calculateOrderDetails() {
        for (meals meal : mealsClientList) {
            totalOrderPrice += meal.getSinglMealPrice();
            totalOrderPrepTime += meal.getSingleMealPrepTime();
        }
    }

    public ArrayList<meals> getMealsClientList() {
        return mealsClientList;
    }

    public void setMealsClientList(ArrayList<meals> mealsClientList) {
        this.mealsClientList = mealsClientList;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public int getOrderID() {
        return orderID;
    }

    public static int getOrderIDCounter() {
        return orderIDCounter-1;
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

}
