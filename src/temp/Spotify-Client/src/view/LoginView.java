package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Image;
import javax.swing.*;

import controller.LoginController;

@SuppressWarnings("serial")
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
	private JLabel jlLoginInformation ;
	private JLabel jlIcon;
	private JLabel textRegistrat;
	private JTextField jtfMail ;
	private JPasswordField jpfPasword;
	private JButton buttonLogin;
	private JButton Registrar;
	private ImageIcon icon;

	@SuppressWarnings("static-access")
	public LoginView(){		
		jpRegister = new JPanel (new GridLayout(2,1));
		jpRegister.setBackground(new Color(60,60,60));
		panelUP();
		panelDown();
		this.getContentPane().add(jpRegister);
		this.setSize(400,600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Spotify");
		this.setIconImage(icon.getImage());
		this.setVisible(true);
	}
	
	private void panelUP(){
		icon = new ImageIcon("./img/Simbol.png");
		Image iconImage = icon.getImage();
		iconImage = iconImage.getScaledInstance((int)(170.0),(int)(170.0), Image.SCALE_SMOOTH);
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
		Registrar = new JButton("Registra't");
		Registrar.setFont(new Font("Century Gothic",0,12));
		textRegistrat = new JLabel("No tens compte? ");
		textRegistrat.setForeground(new Color(200,200,200));
		textRegistrat.setFont(new Font("Century Gothic",0,12));
		jpRegistered.add(textRegistrat);
		jpRegistered.add(Registrar);
		jpRegistered.setBackground(new Color(60,60,60));
		jpButtons.add(jpRegistered);
		jpButtons.add(new JLabel());
		jpButtons.add(jlLoginInformation);
		jpButtons.setBackground(new Color(60,60,60));
		jpInferior.add(jpButtons);
		jpInferior.setBackground(new Color(60,60,60));
		jpRegister.add(jpInferior);
	}
	
	private void mailInformation(){
		jpMail = new JPanel ();
		jlNameMail = new JLabel ("Nom d'usuari :");
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
		nomcontrasenya = new JLabel("Contrasenya: ");
		nomcontrasenya.setForeground(new Color(200,200,200));
		nomcontrasenya.setFont(new Font("Century Gothic",0,12));
		jpPassword.add(nomcontrasenya);
		jpPassword.setBackground(new Color(60,60,60));
		jpfPasword = new JPasswordField();
		jpfPasword.setFont(new Font("Century Gothic",0,11));
		jpfPasword.setPreferredSize(new Dimension((int)(220.0),(int)(25.0)));
		jpPassword.add(jpfPasword);
	}
	
	public void registreControladorRegistra(LoginController loginController){
		Registrar.addActionListener(loginController);
		Registrar.setActionCommand("REGISTRAR");
	}
	
	public void registreControladorLogin(LoginController loginController){
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
}
