package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        super("Login Screen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2, 2));
        loginPanel.setBackground(new Color(80,80,80));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        usernameLabel.setForeground(Color.white);
        passwordLabel.setForeground(Color.white);
        usernameField.setMaximumSize(new Dimension(50, 25));
        usernameField.setPreferredSize(new Dimension(50, 25));
        usernameField.setMinimumSize(new Dimension(50, 25));
        passwordField.setPreferredSize(new Dimension(50, 25));

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        int padding = 10;
        loginPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
