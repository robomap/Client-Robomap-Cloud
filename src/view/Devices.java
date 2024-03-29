package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.RoundJTextField;

public class Devices extends JFrame {    
    private JPanel jpRegister;
    private JLabel jlIcon;
    private JLabel jlTitle;
    private RoundJTextField jtfDeviceName;
    private JComboBox<String> jcbDeviceProtocol;
    private RoundJTextField jtfDeviceDescription;
    private RoundJTextField jtfDeviceLatitude;
    private RoundJTextField jtfDeviceLongitude;
    private JButton buttonLogin;
    private JButton Registrar;

    public Devices() {
        jpRegister = new JPanel(new BorderLayout());
        //jpRegister.setBackground(new Color(50,50,50));
        addComponents();
        this.getContentPane().add(jpRegister);
        this.setSize(700, 500);
        this.setMinimumSize(new Dimension(600, 200));
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setTitle("Robomap Cloud Client - Configuration");
        this.setVisible(true);
        setLogoTaskbar();
    }

    private String formatCoordinates(double coordinate) {
        DecimalFormat df = new DecimalFormat("##.###");
        int degrees = (int) coordinate;
        double minutes = Math.abs((coordinate - degrees) * 60);
        int minutesInt = (int) minutes;
        double seconds = (minutes - minutesInt) * 60;
        return degrees + "° " + minutesInt + "' " + df.format(seconds) + "\"";
    }

    private void addComponents() {
        addLogoAndTitle();
        addDeviceInputs();
        addButtons();
    }
    
    private void addLogoAndTitle() {
        ImageIcon icon = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo_black.png");
        Image iconImage = icon.getImage();
        iconImage = iconImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    
        jlIcon = new JLabel(new ImageIcon(iconImage));
    
        JLabel titleLabel = new JLabel("Configure your first device");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
    
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.add(jlIcon, BorderLayout.WEST);
        logoPanel.add(titleLabel, BorderLayout.CENTER);
    
        jpRegister.add(logoPanel, BorderLayout.NORTH);
        jpRegister.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER); // Adding space between title and device inputs
    }

    private void addDeviceInputs() {
        JPanel jpInputs = new JPanel(new BorderLayout());
        JPanel jpContent = new JPanel();
        jpContent.setLayout(new BoxLayout(jpContent, BoxLayout.Y_AXIS));
        
        // Panel for device name
        JPanel jpDeviceName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jlDeviceName = new JLabel("Device name");
        jtfDeviceName = new RoundJTextField(15);
        jtfDeviceName.setPreferredSize(new Dimension(220, 25));
        jpDeviceName.add(jlDeviceName);
        jpDeviceName.add(jtfDeviceName);
        jpContent.add(jpDeviceName);
        jpContent.add(Box.createRigidArea(new Dimension(0, 5))); // Adding space between rows
        
        // Panel for device protocol
        JPanel jpDeviceProtocol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jlDeviceProtocol = new JLabel("Device protocol");
        jcbDeviceProtocol = new JComboBox<>(new String[]{"File protocol", "API", "OPC-UA"});
        jcbDeviceProtocol.setPreferredSize(new Dimension(220, 25));
        jcbDeviceProtocol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcbDeviceProtocol.getSelectedItem().equals("File protocol")) {
                    // Implement your file selection function here
                    System.out.println("File protocol selected. Implement your file selection logic here.");
                }
            }
        });
        jpDeviceProtocol.add(jlDeviceProtocol);
        jpDeviceProtocol.add(jcbDeviceProtocol);
        jpContent.add(jpDeviceProtocol);
        jpContent.add(Box.createRigidArea(new Dimension(0, 5))); // Adding space between rows
        
        // Panel for device description
        JPanel jpDeviceDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jlDeviceDescription = new JLabel("Device description");
        jtfDeviceDescription = new RoundJTextField(15);
        jtfDeviceDescription.setPreferredSize(new Dimension(220, 25));
        jpDeviceDescription.add(jlDeviceDescription);
        jpDeviceDescription.add(jtfDeviceDescription);
        jpContent.add(jpDeviceDescription);
        jpContent.add(Box.createRigidArea(new Dimension(0, 5))); // Adding space between rows
        
        // Panel for device latitude and longitude
        JPanel jpDeviceLatitudeLongitude = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jlDeviceLatitude = new JLabel("Device latitude");
        jtfDeviceLatitude = new RoundJTextField(15);
        jtfDeviceLatitude.setPreferredSize(new Dimension(100, 25));
        JLabel jlDeviceLongitude = new JLabel("Device longitude");
        jtfDeviceLongitude = new RoundJTextField(15);
        jtfDeviceLongitude.setPreferredSize(new Dimension(100, 25));
        jpDeviceLatitudeLongitude.add(jlDeviceLatitude);
        jpDeviceLatitudeLongitude.add(jtfDeviceLatitude);
        jpDeviceLatitudeLongitude.add(jlDeviceLongitude);
        jpDeviceLatitudeLongitude.add(jtfDeviceLongitude);
        jpContent.add(jpDeviceLatitudeLongitude);
        jtfDeviceLatitude.setText(formatCoordinates(40.7128)); 
        jtfDeviceLongitude.setText(formatCoordinates(-74.0060)); 

        jpInputs.add(jpContent, BorderLayout.CENTER);
        jpRegister.add(jpInputs, BorderLayout.CENTER);

       
        // Panel for file selection
        JPanel jpFileSelection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton jbSelectFile = new JButton("Select File");
        jbSelectFile.setEnabled(false); // Initially disabled
        jpFileSelection.add(jbSelectFile);
        jpContent.add(jpFileSelection);

        jcbDeviceProtocol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcbDeviceProtocol.getSelectedItem().equals("File protocol")) {
                    // If "File protocol" is selected, enable the button
                    jbSelectFile.setEnabled(true);
                } else {
                    // If any other protocol is selected, disable the button
                    jbSelectFile.setEnabled(false);
                }
            }
        });

        jbSelectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Devices.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    // Do something with the selected file path, for example:
                    System.out.println("Selected file: " + selectedFilePath);
                }
            }
        });

        jpInputs.add(jpContent, BorderLayout.CENTER);
        jpRegister.add(jpInputs, BorderLayout.CENTER);
    }
    

    private void addButtons() {
        JPanel jpButtons = new JPanel(new BorderLayout());

        Registrar = new JButton("Skip");
        Registrar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jpButtons.add(Registrar, BorderLayout.WEST);
        buttonLogin = new JButton("Add");
        buttonLogin.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jpButtons.add(buttonLogin, BorderLayout.EAST);
        jpRegister.add(jpButtons, BorderLayout.SOUTH);

        Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to another view or perform action
                System.out.println("Skip button clicked. Switching view...");
                Dashboard authenticationView = new Dashboard();
                authenticationView.setVisible(true);
                dispose();
            }
        });
    }
	
    public void setLogoTaskbar() {
        final Taskbar taskbar = Taskbar.getTaskbar();
        Image originalImage = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo.png").getImage();
        Image resizedImage = originalImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        
        try {
            taskbar.setIconImage(resizedImage);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
    }
}
