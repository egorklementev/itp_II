package main.java;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.LinkedList;
import java.util.PriorityQueue;

class CabinetScreen extends JFrame {

    private String libDoc = null;
    private String userDoc = null;

    JPanel mainPanel = new JPanel();

    /** Creates the window according to the type of the user (librarian or not) */
    CabinetScreen(boolean isLibrarian) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            if (isLibrarian) // Different GUIs for the users and librarians
                createLibGUI();
            else
                createUserGUI();
        });
    }

    /** Initialization of the objects of the personal area page of librarians */
    private void createLibGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // From top to bottom
        setResizable(false);

        // Table of library documents
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel docModel = new DocTableModel(Main.documents);
        JTable libDocTable = new JTable(docModel);
        ListSelectionModel libCellSelectionModel = libDocTable.getSelectionModel();
        libCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        libCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = libDocTable.getSelectedRows();
            int[] selectedColumns = libDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    libDoc = libDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane libBookScroll = new JScrollPane(libDocTable);
        libBookScroll.setPreferredSize(new Dimension(50, 50)); // Indentation
        libBookScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        libDocBox.add(libBookScroll);
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // List of users
        Box userBox = Box.createHorizontalBox();
        userBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel userModel = new UserTableModel(Main.users);
        JTable userDocTable = new JTable(userModel);
        ListSelectionModel userCellSelectionModel = userDocTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = userDocTable.getSelectedRows();
            int[] selectedColumns = userDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    userDoc = userDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane userScroll = new JScrollPane(userDocTable);
        userScroll.setPreferredSize(new Dimension(50, 50));
        userScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userBox.add(userScroll);
        userBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box logoutBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText(""); // Reset the password field
        });
        // Requests button
        JButton requestsBtn = new JButton("Requests");
        requestsBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        requestsBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.requests = new RequestsScreen();
            Main.requests.setLocationRelativeTo(null);
            Main.requests.setVisible(true);
        });
        logoutBox.add(logoutBtn);
        logoutBox.add(Box.createRigidArea(new Dimension(250, 0)));
        logoutBox.add(requestsBtn);

        // Buttons related to documents
        Box docBtnBox = Box.createHorizontalBox();
        // Add document button
        JButton addDocBtn = new JButton("Add new");
        addDocBtn.addActionListener(e -> {
            Main.cabinet.setVisible(false);
            Main.docAdd.setLocationRelativeTo(null);
            Main.docAdd.setVisible(true);
        });
        addDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(addDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Add copy of the document button
        JButton addDocCopyBtn = new JButton("Add copy");
        addDocCopyBtn.addActionListener(e -> {
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                doc.setCopies(doc.getCopies() + 1);
                DataBase.addDoc(doc);
                JOptionPane.showMessageDialog(mainPanel, "Copy added!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        addDocCopyBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(addDocCopyBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Document info button
        JButton infoDocBtn = new JButton(" Info ");
        infoDocBtn.addActionListener(e -> {
            String docTitle = libDoc;
            if (docTitle != null) {
                Document doc = Main.findDoc(docTitle);
                String info = "";
                info += "Information about the document:\n";
                info += "Title:  " + doc.getTitle() + "\n";
                info += "Authors:  " + doc.getAuthors() + "\n";
                info += "Price:  " + doc.getPrice() + " rubles.\n";
                info += "Copies left:  " + doc.getCopies() + "\n";
                if (doc.isReference()) info += "This is a reference document.\n";
                if (doc instanceof Book) if (doc.isBestSeller()) info += "This is a bestseller book.\n";
                if (doc.isOutstanding()) info += "For this document is placed the outstanding request.";
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(infoDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Modify the document button
        JButton modifyDocBtn = new JButton("Modify");
        modifyDocBtn.addActionListener(e -> {
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            } else {
                Main.docMod = new DocModifyScreen(Main.findDoc(libDoc));
                Main.docMod.setLocationRelativeTo(null);
                Main.docMod.setVisible(true);
                Main.cabinet.setVisible(false);
            }
        });
        modifyDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(modifyDocBtn);
        docBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        // Remove the document button
        JButton removeDocBtn = new JButton("Remove");
        removeDocBtn.addActionListener(e -> {
            if (libDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            } else {
                // Check and create the list of the users (if any) that are ordering this document at the moment
                boolean docIsBooking = false;
                String userList = "";
                for (int i = 0; i < Main.users.size(); ++i) {
                    for (int j = 0; j < Main.users.get(i).getBookings().size(); ++j) {
                        if (Main.users.get(i).getBookings().get(j).getDoc().getTitle().equals(libDoc)){
                            docIsBooking = true;
                            userList += Main.users.get(i).getFirstName() + " " + Main.users.get(i).getSecondName() + "\n";
                        }
                    }
                }
                if (docIsBooking) {
                    // Forbid the removal of the document if some users are ordering it at the moment
                    JOptionPane.showMessageDialog(mainPanel, "The document is currently ordering!\nList of users who is ordering this document:\n" + userList);
                } else {
                    DataBase.deleteDoc(Main.findDoc(libDoc));
                    Main.documents.remove(Main.findDoc(libDoc));
                    JOptionPane.showMessageDialog(mainPanel, "The document has been removed!");
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(true);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        removeDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        docBtnBox.add(removeDocBtn);

        // Buttons related to users
        Box userBtnBox = Box.createHorizontalBox();
        // View user info button
        JButton viewUserBtn = new JButton("Orders");
        viewUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        viewUserBtn.addActionListener(l -> {
            String username = userDoc;
            if (username != null) {
                Main.userView = new UserViewScreen(Main.findUser(username));
                Main.userView.setVisible(true);
                Main.userView.setLocationRelativeTo(null);
                Main.cabinet.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            }
        });
        // Add new user button
        JButton addUserBtn = new JButton("Add new");
        addUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addUserBtn.addActionListener(l -> {
            Main.cabinet.setVisible(false);
            Main.userAdd.setLocationRelativeTo(null);
            Main.userAdd.setVisible(true);
        });
        // Modify the user button
        JButton modifyUserBtn = new JButton("Modify");
        modifyUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        modifyUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            } else {
                Main.cabinet.setVisible(false);
                Main.userMod = new UserModifyScreen(Main.findUser(userDoc), null);
                Main.userMod.setLocationRelativeTo(null);
                Main.userMod.setVisible(true);
            }
        });
        // Remove the user button
        JButton removeUserBtn = new JButton("Remove");
        removeUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        removeUserBtn.addActionListener(l -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the user!");
            } else {
                if (Main.findUser(userDoc).getBookings().size() > 0) {
                    JOptionPane.showMessageDialog(mainPanel, "The user still has orders!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "The user has been removed!");
                    DataBase.deleteUser(Main.findUser(userDoc));
                    Main.users.remove(Main.findUser(userDoc));
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(true);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        // Debtors button
        JButton debtorUserBtn = new JButton("Debtors");
        debtorUserBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        debtorUserBtn.addActionListener(l -> {
            Main.cabinet.setVisible(false);
            Main.debtors.setLocationRelativeTo(null);
            Main.debtors.setVisible(true);
        });
        userBtnBox.add(addUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(viewUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(modifyUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(removeUserBtn);
        userBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userBtnBox.add(debtorUserBtn);

        //Label of library documents
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of users
        JLabel userLabel = new JLabel();
        userLabel.setText("Users");
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(logoutBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(docBtnBox, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(450, 640));
        pack();
        setLocationRelativeTo(null);
    }

    /** Initialization of the objects of the personal area page of patrons */
    private void createUserGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // Table of library books
        Box libDocBox = Box.createHorizontalBox();
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        DocTableModel docModel = new DocTableModel(Main.documents);
        JTable libDocTable = new JTable(docModel);
        ListSelectionModel libCellSelectionModel = libDocTable.getSelectionModel();
        libCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        libCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = libDocTable.getSelectedRows();
            int[] selectedColumns = libDocTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    libDoc = libDocTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane libDocScroll = new JScrollPane(libDocTable);
        libDocScroll.setPreferredSize(new Dimension(50, 50));
        libDocScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        libDocBox.add(libDocScroll);
        libDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Table of user (active account) documents
        Box userDocBox = Box.createHorizontalBox();
        userDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        TableModel userModel;
        if (Main.activeUser != null) {
            userModel = new UserDocTableModel(Main.activeUser.getBookings());
        } else {
            userModel = null;
        }
        JTable userTable = new JTable(userModel);
        ListSelectionModel userCellSelectionModel = userTable.getSelectionModel();
        userCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userCellSelectionModel.addListSelectionListener(e -> {
            int[] selectedRow = userTable.getSelectedRows();
            int[] selectedColumns = userTable.getSelectedColumns();
            for (int i = 0; i < selectedRow.length; i++) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    userDoc = userTable.getValueAt(selectedRow[i], 0).toString();
                }
            }
        });

        JScrollPane userDocScroll = new JScrollPane(userTable);
        userDocScroll.setPreferredSize(new Dimension(50, 50));
        userDocScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userDocBox.add(userDocScroll);
        userDocBox.add(Box.createRigidArea(new Dimension(10, 0)));

        // Log out button
        Box profileBox = Box.createHorizontalBox();
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            for (int i = 0; i < Main.users.size(); ++i)
                if (Main.users.get(i).getUsername().equals(Main.activeUser.getUsername()))
                    Main.users.get(i).copyData(Main.activeUser);
            Main.cabinet.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
            Main.login.passField.setText("");
        });
        // See the profile button
        JButton profileBtn = new JButton("My profile");
        profileBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        profileBtn.addActionListener(e -> {
            String info = "";
            info += "Information about me:\n";
            info += "Username:  " + Main.activeUser.getUsername() + "\n";
            info += "Password:  " + Main.activeUser.getPassword() + "\n";
            info += "First name:  " + Main.activeUser.getFirstName() + "\n";
            info += "Second name:  " + Main.activeUser.getSecondName() + "\n";
            info += "Address:  " + Main.activeUser.getAddress() + "\n";
            info += "Phone number:  " + Main.activeUser.getPhone() + "\n";
            if (Main.activeUser instanceof Student) info += "I am a student";
            else if (Main.activeUser instanceof Instructor) info += "I am an instructor";
            else if (Main.activeUser instanceof TA) info += "I am a teacher assistant";
            else if (Main.activeUser instanceof VisitingProfessor) info += "I am a visiting professor";
            else if (Main.activeUser instanceof Professor) info += "I am a professor";
            JOptionPane.showMessageDialog(mainPanel, info);
        });
        // Change the profile button
        JButton changeProfBtn = new JButton("Change profile");
        changeProfBtn.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        changeProfBtn.addActionListener(e -> {
            Main.changeProf = new ProfChangeScreen(Main.findUser(Main.activeUser.getUsername()));
            Main.cabinet.setVisible(false);
            Main.changeProf.setLocationRelativeTo(null);
            Main.changeProf.setVisible(true);
        });
        profileBox.add(logoutBtn);
        profileBox.add(Box.createRigidArea(new Dimension(125, 0)));
        profileBox.add(profileBtn);
        profileBox.add(Box.createRigidArea(new Dimension(5, 0)));
        profileBox.add(changeProfBtn);

        // Order the document button
        Box orderBox = Box.createHorizontalBox();
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener(e -> {
            if (libDoc != null) {
                Document doc = Main.findDoc(libDoc);
                boolean alreadyHas = false; // Check whether the patron already has one copy of the book
                for (int i = 0; i < Main.activeUser.getBookings().size(); ++i) {
                    if (doc.getTitle().equals(Main.activeUser.getBookings().get(i).getDoc().getTitle()))
                        alreadyHas = true;
                }
                if (alreadyHas) {
                    JOptionPane.showMessageDialog(mainPanel, "You already have one copy of this document!");
                } else if (Main.actualCopies(doc.getTitle()) <= 0) {
                    JOptionPane.showMessageDialog(mainPanel, "There is no more such documents in the library!");
                } else if (doc.getCopies() <= 0) {
                    if (doc.isOutstanding()) {
                        JOptionPane.showMessageDialog(mainPanel, "This document has been placed by an outstanding\n" +
                                "request. Please wait before it will be available.");
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "All these documents are booked!\nYou have" +
                                " been added to the queue!");
                        boolean hasReqBefore = false;
                        for (int i = 0; i < Main.reqDocs.size(); ++i) {
                            if (doc.getTitle().equals(Main.reqDocs.get(i).getTitle())) {
                                hasReqBefore = true;
                                Main.priorityQueues.get(i).add(Main.activeUser);
                                Main.cabinet.setVisible(false);
                                int duration = 21;                      // Usually it is 3 weeks
                                if (doc instanceof Book) if (doc.isBestSeller())
                                    duration = 14;                      // If the document is book-bestseller, then 2 weeks
                                if (Main.activeUser.isFaculty())
                                    duration = 28;                      // If user is faculty member, then 4 weeks
                                if (doc instanceof AudioVideo)
                                    duration = 14;                      // If the document is AV-material, then 2 weeks
                                if (Main.activeUser instanceof VisitingProfessor)
                                    duration = 7;    // Special duration for the VP
                                DataBase.doOrder(Main.activeUser, doc, duration, Main.date, false, false, false, false);
                                Main.activeUser.addBooking(new Booking(doc, duration, false));
                                Main.cabinet = new CabinetScreen(false);
                                Main.cabinet.setLocationRelativeTo(null);
                                Main.cabinet.setVisible(true);
                            }
                        }
                        if (!hasReqBefore) {
                            Main.reqDocs.add(doc);
                            Main.priorityQueues.add(new PriorityQueue<>(Main.priorityComparator));
                            Main.priorityQueues.get(0).add(Main.activeUser);
                            Main.cabinet.setVisible(false);
                            int duration = 21; // Usually it is 3 weeks
                            if (doc instanceof Book) if (doc.isBestSeller())
                                duration = 14; // If the document is book-bestseller, then 2 weeks
                            if (Main.activeUser.isFaculty())
                                duration = 28; // If user is faculty member, then 4 weeks
                            if (doc instanceof AudioVideo)
                                duration = 14; // If the document is AV-material, then 2 weeks
                            if (Main.activeUser instanceof VisitingProfessor)
                                duration = 7;  // Special duration for the VP
                            DataBase.doOrder(Main.activeUser, doc, duration, Main.date, false, false, false, false);
                            Main.activeUser.addBooking(new Booking(doc, duration, false));
                            Main.cabinet = new CabinetScreen(false);
                            Main.cabinet.setLocationRelativeTo(null);
                            Main.cabinet.setVisible(true);
                        }
                    }
                } else if (doc.isReference()) {
                    JOptionPane.showMessageDialog(mainPanel, "You cannot order a reference document!");
                }else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully ordered!");
                    Main.cabinet.setVisible(false);
                    doc.setCopies(doc.getCopies() - 1);
                    DataBase.addDoc(doc);
                    int duration = 21;                                                 // Usually it is 3 weeks
                    if (doc instanceof Book) if (doc.isBestSeller()) duration = 14;    // If the document is book-bestseller, then 2 weeks
                    if (Main.activeUser.isFaculty()) duration = 28;                    // If user is faculty member, then 4 weeks
                    if (doc instanceof AudioVideo) duration = 14;                      // If the document is AV-material, then 2 weeks
                    if (Main.activeUser instanceof VisitingProfessor) duration = 7;    // Special duration for the VP
                    DataBase.doOrder(Main.activeUser, doc, duration, Main.date, false, false, true, false);
                    Main.activeUser.addBooking(new Booking(doc, duration, true));
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the document!");
            }
        });
        orderBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        orderBox.add(orderBtn);
        orderBox.add(Box.createRigidArea(new Dimension(5, 0)));

        Box orderBtnBox = Box.createHorizontalBox();
        // Info about order button
        JButton infoDocBtn = new JButton(" Order info ");
        infoDocBtn.addActionListener(e -> {
            if (userDoc != null) {
                Booking booking = Main.activeUser.findBooking(userDoc);
                String info = "";
                info += "Information about the order:\n";
                info += "Title:  " + booking.getDoc().getTitle() + ".\n";
                info += "Authors: " + booking.getDoc().getAuthors() + ".\n";
                info += "Price: " + booking.getDoc().getPrice() + " rubles.\n";
                if (booking.hasReceived()) {
                    info += "Date of booking: " + booking.getDate().toString() + ".\n";
                    info += "Time left: " + booking.getTimeLeft() + " days.\n";
                } else {
                    info += "Date of booking: unknown \n(the document is not received yet).\n";
                    info += "Time left: unknown \n(the document is not received yet).\n";
                }
                if (booking.getDoc().isReference()) info += "This is a reference document.\n";
                if (booking.getDoc() instanceof Book) if(booking.getDoc().isBestSeller()) info += "This is a bestseller book.\n";
                if (booking.isOverdue()) {
                    int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice() ? booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                    info += "The booking is overdue. The fee is: " + fine + " rubles.";
                }
                JOptionPane.showMessageDialog(mainPanel, info);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // Return the order button
        JButton returnDocBtn = new JButton(" Return ");
        returnDocBtn.addActionListener(e -> {
            if (userDoc == null) {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            } else {
                Booking booking = Main.activeUser.findBooking(userDoc);
                if (booking.hasRequestedByUser()) {
                    JOptionPane.showMessageDialog(mainPanel, "Already requested!");
                } else if (!booking.hasReceived()) {
                    JOptionPane.showMessageDialog(mainPanel, "You have not received the document yet!");
                } else if (booking.hasRequestedByLib()) {
                    if (booking.isOverdue()) {
                        int fine = (-1 * booking.getTimeLeft()) * 100 >= booking.getDoc().getPrice() ? booking.getDoc().getPrice() : (-1 * booking.getTimeLeft()) * 100;
                        JOptionPane.showMessageDialog(mainPanel, "Your order of document \"" + booking.getDoc().getTitle() + "\" is overdue!\n" +
                                "For this reason you have to pay fee of " + fine + " rubles.");
                    }
                    JOptionPane.showMessageDialog(mainPanel, "The document has successfully returned!");
                    boolean hasFound = false;
                    for (int i = 0; i < Main.documents.size(); ++i) {
                        if (Main.documents.get(i).getTitle().equals(booking.getDoc().getTitle())) {
                            hasFound = true;
                            Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                            if (Main.documents.get(i).isOutstanding()) Main.documents.get(i).setOutstanding(false);
                            DataBase.addDoc(Main.documents.get(i));
                            DataBase.deleteOrder(Main.activeUser, booking.getDoc());
                            Main.activeUser.getBookings().remove(booking);
                            // Check for the users who has requested for this document
                            for (int j = 0; j < Main.reqDocs.size(); ++j) {
                                if (Main.reqDocs.get(j).getTitle().equals(booking.getDoc().getTitle())) {
                                    Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() - 1);
                                    DataBase.addDoc(Main.documents.get(i));
                                    User user = Main.priorityQueues.get(j).poll();
                                    if (user == null) {
                                        Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                                        DataBase.addDoc(Main.documents.get(i));
                                        Main.reqDocs.remove(j);
                                        Main.priorityQueues.remove(j);
                                        break;
                                    }
                                    booking.getDoc().setOutstanding(false);
                                    user.notify(new Notification(1, Main.reqDocs.get(j).getTitle()));
                                    DataBase.replaceNotifications(user);
                                }
                            }
                        }
                    }
                    if (!hasFound) {
                        booking.getDoc().setCopies(1);
                        if (booking.getDoc().isOutstanding()) booking.getDoc().setOutstanding(false);
                        Main.documents.add(booking.getDoc());
                        DataBase.addDoc(booking.getDoc());
                    }
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Successfully requested for returning!");
                    booking.userRequest();
                    DataBase.doOrder(Main.activeUser, booking.getDoc(), booking.getDuration(), booking.getDate(), booking.hasRequestedByLib(), booking.hasRequestedByUser(), booking.hasReceived(), booking.hasRenewed());
                    Main.cabinet.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                }
            }
        });
        returnDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // Renew button
        JButton renewBtn = new JButton("  Renew  ");
        renewBtn.addActionListener(e -> {
            if (userDoc != null) {
                if (!Main.activeUser.findBooking(userDoc).hasReceived()) {
                    JOptionPane.showMessageDialog(mainPanel, "You have not received the document yet!");
                } else if (!Main.activeUser.findBooking(userDoc).isOverdue()) {
                    JOptionPane.showMessageDialog(mainPanel, "This booking is not overdue yet!");
                } else if (Main.findDoc(userDoc).isOutstanding()) {
                    JOptionPane.showMessageDialog(mainPanel, "The outstanding request is\nplaced for this docuemnt!\n" +
                            "You cannot renew it!");
                } else if (Main.activeUser.findBooking(Main.findDoc(userDoc).getTitle()).hasRenewed() && !(Main.activeUser instanceof VisitingProfessor)) {
                    JOptionPane.showMessageDialog(mainPanel, "You have already renewed this document!\nYou are not" +
                            "allowed to do it again!");
                } else {
                    Booking booking = Main.activeUser.findBooking(userDoc);
                    Main.cabinet.setVisible(false);
                    int duration = 21; // Usually it is 3 weeks
                    if (booking.getDoc() instanceof Book) if (booking.getDoc().isBestSeller())
                        duration = 14; // If the document is book-bestseller, then 2 weeks
                    if (Main.activeUser.isFaculty())
                        duration = 28; // If user is faculty member, then 4 weeks
                    if (booking.getDoc() instanceof AudioVideo)
                        duration = 14; // If the document is AV-material, then 2 weeks
                    if (Main.activeUser instanceof VisitingProfessor)
                        duration = 7;  // Special duration for the VP
                    booking.setRenewed();
                    booking.setDate(Main.date);
                    DataBase.doOrder(Main.activeUser, booking.getDoc(), duration, Main.date, false, false, true, true);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                    JOptionPane.showMessageDialog(mainPanel, "Successfully renewed for " + duration + " days!");
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Select the order!");
            }
        });
        infoDocBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        orderBtnBox.add(infoDocBtn);
        orderBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        orderBtnBox.add(returnDocBtn);
        orderBtnBox.add(Box.createRigidArea(new Dimension(5, 0)));
        orderBtnBox.add(renewBtn);

        //Label of library documents
        JLabel libLabel = new JLabel();
        libLabel.setText("Library documents");
        libLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //Label of user documents
        JLabel userBooksLabel = new JLabel();
        userBooksLabel.setText("Your documents");
        userBooksLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(profileBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(libLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(libDocBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(orderBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(userBooksLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(userDocBox, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(orderBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(450, 640));
        pack();
        setLocationRelativeTo(null);

        if (Main.activeUser != null) {
            for (int i = 0; i < Main.activeUser.getNotifications().size(); ++i) {
                showNotification(Main.activeUser.getNotifications().get(i));
                Main.activeUser.getNotifications().remove(i);
            }
            DataBase.replaceNotifications(Main.activeUser);
        }
    }

    private void showNotification (Notification n) {
        switch (n.getType()) {
            case 1: { // Document receiving
                if (Main.date.compareTo(n.getDate()) > 0 && Main.date.getDay() != n.getDate().getDay()) {
                    // Notification about not receiving of the requested document
                    JOptionPane.showMessageDialog(mainPanel, "You have been removed from the queue\n" +
                            "due to your absense.");
                    DataBase.deleteOrder(Main.activeUser, Main.findDoc(n.getDoc()));
                    Main.activeUser.getBookings().remove(Main.activeUser.findBooking(n.getDoc()));
                    for (int i = 0; i < Main.reqDocs.size(); ++i) {
                        if (Main.reqDocs.get(i).getTitle().equals(n.getDoc())) {
                            Main.priorityQueues.get(i).poll();
                            User u = Main.priorityQueues.get(i).poll();
                            if (u == null) {
                                Main.documents.get(i).setCopies(Main.documents.get(i).getCopies() + 1);
                                DataBase.addDoc(Main.documents.get(i));
                                Main.reqDocs.remove(i);
                                Main.priorityQueues.remove(i);
                                break;
                            }
                            Main.findDoc(n.getDoc()).setOutstanding(false);
                            u.notify(new Notification(1, n.getDoc()));
                        }
                    }
                } else {
                    // Dialog window about receiving or rejection of the requested document
                    String info = "";
                    info += "The requested document is available.\n" +
                            "Do you want to order it?\n";
                    Object[] obj = new Object[]{"Yes","No"};
                    info += "Document's title: " + n.getDoc() + "\n";
                    int result = JOptionPane.showOptionDialog(mainPanel,
                            info,
                            "Message",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            obj,
                            obj[0]
                            );
                    if (result == JOptionPane.YES_OPTION) {
                        Booking b = Main.activeUser.findBooking(n.getDoc());
                        b.setReceived();
                        DataBase.doOrder(Main.activeUser, b.getDoc(), b.getDuration(), b.getDate(), b.hasRequestedByLib(), b.hasRequestedByUser(),
                        true, false);
                    } else {
                        DataBase.deleteOrder(Main.activeUser, Main.activeUser.findBooking(n.getDoc()).getDoc());
                        Main.activeUser.getBookings().remove(Main.activeUser.findBooking(n.getDoc()));
                        for (int i = 0; i < Main.reqDocs.size(); ++i) {
                            if (Main.reqDocs.get(i).getTitle().equals(n.getDoc())) {
                                Main.reqDocs.get(i).setOutstanding(false);
                                User u = Main.priorityQueues.get(i).poll();
                                if (u != null) {
                                    u.notify(new Notification(1, Main.reqDocs.get(i).getTitle()));
                                    DataBase.replaceNotifications(u);
                                }
                            }
                        }
                    }
                }
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(mainPanel, "You have been removed from the queue\n" +
                        "due to the outstanding request.");
                break;
            }
            case 3: {
                Booking booking = Main.activeUser.findBooking(n.getDoc());
                JOptionPane.showMessageDialog(mainPanel, "Your order of document \"" + n.getDoc() + "\" is overdue!\n" +
                        "For this reason you have to pay fee of " + n.getFine() + " rubles.");
                break;
            }
            case 4: { break; }
            default: { break; }
        }
    }

}
