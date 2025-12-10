package changpic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class MealAdder extends JFrame {

    private ArrayList<Meals> dailyMealsList = serializableFile.getMealsFataFomeFile(RestaurantOrderManagementApp.PATH_MEALS);
    private JTextField mealNameField;
    private JTextField mealPriceField;
    private JTextField mealPrepTimeField;
    private JTextArea mealDescriptionField;
    private JTextField imagePathField;

    public MealAdder() {
        this.setTitle("Add a New Meal");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        BackgroundPanel backgroundPanel = new BackgroundPanel("img\\offer-background.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        BackgroundPanel.setIconFrame(this, "img\\IconProject2.jpg");

        JLabel nameLabel = new JLabel("Meal Name:");
        mealNameField = new JTextField(20);
        JLabel priceLabel = new JLabel("Meal Price:");
        mealPriceField = new JTextField(20);
        JLabel prepTimeLabel = new JLabel("Preparation Time (min):");
        mealPrepTimeField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Meal Description:");
        mealDescriptionField = new JTextArea(3, 20);
        mealDescriptionField.setLineWrap(true);
        mealDescriptionField.setWrapStyleWord(true);
        JLabel imagePathLabel = new JLabel("Meal Image Path:");
        imagePathField = new JTextField(15);
        JButton browseButton = new JButton("Browse");

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    imagePathField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        JButton addButton = new JButton("Add Meal");
        styleButton(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
                addAnewMeal();
                RestaurantOrderManagementApp.adminPanel.setVisible(true);
            }
        });

        JButton closeNewMealButton = new JButton("Close");
        styleButton(closeNewMealButton);
        closeNewMealButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            this.setVisible(false);
            RestaurantOrderManagementApp.adminPanel.setVisible(true);
        });

        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        prepTimeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        imagePathLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        priceLabel.setForeground(Color.GRAY);
        prepTimeLabel.setForeground(Color.GRAY);
        nameLabel.setForeground(Color.GRAY);
        descriptionLabel.setForeground(Color.GRAY);
        imagePathLabel.setForeground(Color.GRAY);

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(mealNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(priceLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(mealPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        backgroundPanel.add(prepTimeLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(mealPrepTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        backgroundPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(new JScrollPane(mealDescriptionField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        backgroundPanel.add(imagePathLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        backgroundPanel.add(imagePathField, gbc);
        gbc.gridx = 2;
        backgroundPanel.add(browseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        backgroundPanel.add(closeNewMealButton, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(addButton, gbc);

        this.add(backgroundPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(255, 188, 47));
        button.setForeground(new Color(56, 56, 72));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    public void addAnewMeal() {
        Meals ob1 = new Meals();

        try {
            ob1.setMealName(mealNameField.getText());
            ob1.setSinglMealPrice(Float.parseFloat(mealPriceField.getText()));
            ob1.setSingleMealPrepTime(Integer.parseInt(mealPrepTimeField.getText()));
            ob1.setMealDetails(mealDescriptionField.getText());
            ob1.setImagePath(imagePathField.getText());
            dailyMealsList.add(ob1);

            serializableFile.saveMealsDataOnFile(dailyMealsList, RestaurantOrderManagementApp.PATH_MEALS);

            JOptionPane.showMessageDialog(this, "Meal added successfully!");

            mealNameField.setText("");
            mealPriceField.setText("");
            mealPrepTimeField.setText("");
            mealDescriptionField.setText("");
            imagePathField.setText("");
            this.setVisible(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for price and preparation time, etc.");
        }
    }
}
