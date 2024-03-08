package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JTable;

import network.ServerComunication;
import model.Song;
import model.SongsManager;
import model.User;
import view.MainView;

public class MainController implements ActionListener, WindowListener {
	private ServerComunication sComunication;
	private TimeCounterThread timeThread;
	private SongsManager songsManager;
	private MainView mainView;
	private int rowArchivos;
	private int rowCanciones;
	private boolean selectArchivosLocales;
	private String username;
	private LinkedList<User> llistaUsuaris;
	private String nameUser;
	private MouseAdapter tableCanciones;
	private int selectPlaylist = 0;
	private MouseAdapter tableArchios;
	private MouseAdapter tableUsers;
	private MouseAdapter playlist;
	private FocusAdapter LeftBuscador ;
	private FocusAdapter RightBuscador; 
	private int id;
	private String songClicked;
	private String artist;
	private String nameEliminar;
	private String artistEliminar;
	private String actualPlaylist;
	private MouseAdapter tableUsersFollowing;
	
	public MouseAdapter getTableUsers() {
		return tableUsers;
	}
	
	public void setTableUsers(MouseAdapter tableUsers) {
		this.tableUsers = tableUsers;
	}
	
	public FocusAdapter getBuscadorUsuario(){
		return RightBuscador;
	}
	
