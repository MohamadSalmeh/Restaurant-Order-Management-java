package changpic;

import javax.swing.*;
import java.awt.*;

import static javax.swing.text.StyleConstants.getBackground;

public class CustomButtonExample {
    static JButton p(JButton button) {
        // إنشاء زر مخصص
     JButton b=new JButton(button.getText()){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // تحديد نصف القطر
                g2.setColor(getForeground());
                g2.drawString(getText(), getWidth() / 8, getHeight() / 2 + 5);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };

        button.setContentAreaFilled(false); // إزالة التعبئة الافتراضية
        button.setFocusPainted(false);     // إزالة تأثير التركيز الافتراضي
        button.setBackground(Color.CYAN);
        button.setForeground(Color.BLACK);
        // إضافة الزر إلى الإطار
        // جعل الإطار مرئياً
        return b;
    }
    
    
    /*            selectButton.addActionListener(e -> {
                ArrayList<meals> ListForSearching = object.getOrderClientList().getLast().getMealsClientList();
                if (checkIfMealExist(ListForSearching, meal.getMealName())) {
                    meal.setMealCount(1);
                } else {
                    object.getOrderClientList().getLast().getMealsClientList().add(meal);
                }
                refreshClientData(object);
                saveClientsDataOnFile(AllClientsList, path_clients);
                JOptionPane.showMessageDialog(frame, meal.getMealName() + " added to your order with id number : " + order.getOrderIDCounter());
            });
*/
}
