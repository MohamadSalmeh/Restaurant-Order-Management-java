package changpic;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RestaurantArchive extends JFrame {

    private final Restaurant admin;

    public RestaurantArchive(Restaurant admin) {
        this.admin = admin;

        setupFrame();
        BackgroundPanel.setIconFrame(this, "img\\IconProject2.jpg");
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setupFrame() {
        setTitle("Restaurant Archive");
        setResizable(false);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    public JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 188, 47));
        headerPanel.setPreferredSize(new Dimension(600, 60));

        JLabel headerLabel = new JLabel("Restaurant Archive");
        headerLabel.setForeground(new Color(56, 56, 72));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerPanel.add(headerLabel);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        String[] columnNames = {"Description", "Value"};
        Object[][] data = {
            {"Number of Daily Requested", admin.getNumberOfDailyRequested()},
            {"Most Requested Meal", admin.getMostRequestedMeal()},
            {"Financial Returns", admin.getFinancialRetuns()},
            {"Daily Financial Returns", admin.getDailyFinancialRetuns()},
            {"Regular Client", admin.getRegularClient()}
        };

        JTable table = new JTable(data, columnNames);

        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(255, 188, 47));
        table.getTableHeader().setForeground(new Color(56, 56, 72));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    public JButton createFooterPanel() {
        JButton buttonCloseArchive = new JButton("Close");
        buttonCloseArchive.setBackground(new Color(255, 188, 47));
        buttonCloseArchive.setForeground(new Color(56, 56, 72));
        buttonCloseArchive.setFont(new Font("Arial", Font.BOLD, 16));
        buttonCloseArchive.addActionListener(e -> {
            this.setVisible(false);
            RestaurantOrderManagementApp.adminPanel.setVisible(true);
        });
        return buttonCloseArchive;
    }

    public void updateData(int numberOfDailyRequested, String mostRequestedMeal, float financialReturns, float dailyFinancialReturns, String regularClient) {
        admin.setNumberOfDailyRequested(numberOfDailyRequested);
        admin.setMostRequestedMeal(mostRequestedMeal);
        admin.setFinancialRetuns((double) financialReturns);
        admin.setDailyFinancialRetuns((double) dailyFinancialReturns);
        admin.setRegularClient(regularClient);
        repaint();
    }
}
