package main.java;

import javax.swing.*;
import java.awt.*;

class DocAddScreen extends JFrame {

    private JComboBox<String> docTypeComboBox;
    private String title;
    private String author;
    private String price;
    private String copies;
    private String edition;
    private String editionYear;
    private String publisher;
    private boolean isReference;
    private boolean isBestSeller;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField priceField;
    private JTextField copiesField;
    private JTextField editionField;
    private JTextField editionYField;
    private JTextField publisherField;

    DocAddScreen(String docType) {
        super("INNObrary");
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            switch(docType) {
                case "Book": {
                    createGUIBook();
                    break;
                }
                case "AV file": {
                    createGUIAV();
                    break;
                }
                case "Journal article": {
                    createGUIArticle();
                    break;
                }
                default:{
                    createGUIBook();
                    break;
                }
            }
        });
    }

    private void createGUIBook() {
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
            Main.docAdd.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Type of the document label
        JLabel typeLabel = new JLabel();
        typeLabel.setText("Type: ");
        typeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] items = {
                "Book",
                "AV file",
                "Journal article"
        };

        // List of types
        Box docTypeBox = Box.createHorizontalBox();
        docTypeBox.setMaximumSize(new Dimension(250, 25));
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));
        docTypeComboBox = new JComboBox<>(items);
        docTypeComboBox.addActionListener(e -> updateType());
        docTypeBox.add(typeLabel);
        docTypeBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docTypeBox.add(docTypeComboBox);
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Boxes for input fields
        Box docAddBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Title label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Title:  ");
        titleLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(titleLabel);
        // Title field
        titleField = new JTextField();
        titleField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(titleField);

        // Author label
        JLabel authorLabel = new JLabel();
        authorLabel.setText("Author:  ");
        authorLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(authorLabel);
        // Title field
        authorField = new JTextField();
        authorField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        authorField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(authorField);

        // Price label
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:  ");
        priceLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(priceLabel);
        // Price field
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(priceField);

        // Copies label
        JLabel copiesLabel = new JLabel();
        copiesLabel.setText("Copies:  ");
        copiesLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(copiesLabel);
        // Copies field
        copiesField = new JTextField();
        copiesField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        copiesField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(copiesField);

        // Edition label
        JLabel editionLabel = new JLabel();
        editionLabel.setText("Edition:  ");
        editionLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(editionLabel);
        // Edition field
        editionField = new JTextField();
        editionField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        editionField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(editionField);

        // Edition year label
        JLabel editionYLabel = new JLabel();
        editionYLabel.setText("Edition year:  ");
        editionYLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(editionYLabel);
        // Edition year field
        editionYField = new JTextField();
        editionYField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        editionYField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(editionYField);

        // Publisher label
        JLabel publisherLabel = new JLabel();
        publisherLabel.setText("Publisher:  ");
        publisherLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(publisherLabel);
        // Publisher field
        publisherField = new JTextField();
        publisherField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        publisherField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(publisherField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Add document button box
        Box docAddBtnBox = Box.createHorizontalBox();
        // Add document button
        JButton docAddBtn = new JButton("Add document");
        docAddBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        docAddBtn.addActionListener(e -> {
                    updateBookData();
                    if (title == null || author == null || price == null || copies == null || edition == null || editionYear == null || publisher == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (title.equals("") || author.equals("") || price.equals("") || copies.equals("") || edition.equals("") || editionYear.equals("") || publisher.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        Book b = new Book(title, author, Integer.parseInt(edition), Integer.parseInt(editionYear), publisher, Integer.parseInt(price), Integer.parseInt(copies), isBestSeller, isReference);
                        Main.documents.add(b);
                        DataBase.addDoc(b);
                        JOptionPane.showMessageDialog(mainPanel, "New document successfully added!");
                        titleField.setText("");
                        authorField.setText("");
                        priceField.setText("");
                        copiesField.setText("");
                        editionField.setText("");
                        editionYField.setText("");
                        publisherField.setText("");
                        Main.cabinet = new CabinetScreen(true);
                        Main.docAdd.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        docAddBtnBox.add(docAddBtn);
        docAddBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Bestseller & reference check boxes
        Box refBSBox = Box.createHorizontalBox();

        JCheckBox refCheckBox = new JCheckBox("Is reference");
        refCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        refCheckBox.addItemListener(e -> isReference = refCheckBox.isSelected());

        JCheckBox BSCheckBox = new JCheckBox("Is bestseller");
        BSCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        BSCheckBox.addItemListener(e -> isBestSeller = BSCheckBox.isSelected());

        refBSBox.add(refCheckBox);
        refBSBox.add(Box.createRigidArea(new Dimension(5, 0)));
        refBSBox.add(BSCheckBox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        mainPanel.add(docTypeBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(refBSBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void createGUIAV() {
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
            Main.docAdd.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Type of the document label
        JLabel typeLabel = new JLabel();
        typeLabel.setText("Type: ");
        typeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] items = {
                "AV file",
                "Book",
                "Journal article"
        };

        // List of types
        Box docTypeBox = Box.createHorizontalBox();
        docTypeBox.setMaximumSize(new Dimension(250, 25));
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));
        docTypeComboBox = new JComboBox<>(items);
        docTypeComboBox.addActionListener(e -> updateType());
        docTypeBox.add(typeLabel);
        docTypeBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docTypeBox.add(docTypeComboBox);
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Boxes for input fields
        Box docAddBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Title label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Title:  ");
        titleLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(titleLabel);
        // Title field
        titleField = new JTextField();
        titleField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(titleField);

        // Author label
        JLabel authorLabel = new JLabel();
        authorLabel.setText("Author:  ");
        authorLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(authorLabel);
        // Title field
        authorField = new JTextField();
        authorField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        authorField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(authorField);

        // Price label
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:  ");
        priceLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(priceLabel);
        // Price field
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(priceField);

        // Copies label
        JLabel copiesLabel = new JLabel();
        copiesLabel.setText("Copies:  ");
        copiesLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(copiesLabel);
        // Copies field
        copiesField = new JTextField();
        copiesField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        copiesField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(copiesField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Add document button box
        Box docAddBtnBox = Box.createHorizontalBox();
        // Add document button
        JButton docAddBtn = new JButton("Add document");
        docAddBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        docAddBtn.addActionListener(e -> {
                    updateAVData();
                    if (title == null || author == null || price == null || copies == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (title.equals("") || author.equals("") || price.equals("") || copies.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        AudioVideo av = new AudioVideo(title, author, Integer.parseInt(price), Integer.parseInt(copies), isReference);
                        Main.documents.add(av);
                        DataBase.addDoc(av);
                        JOptionPane.showMessageDialog(mainPanel, "New document successfully added!");
                        titleField.setText("");
                        authorField.setText("");
                        priceField.setText("");
                        copiesField.setText("");
                        Main.cabinet = new CabinetScreen(true);
                        Main.docAdd.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        docAddBtnBox.add(docAddBtn);
        docAddBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Bestseller & reference check boxes
        Box refBSBox = Box.createHorizontalBox();

        JCheckBox refCheckBox = new JCheckBox("Is reference");
        refCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        refCheckBox.addItemListener(e -> isReference = refCheckBox.isSelected());

        refBSBox.add(refCheckBox);
        refBSBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        mainPanel.add(docTypeBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(refBSBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void createGUIArticle() {
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
            Main.docAdd.setVisible(false);
            Main.cabinet.setLocationRelativeTo(null);
            Main.cabinet.setVisible(true);
        });
        backBtnBox.add(backBtn);
        backBtnBox.add(Box.createRigidArea(new Dimension(200, 0)));

        // Type of the document label
        JLabel typeLabel = new JLabel();
        typeLabel.setText("Type: ");
        typeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] items = {
                "Journal article",
                "AV file",
                "Book"
        };

        // List of types
        Box docTypeBox = Box.createHorizontalBox();
        docTypeBox.setMaximumSize(new Dimension(250, 25));
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));
        docTypeComboBox = new JComboBox<>(items);
        docTypeComboBox.addActionListener(e -> updateType());
        docTypeBox.add(typeLabel);
        docTypeBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docTypeBox.add(docTypeComboBox);
        docTypeBox.add(Box.createRigidArea(new Dimension(15, 0)));

        // Boxes for input fields
        Box docAddBox = Box.createHorizontalBox();
        Box labelBox = Box.createVerticalBox();
        Box fieldBox = Box.createVerticalBox();

        // Title label
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Title:  ");
        titleLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(titleLabel);
        // Title field
        titleField = new JTextField();
        titleField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(titleField);

        // Author label
        JLabel authorLabel = new JLabel();
        authorLabel.setText("Author:  ");
        authorLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(authorLabel);
        // Title field
        authorField = new JTextField();
        authorField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        authorField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(authorField);

        // Price label
        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price:  ");
        priceLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(priceLabel);
        // Price field
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(priceField);

        // Copies label
        JLabel copiesLabel = new JLabel();
        copiesLabel.setText("Copies:  ");
        copiesLabel.setFont(new Font("name", Font.BOLD, 15));
        labelBox.add(Box.createRigidArea(new Dimension(0, 5)));
        labelBox.add(copiesLabel);
        // Copies field
        copiesField = new JTextField();
        copiesField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        copiesField.setMaximumSize(new Dimension(160, 20));
        fieldBox.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldBox.add(copiesField);

        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(labelBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));
        docAddBox.add(fieldBox);
        docAddBox.add(Box.createRigidArea(new Dimension(5, 0)));

        // Add document button box
        Box docAddBtnBox = Box.createHorizontalBox();
        // Add document button
        JButton docAddBtn = new JButton("Add document");
        docAddBtn.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        docAddBtn.addActionListener(e -> {
                    updateAVData();
                    if (title == null || author == null || price == null || copies == null) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else if (title.equals("") || author.equals("") || price.equals("") || copies.equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input data!");
                    } else {
                        JournalArticle ja = new JournalArticle(title, author, Integer.parseInt(price), Integer.parseInt(copies), isReference);
                        Main.documents.add(ja);
                        DataBase.addDoc(ja);
                        JOptionPane.showMessageDialog(mainPanel, "New document successfully added!");
                        titleField.setText("");
                        authorField.setText("");
                        priceField.setText("");
                        copiesField.setText("");
                        Main.cabinet = new CabinetScreen(true);
                        Main.docAdd.setVisible(false);
                        Main.cabinet.setLocationRelativeTo(null);
                        Main.cabinet.setVisible(true);
                    }
                }
        );
        docAddBtnBox.add(docAddBtn);
        docAddBtnBox.add(Box.createRigidArea(new Dimension(0, 0)));

        // Bestseller & reference check boxes
        Box refBSBox = Box.createHorizontalBox();

        JCheckBox refCheckBox = new JCheckBox("Is reference");
        refCheckBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        refCheckBox.addItemListener(e -> isReference = refCheckBox.isSelected());

        refBSBox.add(refCheckBox);
        refBSBox.add(Box.createRigidArea(new Dimension(0, 0)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backBtnBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        mainPanel.add(docTypeBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(refBSBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(docAddBtnBox);

        getContentPane().add(mainPanel);

        setPreferredSize(new Dimension(330, 450));
        pack();
        setLocationRelativeTo(null);
    }

    private void updateBookData() {
        title = titleField.getText();
        author = authorField.getText();
        price = priceField.getText();
        copies = copiesField.getText();
        edition = editionField.getText();
        editionYear = editionYField.getText();
        publisher = publisherField.getText();
    }

    private void updateAVData() {
        title = titleField.getText();
        author = authorField.getText();
        price = priceField.getText();
        copies = copiesField.getText();
    }

    private void updateType() {
        Main.docAdd.setVisible(false);
        Main.docAdd = new DocAddScreen((String) docTypeComboBox.getSelectedItem());
        Main.docAdd.setLocationRelativeTo(null);
        Main.docAdd.setVisible(true);
    }

}
