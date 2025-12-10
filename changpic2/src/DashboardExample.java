import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sliding Dashboard Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        // إنشاء لوحة التحكم
        JPanel dashboard = new JPanel();
        dashboard.setBackground(Color.LIGHT_GRAY);
        dashboard.setBounds(0, 0, 200, 400);
        frame.add(dashboard);

        // زر التحكم
        JButton toggleButton = new JButton("Toggle Dashboard");
        toggleButton.setBounds(220, 20, 150, 30);
        frame.add(toggleButton);

        // إضافة حدث للزر مع حركة انزلاقية
        toggleButton.addActionListener(new ActionListener() {
            private boolean isOpen = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(5, null);
                timer.addActionListener(new ActionListener() {
                    int x = dashboard.getX();

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (isOpen) {
                            x -= 5; // تحرك للخارج
                            if (x <= -200) { // إخفاء كامل
                                timer.stop();
                                isOpen = false;
                            }
                        } else {
                            x += 5; // تحرك للداخل
                            if (x >= 0) { // إظهار كامل
                                timer.stop();
                                isOpen = true;
                            }
                        }
                        dashboard.setBounds(x, 0, 200, 400);
                    }
                });
                timer.start();
            }
        });

        // جعل الإطار مرئيًا
        frame.setVisible(true);
    }
}
