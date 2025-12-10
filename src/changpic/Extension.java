package changpic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class Extension {

    public static void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(255, 188, 47));
        button.setForeground(new Color(56, 56, 72));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    public static void styleSmallButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 30)); // Smaller button size
        button.setBackground(new Color(255, 188, 47)); // Steel Blue
        button.setForeground(new Color(56, 56, 72));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font size
    }

}
