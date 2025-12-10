package changpic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {

    private int numberOfDailyRequested;//
    private String mostRequestedMeal;//
    private static double FinancialRetuns=0;
    private double dailyFinancialRetuns = 0;//
    private String regularClient;

    static{
        loadFinancialRetuns();
    }
    
    public Restaurant(){
        saveFinancialRetuns();
    }
    
    public int getNumberOfDailyRequested() {
        return numberOfDailyRequested;
    }

    public void setNumberOfDailyRequested(int numberOfDailyRequested) {
        this.numberOfDailyRequested = numberOfDailyRequested;
    }

    public String getMostRequestedMeal() {
        return mostRequestedMeal;
    }

    public void setMostRequestedMeal(String mostRequestedMeal) {
        this.mostRequestedMeal = mostRequestedMeal;
    }

    public double getFinancialRetuns() {
        return FinancialRetuns;
    }

    public void setFinancialRetuns(double FinancialRetuns) {
        this.FinancialRetuns += FinancialRetuns;
    }

    public double getDailyFinancialRetuns() {
        return dailyFinancialRetuns;
    }

    public void setDailyFinancialRetuns(double dailyFinancialRetuns) {
        this.dailyFinancialRetuns += dailyFinancialRetuns;
        this.setFinancialRetuns(dailyFinancialRetuns);
    }

    public String getRegularClient() {
        return regularClient;
    }

    public void setRegularClient(String regularClient) {
        this.regularClient = regularClient;
    }

    //////////////////////////////////////////////////////admin method/////////////////////////////////////////////////////////////
    public String getMostRequestedMeal(ArrayList<Meals> mealListForSearching) {
        Meals mealMostReauested = mealListForSearching.getFirst();
        for (Meals i : mealListForSearching) {
            if (mealMostReauested.getRequestedMeal() < i.getRequestedMeal()) {
                mealMostReauested = i;
            }
        }
        return mealMostReauested.getMealName();
    }

    public String getClientNameWhoComesToTheRestaurantAlot(ArrayList<Clients> ClientListForSearching) {
   Clients mostClientComing = ClientListForSearching.getFirst();
        for (Clients i : ClientListForSearching) {
            if (mostClientComing.getNumberOfVisits() < i.getNumberOfVisits()) {
                mostClientComing = i;
            }
        }
        return mostClientComing.getUserNameAccount();
    }

    public static void loadFinancialRetuns() {
        try (BufferedReader reader = new BufferedReader(new FileReader("FinancialRetunsToTheChillRestaurant.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                FinancialRetuns = Double.parseDouble(line);
            } else {
                FinancialRetuns = 0.0;
            }
        } catch (IOException e) {
            FinancialRetuns = 0.0;
        }
    }

    public static void saveFinancialRetuns() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FinancialRetunsToTheChillRestaurant.txt"))) {
            writer.write(String.valueOf(FinancialRetuns));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
