package changpic;

import java.awt.Graphics;
import java.awt.Image;
import java.util.jar.JarFile;
import javax.swing.*;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;
private static ImageIcon iconFrame;
   public BackgroundPanel(){}
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

    public static void setIconFrame(JFrame frame,String path){
        iconFrame=new ImageIcon(path);
        frame.setIconImage(iconFrame.getImage());
    }
}
