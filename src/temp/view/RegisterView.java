package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import temp.controller.RegisterController;

@SuppressWarnings("serial")
public class RegisterView extends JFrame{
	private JPanel nouRegistre;
	private JPanel partInferior;
	private JPanel PosBoto;
	private JLabel icn;
	private JPanel panelNick;
	private JTextField Nick;
	private JLabel IntroNick;
	private JPanel panelCorreu;
	private JTextField NouCorreu;
	private JLabel IntroCorreu;
	private JPanel panelContrasenya;
	private JPasswordField NouContrasenya;
	private JLabel IntroContrasenya;
	private ImageIcon icon;
	private Image Spoti;
	private Image tamany;
	private ImageIcon fin;
	private JLabel OmplirRespostaRegistre;
	private JButton Registrat;
	@SuppressWarnings("unused")
	private double width;
	@SuppressWarnings("unused")
	private double height;
	@SuppressWarnings("static-access")
	public RegisterView(){
		width = screenWidth();
		height = screenHeight();
		
		nouRegistre= new JPanel (new GridLayout(2,1));
		Logo();
		OmplirDades();
		//nouRegistre.add(new JLabel());
		nouRegistre.add(partInferior);
		nouRegistre.setBackground(new Color(60,60,60));
		//aqui establim les condicions del JFrame
		this.setResizable(false);
		this.getContentPane().add(nouRegistre);
		this.setSize(400,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Registra't a Spotify");
		this.setIconImage(icon.getImage());
		
	}
	
	public void exitRegister(){
		JOptionPane.showMessageDialog(new JFrame(),"Usuari registrat correctament!");
		this.setVisible(false);
	}
	
	public double screenWidth(){
		@SuppressWarnings("unused")
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (screenSize.getWidth());
	}
	
	public double screenHeight(){
		@SuppressWarnings("unused")
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (screenSize.getHeight());
	}
	
	public void Logo(){
		//posem licone
		icon = new ImageIcon("./img/Simbol.png");
		Spoti = icon.getImage();
		tamany = Spoti.getScaledInstance(170,170, Image.SCALE_SMOOTH);
		fin = new ImageIcon (tamany);
		icn = new JLabel(fin);
		nouRegistre.add(icn);
	}
	
	public void OmplirDades(){
		//dividim la part iferior en 4 grids
		partInferior= new JPanel(new GridLayout(4,1));
		//aqui registrem el nick
		RegistraNick();
		//aqui el correu
		RegistraCorreu();
		//aqui la contrasenya
		RegistraContrasenya();
		//aqui el boto
		BotoRegistre();
		//aqui el color del background 
		partInferior.setBackground(new Color(60,60,60));
	}
	
	public void RegistraNick(){
		// ens creem un panel per la part que volem insertar el nick
		panelNick= new JPanel();
		// prinetem el nom d'usuari
		IntroNick = new JLabel("Nom d'usuari: ");
		IntroNick.setForeground(new Color(200,200,200));
		IntroNick.setFont(new Font("Century Gothic",0,12));
		// aqui es on lusuari introduira el nick
		Nick = new JTextField(); 
		// poem la mida del text area
		Nick.setPreferredSize(new Dimension(220,25));
		Nick.setFont(new Font("Century Gothic",0,11));
		//all inserim al jpanel les coses que hem creat
		panelNick.add(IntroNick);
		panelNick.add(Nick);
		//canviem color
		panelNick.setBackground(new Color(60,60,60));
		//all ho inserim al panel
		partInferior.add(panelNick);	
	}
	
	public void RegistraCorreu(){
		panelCorreu= new JPanel();
		IntroCorreu = new JLabel("Correu:            ");
		IntroCorreu.setFont(new Font("Century Gothic",0,12));
		IntroCorreu.setForeground(new Color(200,200,200));
		NouCorreu = new JTextField(); 
		NouCorreu.setPreferredSize(new Dimension(220,25));
		NouCorreu.setFont(new Font("Century Gothic",0,11));
		panelCorreu.add(IntroCorreu);
		panelCorreu.add(NouCorreu);
		panelCorreu.setBackground(new Color(60,60,60));
		partInferior.add(panelCorreu);
	}
	
	public void RegistraContrasenya(){
		//introduim la contrasenya
		panelContrasenya= new JPanel();
		IntroContrasenya = new JLabel("Contrasenya:  ");
		IntroContrasenya.setFont(new Font("Century Gothic",0,12));
		IntroContrasenya.setForeground(new Color(200,200,200));
		NouContrasenya = new JPasswordField(); 
		NouContrasenya.setPreferredSize(new Dimension(220,25));
		NouContrasenya.setFont(new Font("Century Gothic",0,11));
		panelContrasenya.add(IntroContrasenya);
		panelContrasenya.add(NouContrasenya);
		panelContrasenya.setBackground(new Color(60,60,60));
		partInferior.add(panelContrasenya);
		new JLabel();
	}
	
	public void BotoRegistre(){
		//introduim el boto
		PosBoto = new JPanel (new GridLayout(3,3));
		Registrat= new JButton ("Registra't");
		OmplirRespostaRegistre = new JLabel ();
		OmplirRespostaRegistre.setForeground(new Color(200,200,200));
		OmplirRespostaRegistre.setFont(new Font("Century Gothic",0,12));
		PosBoto.add(new JLabel());
		PosBoto.add(OmplirRespostaRegistre);
		PosBoto.add(Registrat);
		PosBoto.add(new JLabel());
		PosBoto.add(new JLabel());
		PosBoto.add(new JLabel());
		PosBoto.add(new JLabel());
		PosBoto.add(new JLabel());
		PosBoto.add(new JLabel());
		Registrat.setFont(new Font("Century Gothic",0,12));
		
		PosBoto.setBackground(new Color(60,60,60));
		partInferior.add(PosBoto);

	}
	
	public void registreControladorRegistrat (RegisterController control){
		//es laccio que escolta
		Registrat.addActionListener(control);
		Registrat.setActionCommand("REGISTRAR-SE");
	}
	
	public String GetContrasenyaRegistre(){
		//obtenim el text de contrasennya
		return String.valueOf(NouContrasenya.getPassword());
	}
	
	public String GetNickRegistre(){
		//obtenim els textos
		return Nick.getText();
	}
	
	public String GetCorreuRegistre(){
		// obtenim Correu
		return NouCorreu.getText();
	}
	
	public void SetOmplirRespostaRegistre(String OmplirRespostaR){
		// omplim les dades de que s'ha liat
		//OmplirRespostaRegistre.setFont(new Font("Century Gothic",0,12));
		//OmplirRespostaRegistre.setForeground(new Color(200,200,200));		
		OmplirRespostaRegistre.setText(OmplirRespostaR);
	}
	
}
