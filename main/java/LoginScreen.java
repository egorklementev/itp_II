package main.java;

import javax.swing.*;
import java.awt.*;

class LoginScreen extends JFrame {

    JPasswordField passField;

    LoginScreen() {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true); // System function
            createGUI();
        });
    }

    /** Initialization of the objects of login page */
    private void createGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // System function

        JPanel mainPanel = new JPanel(); // Main container
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Objects will displace from top to bottom
        setResizable(false);

        // Username field
        JTextField userField = new JTextField();
        userField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userField.setMaximumSize(new Dimension(160, 20));

        // Password field
        passField = new JPasswordField();
        passField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passField.setMaximumSize(new Dimension(160, 20));

        // Log in button
        JButton loginBtn = new JButton("Sign in ");
        loginBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> { // What to do after login button has pressed
                if (checkForUser(userField.getText(), new String(passField.getPassword()))){
                    Main.activeUser = Main.findUser(userField.getText());
                    Main.cabinet = new CabinetScreen(Main.activeUser instanceof Librarian);
                    Main.cabinet.setLocationRelativeTo(null);
                    Main.cabinet.setVisible(true);
                    Main.login.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Wrong login or password!");
                }
        });

        // Register button
        JButton registerBtn = new JButton("Sign up");
        registerBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> {
            Main.login.setVisible(false);
            Main.register.setLocationRelativeTo(null);
            Main.register.setVisible(true);
        });

        // Main label
        JLabel logo = new JLabel();
        logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        logo.setFont(new Font("name", Font.PLAIN, 30));
        logo.setText("INNObrary");

        mainPanel.add(Box.createRigidArea(new Dimension(0, 75))); // Indentation
        mainPanel.add(logo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(userField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(passField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(registerBtn);

        getContentPane().add(mainPanel); // See the actual content of the container

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    /** Checks whether given username and password of some user are exists in the system
     * @return whether given username and password of some user are exists in the system */
    private boolean checkForUser(String username, String password) {
        for (int i = 0; i < Main.users.size(); ++i) {
            if (Main.users.get(i).getUsername().equals(username) && Main.users.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}