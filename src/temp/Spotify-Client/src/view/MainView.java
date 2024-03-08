package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import controller.MainController;
import model.Song;

import model.TimeManager;
import model.User;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class MainView extends JFrame {	
	private JMenuBar barraMenu;
	private JPopupMenu jpmArchivos;
	private JPopupMenu jpmUsuarios;
	private JPopupMenu jpmNuevaPlaylist;
	private JPopupMenu jpmPlaylist;
	private JMenu menu;
	private JMenu NickMenu;
	private JMenu puntuar;
	private JMenuItem menuItemNuevaLista;
	private JMenu playlist;
	private JMenuItem menuItemNombreUsuario;
	private JMenuItem menuItemPause;
	private JMenuItem menuItemSalir;
	private JMenuItem menuItemBuscar;
	private JMenuItem menuItemZoom;
	private JMenuItem menuItemAlejar;
	private JMenuItem menuItemAcercar;
	private JMenuItem playlistItemEliminar;
	private JButton Play;
	private JButton Enrera;
	private JButton Davant;
	private JButton Stop;
	private JButton explorarButton;
	private JButton archivosButton;
	private JButton actividadButton;
	private JButton radioButton;
	private JButton cancionesButton;
	private JButton albumesButton;
	private JButton emisorasButton;
	private JButton pauseArchivosButton;
	private JButton buscarButton;
	private JButton reproducirArchivosButton;
	private JButton abrirCarpetaButton;
	private JButton FletxaDownButton;
	private JButton pauseButtonCanciones;
	private JButton reproducirButtonCanciones;
	private JButton nuevaPlaylistButton;
	private JButton searchUser;
	private JProgressBar barraReproduccio;
	private JPanel PanellReproduccio;
	private JPanel jpList;
	private JPanel jpCenter;
	private JPanel Center;
	private JPanel jpCenterarchivos;
	private JLabel jlMensajeError;
	private JLabel tempsReproduccio;
	private JLabel jlWelcome;
	private JLabel imageAlbumCanciones;
	private JLabel jlLine;
	private JLabel jlWorking;
	private JLabel jlNameArchivos;
	private JLabel imageAlbumArchivos;
	private JLabel jlNameCanciones;
	private JLabel imageLineArchivos;
	private JLabel jlUser;
	private JLabel nombreCancion;
	private JLabel tempsfinalReproduccio;
	private JLabel jlError;
	private Image auxLine1;
	private Image auxLine2;
	private Image auxLine3;
	private Image auxLineArchivos1;
	private Image auxLineArchivos2;
	private Image auxLineArchivos3;
	private Image auxAlbum1;
	private Image auxAlbum2;
	private Image auxAlbumArchivos1;
	private Image auxAlbumArchivos2;
	private DefaultTableModel tableModelarchivos;
	private DefaultTableModel tableModelCanciones;
	private DefaultTableModel tableModelUsername;
	private double width;
	private double height;
	private JTextField auxTextField_1;
	private JTextField auxTextField_2;
	private JScrollPane jspList;
	private String username;
	private JTextField jtfBuscador;
	private JTextField jtfBuscadorUsuario;
	private JTable jTableCanciones;
	private JTable jTableArchivos;
	private JTable jTableUsers;
	private JDialog jdPlaylist;
	private LinkedList<PlayListButton> listPlaylists;
	private int playlistSelected = 0;
	private DefaultTableModel tableModelFollowing;
	private JTable jTableFollowers;

	public int getPlaylistSelected() {
		return playlistSelected;
	}

	public void setPlaylistSelected(int playlistSelected) {
		this.playlistSelected = playlistSelected;
	}

	public MainView(){
		listPlaylists = new LinkedList<PlayListButton>();
		setWidthHeight();
		startPanel();
		leftPanel();
		centerPanel();
		centerPanelInicio();
		centerPanelCanciones();
		centerPanelArchivosLocales();
		centerPanelEnProceso();
		rightPanel();
		endPanel();
		jFrameConfiguration();
		//Activem l'actualitzacio del programa
		centerPanelCancionesVisible();
		centerPanelInicio();
		centerPanelInicioVisible();	
	}
	
	public void setWidthHeight(){
		width = screenWidth();
		height = screenHeight();
	}
	
	public void registreWindow(MainController mainController){
		this.addWindowListener(mainController);
	}
	
	public void jFrameConfiguration(){
		this.add(Center, BorderLayout.CENTER);
		this.getContentPane().setBackground(new Color(30,30,30));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH );
		this.setMinimumSize(new Dimension(700,600));
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setFont(new java.awt.Font("Century Gothic",0,11));
		this.setTitle("Spotify");
		ImageIcon icon = new ImageIcon("./img/Simbol.png");
		this.setIconImage(icon.getImage());
	}
	
	public void setUsername(String username){
		this.username = username;
		jlUser.setText(username + " ");
	}

	public String getUsername(){
		return username;
	}
	
	public void phregistreBuscarUsuari (MainController mainController){
		jtfBuscadorUsuario.addFocusListener(mainController.getBuscadorUsuario());
	}
	
	public void phregistreBuscar(MainController mainController){
		jtfBuscador.addFocusListener(mainController.getBuscador());
	}
	
	public String getJtfBuscador() {
		return jtfBuscador.getText();
	}
	public void setJtfBuscador(String text) {
		jtfBuscador.setText(text);
	}
	public String getJtfBuscadorUsuario() {
		return jtfBuscadorUsuario.getText();
	}
	public void setJtfBuscadorUsuario(String text) {
		jtfBuscadorUsuario.setText(text);
	}
	
	@SuppressWarnings("rawtypes")
	public void centerPanelArchivosLocales(){
		JPanel jpUParchivos = new JPanel(new BorderLayout());
		JPanel jpDOWNarchivos = new JPanel(new BorderLayout());
		jpUParchivos.setBackground(new Color(60,60,60));
		jpDOWNarchivos.setBackground(new Color(60,60,60));
	   
		ImageIcon album = new ImageIcon("./img/Album.png");
		auxAlbumArchivos1 = album.getImage();
		auxAlbumArchivos1 = auxAlbumArchivos1.getScaledInstance((int)(380.0/1920.0*width), (int)(300.0/1920.0*width), Image.SCALE_SMOOTH);
		auxAlbumArchivos2 = auxAlbumArchivos1.getScaledInstance((int)(190.0/1920.0*width), (int)(150.0/1920.0*width), Image.SCALE_SMOOTH);
		imageAlbumArchivos = new JLabel(new ImageIcon (auxAlbumArchivos1));
		
		JPanel jpPageStartArchivos = new JPanel();
	    jpPageStartArchivos.setBackground(new Color(60,60,60));
		
	    jlNameArchivos = new JLabel();
		jlNameArchivos.setText("Archivos Locales");
		jlNameArchivos.setForeground(Color.WHITE);
		jlNameArchivos.setFont(new java.awt.Font("Century Gothic",0,(int)(55.0/1920.0*width)));
		
		ImageIcon pauseArchivos = new ImageIcon("./img/Pause.png");
		Image auxPauseArchivos = pauseArchivos.getImage();
		auxPauseArchivos = auxPauseArchivos.getScaledInstance((int)(150.0/1920.0*width), (int)(30.0/1920.0*width), Image.SCALE_SMOOTH);
		pauseArchivosButton = new JButton(new ImageIcon (auxPauseArchivos));
		pauseArchivosButton.setBackground(null);
		pauseArchivosButton.setBorder(BorderFactory.createEmptyBorder());
		pauseArchivosButton.setVisible(false);
		
		ImageIcon reproducirArchivos = new ImageIcon("./img/Reproducir.png");
		Image auxReproducirArchivos = reproducirArchivos.getImage();
		auxReproducirArchivos = auxReproducirArchivos.getScaledInstance((int)(150.0/1920.0*width), (int)(30.0/1920.0*width), Image.SCALE_SMOOTH);
		reproducirArchivosButton = new JButton(new ImageIcon (auxReproducirArchivos));
		reproducirArchivosButton.setBackground(null);
		reproducirArchivosButton.setBorder(BorderFactory.createEmptyBorder());			
		
		ImageIcon abrirCarpeta = new ImageIcon("./img/abrirCarpeta.png");
		Image auxAbrirCarpeta = abrirCarpeta.getImage();
		auxAbrirCarpeta = auxAbrirCarpeta.getScaledInstance((int)(150.0/1920.0*width), (int)(30.0/1920.0*width), Image.SCALE_SMOOTH);
		abrirCarpetaButton = new JButton(new ImageIcon (auxAbrirCarpeta));
		abrirCarpetaButton.setBackground(null);
		abrirCarpetaButton.setBorder(BorderFactory.createEmptyBorder());
		abrirCarpetaButton.setVisible(true);
		
		JPanel jpReproducir = new JPanel();
		jpReproducir.setBackground(new Color(60,60,60));
		jpReproducir.add(pauseArchivosButton);
		jpReproducir.add(reproducirArchivosButton);	 
		
		jpReproducir.setPreferredSize(new Dimension((int)(150.0/1920.0*width),(int)(30.0/1920.0*width)));
		jpPageStartArchivos.setLayout(new BorderLayout());
		
		JPanel jpReproducirArchivos = new JPanel();
		jpReproducirArchivos.setBackground(new Color(60,60,60));
		jpReproducirArchivos.add(pauseArchivosButton);
		jpReproducirArchivos.add(reproducirArchivosButton);
		jpReproducirArchivos.add(imageAlbumArchivos);
		jpReproducirArchivos.setPreferredSize(new Dimension((int)(150.0/1920.0*width),(int)(30.0/1920.0*width)));
		jpPageStartArchivos.setLayout(new BorderLayout());
		jpPageStartArchivos.add(imageAlbumArchivos, BorderLayout.WEST);
		JPanel jpAux = new JPanel(new BorderLayout());
		jpAux.add(jpReproducirArchivos,BorderLayout.CENTER);
		jpAux.add(abrirCarpetaButton,BorderLayout.PAGE_START);
		jpAux.setBackground(new Color(60,60,60));
		
		JPanel jpCancionesArchivos = new JPanel(new BorderLayout());
		jpCancionesArchivos.add(jlNameArchivos,BorderLayout.PAGE_START);
		jpCancionesArchivos.add(jpAux, BorderLayout.WEST);
		jpCancionesArchivos.setBackground(new Color(60,60,60));
		
		jpPageStartArchivos.add(jpCancionesArchivos, BorderLayout.CENTER);
		
		ImageIcon line = new ImageIcon("./img/line-01.png");
		auxLineArchivos1 = line.getImage();
		auxLineArchivos1 = auxLineArchivos1.getScaledInstance((int)(1200.0/1920.0*width), 50, Image.SCALE_SMOOTH);
		auxLineArchivos2 = auxLineArchivos1.getScaledInstance((int)(900.0/1920.0*width), 50, Image.SCALE_SMOOTH);
		auxLineArchivos3 = auxLineArchivos1.getScaledInstance((int)(410.0/1920.0*width), 30, Image.SCALE_SMOOTH);
		imageLineArchivos = new JLabel(new ImageIcon (auxLineArchivos1));
		jpPageStartArchivos.add(imageLineArchivos, BorderLayout.PAGE_END);
		jpUParchivos.add(jpPageStartArchivos, BorderLayout.PAGE_START);
		
		Object [][] dataCancionesArchivos = null;
		
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
				java.lang.String.class,
		};
		String[] header = {"Cancion", "Artista"};;
		tableModelarchivos = new DefaultTableModel(dataCancionesArchivos, header) {
		    Class[] tipos = tipoColumnas;
		    
		    @SuppressWarnings("unchecked")
			@Override
		    public Class getColumnClass(int ColumnIndex){
		    	return tipos[ColumnIndex];
		    }
		    
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		jTableArchivos = new JTable();
		jTableArchivos.getTableHeader().setBackground(new Color(60,60,60));
		jTableArchivos.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		jTableArchivos.getTableHeader().setForeground(new Color(200,200,200));
		jTableArchivos.getTableHeader().setFont(new Font("Century Gothic",0,18));
		jTableArchivos.getTableHeader().setOpaque(false);
		jTableArchivos.setModel(tableModelarchivos);
		jTableArchivos.setForeground(new Color(200,200,200));
		jTableArchivos.setFont(new Font("Century Gothic",0,15));
		jTableArchivos.setBorder(BorderFactory.createEmptyBorder());
		jTableArchivos.setBackground(new Color(60,60,60));
		jTableArchivos.setShowVerticalLines(false);
		jTableArchivos.setShowHorizontalLines(false);
		jTableArchivos.setCellSelectionEnabled(false);
		jTableArchivos.setRowHeight(50);
		TableCellRenderer baseRenderer = jTableArchivos.getTableHeader().getDefaultRenderer();
		jTableArchivos.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		jTableArchivos.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane tablearchivos = new JScrollPane(jTableArchivos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablearchivos.setBorder(BorderFactory.createEmptyBorder());
		tablearchivos.setBackground(new Color(60,60,60));
		tablearchivos.getVerticalScrollBar().setOpaque(false);
		tablearchivos.getVerticalScrollBar().setUnitIncrement(16);
		tablearchivos.getViewport().setBackground(new Color(60,60,60));
		
		JPanel jpTablearchivos = new JPanel();
		jpTablearchivos.setBackground(new Color(60,60,60));
		jpTablearchivos.add(tablearchivos);
		jpDOWNarchivos.add(new JLabel("                      "), BorderLayout.LINE_START);
		jpDOWNarchivos.add(new JLabel("                         "), BorderLayout.LINE_END);
		jpDOWNarchivos.add(new JLabel("                      "), BorderLayout.PAGE_END);
		jpDOWNarchivos.add(tablearchivos,BorderLayout.CENTER);
		
		jpCenterarchivos = new JPanel(new BorderLayout());
		jpCenterarchivos.setBackground(new Color(60,60,60));
		jpCenterarchivos.add(jpUParchivos,BorderLayout.PAGE_START);
		jpCenterarchivos.add(jpDOWNarchivos,BorderLayout.CENTER);		
	}
	
	public class TableHeaderRenderer implements TableCellRenderer {
	    private final TableCellRenderer baseRenderer;
	    public TableHeaderRenderer(TableCellRenderer baseRenderer) {
	        this.baseRenderer = baseRenderer;
	    }
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        JComponent c = (JComponent)baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBorder(new EmptyBorder(2,2,2,2));
	        return c;
	    }
	}

	public void actualizarTableUsers(LinkedList<User> usersList,String searchUserName){
		try{
			boolean trobat = false;
			for (int i=0; i< usersList.size() && !trobat; i++){
				if (searchUserName.equals(usersList.get(i).getUserName())){
					trobat=true;
				}
			}
			if (trobat){
				Object [][] dataUsers = null;
				dataUsers = new String[1][1];
				String[] header = {"Usuario"};;
				dataUsers[0][0] = searchUserName;
				tableModelUsername.setDataVector(dataUsers, header);
			}
		}catch(NullPointerException e){}
	}
	public void actualizarTableUsersFollowing(LinkedList<String> listFollowing){
		System.out.println("Lista: " + listFollowing);
		boolean printat = false;
		try{
			boolean trobat = false;
			Object [][] dataUsers = null;
			dataUsers = new String[listFollowing.size()][1];
			String[] header = {"Following"};;
			for (int i=0; i < listFollowing.size() && !trobat; i++){	
				dataUsers[i][0] = listFollowing.get(i);
				tableModelFollowing.setDataVector(dataUsers, header);
				printat = true;
			}
		}catch(NullPointerException e){

		}
		if(!printat){
			Object [][] dataUsers = null;
			String[] header = {"Following"};;	
			tableModelFollowing.setDataVector(dataUsers, header);
		}
	}
	
	public void actualizarTableCanciones(LinkedList<Song> cancionesCancionesList){
		try{
			Object [][] dataCancionesCanciones = null;
			dataCancionesCanciones = new Object[cancionesCancionesList.size()][5];
			for(int i = 0;i < cancionesCancionesList.size(); i++){
				dataCancionesCanciones[i][0] = cancionesCancionesList.get(i).getSongName();
				dataCancionesCanciones[i][1] = cancionesCancionesList.get(i).getArtistName();
				dataCancionesCanciones[i][2] = (cancionesCancionesList.get(i).getGenreName());
				dataCancionesCanciones[i][3] = (cancionesCancionesList.get(i).getAlbumName());
				ImageIcon estrella = new ImageIcon("./img/Estrella.png");
				Image auxEstrella = estrella.getImage();
				auxEstrella = auxEstrella.getScaledInstance((int)(30.0/1920.0*width),(int)(30.0/1920.0*width),Image.SCALE_SMOOTH);
				estrella = new ImageIcon(auxEstrella);
				
				ImageIcon estrellaBuida = new ImageIcon("./img/EstrellaBuida.png");
				Image auxEstrellaBuida = estrellaBuida.getImage();
				auxEstrellaBuida = auxEstrellaBuida.getScaledInstance((int)(30.0/1920.0*width),(int)(30.0/1920.0*width),Image.SCALE_SMOOTH);
				estrellaBuida = new ImageIcon(auxEstrellaBuida);
				
				JPanel jpPuntuacio = new JPanel();
				jpPuntuacio.setBackground(new Color(60,60,60));
				jpPuntuacio.add(new JLabel("        "));
				int j;
				for(j = 0; j < cancionesCancionesList.get(i).getPuntuacion(); j++){
					jpPuntuacio.add(new JLabel(estrella));
					jpPuntuacio.add(new JLabel("  "));
				}
				while(j < 5){
					jpPuntuacio.add(new JLabel(estrellaBuida));
					jpPuntuacio.add(new JLabel("  "));
					j++;
				}
				jpPuntuacio.setLayout(new BoxLayout(jpPuntuacio, BoxLayout.X_AXIS));
				dataCancionesCanciones[i][4] = (jpPuntuacio);
			}	
			String[] header = {"Cancion", "Artista", "Genero", "Album", "Puntuacion"};;
			tableModelCanciones.setDataVector(dataCancionesCanciones, header);
			
		}catch(NullPointerException e){}
	}
	
	public void actualizarTablePlaylist(User user) {
		List<String> playLists = user.getPlayLists();
		try{
			Object[][] dataCancionesCanciones = new Object[playLists.size()][1];
			
			try{
				for(int i = 0; i < playLists.size(); i++) {
					dataCancionesCanciones[i][0] = playLists.get(i);
				}
			}catch(NullPointerException e){
				dataCancionesCanciones[0][0] = " ";
			}
			String[] header = {"Playlists"};
			tableModelCanciones.setDataVector(dataCancionesCanciones, header);
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarTableArchivos(LinkedList<Song> cancionesArchivosList){
		try{
			Object [][] dataCancionesArchivos = null;
			dataCancionesArchivos = new Object[cancionesArchivosList.size()][3];
			for(int i = 0;i < cancionesArchivosList.size(); i++){
				dataCancionesArchivos[i][0] = cancionesArchivosList.get(i).getSongName();
				dataCancionesArchivos[i][1] = cancionesArchivosList.get(i).getArtistName();
			}	
			String[] header = {"Cancion", "Artista"};;
			tableModelarchivos.setDataVector(dataCancionesArchivos, header);
			
		}catch(NullPointerException e){}
	}

	public void  centerPanelArchivosLocalesVisible() {
		jpCenter.setVisible(false);
		jlWelcome.setVisible(false);
		jlWorking.setVisible(false);
		Center.add(jpCenterarchivos,BorderLayout.CENTER);
		jpCenterarchivos.setVisible(true);	
	}
	
	public void centerPanel(){
		Center = new JPanel(new BorderLayout());
		Center.setBackground(new Color(40,40,40));
	}
	
	public void centerPanelInicio(){
	    ImageIcon welcome = new ImageIcon("./img/Welcome.jpg");
		Image auxWelcome = welcome.getImage();
		auxWelcome = auxWelcome.getScaledInstance((int)(687.0/1920.0*width),(int)(450.0/1920.0*width),Image.SCALE_SMOOTH);
		jlWelcome = new JLabel(new ImageIcon(auxWelcome));
		Center.add(jlWelcome, BorderLayout.CENTER);
	}

	public void centerPanelEnProceso(){
	    ImageIcon working = new ImageIcon("./img/Working.png");
		Image auxWorking = working.getImage();
		auxWorking = auxWorking.getScaledInstance((int)(787.0/1920.0*width),(int)(450.0/1920.0*width),Image.SCALE_SMOOTH);
		jlWorking = new JLabel(new ImageIcon(auxWorking));
	}

	public void  centerPanelEnProcesoVisible(){
		jpCenter.setVisible(false);
		jlWelcome.setVisible(false);
		jpCenterarchivos.setVisible(false);
		Center.add(jlWorking, BorderLayout.CENTER);
		jlWorking.setVisible(true);
	}
	
	public void  centerPanelInicioVisible(){
		jpCenter.setVisible(false);
		jlWorking.setVisible(false);
		jpCenterarchivos.setVisible(false);
		startPanel();
		jlWelcome.setVisible(true);
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
	
	public void centerPanelCanciones(){
		JPanel jpUPcanciones = new JPanel(new BorderLayout());
		JPanel jpDOWNcanciones = new JPanel(new BorderLayout());
		jpUPcanciones.setBackground(new Color(60,60,60));
		jpDOWNcanciones.setBackground(new Color(60,60,60));
	    
		ImageIcon album = new ImageIcon("./img/Album.png");
		auxAlbum1 = album.getImage();
		auxAlbum1 = auxAlbum1.getScaledInstance((int)(380.0/1920.0*width), (int)(300.0/1920.0*width), Image.SCALE_SMOOTH);
		auxAlbum2 = auxAlbum1.getScaledInstance((int)(190.0/1920.0*width), (int)(150.0/1920.0*width), Image.SCALE_SMOOTH);
		imageAlbumCanciones = new JLabel(new ImageIcon (auxAlbum1));
	   
		JPanel jpPageStartCanciones = new JPanel();
	    jpPageStartCanciones.setBackground(new Color(60,60,60));
		
	    jlNameCanciones = new JLabel();
		jlNameCanciones.setText("Canciones");
		jlNameCanciones.setForeground(Color.WHITE);
		jlNameCanciones.setFont(new java.awt.Font("Century Gothic",0,(int)(55.0/1920.0*width)));
		
		ImageIcon pause = new ImageIcon("./img/PauseCanciones.png");
		Image auxPause = pause.getImage();
		auxPause = auxPause.getScaledInstance((int)(150.0/1920.0*width), (int)(30.0/1920.0*width), Image.SCALE_SMOOTH);
		pauseButtonCanciones = new JButton(new ImageIcon (auxPause));
		pauseButtonCanciones.setBackground(null);
		pauseButtonCanciones.setBorder(BorderFactory.createEmptyBorder());
		pauseButtonCanciones.setVisible(false);
		
		ImageIcon reproducir = new ImageIcon("./img/ReproducirCanciones.png");
		Image auxReproducir = reproducir.getImage();
		auxReproducir = auxReproducir.getScaledInstance((int)(150.0/1920.0*width), (int)(30.0/1920.0*width), Image.SCALE_SMOOTH);
		reproducirButtonCanciones = new JButton(new ImageIcon (auxReproducir));
		reproducirButtonCanciones.setBackground(null);
		reproducirButtonCanciones.setBorder(BorderFactory.createEmptyBorder());			
		reproducirButtonCanciones.setVisible(true);
		
		JPanel jpReproducirCanciones = new JPanel();
		jpReproducirCanciones.setBackground(new Color(60,60,60));
		jpReproducirCanciones.add(pauseButtonCanciones);
		jpReproducirCanciones.add(reproducirButtonCanciones);
		jpReproducirCanciones.setPreferredSize(new Dimension((int)(150.0/1920.0*width),(int)(30.0/1920.0*width)));
		
		jpPageStartCanciones.setLayout(new BorderLayout());
		jpPageStartCanciones.add(imageAlbumCanciones, BorderLayout.WEST);

		JPanel jpCanciones = new JPanel(new BorderLayout());	
		jpCanciones.add(jlNameCanciones,BorderLayout.PAGE_START);
		jpCanciones.add(jpReproducirCanciones,BorderLayout.WEST);
		jpCanciones.setBackground(new Color(60,60,60));
		jpPageStartCanciones.add(jpCanciones, BorderLayout.CENTER);
		
	    ImageIcon line = new ImageIcon("./img/line-01.png");
		auxLine1 = line.getImage();
		auxLine1 = auxLine1.getScaledInstance((int)(1200.0/1920.0*width), 50, Image.SCALE_SMOOTH);
		auxLine2 = auxLine1.getScaledInstance((int)(900.0/1920.0*width), 50, Image.SCALE_SMOOTH);
		auxLine3 = auxLine1.getScaledInstance((int)(410.0/1920.0*width), 30, Image.SCALE_SMOOTH);
		jlLine = new JLabel(new ImageIcon (auxLine1));
		jpPageStartCanciones.add(jlLine, BorderLayout.PAGE_END);
		jpUPcanciones.add(jpPageStartCanciones, BorderLayout.PAGE_START);
		
		String[][] dataCanciones = null;
		
		@SuppressWarnings("rawtypes")
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				JButton.class
		};
		String[] header = {"Cancion", "Artista", "Genero", "Album", "Puntuacion"};;
		tableModelCanciones = new DefaultTableModel(dataCanciones, header) {
		    @SuppressWarnings("rawtypes")
			Class[] tipos = tipoColumnas;
		    
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public Class getColumnClass(int ColumnIndex){
		    	return tipos[ColumnIndex];
		    }
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		jTableCanciones = new JTable();
		jTableCanciones.getTableHeader().setBackground(new Color(60,60,60));
		jTableCanciones.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		jTableCanciones.getTableHeader().setForeground(new Color(200,200,200));
		jTableCanciones.getTableHeader().setOpaque(false);
		jTableCanciones.getTableHeader().setFont(new Font("Century Gothic",0,18));
		jTableCanciones.setModel(tableModelCanciones);
		jTableCanciones.setForeground(new Color(200,200,200));
		jTableCanciones.setFont(new Font("Century Gothic",0,15));
		jTableCanciones.setBorder(BorderFactory.createEmptyBorder());
		jTableCanciones.setBackground(new Color(60,60,60));
		jTableCanciones.setShowVerticalLines(false);
		jTableCanciones.setShowHorizontalLines(false);
		jTableCanciones.setCellSelectionEnabled(false);
		jTableCanciones.setRowHeight(50);
		TableCellRenderer baseRendererCanciones = jTableCanciones.getTableHeader().getDefaultRenderer();
		jTableCanciones.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRendererCanciones));
		jTableCanciones.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane table = new JScrollPane(jTableCanciones, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.setBackground(new Color(60,60,60));
		table.getVerticalScrollBar().setOpaque(false);
		table.getVerticalScrollBar().setUnitIncrement(16);
		table.getViewport().setBackground(new Color(60,60,60));
			
		JPanel jpTable = new JPanel();
		jpTable.setBackground(new Color(60,60,60));
		jpTable.add(table);
		jpDOWNcanciones.add(new JLabel("                      "), BorderLayout.LINE_START);
		jpDOWNcanciones.add(new JLabel("                         "), BorderLayout.LINE_END);
		jpDOWNcanciones.add(new JLabel("                      "), BorderLayout.PAGE_END);
		jpDOWNcanciones.add(table,BorderLayout.CENTER);
		
		jpCenter = new JPanel(new BorderLayout());
		jpCenter.setBackground(new Color(60,60,60));
		jpCenter.add(jpUPcanciones,BorderLayout.PAGE_START);
		jpCenter.add(jpDOWNcanciones,BorderLayout.CENTER);
	}
	
	public JLabel getJlNameCanciones() {
		return jlNameCanciones;
	}

	public void setJlNameCanciones(JLabel jlNameCanciones) {
		this.jlNameCanciones = jlNameCanciones;
	}

	public void  centerPanelCancionesVisible(){
		jlWorking.setVisible(false);
		jlWelcome.setVisible(false);
		jpCenterarchivos.setVisible(false);
		Center.add(jpCenter,BorderLayout.CENTER);
		jpCenter.setVisible(true);
	}
	
	public void setTextJLNameCanciones(String s){
		jlNameCanciones.setText(s);
	}
	
	public void endPanel(){	
		PanellReproduccio = new JPanel();
		PanellReproduccio.setBackground(new Color (50,50,50));
		PanellReproduccio.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(80.0/1920.0*width)));
		retrocederButton();
		setStopButton();
		playButton();
		avanzarButton();
		reproducirCancionesButton();
		this.getContentPane().add(PanellReproduccio, BorderLayout.PAGE_END);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void rightPanel(){	
		JPanel jpRight = new JPanel(new BorderLayout());
		jpRight.setBackground(new Color(50,50,50));
		jpRight.setPreferredSize(new Dimension((int)(200.0/1920.0*width),0));
		JPanel jpBuscadorUsuariTotal = new JPanel(new BorderLayout());
		jpBuscadorUsuariTotal.setBackground(new Color(50,50,50));
		// buscador
		JPanel jpBuscadorUsuari = new JPanel();
		jtfBuscadorUsuario = new JTextField("Buscar Usuario");
				// new PlaceHolder("Buscar Usari");
		jtfBuscadorUsuario.setPreferredSize(new Dimension((int)(140.0/1920.0*width),(int)(20.0/1920.0*width)));
		jtfBuscadorUsuario.setBackground(new Color(50,50,50));
		jtfBuscadorUsuario.setBorder(BorderFactory.createEmptyBorder());
		jtfBuscadorUsuario.setForeground(new Color(200,200,200));
		jtfBuscadorUsuario.setFont(new java.awt.Font("Century Gothic",0,11));
		jpBuscadorUsuari.add(jtfBuscadorUsuario);
		jpBuscadorUsuari.setBackground(new Color(50,50,50));
		
		ImageIcon buscarUsuari = new ImageIcon("./img/Buscar.png");
		searchUser = new JButton(buscarUsuari);
		searchUser.setBackground(null);
		searchUser.setBackground(new Color(50,50,50));
		searchUser.setBorder(BorderFactory.createEmptyBorder());	
		JPanel jpBuscarUsuari = new JPanel();
		jpBuscarUsuari.add(searchUser);
		jpBuscarUsuari.setBackground(new Color(50,50,50));
		
		jpBuscadorUsuariTotal.add(jpBuscadorUsuari, BorderLayout.WEST);
		jpBuscadorUsuariTotal.add(jpBuscarUsuari, BorderLayout.EAST);
		
		Object [][] dataUsuariosUsername = null;
		
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
		};
		String[] headerUsername = {"Username"};;
		
		tableModelUsername = new DefaultTableModel(dataUsuariosUsername, headerUsername) {
		    Class[] tipos = tipoColumnas;
		    
		    @Override
		    public Class getColumnClass(int ColumnIndex){
		    	return tipos[ColumnIndex];
		    }
		    
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		jTableUsers = new JTable();
		jTableUsers.getTableHeader().setBackground(new Color(50,50,50));
		jTableUsers.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		jTableUsers.getTableHeader().setForeground(new Color(200,200,200));
		jTableUsers.getTableHeader().setFont(new Font("Century Gothic",0,15));
		jTableUsers.getTableHeader().setOpaque(false);
		jTableUsers.setModel(tableModelUsername);
		jTableUsers.setForeground(new Color(150,150,150));
		jTableUsers.setFont(new Font("Century Gothic",0,14));
		jTableUsers.setBorder(BorderFactory.createEmptyBorder());
		jTableUsers.setBackground(new Color(50,50,50));
		jTableUsers.setShowVerticalLines(false);
		jTableUsers.setShowHorizontalLines(false);
		jTableUsers.setCellSelectionEnabled(false);
		jTableUsers.setRowHeight(30);
		TableCellRenderer baseRenderer = jTableUsers.getTableHeader().getDefaultRenderer();
		jTableUsers.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		jTableUsers.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane tableUsers = new JScrollPane(jTableUsers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableUsers.setBorder(BorderFactory.createEmptyBorder());
		tableUsers.setBackground(new Color(50,50,50));
		tableUsers.getVerticalScrollBar().setOpaque(false);
		tableUsers.getVerticalScrollBar().setUnitIncrement(16);
		tableUsers.getViewport().setBackground(new Color(50,50,50));
		jpRight.add(jpBuscadorUsuariTotal,BorderLayout.NORTH);
		jpRight.add(tableUsers, BorderLayout.CENTER);
		jpRight.add(RightPanelFollowers(), BorderLayout.SOUTH);
		this.add(jpRight, BorderLayout.LINE_END);
	}
	
	
	
	public JScrollPane RightPanelFollowers(){
		
		Object [][] dataUsuariosFollowers = null;
		
		@SuppressWarnings("rawtypes")
		Class[] tipoColumnas = new Class[]{
				java.lang.String.class,
		};
		String[] headerFollowers = {"Followers"};;
		
		tableModelFollowing = new DefaultTableModel(dataUsuariosFollowers, headerFollowers) {
		    @SuppressWarnings("rawtypes")
			Class[] tipos = tipoColumnas;
		    
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public Class getColumnClass(int ColumnIndex){
		    	return tipos[ColumnIndex];
		    }
		    
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		jTableFollowers = new JTable();
		jTableFollowers.getTableHeader().setBackground(new Color(50,50,50));
		jTableFollowers.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
		jTableFollowers.getTableHeader().setForeground(new Color(200,200,200));
		jTableFollowers.getTableHeader().setFont(new Font("Century Gothic",0,15));
		jTableFollowers.getTableHeader().setOpaque(false);
		jTableFollowers.setModel(tableModelFollowing);
		
		jTableFollowers.setForeground(new Color(150,150,150));
		jTableFollowers.setFont(new Font("Century Gothic",0,14));
		jTableFollowers.setBorder(BorderFactory.createEmptyBorder());
		jTableFollowers.setBackground(new Color(50,50,50));
		jTableFollowers.setShowVerticalLines(false);
		jTableFollowers.setShowHorizontalLines(false);
		jTableFollowers.setCellSelectionEnabled(false);
		jTableFollowers.setRowHeight(30);
		TableCellRenderer baseRenderer = jTableFollowers.getTableHeader().getDefaultRenderer();
		jTableFollowers.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		jTableFollowers.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });	
		
		JScrollPane tableFollowers = new JScrollPane(jTableFollowers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//tableFollowers.setPreferredSize(new Dimension((int)(200.0/1920.0*width),(int)(200.0/1920.0*width)));
		tableFollowers.setBackground(new Color(50,50,50));
		tableFollowers.setBorder(BorderFactory.createEmptyBorder());
		tableFollowers.getVerticalScrollBar().setOpaque(false);
		tableFollowers.getVerticalScrollBar().setUnitIncrement(16);
		tableFollowers.getViewport().setBackground(new Color(50,50,50));
		//jpRight.add(tableFollowers, BorderLayout.CENTER);
		return tableFollowers;
	}

	
	public void registreFollowing(MainController mainController){
		jTableFollowers.addMouseListener(mainController.getTableUsersFollowing());
	}
	
	public String getTextUsuari(){
		return jtfBuscadorUsuario.getText();
	}
	
	public void registreBuscarUsuari (MainController mainController){
		searchUser.addActionListener(mainController);
		searchUser.setActionCommand("buscarFollowers");
	}

	public JPanel cancionActual(){	
		ImageIcon albumCaratula = new ImageIcon("./img/AlbumCaratula-1.png");
		Image auxAlbumCaratula = albumCaratula.getImage();
		auxAlbumCaratula = auxAlbumCaratula.getScaledInstance((int)(300.0/1920.0*width), (int)(300.0/1920.0*width), Image.SCALE_SMOOTH);
		JLabel aux = new JLabel(new ImageIcon(auxAlbumCaratula));
		
		nombreCancion = new JLabel();
		nombreCancion.setText("Ninguna cancion actualmente");
		nombreCancion.setFont(new Font("Century Gothic",0,(int)(16.0/1920.0*width)));
		nombreCancion.setForeground(new Color(200,200,200));
		nombreCancion.setPreferredSize(new Dimension((int)(20.0/1920.0*width),(int)(30.0/1920.0*width)));
		
		JPanel jpCaratula = new JPanel();
		jpCaratula.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(300.0/1920.0*width)));
		jpCaratula.setBackground(new Color(50,50,50));
		jpCaratula.add(aux);

		JPanel cancionActual = new JPanel(new BorderLayout());
		cancionActual.setBackground(new Color(50,50,50));	
		cancionActual.add(jpCaratula,BorderLayout.CENTER);
		cancionActual.add(nombreCancion,BorderLayout.SOUTH);
		return cancionActual;
	}
	
	public void leftPanel(){
		jpList = new JPanel(new GridLayout(10,1));
		jpList.setBackground(new Color(50,50,50));
		TitolWest();
		ExplorarWest();
		ActividadWest();
	    RadioWest();
	    MusicaWest();
	    CancionesWest();
		AlbumesWest();
	    EmisorasWest();
	    ArchivosWest();
	    PlaylistWest();
	    ListWest();
	    centerButtons();
	    JPanel cancionActual = cancionActual();
		JPanel jpleft = new JPanel();
		jpleft.setBackground(new Color(50,50,50));
		jpleft.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(800.0/1920.0*width)));
		jpleft.add(jspList, BorderLayout.NORTH);
		jpleft.add(nuevaPlaylistButton, BorderLayout.CENTER);
		jpleft.add(cancionActual,BorderLayout.SOUTH);		
	    this.getContentPane().add(jpleft, BorderLayout.WEST);
	    //jpList.setLayout(new GridLayout ((17+listPlaylists.size()),1));
	}
	
	public void TitolWest(){
		JLabel jlabPrincipal = new JLabel("     PRINCIPAL"); 
		jlabPrincipal.setFont(new java.awt.Font("Century Gothic",0,12));
		jlabPrincipal.setForeground(new Color(200,200,200));
		jlabPrincipal.setPreferredSize(new Dimension((int)(260.0/1920.0*width),(int)(40.0/1920.0*width)));
		jpList.add(jlabPrincipal, BorderLayout.NORTH);
	}
	
	public void ExplorarWest(){
		ImageIcon explorar = new ImageIcon("./img/Explorar.png");
		Image auxExplorar = explorar.getImage();
		auxExplorar = auxExplorar.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		explorar = new ImageIcon(auxExplorar);
		explorarButton = new JButton(explorar);
	    JLabel jlExplorar = new JLabel("                Inicio");
	    jlExplorar.setForeground(new Color(200,200,200));
	    jlExplorar.setFont(new java.awt.Font("Century Gothic",0,12));
	    explorarButton.add(jlExplorar, BorderLayout.WEST);
	    explorarButton.setBackground(null);
	    explorarButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
		explorarButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(explorarButton);
	}
	
	public void ActividadWest(){
		ImageIcon actividad = new ImageIcon("./img/Actividad.png");
	    Image auxActividad = actividad.getImage();
	    Image tamanyActividad = auxActividad.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
	    actividad = new ImageIcon(tamanyActividad);
	    actividadButton = new JButton(actividad);
	    JLabel jlActividad = new JLabel("                Actividad");
	    jlActividad.setForeground(new Color(200,200,200));
	    jlActividad.setFont(new java.awt.Font("Century Gothic",0,12));
	    actividadButton.add(jlActividad, BorderLayout.CENTER);
	    actividadButton.setBackground(null);
	    actividadButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
		actividadButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(actividadButton);
	}
	
	public void RadioWest(){
		ImageIcon radio = new ImageIcon("./img/Radio.png");
		Image auxRadio = radio.getImage();
		Image tamanyRadio = auxRadio.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		radio = new ImageIcon(tamanyRadio);
		radioButton = new JButton(radio);
	    JLabel jlRadio = new JLabel("                Radio");
	    jlRadio.setForeground(new Color(200,200,200));
	    jlRadio.setFont(new java.awt.Font("Century Gothic",0,12));
	    radioButton.add(jlRadio, BorderLayout.CENTER);
	    radioButton.setBackground(null);
	    radioButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
	    radioButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(radioButton);
	}
	
	public void MusicaWest(){
		JLabel jlabMusica = new JLabel("     TU MÚSICA");
		jlabMusica.setFont(new java.awt.Font("Century Gothic",0,12));
		jlabMusica.setForeground(new Color(200,200,200));
		jlabMusica.setPreferredSize(new Dimension((int)(260.0/1920.0*width),(int)(40.0/1920.0*width)));
		jpList.add(jlabMusica, BorderLayout.WEST);
	}
	
	public void CancionesWest(){
		ImageIcon canciones = new ImageIcon("./img/Canciones.png");
		Image auxCanciones = canciones.getImage();
		Image tamanyCanciones = auxCanciones.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		canciones = new ImageIcon(tamanyCanciones);
		cancionesButton = new JButton(canciones);
	    
	    JLabel jlCanciones = new JLabel("                Canciones");
	    jlCanciones.setForeground(new Color(200,200,200));
	    jlCanciones.setFont(new java.awt.Font("Century Gothic",0,12));
	    cancionesButton.add(jlCanciones, BorderLayout.CENTER);
	    cancionesButton.setBackground(null);
	    cancionesButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
	    cancionesButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(cancionesButton);
	}
	
	public void AlbumesWest(){
		 ImageIcon albumes = new ImageIcon("./img/Albumes.png");
		 Image auxAlbumes = albumes.getImage();
		 Image tamanyAlbumes = auxAlbumes.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		 albumes = new ImageIcon(tamanyAlbumes);
		 albumesButton = new JButton(albumes);
		 JLabel jlAlbumes = new JLabel("                Álbumes");
		 jlAlbumes.setForeground(new Color(200,200,200));
		 jlAlbumes.setFont(new java.awt.Font("Century Gothic",0,12));
		 albumesButton.add(jlAlbumes, BorderLayout.CENTER);
		 albumesButton.setBackground(null);
		 albumesButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
		 albumesButton.setBorder(BorderFactory.createEmptyBorder());	
		 jpList.add(albumesButton);
	}
	
	public void EmisorasWest(){
		ImageIcon emisoras = new ImageIcon("./img/Emisoras.png");
		Image auxEmisoras = emisoras.getImage();
		Image tamanyEmisoras = auxEmisoras.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
	    emisoras = new ImageIcon(tamanyEmisoras);
		emisorasButton = new JButton(emisoras);
	    JLabel jlEmisoras = new JLabel("                Emisoras");
	    jlEmisoras.setForeground(new Color(200,200,200));
	    jlEmisoras.setFont(new java.awt.Font("Century Gothic",0,12));
	    emisorasButton.add(jlEmisoras, BorderLayout.CENTER);
	    emisorasButton.setBackground(null);
	    emisorasButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
	    emisorasButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(emisorasButton);
	}
	
	public void ArchivosWest(){
		ImageIcon archivos = new ImageIcon("./img/Archivos.png");
	    Image auxArchivos = archivos.getImage();
	    auxArchivos = auxArchivos.getScaledInstance((int)(301.0/1920.0*width),(int)(33.0/1920.0*width),Image.SCALE_SMOOTH);
		archivos = new ImageIcon(auxArchivos);
	    archivosButton = new JButton(archivos);
	    JLabel jlArchivos = new JLabel("                Archivos Locales");
	    jlArchivos.setForeground(new Color(200,200,200));
	    jlArchivos.setFont(new java.awt.Font("Century Gothic",0,12));
	    archivosButton.add(jlArchivos, BorderLayout.CENTER);
	    archivosButton.setBackground(null);
	    archivosButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(25.0/1920.0*width)));
	    archivosButton.setBorder(BorderFactory.createEmptyBorder());	
	    jpList.add(archivosButton);
	}
	
	public void PlaylistWest(){
		JLabel jlabPlaylist = new JLabel("     PLAYLISTS");
		jlabPlaylist.setFont(new java.awt.Font("Century Gothic",0,12));
		jlabPlaylist.setForeground(new Color(200,200,200));
		jlabPlaylist.setPreferredSize(new Dimension((int)(260.0/1920.0*width),(int)(40.0/1920.0*width)));
		jpList.add(jlabPlaylist, BorderLayout.WEST);
	}
	
	public void ListWest(){		
		jspList = new JScrollPane(jpList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspList.setBorder(BorderFactory.createEmptyBorder());
		jspList.getVerticalScrollBar().setOpaque(false);
		jspList.getVerticalScrollBar().setUnitIncrement(16);
		jspList.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(400.0/1920.0*width)));
	}
	
	public void centerButtons(){
		ImageIcon nuevaPlaylist = new ImageIcon("./img/nuevaPlaylist.png");
		Image auxNuevaPlaylist = nuevaPlaylist.getImage();
	    Image tamanyNuevaPlaylist = auxNuevaPlaylist.getScaledInstance((int)(301.0/1920.0*width),(int)(79.0/1920.0*width),Image.SCALE_SMOOTH);
		nuevaPlaylist = new ImageIcon(tamanyNuevaPlaylist);
		nuevaPlaylistButton = new JButton(nuevaPlaylist);
	    JLabel jlnuevaPlaylist = new JLabel("                    Nueva Lista");
	    jlnuevaPlaylist.setForeground(new Color(200,200,200));
	    jlnuevaPlaylist.setFont(new java.awt.Font("Century Gothic",0,12));
	    nuevaPlaylistButton.add(jlnuevaPlaylist, BorderLayout.CENTER);
	    nuevaPlaylistButton.setBackground(null);
	    nuevaPlaylistButton.setPreferredSize(new Dimension((int)(300.0/1920.0*width),(int)(60.0/1920.0*width)));
	    nuevaPlaylistButton.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public JPanel barraMenu(){
		JPanel jpMenu = new JPanel(new GridLayout(1,3));
		barraMenu = new JMenuBar();
		barraMenu.setBackground(new Color(60, 60, 60));
		barraMenu.setBorderPainted(false);
		menuArchivo();
		menuEditar();
		menuVer();
		menuReproduccio();		
		jpMenu.setBackground(new Color(60, 60, 60));
		jpMenu.add(barraMenu, BorderLayout.WEST);
		return jpMenu;
	}

	public void menuVer(){
		menu = new JMenu("Ver");
		menu.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.setForeground(Color.WHITE);
		barraMenu.add(menu);
		menuItemAcercar = new JMenuItem("Acercar");
		menuItemAcercar.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemAcercar);
		menuItemAlejar = new JMenuItem("Alejar");
		menuItemAlejar.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemAlejar);
		menuItemZoom = new JMenuItem("Restablecer zoom");
		menuItemZoom.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemZoom);
	}
	
	public void menuEditar(){
		menuItemBuscar = new JMenuItem("Buscar");
		menuItemBuscar.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemBuscar);
	}
	
	public void menuArchivo(){
		menu = new JMenu("Archivo");
		menu.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.setForeground(Color.WHITE);
		barraMenu.add(menu,BorderLayout.WEST);
		menuItemNuevaLista = new JMenuItem("Nueva Lista");
		menuItemNuevaLista.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemNuevaLista);
		menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemSalir);	
	}
	
	public void menuReproduccio(){
		menu = new JMenu("Reproduccion");
		menu.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.setForeground(Color.WHITE);
		barraMenu.add(menu);
		menuItemPause = new JMenuItem("Pausa                                   ");
		menuItemPause.setFont(new java.awt.Font("Century Gothic",0,11));
		menu.add(menuItemPause);
	}
	
	public void registerMenuPause(MainController controll){
		menuItemPause.addActionListener(controll);
		menuItemPause.setActionCommand("menuPause");
	}

	public void registerMenuSalir(MainController controll){
		menuItemSalir.addActionListener(controll);
		menuItemSalir.setActionCommand("menuSalir");
	}
	
	public JPanel buscador(){
		JPanel jpBuscador = new JPanel();
		jtfBuscador = new JTextField("Buscar");
		jtfBuscador.setPreferredSize(new Dimension(140,20));
		jtfBuscador.setBackground(new Color(60,60,60));
		jtfBuscador.setBorder(BorderFactory.createEmptyBorder());
		jtfBuscador.setForeground(new Color(200,200,200));
		jtfBuscador.setFont(new java.awt.Font("Century Gothic",0,11));
		jpBuscador.add(jtfBuscador);
		jpBuscador.setBackground(new Color(60,60,60));
		return jpBuscador;
	}
	
	public JPanel jpIcon(){
		ImageIcon buscar = new ImageIcon("./img/Buscar.png");
		buscarButton = new JButton(buscar);
		buscarButton.setBackground(null);
		buscarButton.setBorder(BorderFactory.createEmptyBorder());	
		JPanel jpBuscar = new JPanel();
		jpBuscar.add(buscarButton);
		jpBuscar.setBackground(new Color(60,60,60));
		return jpBuscar;
	}
	
	public JPanel user(String username){
		jlUser = new JLabel(" ");
		jlUser.setBackground(new Color(50,50,50));
		jlUser.setFont(new Font("Century Gothic", 0, 12));
		jlUser.setForeground(new Color(200,200,200));
		JPanel jpUser = new JPanel();
		jpUser.setBackground(new Color(60,60,60));
		
	    ImageIcon FletxaDown = new ImageIcon("./img/FletxaDown.png");
	    FletxaDownButton = new JButton(FletxaDown);
	    FletxaDownButton.setBackground(null);
	    FletxaDownButton.setBorder(BorderFactory.createEmptyBorder());
		
		jpUser.add(jlUser);
		jpUser.add(FletxaDownButton);
		return jpUser;
	}
	
	public void startPanel(){
		JPanel startPanel = new JPanel(new BorderLayout());
		JPanel jpMenu = new JPanel();
		JPanel jpBuscador = new JPanel();
		JPanel jpBuscar = new JPanel();
		JPanel jpBuscadorFull = new JPanel();
		JPanel jpUser = new JPanel();
		
		jpUser = user(username);
		jpBuscador = buscador();
		jpMenu = barraMenu();
		jpBuscar = jpIcon();
		startPanel.setBackground(new Color(60,60,60));
		jpBuscadorFull.setBackground(new Color(60,60,60));
		jpBuscadorFull.add(jpBuscador,BorderLayout.WEST);
		jpBuscadorFull.add(jpBuscar,BorderLayout.EAST);
		
		startPanel.add(jpMenu,BorderLayout.PAGE_START);
		startPanel.add(jpBuscadorFull,BorderLayout.WEST);
		startPanel.add(jpUser,BorderLayout.EAST);
		this.add(startPanel, BorderLayout.PAGE_START);
	}
	
	public void OpcionsNick(){
		menuItemNombreUsuario= new JMenuItem("Perfil                                              ");
		menuItemNombreUsuario.setBackground(Color.DARK_GRAY);
		menuItemNombreUsuario.setForeground(Color.WHITE);
		NickMenu.add(menuItemNombreUsuario);
		menuItemNombreUsuario= new JMenuItem("Sortir");
		menuItemNombreUsuario.setBackground(Color.DARK_GRAY);
		menuItemNombreUsuario.setForeground(Color.WHITE);
		NickMenu.add(menuItemNombreUsuario);	
	}
	
	public void setStopButton(){
		ImageIcon stop = new ImageIcon("./img/stop.png");
		Image auxStop = stop.getImage();
		auxStop = auxStop.getScaledInstance((int)(40.0/1920.0*width),(int)(40.0/1920.0*width), Image.SCALE_SMOOTH);
		stop = new ImageIcon(auxStop);
		Stop = new JButton(stop);
		Stop.setBorder(BorderFactory.createEmptyBorder());
		Stop.setBackground(new Color (50,50,50));
		Stop.setVisible(false);
		PanellReproduccio.add(Stop);
	}
	
	public void playButton(){
		ImageIcon play = new ImageIcon("./img/play.png");
		Image auxPlay = play.getImage();
		Image tamanyPlay = auxPlay.getScaledInstance((int)(40.0/1920.0*width),(int)(40.0/1920.0*width), Image.SCALE_SMOOTH);
		play = new ImageIcon (tamanyPlay);
		
		Play = new JButton(play);
		Play.setBorder(BorderFactory.createEmptyBorder());
		Play.setBackground(new Color (50,50,50));
		
		PanellReproduccio.add(Play);
	}
	
	public void retrocederButton(){
		ImageIcon enrera = new ImageIcon("./img/enrrera.png");
		Image auxEnrera = enrera.getImage();
		Image tamanyEnrera = auxEnrera.getScaledInstance((int)(40.0/1920.0*width),(int)(40.0/1920.0*width), Image.SCALE_SMOOTH);
		enrera = new ImageIcon (tamanyEnrera);
		Enrera = new JButton(enrera);
		Enrera.setBorder(BorderFactory.createEmptyBorder());
		Enrera.setBackground(new Color (50,50,50));
		PanellReproduccio.add(Enrera);
	}
	
	public void avanzarButton(){
		
		ImageIcon davant = new ImageIcon("./img/davant.png");
		Image auxDavant = davant.getImage();
		Image tamanyDavant = auxDavant.getScaledInstance((int)(40.0/1920.0*width),(int)(40.0/1920.0*width), Image.SCALE_SMOOTH);
		davant = new ImageIcon (tamanyDavant);
		Davant = new JButton(davant);
		Davant.setBorder(BorderFactory.createEmptyBorder());
		Davant.setBackground(new Color (50,50,50));
		PanellReproduccio.add(Davant);
	}
	
	public void update(){
		width = this.getWidth();
		height = this.getHeight();
		jlNameCanciones.setFont(new java.awt.Font("Century Gothic",0,(int)(55.0/1920.0*width)));
		jlNameArchivos.setFont(new java.awt.Font("Century Gothic",0,(int)(55.0/1920.0*width)));
		barraReproduccio.setPreferredSize(new Dimension((int)(1200.0/1920.0*width),(int)(10.0/1920.0*width)));
		jpList.setLayout(new GridLayout ((10+listPlaylists.size()),1));
		
		if(width < 1100 || height < 600){
			jTableArchivos.getColumnModel().setColumnMargin(0);
			imageAlbumCanciones.setIcon(new ImageIcon (auxAlbum2));
			imageAlbumArchivos.setIcon(new ImageIcon (auxAlbumArchivos2));
		}else{
			jTableArchivos.getColumnModel().setColumnMargin((int)(50.0/1920.0*width));
			imageAlbumCanciones.setIcon(new ImageIcon (auxAlbum1));
			imageAlbumArchivos.setIcon(new ImageIcon (auxAlbumArchivos1));
		}
		if(width < 1100){
			jlLine.setIcon(new ImageIcon (auxLine3));
			imageLineArchivos.setIcon(new ImageIcon (auxLineArchivos3));
		}else if(width < 1300){
			jlLine.setIcon(new ImageIcon (auxLine2));
			imageLineArchivos.setIcon(new ImageIcon (auxLineArchivos2));
		}else{
			jlLine.setIcon(new ImageIcon (auxLine1));
			imageLineArchivos.setIcon(new ImageIcon (auxLineArchivos1));
		}	
	}
	
	public void reproducirCancionesButton(){
		tempsReproduccio = new JLabel();
		tempsReproduccio.setText("00:00");
		tempsReproduccio.setForeground(new Color(200,200,200));
		barraReproduccio = new JProgressBar();
		barraReproduccio.setPreferredSize(new Dimension((int)(1200.0/1920.0*width),(int)(10.0/1920.0*width)));
		barraReproduccio.setBorderPainted(false);
		barraReproduccio.setBackground(new Color (40,40,40));
		barraReproduccio.setForeground(Color.GREEN);
		barraReproduccio.setValue(0);
		tempsfinalReproduccio= new JLabel();
		tempsfinalReproduccio.setText("00:00");
		tempsfinalReproduccio.setForeground(Color.WHITE);
		PanellReproduccio.add(tempsReproduccio);
		PanellReproduccio.add(barraReproduccio);
		PanellReproduccio.add(tempsfinalReproduccio);
	}
	
	public void setBotoPlay(boolean selectArchivosLocales){
		Play.setVisible(false);
		Stop.setVisible(true);	
		if(selectArchivosLocales){
			reproducirArchivosButton.setVisible(false);
			pauseArchivosButton.setVisible(true);
		}else{
			reproducirButtonCanciones.setVisible(false);
			pauseButtonCanciones.setVisible(true);
		}
	}
	
	public void noDisponible(){
		JOptionPane.showMessageDialog(this, "Funcion no disponible");
	}
	
	public void setBotoStopArchivos(){
		reproducirArchivosButton.setVisible(true);
		pauseArchivosButton.setVisible(false);
	}
	
	public void setBotoPlayArchivos(){	
		reproducirArchivosButton.setVisible(false);
		pauseArchivosButton.setVisible(true);
	}
	
	public void SetBotoStopCanciones(){
		reproducirButtonCanciones.setVisible(true);
		pauseButtonCanciones.setVisible(false);
	}
	
	public void SetBotoPlayCanciones(){
		reproducirButtonCanciones.setVisible(false);
		pauseButtonCanciones.setVisible(true);
	}
	
	public void setBotoStop(){
		Play.setVisible(true);
		Stop.setVisible(false);		
	}
	
	public void regitreCintroladorDavant(MainController mainController){
		Davant.addActionListener(mainController);
		Davant.setActionCommand("Davant");
	}
	
	public void regitreCintroladorEnrera(MainController mainController){
		Enrera.addActionListener(mainController);
		Enrera.setActionCommand("Enrera");
	}
	
	public void registreControladorExplorar(MainController mainController){
		explorarButton.addActionListener(mainController);
		explorarButton.setActionCommand("Explorar");
	}
	
	public String getTextBuscar(){
		return jtfBuscador.getText();
	}
	
	public void registreControladorStop(MainController controll){
		Stop.addActionListener(controll);
		Stop.setActionCommand("Stop");
	}
	
	public void registreControladorPause(MainController mainController){
		pauseButtonCanciones.addActionListener(mainController);
		pauseButtonCanciones.setActionCommand("PauseCanciones");
	}
	
	public void registreControladorPauseArchivos(MainController mainController){
		pauseArchivosButton.addActionListener(mainController);
		pauseArchivosButton.setActionCommand("PauseArchivos");
	}
	
	public void registreControladorReproduirArchivos(MainController mainControllers){
		reproducirArchivosButton.addActionListener(mainControllers);
		reproducirArchivosButton.setActionCommand("ReproducirArchivos");	
	}
	
	public void registreControladorReproduir(MainController controll){
		reproducirButtonCanciones.addActionListener(controll);
		reproducirButtonCanciones.setActionCommand("ReproducirCanciones");
	}
	
	public void registreControladorPlay(MainController controll){
		Play.addActionListener(controll);
		Play.setActionCommand("Play");
	}
	
	public void registreControladorAbrirCarpeta(MainController controll){
		abrirCarpetaButton.addActionListener(controll);
		abrirCarpetaButton.setActionCommand("AbrirCarpeta");
	}
	
	public void registreControladorActividad(MainController mainController){
		actividadButton.addActionListener(mainController);
		actividadButton.setActionCommand("Actividad");
	}
	
	public void registreControladorRadio(MainController mainController){
		radioButton.addActionListener(mainController);
		radioButton.setActionCommand("Radio");
	}
	
	public void registreControladorCanciones(MainController mainController){
		cancionesButton.addActionListener(mainController);
		cancionesButton.setActionCommand("Canciones");
	}

	public void registreControladorArchivosLocales(MainController mainController){
		archivosButton.addActionListener(mainController);
		archivosButton.setActionCommand("Archivos");
	}
	
	public void registreControladorAlbumes(MainController mainController){
		albumesButton.addActionListener(mainController);
		albumesButton.setActionCommand("Albumes");
	}
	
	public void registreControladorEmisoras(MainController mainController){
		emisorasButton.addActionListener(mainController);
		emisorasButton.setActionCommand("Emisoras");
	}

	public void registreControladorBuscar(MainController mainController){
		buscarButton.addActionListener(mainController);
		buscarButton.setActionCommand("Buscar");
	}

	public void registreMenuAcercar(MainController mainController){
		menuItemAcercar.addActionListener(mainController);
		menuItemAcercar.setActionCommand("Acercar");
	}
	
	public void registreMenuAlejar(MainController mainController){
		menuItemAlejar.addActionListener(mainController);
		menuItemAlejar.setActionCommand("Alejar");
	}
	
	public void registreMenuZoom(MainController mainController){
		menuItemZoom.addActionListener(mainController);
		menuItemZoom.setActionCommand("Zoom");
	}
	
	public void registreMenuBuscar(MainController mainController){
		menuItemBuscar.addActionListener(mainController);
		menuItemBuscar.setActionCommand("BuscarMenu");
	}
	
	public void setNombreCancion(String songName){
		nombreCancion.setText(songName);
	}
	
	public void registreTableArchivos(MainController mainController){
		jpmArchivos = new JPopupMenu();
		JMenuItem newPlaylist =	new JMenuItem("Añadir nueva Playlist");
		newPlaylist.addActionListener(mainController);
		newPlaylist.setActionCommand("NewPlaylistPopupMenu");
		jpmArchivos.add(newPlaylist);
		jpmArchivos.addSeparator();
		puntuar =	new JMenu();
		puntuar.setText("Puntuar");
		JMenuItem puntuarZero = new JMenuItem("0");
		puntuarZero.addActionListener(mainController);
		puntuarZero.setActionCommand("PuntuarZero");
		puntuar.add(puntuarZero);
		JMenuItem puntuarUno = new JMenuItem("1");
		puntuarUno.addActionListener(mainController);
		puntuarUno.setActionCommand("PuntuarUno");
		puntuar.add(puntuarZero);
		JMenuItem puntuarDos = new JMenuItem("2");
		puntuarDos.addActionListener(mainController);
		puntuarDos.setActionCommand("PuntuarDos");
		puntuar.add(puntuarZero);
		JMenuItem puntuarTres = new JMenuItem("3");
		puntuarTres.addActionListener(mainController);
		puntuarTres.setActionCommand("PuntuarTres");
		puntuar.add(puntuarZero);
		JMenuItem puntuarCuatro = new JMenuItem("4");
		puntuarCuatro.addActionListener(mainController);
		puntuarCuatro.setActionCommand("PuntuarCuatro");
		puntuar.add(puntuarZero);
		JMenuItem puntuarCinco = new JMenuItem("5");
		puntuarCinco.addActionListener(mainController);
		puntuarCinco.setActionCommand("PuntuarCinco");
		puntuar.add(puntuarZero);
		puntuar.add(puntuarUno);
		puntuar.add(puntuarDos);
		puntuar.add(puntuarTres);
		puntuar.add(puntuarCuatro);
		puntuar.add(puntuarCinco);
		jpmArchivos.add(puntuar);
		playlist =	new JMenu();
		playlist.setText("Añadir a la playlist");
		if(listPlaylists.size() != 0){
			for(int i = 0; i < listPlaylists.size(); i++){
				JMenuItem playlistItem = new JMenuItem(listPlaylists.get(i).getNomllista());
				playlistItem.addActionListener(mainController);
				playlistItem.setActionCommand("Playlist:"+listPlaylists.get(i).getNomllista());
				playlist.add(playlistItem);
			}
		}
		jpmArchivos.addSeparator();
		jpmArchivos.add(playlist);
		jTableArchivos.addMouseListener(mainController.getTableArchios());
	}

	public void registrePopupMenuPlaylist(MainController mainController){
		jpmPlaylist = new JPopupMenu();
		JMenuItem eliminar = new JMenuItem("Eliminar cancion de esta playlist");
		eliminar.addActionListener(mainController);
		eliminar.setActionCommand("EliminarDeEsta");
		jpmPlaylist.add(eliminar);
	}
	
	public void showPoputpMenuArchivos(Component c, int x, int y){
		jpmArchivos.show(c, x, y);
	}
	
	public void registreTableUsers(MainController mainController){
		jpmUsuarios = new JPopupMenu();
		JMenuItem follow =	new JMenuItem("Seguir");
		follow.addActionListener(mainController);
		follow.setActionCommand("Follow");
		jpmUsuarios.add(follow);
		jpmUsuarios.addSeparator();
		JMenuItem unfollow  = new JMenuItem();
		unfollow.setText("Dejar de seguir");
		unfollow.addActionListener(mainController);
		unfollow.setActionCommand("Unfollow");
		jpmUsuarios.add(unfollow);	
		jTableUsers.addMouseListener(mainController.getTableUsers());
	}
	
	public void showPopupMenuUsers(Component c, int x, int y){
		jpmUsuarios.show(c, x, y);
	}
	
	public String getNameList(int id){
		return listPlaylists.get(id).getNomllista();
	}
	
	public void showPopupMenuPlaylst(Component c, int x, int y){
		jpmPlaylist.show(c, x, y);
	}
	
	public void showPopupMenuPlaylsts(Component c, int x, int y){
		jpmNuevaPlaylist.show(c, x, y);
	}
	
	public void showPopupMenuCanciones(Component c, int x, int y){
		jpmArchivos.show(c, x, y);
	}
	
	public void registreTableCanciones(MainController mainController){
		jTableCanciones.addMouseListener(mainController.getTableCanciones());
	}
	
	public void setProgressBar(double i,  long totalTime, long timeRead){
		TimeManager timeManager = new TimeManager();
		barraReproduccio.setValue((int)i);
		totalTime = totalTime - timeRead;
		tempsfinalReproduccio.setText(timeManager.getTimeMinutesSeconds(totalTime));
		tempsReproduccio.setText(timeManager.getTimeMinutesSeconds(timeRead));
	}
	
	public void setMaximumProgressBar(int i){
		barraReproduccio.setMaximum(i);
	}
	
	public void registreControladorNovaLLista(MainController controll){
		nuevaPlaylistButton.addActionListener(controll);
		nuevaPlaylistButton.setActionCommand("NovaLlista");
	}
	
	public void registreNovaLListaMenu(MainController mainController){
		menuItemNuevaLista.addActionListener(mainController);
		menuItemNuevaLista.setActionCommand("PlayListMenu");
	}
	
	public int getID(String name){
		int id = 0;
		for(int i = 0; i < listPlaylists.size(); i++){
			if(name.equals(listPlaylists.get(i).getNomllista())){
				id = i;
			}
		}
		return id;
	}
	
	public void actualizarPlaylists(List<String> playlists,MainController mainController){
		if(listPlaylists.size() != 0){		
			int p = listPlaylists.size();
			listPlaylists.clear();
			for(int i = 0; i < p; i++){
				jpList.remove(10);
			}
		}
		int i = 0;
		for(String string : playlists){
			PlayListButton button = new PlayListButton(width);
			listPlaylists.add(button);
			jpList.add(listPlaylists.get(i).getButton());
			listPlaylists.get(i).setJLPlaylist(string);	
		    popupMenuPlaylist(mainController);
		    listPlaylists.get(i).getButton().addActionListener(mainController);
		    listPlaylists.get(i).getButton().setActionCommand("LeftButton:"+listPlaylists.get(i).getNomllista());
		    listPlaylists.get(i).getButton().addMouseListener(mainController.getPlaylist());  
			i++;
		}
	}

	 public void popupMenuPlaylist(MainController mainController){
		 	jpmNuevaPlaylist= new JPopupMenu();
		    playlistItemEliminar = new JMenuItem("Eliminar Playlist");
		    jpmNuevaPlaylist.add(playlistItemEliminar);
		    playlistItemEliminar.addActionListener(mainController);
		    playlistItemEliminar.setActionCommand("EliminarPlaylist");
	 }
	 
	 public void JDialogCanviarNom(MainController mainController){			
		jdPlaylist = new JDialog(this ,"Introduir Nom ",true);
		JPanel aux = new JPanel (new GridLayout(3,1));
		JLabel auxLabel = new JLabel ("Introduce el nombre de la playlist:          ");
		
		auxTextField_1 = new JTextField();	
		auxTextField_1.setBackground(new Color(50,50,50));
		auxTextField_1.setPreferredSize(new Dimension (230,30));
		auxTextField_1.setForeground(Color.WHITE);
		auxTextField_1.setFont(new java.awt.Font("Century Gothic",0,12));
		JPanel aux3 = new JPanel (new GridLayout(2,2));
		aux3.setBackground(new Color(50,50,50));
			
		JButton dialegNom = new JButton ("Acceptar");
		
		jlError= new JLabel("");
		jlError.setForeground(Color.WHITE);			
		jlError.setFont(new java.awt.Font("Century Gothic",0,12));			
		aux3.add(jlError);
		aux3.add(dialegNom);
		aux3.add(new JLabel(""));
		aux3.add(new JLabel(""));
		
		JPanel aux2 = new JPanel();
		aux2.add(auxLabel);
		aux2.setBackground(new Color(50,50,50));
		aux2.add(auxTextField_1);	
		aux.add(new JLabel(""));
		aux.add(aux2);
		aux.add(aux3);
		aux.setBackground(new Color(50,50,50));
		auxLabel.setForeground(Color.WHITE);
		auxLabel.setFont(new java.awt.Font("Century Gothic",0,12));	
		jdPlaylist.add(aux);
		
		dialegNom .addActionListener(mainController);
		dialegNom .setActionCommand("NouNom");
		
		jdPlaylist.setLocationRelativeTo(null);
		jdPlaylist.setSize(new Dimension (500,200));
		jdPlaylist.getContentPane().setBackground(new Color(50,50,50));
		jdPlaylist.setVisible(true); 
		jdPlaylist.validate(); 	
	}
	
	public void nuevaPlaylist(MainController mainController){		
		jdPlaylist = new JDialog(this ,"Introduir Nom ",true);
		JPanel jpAux_1 = new JPanel (new GridLayout(3,2));
		JLabel jlAux = new JLabel("Introduce el nombre de la playlist:          ");
		
		auxTextField_2 = new JTextField();
		auxTextField_2.setBackground(new Color(50,50,50));
		auxTextField_2.setPreferredSize(new Dimension (250,30));
		auxTextField_2.setForeground(new Color(200,200,200));
		auxTextField_2.setFont(new java.awt.Font("Century Gothic",0,12));
		JPanel jpAux_2 = new JPanel(new GridLayout(2,2));
		jpAux_2.setBackground(new Color(50,50,50));	
		JButton aceptarButton = new JButton ("Aceptar");
		jlMensajeError= new JLabel("");
		jlMensajeError.setForeground(new Color(200,200,200));			
		jlMensajeError.setFont(new java.awt.Font("Century Gothic",0,12));	
		jpAux_2.add(jlMensajeError);
		jpAux_2.add(aceptarButton);
		jpAux_2.add(new JLabel(""));
		jpAux_2.add(new JLabel(""));		
		JPanel jpAux_3 = new JPanel();
		jpAux_3.add(jlAux);
		jpAux_3.setBackground(new Color(50,50,50));
		jpAux_3.add(auxTextField_2);	
		jpAux_1.add(new JLabel(""));
		jpAux_1.add(jpAux_3);
		jpAux_1.add(jpAux_2);
		jpAux_1.setBackground(new Color(50,50,50));
		jlAux.setForeground(new Color(200,200,200));
		jlAux.setFont(new java.awt.Font("Century Gothic",0,12));
		jdPlaylist.add(jpAux_1);
		
		aceptarButton.addActionListener(mainController);
		aceptarButton.setActionCommand("AceptarNombre");
		
		jdPlaylist.setResizable(false);
		jdPlaylist.setLocationRelativeTo(null);
		jdPlaylist.setSize(new Dimension(550,180));
		jdPlaylist.setBackground(new Color(50,50,50));
		jdPlaylist.setVisible( true ); 
		jdPlaylist.validate(); 
	}
	
	public void canviarNomPlaylist(){
		for(int i = 0; i < listPlaylists.size(); i++){
			if(playlistSelected == i){		
				listPlaylists.get(i).setNomllista(auxTextField_1.getText());
				if (!NomRepetit()){
					//arrayListPlaylists.get(i).setJLPlaylist(auxTextField_1.getText());
					jdPlaylist.setVisible(false);
				}else{
					jlError.setText("NomRepetit");
				}
			}
		}
		
	}
	
	public void IntroduirNom(MainController mainController){
		if (auxTextField_2.getText().length() == 0){
			jlMensajeError.setText(" Error, no has introducido ningun nombre!");
		}else if(!playlistNameRepetit()){
			jdPlaylist.setVisible(false);
			mainController.createPlayList(auxTextField_2.getText());
		}else{
			jlMensajeError.setText(" Error, el nombre de la playlist ya existe!");
		}
	}
	
	public boolean NomRepetit(){	
		for(int i = 0 ; i < listPlaylists.size(); i++ ){
			if (playlistSelected!= i){
				if (listPlaylists.get(playlistSelected).getNomllista().equals(listPlaylists.get(i).getNomllista())){	
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean playlistNameRepetit(){
		for (int i = 0 ; i< listPlaylists.size(); i++ ){
			if (auxTextField_2.getText().equals(listPlaylists.get(i).getNomllista())){
				return true;
			}
		}
		return false;
	}
}