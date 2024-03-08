package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Dibuix;
import model.Song;
import model.User;
import Controller.ControllerServer;

@SuppressWarnings("serial")
public class ViewServer extends JFrame {
	private static final String[] SONG_NAMES = {"NOM", "GENERE", "ALBUM", "ARTISTA", "PATH"};
	private static final String[] USER_NAMES = {"NOM", "DATA REGISTRE", "DATA ACCES", "NUM LLISTES", "NUM SONGS", "FOLLOWING", "FOLLOWERS"};
	private String[] USER_NAMES_FOLLOWERS = {"NOM", "DATA REGISTRE", "DATA ACCES", "NUM LLISTES", "NUM SONGS", "FOLLOWING", "FOLLOWERS"};
	private DefaultTableModel songs;
	private DefaultTableModel users;
	private JPanel songsPanel;
	private JPanel usersPanel;
	private JTable songsTable;
	private JTable usersTable;
	private JPanel p_A;
	private JPanel p_E;
	private JPanel frame;
	private JTabbedPane tabs1;
	private JTabbedPane tabs2;
	private JPopupMenu popup;
	private JTextField nameText;
	private JTextField genereText;
	private JTextField albumText;
	private JButton pathButton;
	private JTextField pathText;
	private JTextField artistText;
	private JLabel name = new JLabel("           Nom");
	private JLabel genere = new JLabel("           Genere");
	private JLabel album = new JLabel("           Album");
	private JLabel artista = new JLabel("           Artista");
	private ControllerServer ctrl;
	List<Song> listSongs = new ArrayList<Song> ();
	private JProgressBar jProgressBar;
	private JButton jbPlay;
	private JButton jbStop;
	private DefaultTableModel followersModel;
	private JPanel followersPanel;
	private List<User> followers = new ArrayList<User> ();
	private JPanel panelUsers;
	
	public ViewServer(ControllerServer ctrl) {		
		this.ctrl = ctrl;

		frame = createFrame();

		p_A = createAditionPanel(ctrl);
		p_E = new JPanel();
		
		
		Dibuix d = new Dibuix(ctrl);	

		p_E.add(d);
		
		createSongsTable();
		createUserTablePanel();
		tabs1 = createTabbedPane();
	    getContentPane().add(tabs1);
		getContentPane().add(frame, BorderLayout.NORTH);
        configureWindow();	
	}
	
	public void update (){
		createUserTablePanel();
		tabs1 = createTabbedPane();
	    getContentPane().add(tabs1);
	}
	
	public void reproductor(){
		ImageIcon play = new ImageIcon("./img/play.png");
		Image auxPlay = play.getImage();
		Image tamanyPlay = auxPlay.getScaledInstance(40,40, Image.SCALE_SMOOTH);
		play = new ImageIcon (tamanyPlay);
		
		jbPlay = new JButton(play);
		jbPlay.setBackground(new Color(60,60,60));
		jbPlay.setBorder(BorderFactory.createEmptyBorder());
		jbPlay.addActionListener(ctrl.getPlay());
		
		ImageIcon stop = new ImageIcon("./img/stop.png");
		Image auxStop = stop.getImage();
		Image tamanyStop = auxStop.getScaledInstance(40,40, Image.SCALE_SMOOTH);
		stop = new ImageIcon (tamanyStop);
		
		jbStop = new JButton(stop);
		jbStop.setBackground(new Color(60,60,60));
		jbStop.setBorder(BorderFactory.createEmptyBorder());
		jbStop.addActionListener(ctrl.getStop());
		
		jProgressBar = new JProgressBar();
		jProgressBar.setPreferredSize(new Dimension(600,10));
		jProgressBar.setBorderPainted(false);
		jProgressBar.setBackground(new Color (40,40,40));
		jProgressBar.setForeground(Color.GREEN);
		jProgressBar.setValue(0);
		
		JPanel jpReproductor = new JPanel();
		jpReproductor.setBackground(new Color(60,60,60));
		jpReproductor.add(jbPlay);
		jpReproductor.add(jbStop);
		jpReproductor.add(jProgressBar);
		jbPlay.setVisible(true);
		jbStop.setVisible(false);
	    getContentPane().add(jpReproductor, BorderLayout.SOUTH);
	}
	
