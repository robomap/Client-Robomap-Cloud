package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import config.AppConfig;
import controller.AuthenticationController;
import service.ApiClient;

public class DevicesView extends JFrame {    
    private JPanel jpRegister;
    private JPanel jpContent;
    private JPanel jpPassword;
    private JPanel jpButtons;
    private JPanel jpButtonLogin;
    private JPanel jpInferior;
    private JPanel jpRegistered;
    private JLabel nomcontrasenya;
    private JLabel jlNameMail;
    private JPanel jpMail;
    private JLabel jlLoginInformation;
    private JLabel jlIcon;
    private JLabel textRegistrat;
    private JTextField jtfMail;
    private JPasswordField jpfPasword;
    private JButton buttonLogin;
    private JButton Registrar;
    private ImageIcon icon;
	private AuthenticationController authenticationController = new AuthenticationController(this);
	final Taskbar taskbar = Taskbar.getTaskbar();

   
    private JLabel jlTitle;
    private JLabel jlDeviceName;
    private JLabel jlDeviceProtocol;
    private JLabel jlDeviceDescription;
    private JLabel jlDeviceLatitude;
    private JLabel jlDeviceLongitude;
    private JTextField jtfDeviceName;
	private JComboBox<String> jcbDeviceProtocol;
    private JTextField jtfDeviceDescription;
    private JTextField jtfDeviceLatitude;
    private JTextField jtfDeviceLongitude;

  

    @SuppressWarnings("serial")
    public DevicesView() {
        jpRegister = new JPanel(new GridLayout(6, 1));
        jpRegister.setBackground(new Color(235, 235, 235));
        addComponents();
        this.getContentPane().add(jpRegister);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setTitle("Robomap Cloud Client - Configuration");
        this.setVisible(true);
		setLogoTaskbar();
    }

    private void addComponents() {
        addLogoAndTitle();
        addDeviceName();
        addDeviceProtocol();
        addDeviceDescription();
        addDeviceLatitudeAndLongitude();
        addButtons();
    }
		
