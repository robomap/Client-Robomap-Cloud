package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class StyledLoginScreen extends JFrame {
    public StyledLoginScreen() {
        setTitle("Styled Login Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setLayout(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("We are NUVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel descriptionLabel = new JLabel("Welcome back! Log in to your account to view today's clients:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(28, 28, 28));

        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();

        JButton loginButton = new JButton("Log in");
        loginButton.setBackground(new Color(182, 157, 230));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);

        formPanel.add(titleLabel);
        formPanel.add(descriptionLabel);
        formPanel.add(emailField);
        formPanel.add(passwordField);
        formPanel.add(loginButton);

        // Main panel with background image
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        setContentPane(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StyledLoginScreen());
    }
}
