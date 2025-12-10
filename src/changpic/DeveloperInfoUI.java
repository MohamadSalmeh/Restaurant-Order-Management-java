package changpic;

import javax.swing.*;
import java.awt.*;

public class DeveloperInfoUI {

    public DeveloperInfoUI() {
        showDevelopersInfo();
    }

    private void showDevelopersInfo() {
        JFrame developersFrame = new JFrame("Developers Information");
        developersFrame.setResizable(false);
        developersFrame.setSize(600, 500);
        developersFrame.setLayout(new BorderLayout(10, 10));
        developersFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        developersFrame.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        developersFrame.add(scrollPane, BorderLayout.CENTER);

        String[][] developersData = {
            {"memo.jpg", "Mohamad Salmeh", "Backend Developer", "salmeh2005@gmail.com"},
            {"moussaPic.jpg", "Moussa Alsgehir", "Swing/Awt Designer", "moussaasd56@gmail.com"},
            {"zain.jpg", "Zain Nehlawy", "Backend Developer", "zain@gmail.com"}
        };

        for (String[] developer : developersData) {
            JPanel developerPanel = new JPanel();
            developerPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2)); 
            developerPanel.setLayout(new BorderLayout(10, 10));
            developerPanel.setMaximumSize(new Dimension(580, 150));
            developerPanel.setBackground(Color.WHITE); 

            BackgroundPanel photoPanel = new BackgroundPanel(developer[0]);
            photoPanel.setPreferredSize(new Dimension(150, 120));
            developerPanel.add(photoPanel, BorderLayout.WEST);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(4, 1));
            infoPanel.setBackground(Color.WHITE); 
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

            JLabel nameLabel = new JLabel("Name: " + developer[1]);
            JLabel roleLabel = new JLabel("Role: " + developer[2]);
            JLabel emailLabel = new JLabel("Email: " + developer[3]);

            Font labelFont = new Font("Segoe UI", Font.BOLD, 14); 
            nameLabel.setFont(labelFont);
            roleLabel.setFont(labelFont);
            emailLabel.setFont(labelFont);

            infoPanel.add(nameLabel);
            infoPanel.add(roleLabel);
            infoPanel.add(emailLabel);

            developerPanel.add(infoPanel, BorderLayout.CENTER);
            mainPanel.add(developerPanel);
        }

        JButton closeButton = new JButton("Close");
        Extension.styleButton(closeButton);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            developersFrame.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245)); 
        buttonPanel.add(closeButton);

        
        mainPanel.add(buttonPanel); 

        developersFrame.add(mainPanel, BorderLayout.CENTER); 
        developersFrame.setLocationRelativeTo(null);
        developersFrame.setVisible(true);
    }
}