	private void panelUP(){
		icon = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo_white.png");
		Image iconImage = icon.getImage();
		this.setIconImage(icon.getImage());
        final Taskbar taskbar = Taskbar.getTaskbar();
		Image originalImage = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo.png").getImage();
		Image resizedImage = originalImage.getScaledInstance((int)(600.0),(int)(600.0), Image.SCALE_SMOOTH);
		
        try {
            taskbar.setIconImage(resizedImage);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
		
		iconImage = iconImage.getScaledInstance((int)(20.0),(int)(20.0), Image.SCALE_SMOOTH);
		jlIcon = new JLabel(new ImageIcon (iconImage));
		jpRegister.add(jlIcon);
	}
	
	private void loginButton(){
		jpButtonLogin = new JPanel(new GridLayout(1,3));
		jpButtonLogin.add(new JLabel());
		buttonLogin = new JButton("Login");
		buttonLogin.setFont(new Font("Century Gothic",0,12));
		jpButtonLogin.setBackground(new Color(235,235,235));;
		jpButtonLogin.add(buttonLogin);
		jpButtonLogin.add(new JLabel());
		
		this.buttonLogin = buttonLogin;
		buttonLogin.setActionCommand("LOGIN");

		if (buttonLogin != null) {
			buttonLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if ("LOGIN".equals(e.getActionCommand())) {
						String email = getUsername();
                    	String password = String.valueOf(jpfPasword.getPassword());
						
						ApiClient apiClient = new ApiClient();

						// Call the login method
						try {
							String response = apiClient.login(email, password);
							System.out.println("Login Response: " + response);


						} catch (IOException io_exception) {
							io_exception.printStackTrace();
						}

						System.out.println("Login button pressed.");
						//authenticationController.actionPerformed(e); 
						
					}
				}
			});
		}
	}
	
	private void panelDown(){
		jpContent = new JPanel (new GridLayout(5,1));
		mailInformation();
		passwordInformation();
		loginButton();
		jpContent.add(jpMail);
		jpContent.add(new JLabel());
		jpContent.setBackground(new Color(235,235,235));
		jpContent.add(jpPassword);
		jpContent.add(new JLabel());
		jpContent.add(jpButtonLogin);
		jpInferior = new JPanel(new GridLayout(2,1));
		jpInferior.add(jpContent);
		jpButtons = new JPanel(new GridLayout(4,1)); 
		jpButtons.add(new JLabel());
		jlLoginInformation = new JLabel();
		jpRegistered = new JPanel (new FlowLayout());	
		Registrar = new JButton("Register");
		Registrar.setFont(new Font("Century Gothic",0,12));
		textRegistrat = new JLabel("Configurate the devices you want to connect.");
		textRegistrat.setForeground(new Color(200,200,200));
		textRegistrat.setFont(new Font("Century Gothic",0,12));
		jpRegistered.add(textRegistrat);
		
		jpRegistered.setBackground(new Color(235,235,235));
		Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebPage(AppConfig.getRegisterUrl());
            }
        });

		jpButtons.add(jpRegistered);
		jpButtons.add(new JLabel());
		jpButtons.add(jlLoginInformation);
		jpButtons.setBackground(new Color(235,235,235));
		jpInferior.add(jpButtons);
		jpInferior.setBackground(new Color(235,235,235));
		jpRegister.add(jpInferior);
	}

	private void openWebPage(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported");
        }
    }
	
	private void mailInformation(){
		jpMail = new JPanel ();
		jlNameMail = new JLabel ("Device name");
		jlNameMail.setForeground(new Color(200,200,200));
		jlNameMail.setFont(new Font("Century Gothic",0,12));
		jpMail.add(jlNameMail);
		jtfMail = new JTextField();
		jtfMail.setFont(new Font("Century Gothic",0,11));
		jtfMail.setPreferredSize(new Dimension((int)(220.0),(int)(25.0)));
		
		jpMail.setBackground(new Color(235,235,235));
		jpMail.add(jtfMail);
	}
	
	private void passwordInformation(){
		jpPassword = new JPanel();
		nomcontrasenya = new JLabel("Protocol");
		nomcontrasenya.setForeground(new Color(200,200,200));
		nomcontrasenya.setFont(new Font("Century Gothic",0,12));
		jpPassword.add(nomcontrasenya);
		jpPassword.setBackground(new Color(235,235,235));
		jpfPasword = new JPasswordField();
		jpfPasword.setFont(new Font("Century Gothic",0,11));
		jpfPasword.setPreferredSize(new Dimension((int)(220.0),(int)(25.0)));
		jpPassword.add(jpfPasword);
	}
	
	public void registreControladorRegistra(AuthenticationController loginController){
		Registrar.addActionListener(loginController);
		Registrar.setActionCommand("REGISTRAR");
	}
	
	public void registreControladorLogin(AuthenticationController loginController){
		loginButton();

		buttonLogin.addActionListener(loginController);
		buttonLogin.setActionCommand("LOGIN");
	}
	public String GetContraenyaEntra(){
		return String.valueOf(jpfPasword.getPassword());
	}
	
	public String getUsername(){
		return jtfMail.getText();
	}
	
	public void setInformacioLogin(String InfoLogin){
		jlLoginInformation.setForeground(new Color(200,200,200));
		jlLoginInformation.setFont(new Font("Century Githic",0,12));
		jlLoginInformation.setText(InfoLogin);
	}


	private void addLogoAndTitle() {
		icon = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo_black.png");
		Image iconImage = icon.getImage();
		iconImage = iconImage.getScaledInstance((int)(40.0),(int)(40.0), Image.SCALE_SMOOTH);
		jlIcon = new JLabel(new ImageIcon(iconImage));
		jpRegister.add(jlIcon);
	
		jlTitle = new JLabel("Configuration of your first device");
		jlTitle.setFont(new Font("Century Gothic", Font.BOLD, 14));
		jpRegister.add(jlTitle);
	}
	

    private void addDeviceName() {
        jpContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jlDeviceName = new JLabel("Device name");
        jlDeviceName.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jtfDeviceName = new JTextField();
        jtfDeviceName.setPreferredSize(new Dimension(220, 25));

        jpContent.add(jlDeviceName);
        jpContent.add(jtfDeviceName);

        jpRegister.add(jpContent);
    }

    private void addDeviceProtocol() {
        jpContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jlDeviceProtocol = new JLabel("Device protocol");
        jlDeviceProtocol.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jcbDeviceProtocol = new JComboBox<>(new String[]{"Protocol 1", "Protocol 2", "Protocol 3"});
        jcbDeviceProtocol.setPreferredSize(new Dimension(220, 25));

        jpContent.add(jlDeviceProtocol);
        jpContent.add(jcbDeviceProtocol);

        jpRegister.add(jpContent);
    }

    private void addDeviceDescription() {
        jpContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jlDeviceDescription = new JLabel("Device description");
        jlDeviceDescription.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jtfDeviceDescription = new JTextField();
        jtfDeviceDescription.setPreferredSize(new Dimension(220, 25));

        jpContent.add(jlDeviceDescription);
        jpContent.add(jtfDeviceDescription);

        jpRegister.add(jpContent);
    }

    private void addDeviceLatitudeAndLongitude() {
        jpContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jlDeviceLatitude = new JLabel("Device latitude");
        jlDeviceLatitude.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jtfDeviceLatitude = new JTextField();
        jtfDeviceLatitude.setPreferredSize(new Dimension(100, 25));
        jlDeviceLongitude = new JLabel("Device longitude");
        jlDeviceLongitude.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jtfDeviceLongitude = new JTextField();
        jtfDeviceLongitude.setPreferredSize(new Dimension(100, 25));

        jpContent.add(jlDeviceLatitude);
        jpContent.add(jtfDeviceLatitude);
        jpContent.add(jlDeviceLongitude);
        jpContent.add(jtfDeviceLongitude);

        jpRegister.add(jpContent);
    }

    private void addButtons() {
        jpButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Registrar = new JButton("Skip");
        Registrar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jpButtons.add(Registrar);
        buttonLogin = new JButton("Add");
        buttonLogin.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        jpButtons.add(buttonLogin);

        jpRegister.add(jpButtons);
    }
	
	public void setLogoTaskbar() {
		final Taskbar taskbar = Taskbar.getTaskbar();
		Image originalImage = new ImageIcon("/Users/eduard5524/Library/CloudStorage/OneDrive-Personal/Business/Robomap/Development/Client-Robomap-Cloud/assets/img/logo.png").getImage();
		Image resizedImage = originalImage.getScaledInstance((int)(600.0),(int)(600.0), Image.SCALE_SMOOTH);
		
        try {
            taskbar.setIconImage(resizedImage);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
	}

	public static void main(String[] args) {
		try {
			File lockFile = new File("app.lock");
			if (!lockFile.createNewFile()) {
				System.out.println("Another instance is already running.");
				return;
			}
	
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if (lockFile.exists()) {
					lockFile.delete();
					System.out.println("Lock file deleted.");
				}
			}));
	
			SwingUtilities.invokeLater(() -> new DevicesView());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
