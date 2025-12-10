package changpic;

import static changpic.RestaurantOrderManagementApp.PATH_MEALS;
import static changpic.RestaurantOrderManagementApp.adminPanel;
import static changpic.RestaurantOrderManagementApp.frame;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class Admin {
    //عرض واجهة الادمن
    public static void showAdminPanel(ArrayList<Clients> AllClientsList, ArrayList<Meals> dailyMealsList, Restaurant admin) {
        adminPanel = new JFrame("Admin Panel");
        adminPanel.setSize(600, 400);
        adminPanel.setResizable(false);
        adminPanel.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        BackgroundPanel backgroundPanel = new BackgroundPanel("img\\back2.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        backgroundPanel.setIconFrame(adminPanel, "img\\IconProject2.jpg");

        JButton viewUsersButton = new JButton("View Users");
        Extension.styleButton(viewUsersButton);
        viewUsersButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            SwingUtilities.invokeLater(() -> new UserListUI(AllClientsList));
            adminPanel.setVisible(false);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(viewUsersButton, gbc);

        JButton changeMealsButton = new JButton("Developers Info");
        Extension.styleButton(changeMealsButton);
        changeMealsButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            SwingUtilities.invokeLater(DeveloperInfoUI::new);
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        backgroundPanel.add(changeMealsButton, gbc);

        JButton addAnewMealButton = new JButton("New meal");
        Extension.styleButton(addAnewMealButton);
        addAnewMealButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            new MealAdder();
            adminPanel.setVisible(false);
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        backgroundPanel.add(addAnewMealButton, gbc);

        JButton removeMealButton = new JButton("remove meal");
        Extension.styleButton(removeMealButton);
        removeMealButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            Meals.removeAmeal(serializableFile.getMealsFataFomeFile(PATH_MEALS));
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(removeMealButton, gbc);

        JButton refreshMealButton = new JButton("refresh meal data");
        Extension.styleButton(refreshMealButton);
        refreshMealButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));

            Meals.refreshMealData(serializableFile.getMealsFataFomeFile(PATH_MEALS));
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        backgroundPanel.add(refreshMealButton, gbc);

        JButton returantInfoButton = new JButton("Restaurant Archive");
        Extension.styleButton(returantInfoButton);
        returantInfoButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            admin.setRegularClient(admin.getClientNameWhoComesToTheRestaurantAlot(AllClientsList));
            admin.setMostRequestedMeal(admin.getMostRequestedMeal(dailyMealsList));
            adminPanel.setVisible(false);
            new RestaurantArchive(admin);
        });

        gbc.gridx = 0;
        gbc.gridy = 2;

        backgroundPanel.add(returantInfoButton, gbc);

        JButton closeButton = new JButton("Close");
        Extension.styleButton(closeButton);
        closeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            adminPanel.setVisible(false);
            frame.setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        backgroundPanel.add(closeButton, gbc);

        adminPanel.add(backgroundPanel);

        adminPanel.setLocationRelativeTo(null);
        adminPanel.setVisible(true);
    }

}
