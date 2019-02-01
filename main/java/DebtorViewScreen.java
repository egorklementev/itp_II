package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DebtorViewScreen extends JFrame {

	private String debt = null;
	private String debtor = null;
    private DebtTableModel debtModel;
    private JScrollPane debtScroll;
    private JTable debtTable;

    DebtorViewScreen() {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI();
        });
    }

    private void createGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // List of user debts (if any)
        Box debtBox = Box.createHorizontalBox();
        debtBox.add(Box.createRigidArea(new Dimension(10, 0)));

        if (debtor != null)
            debtModel = new DebtTableModel(Main.findUser(debtor).getBookings());
        else
            debtModel = new DebtTableModel(new ArrayList<>());
        debtTable = new JTable(debtModel);
        ListSelectionModel debtCellSelectionModel = debtTable.getSelectionModel();
        debtCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        debtCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = debtTable.getSelectedRows();
            int[] selectedColumns = debtTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    debt = debtTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        debtScroll = new JScrollPane(debtTable);
        debtScroll.setPreferredSize(new Dimension(50, 50));
        debtScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        debtBox.add(debtScroll);
        debtBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of user books
        Box debtorBox = Box.createHorizontalBox();
        debtorBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel debtorModel = new DebtorTableModel(Main.users);
        JTable debtorTable = new JTable(debtorModel);
        ListSelectionModel userCellSelectionModel = debtorTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = debtorTable.getSelectedRows();
            int[] selectedColumns = debtorTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    debtor = debtorTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
            // The lines below are used to real-time update the table
            List<Booking> list = new LinkedList<>();
            list.addAll(Main.findUser(debtor).getBookings());
            debtModel.replace(list);
            debtTable.revalidate();
        });

        JScrollPane debtorScroll = new JScrollPane(debtorTable);
        debtorScroll.setPreferredSize(new Dimension(50, 50));
        debtorScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        debtorBox.add(debtorScroll);
        debtorBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> { // What to do when back button was pressed
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
            Main.debtors.setVisible(false);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(235, 0)));

        // Debt info button
        JButton debtViewBtn = new JButton("Info");
        debtViewBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        debtViewBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (debt == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select user's debt!");
            } else {
                Booking booking = Main.findUser(debtor).findBooking(debt);
                String info = "";
                info += "Information about user's debt:\n";
                info += "Title:  " + booking.getDoc().getTitle() + ".\n";
                info += "Authors:  " + booking.getDoc().getAuthors() + ".\n";
                info += "Price:  " + booking.getDoc().getPrice() + " rubles.\n";
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
                info += "Date of booking: " + booking.getDate().toString() + ".\n";
                info += "Overdue:  " + (-1 * booking.getTimeLeft()) + " days.\n"; // Calculating the overdue
                int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice() ? booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                info += "Current fine: " + fine + " rubles.\n"; // Calculated fine
                JOptionPane.showMessageDialog(mainPanel, info);
            }
        });
        debtViewBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of the debts
        JLabel debtorLabelBox = new JLabel();
        debtorLabelBox.setText("Debts");
        debtorLabelBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(debtorBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(debtorLabelBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(debtBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(debtViewBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