	public void setMaximumProgressBar(int i){
		jProgressBar.setMaximum(i);
	}
	
	public void showErrorPlay(String name){
		JOptionPane.showMessageDialog(new JFrame(), "Error al reproducir la cancion: " + name);
	}
	
	public JButton getJbPlay() {
		return jbPlay;
	}

	public void setJbPlay(JButton jbPlay) {
		this.jbPlay = jbPlay;
	}

	public JButton getJbStop() {
		return jbStop;
	}

	public void setJbStop(JButton jbStop) {
		this.jbStop = jbStop;
	}

	public JProgressBar getjProgressBar() {
		return jProgressBar;
	}

	public void setjProgressBar(double i,  long totalTime, long timeRead){
		jProgressBar.setValue((int)i);
	}

	public JPanel createAditionPanel(ControllerServer ctrl) {
		JPanel p_A = new JPanel();
		GridLayout infoAdition = new GridLayout (13,3,100,0);
		p_A.setLayout(infoAdition);
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		name.setForeground(Color.WHITE);
		p_A.add(name);
		nameText = new JTextField();
		p_A.add(nameText);
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		genere.setForeground(Color.WHITE);
		p_A.add(genere);
		genereText = new JTextField();
		p_A.add(genereText);
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		album.setForeground(Color.WHITE);
		p_A.add(album);
		albumText = new JTextField();
		p_A.add(albumText);
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		artista.setForeground(Color.WHITE);
		p_A.add(artista);
		artistText = new JTextField();
		p_A.add(artistText);
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		pathButton = new JButton("Path");		
		p_A.add(pathButton);
		pathText = new JTextField();
		p_A.add(pathText);
		pathButton.addActionListener(ctrl.getOnExaminate());
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		p_A.add(new JLabel());
		JButton addSong = new JButton("Afegeix");
		p_A.add(addSong);
		addSong.addActionListener(ctrl.getOnAdd());
		p_A.setBackground(new Color(60,60,60));		
		return p_A;	
	}
	
	public void setNameText(JTextField nameText) {
		this.nameText = nameText;
	}

	public void setGenereText(JTextField genereText) {
		this.genereText = genereText;
	}

	public void setAlbumText(JTextField albumText) {
		this.albumText = albumText;
	}

	public void setArtistText(JTextField artistText) {
		this.artistText = artistText;
	}

	public void setPathText(JTextField pathText) {
		this.pathText = pathText;
	}

	public JPanel createFrame() {
		frame = new JPanel();
		frame.setPreferredSize(new Dimension(200,100));
		frame.setLayout(new GridLayout(1,1));
		ImageIcon image = new ImageIcon("./img/Simbol.png");
		ImageIcon image_simbol = new ImageIcon("./img/Simbol.png");
		this.setIconImage(image_simbol.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH );
		this.setMinimumSize(new Dimension(800,600));
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		ImageIcon icon = new ImageIcon("./img/Simbol.png");
		this.setIconImage(icon.getImage());
		JLabel label = new JLabel("", image, JLabel.CENTER);
		frame.add( label, BorderLayout.CENTER );
		frame.setBackground(new Color(60,60,60));
		return frame;
		
	}
	
	public JTabbedPane createTabbedPane() {
		tabs2 = new JTabbedPane();
		tabs1 = new JTabbedPane();
		tabs2.add("Llista + Elimina", songsPanel);
        tabs2.add("Addicio", p_A);
        tabs2.add("Estadistiques", p_E);
        tabs1.add("Gestio Musica", tabs2);
        tabs1.add("Gestio Usuaris", panelUsers);
        return tabs1;     
	}
	
	public void updateSongs(String[][] data) {
		songs.setDataVector(data, SONG_NAMES);
	}
	
	public void updateUsers(String[][] data) {
		users.setDataVector(data, USER_NAMES);
	}
	
