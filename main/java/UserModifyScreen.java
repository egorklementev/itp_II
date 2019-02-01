package main.java;

import javax.swing.*;
import java.awt.*;

class UserModifyScreen extends JFrame {

    private JComboBox<String> userTypeComboBox;
    private String password;
    private String firstName;
    private String secondName;
    private String address;
    private String phone;
    private JTextField passwordField;
    private JTextField fNameField;
    private JTextField sNameField;
    private JTextField addressField;
    private JTextField phoneField;

    private User finalUser;

    UserModifyScreen(User user, User finalUser) {
        super("INNObrary");
        this.finalUser = finalUser;
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            createGUI(user);
        });
    }

    private void createGUI(User user) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);

        // Back button box
        Box backBtnBox = Box.createHorizontalBox();
        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.userMod.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Type of the document label
        JLabel typeLabel = new JLabel();
        typeLabel.setText("Type: ");
        typeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] items = new String[]{"Student", "Instructor", "TA", "Visiting Professor", "Professor"};
        if (finalUser == null) {
            if (user instanceof Student) {
                items = new String[]{"Student", "Instructor", "TA", "Visiting Professor", "Professor"};
            } else if (user instanceof Instructor) {
                items = new String[]{"Instructor", "TA", "Visiting Professor", "Professor", "Student"};
            } else if (user instanceof TA) {
                items = new String[]{"TA", "Visiting Professor", "Professor", "Student", "Instructor"};
            } else if (user instanceof VisitingProfessor) {
                items = new String[]{"Visiting Professor", "Professor", "Student", "Instructor", "TA"};
            } else if (user instanceof Professor) {
                items = new String[]{"Professor", "Student", "Instructor", "TA", "Visiting Professor"};
            }
        } else {
            if (finalUser instanceof Student) {
                items = new String[]{"Student", "Instructor", "TA", "Visiting Professor", "Professor"};
            } else if (finalUser instanceof Instructor) {
                items = new String[]{"Instructor", "TA", "Visiting Professor", "Professor", "Student"};
            } else if (finalUser instanceof TA) {
                items = new String[]{"TA", "Visiting Professor", "Professor", "Student", "Instructor"};
            } else if (finalUser instanceof VisitingProfessor) {
                items = new String[]{"Visiting Professor", "Professor", "Student", "Instructor", "TA"};
            } else if (finalUser instanceof Professor) {
                items = new String[]{"Professor", "Student", "Instructor", "TA", "Visiting Professor"};
            }
        }

        // List of user types
        Box userTypeBox = Box.createHorizontalBox();
        userTypeBox.setMaximumSize(new Dimension(250, 25));
        userTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));
        userTypeComboBox = new JComboBox<>(items);
        userTypeComboBox.addActionListener(e -> updateType(user));
        userTypeBox.add(typeLabel);
        userTypeBox.add(Box.createRigidArea(new Dimension(5, 0)));
        userTypeBox.add(userTypeComboBox);
        userTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Boxes for input fields
        Box modifyBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Password label
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password:  ");
        passwordLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(passwordLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Password field
        passwordField = new JTextField();
        passwordField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(160, 20));
        passwordField.setText(user.getPassword());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(passwordField);

        // First name label
        JLabel fNameLabel = new JLabel();
        fNameLabel.setText("First name:  ");
        fNameLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(fNameLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // First name field
        fNameField = new JTextField();
        fNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        fNameField.setMaximumSize(new Dimension(160, 20));
        fNameField.setText(user.getFirstName());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(fNameField);

        // Second name label
        JLabel sNameLabel = new JLabel();
        sNameLabel.setText("Second name:  ");
        sNameLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(sNameLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Second name field
        sNameField = new JTextField();
        sNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        sNameField.setMaximumSize(new Dimension(160, 20));
        sNameField.setText(user.getSecondName());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(sNameField);

        // Address label
        JLabel addressLabel = new JLabel();
        addressLabel.setText("Address:  ");
        addressLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(addressLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Address field
        addressField = new JTextField();
        addressField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addressField.setMaximumSize(new Dimension(160, 20));
        addressField.setText(user.getAddress());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(addressField);

        // Phone label
        JLabel phoneLabel = new JLabel();
        phoneLabel.setText("Phone number:  ");
        phoneLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(phoneLabel);
        // Phone field
        phoneField = new JTextField();
        phoneField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        phoneField.setMaximumSize(new Dimension(160, 20));
        phoneField.setText(user.getPhone());
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(phoneField);

        modifyBox.add(Box.createRigidArea(new Dimension(15, 0)));
        modifyBox.add(labelBox);
        modifyBox.add(fieldBox);
        modifyBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Modify button box
        Box modifyBtnBox = Box.createHorizontalBox();
        // Modify button
        JButton modifyBtn = new JButton("Modify user");
        modifyBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        modifyBtn.addActionListener(e -> {
                    updateData();
                    if (password == null || firstName == null || secondName == null || address == null || phone == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (password.equals("") || firstName.equals("") || secondName.equals("") || address.equals("") || phone.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        if (finalUser == null) updateType(user);
                        finalUser.setPassword(password);
                        finalUser.setFirstName(firstName);
                        finalUser.setSecondName(secondName);
                        finalUser.setAddress(address);
                        finalUser.setPhone(phone);
                        Main.users.remove(Main.findUser(user.getUsername()));
                        Main.users.add(finalUser);
                        DataBase.addUser(finalUser);
                        JOptionPane.showMessageDialog(mainPanel, "The user successfully modified!");
                        passwordField.setText("");
                        fNameField.setText("");
                        sNameField.setText("");
                        addressField.setText("");
                        phoneField.setText("");
                        Main.requests = new RequestsScreen();
                        Main.cabinet = new CabinetScreen(true);
                        Main.userMod.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        modifyBtnBox.add(modifyBtn);
        modifyBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 45)));
        mainPanel.add(userTypeBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(modifyBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 45)));
        mainPanel.add(modifyBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateData () {
        password = passwordField.getText();
        firstName = fNameField.getText();
        secondName = sNameField.getText();
        address = addressField.getText();
        phone = phoneField.getText();
    }

    private void updateType(User user) {
        Main.userMod.setVisible(false);
        switch ((String) userTypeComboBox.getSelectedItem()) {
            case "Student": {finalUser = new Student(user); break;}
            case "Instructor": {finalUser = new Instructor(user); break;}
            case "TA": {finalUser = new TA(user); break;}
            case "Visiting Professor": {finalUser = new VisitingProfessor(user); break;}
            case "Professor": {finalUser = new Professor(user); break;}
        }
        Main.userMod = new UserModifyScreen(user, finalUser);
        Main.userMod.setLocationRelativeTo(null);
        Main.userMod.setVisible(true);
    }

}
