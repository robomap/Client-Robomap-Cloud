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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginView extends JFrame {
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
	final Taskbar taskbar = Taskbar.getTaskbar();
	@SuppressWarnings("serial")
	public LoginView() {			
		
		jpRegister = new JPanel (new GridLayout(2,1));
		jpRegister.setBackground(new Color(60,60,60));
		panelUP();
		panelDown();
		this.getContentPane().add(jpRegister);
		this.setSize(400,600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Robomap Cloud Client");
		this.setIconImage(icon.getImage());
		this.setVisible(true);
	}
		
	private void panelUP(){
		icon = new ImageIcon("/Users/eduard5524/Desktop/integration_robomap_cloud/integration_robomap_cloud/img/logo_white.png");
		Image iconImage = icon.getImage();
		this.setIconImage(icon.getImage());
        final Taskbar taskbar = Taskbar.getTaskbar();
		Image originalImage = new ImageIcon("/Users/eduard5524/Desktop/integration_robomap_cloud/integration_robomap_cloud/img/logo.png").getImage();
		Image resizedImage = originalImage.getScaledInstance((int)(600.0),(int)(600.0), Image.SCALE_SMOOTH);
		
        try {
            //set icon for mac os (and other systems which do support this method)
            taskbar.setIconImage(resizedImage);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
		iconImage = iconImage.getScaledInstance((int)(60.0),(int)(60.0), Image.SCALE_SMOOTH);
		jlIcon = new JLabel(new ImageIcon (iconImage));
		jpRegister.add(jlIcon);
	}
	
	private void loginButton(){
		jpButtonLogin = new JPanel(new GridLayout(1,3));
		jpButtonLogin.add(new JLabel());
		buttonLogin = new JButton("Login");
		buttonLogin.setFont(new Font("Century Gothic",0,12));
		jpButtonLogin.setBackground(new Color(60,60,60));;
		jpButtonLogin.add(buttonLogin);
		jpButtonLogin.add(new JLabel());
	}
	
	private void panelDown(){
		jpContent = new JPanel (new GridLayout(5,1));
		mailInformation();
		passwordInformation();
		loginButton();
		jpContent.add(jpMail);
		jpContent.add(new JLabel());
		jpContent.setBackground(new Color(60,60,60));
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
		textRegistrat = new JLabel("You don't have an account? ");
		textRegistrat.setForeground(new Color(200,200,200));
		textRegistrat.setFont(new Font("Century Gothic",0,12));
		jpRegistered.add(textRegistrat);
		jpRegistered.add(Registrar);
		jpRegistered.setBackground(new Color(60,60,60));
		Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebPage("https://cloud.robomap.ai/auth/register");
            }
        });

		
		jpButtons.add(jpRegistered);
		jpButtons.add(new JLabel());
		jpButtons.add(jlLoginInformation);
		jpButtons.setBackground(new Color(60,60,60));
		jpInferior.add(jpButtons);
		jpInferior.setBackground(new Color(60,60,60));
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
		jlNameMail = new JLabel ("Username");
		jlNameMail.setForeground(new Color(200,200,200));
		jlNameMail.setFont(new Font("Century Gothic",0,12));
		jpMail.add(jlNameMail);
		jtfMail = new JTextField();
		jtfMail.setFont(new Font("Century Gothic",0,11));
		jtfMail.setPreferredSize(new Dimension((int)(220.0),(int)(25.0)));
		
		jpMail.setBackground(new Color(60,60,60));
		jpMail.add(jtfMail);
	}
	
	private void passwordInformation(){
		jpPassword = new JPanel();
		nomcontrasenya = new JLabel("Password");
		nomcontrasenya.setForeground(new Color(200,200,200));
		nomcontrasenya.setFont(new Font("Century Gothic",0,12));
		jpPassword.add(nomcontrasenya);
		jpPassword.setBackground(new Color(60,60,60));
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
	
	public static void main(String[] args) {
		try {
			File lockFile = new File("app.lock");
			if (!lockFile.createNewFile()) {
				System.out.println("Another instance is already running.");
				return;
			}
	
			SwingUtilities.invokeLater(() -> new AuthenticationView());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}