	public void createSongsTable() {

		songs = new DefaultTableModel(new String[][]{}, SONG_NAMES){
			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		};
		
		songsTable = new JTable(songs);
		songsTable.setFillsViewportHeight(true);
		
		songsTable.addMouseListener(ctrl.getOnMouse());
		
		songsPanel = new JPanel();
		songsPanel.setLayout(new BorderLayout());
		songsPanel.add(new JScrollPane(songsTable));
	}
	

//	String[] columnNames2, String[][] data2
	public void createUserTablePanel() {
		String[][] data = null;
		users = new DefaultTableModel(data, USER_NAMES) {
		public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
		}
		};
		usersTable = new JTable(users);
		usersTable.setFillsViewportHeight(true);
		
		usersPanel = new JPanel();
		usersPanel.setLayout(new BorderLayout());
		usersPanel.add(new JScrollPane(usersTable));
		
		usersTable.addMouseListener(ctrl.getOnMouseUser());
		
		panelUsers = new JPanel();
		panelUsers.setLayout(new GridLayout(2,1));
		panelUsers.add(usersPanel);
		followersModel = new DefaultTableModel(new String[][]{}, USER_NAMES_FOLLOWERS) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		};
		
		JTable followersTable = new JTable(followersModel);
		followersTable.setFillsViewportHeight(true);
		followersTable.addMouseListener(ctrl.getOnMouserUserFollowers());
		followersPanel = new JPanel();
		followersPanel.setLayout(new BorderLayout());
		followersPanel.add(new JScrollPane(followersTable)); 
		panelUsers.add(followersPanel); 
	}
	
	public void configureWindow() {
		setSize(650,500);
		setTitle("Spotify Server");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	
	public void showPopupMenu(int x, int y, int id) {	
		popup = new JPopupMenu();
		if(id == 1){
			JMenuItem menu = new JMenuItem("Elimina");
			popup.add(menu);
			popup.show(songsPanel, x, y);
			menu.addMouseListener(ctrl.getOnDelete());
		}
		else{
			if(id == 2){
				JMenuItem menu = new JMenuItem("Veure Followers");
				JMenuItem userMenu = new JMenuItem("Elimina Usuari");
				popup.add(menu);
				popup.add(userMenu);
				popup.show(usersPanel, x, y);
				menu.addMouseListener(ctrl.getOnFollowers());
				userMenu.addMouseListener(ctrl.getOnUserDelete());
			}
		}
		if(id == 3){
			JMenuItem userMenu = new JMenuItem("Elimina Usuari");
			popup.add(userMenu);
			popup.show(usersPanel, x, y);
			userMenu.addMouseListener(ctrl.getOnUserDelete());
		}		
	}
	
	public int returnSelectedRow() {
		return songsTable.getSelectedRow();
	}
	
	public String getNameText() {	
		return nameText.getText();
	}
	
	public String getGenereText() {
		return genereText.getText();
	}
	
	public String getAlbumText() {
		return albumText.getText();
	}
	
	public String getArtistText() {	
		return artistText.getText();	
	}
	
	public String getPathText() {
		String s = pathText.getText();
		String path = "";
		for(int i = 0; i < s.length(); i++){
			if(s.codePointAt(i) == 92){
				path = path + "/";
			}else{
				path = path + s.charAt(i);
			}
		}
		return path;
	}
	
	public void setPathText(String text) {
		pathText.setText(text);
		pathText.setEditable(false);
	}
	
	public void clearFields(){
		nameText.setText("");
		genereText.setText("");
		albumText.setText("");
		artistText.setText("");
		pathText.setText("");
	}

	public void setPlay() {
		jbPlay.setVisible(false);
		jbStop.setVisible(true);
	}

	public void setStop() {
		// TODO Auto-generated method stub
		jbPlay.setVisible(true);
		jbStop.setVisible(false);
	}

	public void viewFollowers(User user, ControllerServer ctrl){
		followers = ctrl.getFollowers(user.getUserName());
		System.out.println(followers);
		createFollowersTablePanel();
	}
	
	
	public void updateFollowers(String[][] dataFollowers) {
		followersModel.setDataVector(dataFollowers, USER_NAMES_FOLLOWERS);
	}
	
	public void createFollowersTablePanel() {	

	}
	
	public List<User> getFollowers() {
		return this.followers;
	}
}
