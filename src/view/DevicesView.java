package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.RoundJTextField;

public class DevicesView extends JFrame {    
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

    public DevicesView() {
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

	private void addComponents() {
		addLogoAndTitle();
		addDeviceInputs();
		addButtons();
	}
	
	private void addLogoAndTitle() {
		ImageIcon icon = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo_black.png");
		Image iconImage = icon.getImage();
		iconImage = iconImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		jlIcon = new JLabel(new ImageIcon(iconImage));
		jpRegister.add(jlIcon, BorderLayout.NORTH);
	
		jlTitle = new JLabel("Configuration of your first device");
		jlTitle.setFont(new Font("Century Gothic", Font.BOLD, 14));
		jpRegister.add(jlTitle, BorderLayout.CENTER);
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
		
		// Panel for device protocol
		JPanel jpDeviceProtocol = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel jlDeviceProtocol = new JLabel("Device protocol");
		jcbDeviceProtocol = new JComboBox<>(new String[]{"Protocol 1", "Protocol 2", "Protocol 3"});
		jcbDeviceProtocol.setPreferredSize(new Dimension(220, 25));
		jpDeviceProtocol.add(jlDeviceProtocol);
		jpDeviceProtocol.add(jcbDeviceProtocol);
		jpContent.add(jpDeviceProtocol);
		
		// Panel for device description
		JPanel jpDeviceDescription = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel jlDeviceDescription = new JLabel("Device description");
		jtfDeviceDescription = new RoundJTextField(15);
		jtfDeviceDescription.setPreferredSize(new Dimension(220, 25));
		jpDeviceDescription.add(jlDeviceDescription);
		jpDeviceDescription.add(jtfDeviceDescription);
		jpContent.add(jpDeviceDescription);
		
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
						MainView authenticationView = new MainView();
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
