package changpic;

import static changpic.RestaurantOrderManagementApp.adminPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class UserListUI {

    private JFrame usersFrame;

    public UserListUI(ArrayList<Clients> allClientsList) {
        usersFrame = new JFrame("Registered Users");
        usersFrame.setSize(600, 400);
        usersFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        usersFrame.setLayout(new BorderLayout());
        usersFrame.setResizable(false);
        String[] columnNames = {"Username", "Visit Count", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable usersTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        for (Clients client : allClientsList) {
            tableModel.addRow(new Object[]{
                client.getUserNameAccount(),
                client.getNumberOfVisits(),
                "View Orders"
            });
        }

        usersTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        usersTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), allClientsList));

        usersTable.getTableHeader().setBackground(new Color(255, 188, 47));
        usersTable.getTableHeader().setForeground(new Color(56, 56, 72));
        usersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        usersTable.setRowHeight(30);
        usersTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(usersTable);
        usersFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.addActionListener(e -> {
            usersFrame.dispose();
            RestaurantOrderManagementApp.adminPanel.setVisible(true);
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
        }
        );
        usersFrame.add(closeButton, BorderLayout.SOUTH);

        usersFrame.setLocationRelativeTo(null);
        usersFrame.setVisible(true);
    }

    private void showOrdersTable(Clients client) {
        JFrame ordersFrame = new JFrame("Orders of " + client.getUserNameAccount());
        ordersFrame.setSize(600, 400);
        ordersFrame.setLayout(new BorderLayout());
        ordersFrame.setResizable(false);
        String[] columnNames = {"Order ID", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable ordersTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        for (order order1 : client.getOrderClientList()) {
            tableModel.addRow(new Object[]{
                order1.getOrderID(),
                "View Order"
            });
        }

        ordersTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        ordersTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox(), client));

        ordersTable.getTableHeader().setBackground(new Color(47, 188, 255));
        ordersTable.getTableHeader().setForeground(new Color(72, 56, 56));
        ordersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        ordersTable.setRowHeight(30);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersFrame.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            ordersFrame.dispose();

        });
        ordersFrame.add(closeButton, BorderLayout.SOUTH);

        ordersFrame.setLocationRelativeTo(usersFrame);
        ordersFrame.setVisible(true);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isClicked;
        private int row;
        private ArrayList<Clients> allClientsList;
        private Clients client;

        public ButtonEditor(JCheckBox checkBox, ArrayList<Clients> allClientsList) {
            super(checkBox);
            this.allClientsList = allClientsList;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
                SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));

            });
        }

        public ButtonEditor(JCheckBox checkBox, Clients client) {
            super(checkBox);
            this.client = client;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
                SwingUtilities.invokeLater(() -> new MusicPlayer("clickSound"));
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = value != null ? value.toString() : "";
            button.setText(label);
            isClicked = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isClicked) {
                if (client != null) {
                    OrderViewUI.viewOrder(client.getOrderClientList().get(row));
                } else {
                    showOrdersTable(allClientsList.get(row));
                }
            }
            isClicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }
    }
}
