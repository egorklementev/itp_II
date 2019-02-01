package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.LinkedList;

class RequestsScreen extends JFrame {

    private String document = null;
    private String user = null;
    private UserReqTableModel usersModel;
    private JScrollPane userScroll;
    private JTable usersTable;

    RequestsScreen() {
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

        // List of document who requested selected document (if any)
        Box requestsBox = Box.createHorizontalBox();
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        if (document != null) {
            LinkedList<User> req_users = new LinkedList<>();
            int index = 0;
            for (int i = 0; i < Main.reqDocs.size(); ++i) if (Main.reqDocs.get(i).getTitle().equals(document)) index = i;
            req_users.addAll(Main.priorityQueues.get(index));
            usersModel = new UserReqTableModel(req_users);
        } else {
            usersModel = new UserReqTableModel(new LinkedList<>());
        }
        usersTable = new JTable(usersModel);
        ListSelectionModel usersCellSelectionModel = usersTable.getSelectionModel();
        usersCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        usersCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = usersTable.getSelectedRows();
            int[] selectedColumns = usersTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    user = usersTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        userScroll = new JScrollPane(usersTable);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        requestsBox.add(userScroll);
        requestsBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of requested documents
        Box reqDocBox = Box.createHorizontalBox();
        reqDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel docModel = new DocReqTableModel();
        JTable docTable = new JTable(docModel);
        ListSelectionModel docCellSelectionModel = docTable.getSelectionModel();
        docCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        docCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = docTable.getSelectedRows();
            int[] selectedColumns = docTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    document = docTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
            // The lines below are used to real-time update the table
            LinkedList<User> req_users = new LinkedList<>();
            int index = 0;
            for (int i = 0; i < Main.reqDocs.size(); ++i) if (Main.reqDocs.get(i).getTitle().equals(document)) index = i;
            req_users.addAll(Main.priorityQueues.get(index));
            usersModel.replace(req_users);
            usersTable.revalidate();
        });

        JScrollPane docScroll = new JScrollPane(docTable);
        docScroll.setPreferredSize(new Dimension(50, 50));
        docScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        reqDocBox.add(docScroll);
        reqDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Back button
        Box backBox = Box.createHorizontalBox();
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        backBtn.addActionListener(e -> { // What to do when back button was pressed
            Main.requests.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBox.add(backBtn);
        backBox.add(Box.createRigidArea(new Dimension(235, 0)));

        // Outstanding user button
        JButton outRequestBtn = new JButton("Make outstanding");
        outRequestBtn.addActionListener(e -> { // What to do when libRequest button was pressed
            if (user == null || usersModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            } else if (Main.findDoc(document).isOutstanding()) {
                JOptionPane.showMessageDialog(mainPanel, "There is already placed an outstanding request!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "The outstanding request\nsuccessfully placed!\n" +
                        "All users in the queue were notified\nabout removal from the queue.\n");
                Document doc = Main.findDoc(document);
                for (int i = 0; i < Main.reqDocs.size(); ++i) {
                    if (doc.getTitle().equals(Main.reqDocs.get(i).getTitle())) {
                        Main.reqDocs.get(i).setOutstanding(true);
                        // Remove all requests related to this document
                        for (int j = 0; j < Main.priorityQueues.size(); ++j) {
                            User u = Main.priorityQueues.get(i).peek();
                            DataBase.deleteOrder(u, Main.reqDocs.get(i));
                            u.getBookings().remove(u.findBooking(Main.reqDocs.get(i).getTitle()));
                            u.notify(new Notification(2));
                            DataBase.replaceNotifications(u);
                            if (!u.getUsername().equals(user)) Main.priorityQueues.get(i).poll();
                        }
                    }
                }
                LinkedList<User> req_users = new LinkedList<>();
                int index = 0;
                for (int i = 0; i < Main.reqDocs.size(); ++i) if (Main.reqDocs.get(i).getTitle().equals(document)) index = i;
                req_users.addAll(Main.priorityQueues.get(index));
                usersModel.replace(req_users);
                usersTable.revalidate();
            }
        });
        outRequestBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of the users
        JLabel usersLabel = new JLabel();
        usersLabel.setText("Users");
        usersLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(reqDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(usersLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(requestsBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(outRequestBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

}
