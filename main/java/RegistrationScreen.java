package main.java;

import javax.swing.*;
import java.awt.*;

class RegistrationScreen extends JFrame {

    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String address;
    private String phone;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField fNameField;
    private JTextField sNameField;
    private JTextField addressField;
    private JTextField phoneField;

    RegistrationScreen() {
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

        // Back button box
        Box backBtnBox = Box.createHorizontalBox();
        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            Main.register.setVisible(false);
            Main.login.setLocationRelativeTo(null);
            Main.login.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Box for input fields
        Box registerBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Username label
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username:  ");
        usernameLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(usernameLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Username field
        usernameField = new JTextField();
        usernameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password:  ");
        passwordLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(passwordLabel);
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // Password field
        passwordField = new JPasswordField();
        passwordField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(160, 20));
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
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(phoneField);

        registerBox.add(Box.createRigidArea(new Dimension(15, 0)));
        registerBox.add(labelBox);
        registerBox.add(fieldBox);
        registerBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Register button box
        Box registerBtnBox = Box.createHorizontalBox();
        // Register button
        JButton registerBtn = new JButton("Register");
        registerBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        registerBtn.addActionListener(e -> {
            updateData();
            if (username == null || password == null || firstName == null || secondName == null || address == null || phone == null || username.equals("admin")) {
                JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
            } else if (username.equals("") || password.equals("") || firstName.equals("") || secondName.equals("") || address.equals("") || phone.equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
            } else {
                Main.activeUser = new User(username, password, false, firstName, secondName, address, phone, 0);
                if (!DataBase.findUser(Main.activeUser)) {
                    DataBase.addUser(Main.activeUser);
                    Main.users.add(Main.activeUser);
                    JOptionPane.showMessageDialog(mainPanel, "Successfully registered!");
                    Main.register.setVisible(false);
                    Main.cabinet = new CabinetScreen(false);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "User with this username already exist!");
                }
            }
        }
        );
        registerBtnBox.add(registerBtn);
        registerBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 65)));
        mainPanel.add(registerBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(registerBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateData () {
        username = usernameField.getText();
        password = passwordField.getText();
        firstName = fNameField.getText();
        secondName = sNameField.getText();
        address = addressField.getText();
        phone = phoneField.getText();
    }

}
