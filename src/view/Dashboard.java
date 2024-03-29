package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Dashboard extends JFrame {
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JPanel centerPanel;

    public Dashboard() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Client Robomap Cloud - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        northPanel = new JPanel();
        northPanel.setBackground(Color.WHITE);
        add(northPanel, BorderLayout.NORTH);

        southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        add(southPanel, BorderLayout.SOUTH);

        eastPanel = new JPanel();
        eastPanel.setBackground(Color.WHITE);
        add(eastPanel, BorderLayout.EAST);

        westPanel = new JPanel();
        westPanel.setBackground(Color.WHITE);
        add(westPanel, BorderLayout.WEST);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.GRAY);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }
}