	public FocusAdapter getBuscador(){
		return LeftBuscador;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setRowUsuarios(String name){
		nameUser = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public MouseAdapter getTableArchios() {
		return tableArchios;
	}
	
	public void setTableArchios(MouseAdapter tableArchios) {
		this.tableArchios = tableArchios;
	}
	
	public MainController(MainView mainView, ServerComunication sComunication, String username) {
		this.mainView = mainView;
		this.timeThread = new TimeCounterThread(mainView);
		songsManager = new SongsManager(mainView, sComunication, username);
		rowArchivos = 0;
		rowCanciones = 0;
		selectArchivosLocales = true;	
		this.setsComunication(sComunication);
		this.sComunication.setMainController(this);
		this.sComunication.setSongsManager(songsManager);
		this.sComunication.startServerComunication();
		sComunication.sendSelection("LIST_USERS");
		sComunication.sendSelection("LIST_FOLLOWING:" + username);
		sComunication.sendSelection("PLAYLISTS:" + username);
		
		playlist = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
	    		if (e.isMetaDown()){
	    			mainView.showPopupMenuPlaylsts(e.getComponent(), e.getX(), e.getY());
	    			mainView.setPlaylistSelected(id);
	    		}
	    	}
	    }; 
	    
		tableUsersFollowing = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					nameUser = "";
					nameUser = target.getModel().getValueAt(row, 0).toString();
					setPanelPlaylist(target.getModel().getValueAt(row, 0).toString());
					selectPlaylist = 1;
					mainView.setTextJLNameCanciones("Playlists de " + nameUser);
				}
			}
		};
		RightBuscador = new FocusAdapter(){	
			public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
				if (mainView.getJtfBuscadorUsuario().isEmpty()){
					mainView.setJtfBuscadorUsuario("Buscar Usuario");
												
				}
			}
				
			@Override
			public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
				if (mainView.getJtfBuscadorUsuario().equals("Buscar Usuario")){
					mainView.setJtfBuscadorUsuario("");
					}
				}
			};

		LeftBuscador = new FocusAdapter(){	
			public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
				if (mainView.getJtfBuscador().isEmpty()){
					mainView.setJtfBuscador("Buscar");							
				}
			}
				
			@Override
			public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
				if (mainView.getJtfBuscador().equals("Buscar")){
					mainView.setJtfBuscador("");
					}
				}
			};

		tableUsers = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					nameUser = "";
					nameUser = target.getModel().getValueAt(row, 0).toString();
					mainView.setTextJLNameCanciones("Playlists de " + nameUser);
					setPanelPlaylist(target.getModel().getValueAt(row, 0).toString());
					selectPlaylist = 1;
				}
				if(e.isMetaDown()){
					mainView.showPopupMenuUsers(e.getComponent(), e.getX(), e.getY());
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					setRowUsuarios(target.getModel().getValueAt(row, 0).toString());
				}
			}
		};		
		
		tableArchios = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2 && (((JTable)(e.getSource())).getSelectedColumn()) < 2){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					cargarCancionArchivos(target.getModel().getValueAt(row, 0).toString());
				}
			
				if(e.isMetaDown() && (((JTable)(e.getSource())).getSelectedColumn()) < 2){
					JTable target = (JTable)e.getSource();
					int rowArchivos = target.getSelectedRow();
					setRowArchivos(rowArchivos);
				}
			}
		};
		
		tableCanciones = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.isMetaDown() && (((JTable)(e.getSource())).getSelectedColumn()) < 4 && selectPlaylist == 0 && !selectArchivosLocales){
					registreTableArchivos();
					mainView.showPopupMenuCanciones(e.getComponent(), e.getX(), e.getY());
					JTable target = (JTable)e.getSource();
					int rowCanciones = target.getSelectedRow();
					setRowCanciones(rowCanciones);
				}else if(e.getClickCount() == 1 && (((JTable)(e.getSource())).getSelectedColumn()) < 4 && selectPlaylist == 0 && !selectArchivosLocales){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					songClicked = target.getModel().getValueAt(row, 0).toString();
					artist = target.getModel().getValueAt(row, 1).toString();
				}else if(e.getClickCount() == 2 && (((JTable)(e.getSource())).getSelectedColumn()) < 4 && selectPlaylist != 1){
					JTable target = (JTable)e.getSource();
					int rowArchivos = target.getSelectedRow();
					String name = target.getModel().getValueAt(rowArchivos, 0).toString();
					playSong(name);
				}else if(e.getClickCount() == 2){
					JTable target = (JTable)e.getSource();
					int rowArchivos = target.getSelectedRow();
					String nameList = target.getModel().getValueAt(rowArchivos, 0).toString();
					mainView.setTextJLNameCanciones("Lista: "+nameList);
					setUserPlaylistSongs(nameList, nameUser);
					selectPlaylist = 3;
				}else if(e.isMetaDown() && (((JTable)(e.getSource())).getSelectedColumn()) < 4 && selectPlaylist == 2 && !selectArchivosLocales){
					mainView.showPopupMenuPlaylst(e.getComponent(), e.getX(), e.getY());
				}else if(e.getClickCount() == 1 && (((JTable)(e.getSource())).getSelectedColumn()) < 4 && selectPlaylist == 2 && !selectArchivosLocales){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					nameEliminar = target.getModel().getValueAt(row, 0).toString();
					artistEliminar = target.getModel().getValueAt(row, 1).toString();
				}
			}
		};
	}
	public MouseAdapter getTableUsersFollowing() {
		return tableUsersFollowing;
	}
	public void setTableUsersFollowing(MouseAdapter tableUsersFollowing) {
		this.tableUsersFollowing = tableUsersFollowing;
	}
	public void registreTableArchivos(){
		mainView.registreTableArchivos(this);
	}
	
	public void actualizarPlaylists(List<String> playlists){
		mainView.actualizarPlaylists(playlists, this);
	}
	
	public MouseAdapter getPlaylist() {
		return playlist;
	}
	public void setPlaylist(MouseAdapter playlist) {
		this.playlist = playlist;
	}
	public MouseAdapter getTableCanciones() {
		return tableCanciones;
	}
	public void setTableCanciones(MouseAdapter tableCanciones) {
		this.tableCanciones = tableCanciones;
	}
	public void setSelectActualArchivosLocales(){
		songsManager.setSelectArchivosLocales(true);
	}
	public void actualizarListFollowing(LinkedList<String> listFollowing){
		mainView.actualizarTableUsersFollowing(listFollowing);
	}
	public void actualizarListUsers(LinkedList<User> userList){
		this.llistaUsuaris = userList;
	}

	public void actualizarUserPlaylist(User user){
		mainView.actualizarTablePlaylist(user);
	}
	
	public void windowClosing(WindowEvent e){
		songsManager.stop();
		File file = new File("./music/" + username + "ActualSong.mp3"); 
		file.delete();
	}
	
	public void playSong(String name){
		songsManager.setSelectArchivosLocales(false);
		songsManager.setCancionActualCancionesPorNombre(name);
		sComunication.sendSelection("PLAY_SONG:"+name);
	}
	
	public void setRowCanciones(int row){
		rowCanciones = row;
	}
	
	public void setRowArchivos(int row){
		rowArchivos = row;
	}
	
	public void avanzar(){
		songsManager.avanzar();
	}
	
	public void setUserPlaylistSongs(String nameList, String nameUser){
		sComunication.sendSelection("READ_PLAYLIST:"+nameUser+":"+nameList);
	}	
	
	public void createPlayList(String playListName) {
		sComunication.sendSelection("CREATE_PLAYLIST:" + username + ":" + playListName);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Play")){
			System.out.println("Play");
			songsManager.setBotoPlay();	
		}else if (e.getActionCommand().equals("Stop") || e.getActionCommand().equals("menuPause")){
			System.out.println("Stop");
			songsManager.setBotoStop();
		}else if (e.getActionCommand().equals("Davant")){
			System.out.println("Davant");
			songsManager.avanzar();
		}else if (e.getActionCommand().equals("Enrera")){
			System.out.println("Enrera");
			songsManager.retroceder();
		}else if (e.getActionCommand().equals("Explorar")){
			mainView.centerPanelInicioVisible();
			System.out.println("Inicio");
		}else if (e.getActionCommand().equals("Actividad")){
			mainView.centerPanelEnProcesoVisible();
			System.out.println("Actividad");
		}else if (e.getActionCommand().equals("Albumes")){
			mainView.centerPanelEnProcesoVisible();
			System.out.println("Albumes");			
		}else if (e.getActionCommand().equals("Emisoras")){
			mainView.centerPanelEnProcesoVisible();
			System.out.println("Emisoras");		
		}else if (e.getActionCommand().equals("Archivos")){
			selectPlaylist = 0;		
			selectArchivosLocales = true;
			mainView.centerPanelArchivosLocalesVisible();
			songsManager.actualizarTableArchivos();
			songsManager.setSelectPantallaArchivosLocales(true);
			System.out.println("Archivos  Locales");			
		}else if (e.getActionCommand().equals("Radio")){
			mainView.centerPanelEnProcesoVisible();
			System.out.println("Radio");	
		}else if (e.getActionCommand().equals("Canciones")){
			mainView.setTextJLNameCanciones("Canciones");
			selectPlaylist = 0;
			selectArchivosLocales = false;
			sComunication.sendSelection("LIST_SONGS");
			mainView.centerPanelCancionesVisible();
			songsManager.setSelectPantallaArchivosLocales(false);
			System.out.println("Panel Canciones");
		}else if (e.getActionCommand().equals("PauseCanciones")){
			songsManager.setBotoStopCanciones();
		}else if (e.getActionCommand().equals("ReproducirCanciones")){
			songsManager.setSelectArchivosLocales(false);
			songsManager.setBotoPlayCanciones();
		}else if(e.getActionCommand().equals("buscarFollowers")){
			if (!mainView.getTextUsuari().equals("") && !mainView.getTextUsuari().equals(username)){
				mainView.actualizarTableUsers(llistaUsuaris,mainView.getTextUsuari());
			}
		}else if (e.getActionCommand().equals("AbrirCarpeta")){
			try {
				songsManager.archivosLocales();
			}catch(UnsupportedAudioFileException | IOException ex){}				
		}else if (e.getActionCommand().equals("PauseArchivos")){
			songsManager.setBotoStopArchivos();					
		}else if (e.getActionCommand().equals("ReproducirArchivos")){
			songsManager.setBotoPlayArchivos();
		}else if (e.getActionCommand().equals("menuSalir")){
			songsManager.stop();
			File file = new File("./music/"+username+"ActualSong.mp3"); 
			file.delete();
			System.exit(0);			
		}else if (e.getActionCommand().equals("Buscar") || e.getActionCommand().equals("BuscarMenu")){
			songsManager.buscar();
		}else if (e.getActionCommand().equals("Acercar") || e.getActionCommand().equals("Alejar") || e.getActionCommand().equals("Zoom")){
			mainView.noDisponible();
		}else if(e.getActionCommand().equals("PuntuarZero")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 0, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if(e.getActionCommand().equals("PuntuarUno")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 1, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if(e.getActionCommand().equals("PuntuarDos")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 2, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if(e.getActionCommand().equals("PuntuarTres")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 3, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if(e.getActionCommand().equals("PuntuarCuatro")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 4, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if(e.getActionCommand().equals("PuntuarCinco")){
			songsManager.setPuntuacioArchivos(rowArchivos, rowCanciones, 5, selectArchivosLocales);
			mainView.actualizarTableArchivos(songsManager.getCancionesArchivosList());
		}else if (e.getActionCommand().equals("NovaLlista") || e.getActionCommand().equals("NewPlaylistPopupMenu") || e.getActionCommand().equals("PlayListMenu")){
			mainView.nuevaPlaylist(this);
		}else if (e.getActionCommand().equals("NouNom")){
			mainView.canviarNomPlaylist();
		}else if (e.getActionCommand().equals("EliminarPlaylist")){
			sComunication.sendSelection("DELETE_PLAYLIST:" + username + ":" + mainView.getNameList(id));
		}else if (e.getActionCommand().equals("AceptarNombre")){
			mainView.IntroduirNom(this);
		}else if(e.getActionCommand().equals("Follow")){
			sComunication.sendSelection("FOLLOW:" + username + ":" + nameUser);
			sComunication.sendSelection("LIST_FOLLOWING:" + username);
		}else if(e.getActionCommand().equals("Unfollow")){
			sComunication.sendSelection("UNFOLLOW:" + username + ":" + nameUser);
			sComunication.sendSelection("LIST_FOLLOWING:" + username);
		}else if(e.getActionCommand().contains("Playlist:")){
			String[] info = e.getActionCommand().split(":");
			if(artist.length() == 0){
				artist = " ";
			}
			sComunication.sendSelection("ADD_SONG_PLAYLIST:" + username + ":" + info[1] + ":" + songClicked + ":" + artist);
		}else if(e.getActionCommand().equals("EliminarDeEsta")){
			sComunication.sendSelection("DELETE_SONG_PLAYLIST:" + username + ":" + actualPlaylist + ":" + nameEliminar + ":" + artistEliminar);
		}else if(e.getActionCommand().contains("LeftButton:")){
			String[] info = e.getActionCommand().split(":");
			mainView.setTextJLNameCanciones("Lista: "+ info[1]);
			setPanelPlaylistLeft(info[1]);
			id = mainView.getID(info[1]);
			selectPlaylist = 2;
		}
	}
	
	public void setPanelPlaylist(String user){
		selectArchivosLocales = false;
		sComunication.sendSelection("USER_PLAYLIST:"+user);
		mainView.centerPanelCancionesVisible();
	}

	public void setPanelPlaylistLeft(String name){
		actualPlaylist = name;
		selectArchivosLocales = false;
		sComunication.sendSelection("READ_PLAYLIST:"+username+":"+name);
		mainView.centerPanelCancionesVisible();
	}
	
	public void setListSongs(LinkedList<Song> listSongs){
		songsManager.setCancionesCancionesList(listSongs);
		songsManager.actualizarTableCanciones();
	}
	
	public void cargarCancionArchivos(String name) {
		songsManager.setSelectArchivosLocales(true);
		songsManager.cargarCancionArchivosPorNombre(name);
	}
	
	public void cargarCancion(String name){
		songsManager.setSelectArchivosLocales(false);
		songsManager.cargarCancionCancionesPorNombre(name);
	}
	
	public void start() {
		timeThread.start();
	}
	
	public void update() {
		mainView.update();
	}

	public ServerComunication getsComunication() {
		return sComunication;
	}

	public void setsComunication(ServerComunication sComunication) {
		this.sComunication = sComunication;
	}



	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}