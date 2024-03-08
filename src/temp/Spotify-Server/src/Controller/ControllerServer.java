package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import model.ConectorDB;
import model.ConfigParser;
import model.DataBaseServer;
import model.FileManager;
import model.ServerConfiguration;
import model.Song;
import model.User;
import Network.Server;
import View.ViewServer;

public class ControllerServer {
	private static Server server;
	private FileManager fM = new FileManager();
	private ViewServer view;
	private static DataBaseServer db = new DataBaseServer();
	private Reproductor reproductor;
	private ConectorDB dbCon;
	private ServerConfiguration serverConf;
	private ActionListener onAdd;
	private ActionListener onExaminate;
	private MouseAdapter onMouse;
	private MouseAdapter onDelete;
	private MouseAdapter onMouseUser;
	private MouseAdapter onFollowers;
	private MouseAdapter onUserDelete;
	private List<Song> songs = new ArrayList<Song>(); 
	private List<User> users = new ArrayList<User>();
	private List<User> followers = new ArrayList<User> ();
	private String [] columnNames1 = {"NOM", "GENERE", "ALBUM", "ARTISTA", "PATH"};
	private String [] columnNames2 = {"NOM", "DATA REGISTRE", "DATA ACCES", "NUM LLISTES", "NUM SONGS", "FOLLOWERS", "FOLLOWING"};
	private String [][] data;
	private Song songAdded;
	private Song songToDelete;
	private User userToShow;
	private boolean init = true;
	boolean isOk = false;
	boolean selectedSong = false;
	private ControllerServer ctrl = this;
	private ActionListener stop;
	private ActionListener play;
	private MouseAdapter onMouserUserFollowers;

	public ControllerServer() {
		this.reproductor = new Reproductor();
		try {
			onExaminate = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String pathFile = null;
					pathFile = fM.openFile();
					if(pathFile != null) {
						if(!fM.isMP3(pathFile)) {
							isOk = false;
							System.out.println("Not an mp3 file");		
						}else {
							isOk = true;
							view.setPathText(pathFile);
						}				
					}	
				}
			};
			
			onAdd = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isOk && view.getNameText().length() > 0 && view.getGenereText().length() > 0 && 
							view.getAlbumText().length() > 0 && view.getArtistText().length() > 0) {
						songAdded = new Song(view.getNameText(), view.getGenereText(), view.getAlbumText(), view.getArtistText(), view.getPathText(),0);		
						try{
							if(db.addSong(songAdded, dbCon)) {
								songs.add(songAdded);
								showMusicTable();
								server.sendMessageAllListSongs();
							}else{							
								view.clearFields();							
							}
						}catch(Exception ex){						
							ex.printStackTrace();						
						}				
					}else {
						System.out.println("Error d'addicio");
					}		
				}
			};
			
			onMouse = new MouseAdapter() {
				@SuppressWarnings("unused")
				boolean primary = false;				
				@Override
				public void mouseClicked(MouseEvent e) {					
					int modifiers = e.getModifiers();					
					if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {					
						primary = true;
						JTable t = (JTable) e.getSource();
						int row = t.getSelectedRow();
						if (row != -1){						
							songToDelete = new Song();						
							songToDelete.setSongName(t.getModel().getValueAt(row,0).toString());
							songToDelete.setGenreName(t.getModel().getValueAt(row,1).toString());
							songToDelete.setAlbumName(t.getModel().getValueAt(row,2).toString());
							songToDelete.setArtistName(t.getModel().getValueAt(row,3).toString());
							songToDelete.setRuta(t.getModel().getValueAt(row,4).toString());
							selectedSong = true;
						}else{
							return;
						}
					}
					
				    if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
				    	if (selectedSong){				    		
				    		System.out.println("Right button clicked. After Left button.");	
				    		System.out.println(songToDelete);
				    		view.showPopupMenu(e.getX(), e.getY(), 1);			    		
				    	}
				    }
				    
				    if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK & e.getClickCount() == 2) {				    		
				    		System.out.println("Left button 2 clicks.Play song.");	
							JTable t = (JTable) e.getSource();
							int row = t.getSelectedRow();
				    		Song songToPlay = new Song();						
							songToPlay.setSongName(t.getModel().getValueAt(row,0).toString());
							songToPlay.setRuta(t.getModel().getValueAt(row,4).toString());
							if(reproductor.loadSong(songToPlay.getRuta())){					    		
								view.showErrorPlay(songToPlay.getSongName());
							}
				    }
				}				
			};
			
			onMouserUserFollowers = new MouseAdapter() {
				@SuppressWarnings({ "deprecation"})
				public void mouseClicked(MouseEvent ev) {									
					if (ev.isMetaDown()) {							
						JTable t = (JTable) ev.getSource();
						int row = t.getSelectedRow();
						if (row != -1){						
							userToShow = new User();						
							userToShow.setUserName(t.getModel().getValueAt(row,0).toString());
							String regDateString = t.getModel().getValueAt(row, 1).toString();
							String[] parts = regDateString.split("-");

							int regYear = Integer.parseInt(parts[0]) - 1900;
							int regMonth = Integer.parseInt(parts[1]) - 1;
							int regDay = Integer.parseInt(parts[2]);
							
							Date registerDate = new Date(regYear, regMonth, regDay);
							String lastDateString = t.getModel().getValueAt(row, 2).toString();
							String[] parts2 = lastDateString.split("-");
							int lastYear = Integer.parseInt(parts2[0]) - 1900;
							int lastMonth = Integer.parseInt(parts2[1]) - 1;
							int lastDay = Integer.parseInt(parts2[2]);
							
							Date lastDate = new Date(lastYear,lastMonth,lastDay);
							userToShow.setRegisterDate(registerDate);
							userToShow.setLastAccessDate(lastDate);							
							userToShow.setNumSongLists(Integer.parseInt(t.getModel().getValueAt(row,3).toString()));
							userToShow.setTotalSongs(Integer.parseInt(t.getModel().getValueAt(row,4).toString()));
							userToShow.setNumFollowers(Integer.parseInt(t.getModel().getValueAt(row,5).toString()));
							userToShow.setNumFollowing(Integer.parseInt(t.getModel().getValueAt(row,6).toString()));
							System.out.println(userToShow);
						}else{
							return;
						}
						view.showPopupMenu(ev.getX(), ev.getY()+200, 3);						
					}
				}	
			};
			
			onMouseUser = new MouseAdapter() {
				@SuppressWarnings({ "deprecation"})
				public void mouseClicked(MouseEvent ev) {									
					if (ev.isMetaDown()) {							
						JTable t = (JTable) ev.getSource();
						int row = t.getSelectedRow();
						if (row != -1){						
							userToShow = new User();						
							userToShow.setUserName(t.getModel().getValueAt(row,0).toString());
							String regDateString = t.getModel().getValueAt(row, 1).toString();
							String[] parts = regDateString.split("-");

							int regYear = Integer.parseInt(parts[0]) - 1900;
							int regMonth = Integer.parseInt(parts[1]) - 1;
							int regDay = Integer.parseInt(parts[2]);
							
							Date registerDate = new Date(regYear, regMonth, regDay);
							String lastDateString = t.getModel().getValueAt(row, 2).toString();
							String[] parts2 = lastDateString.split("-");
							int lastYear = Integer.parseInt(parts2[0]) - 1900;
							int lastMonth = Integer.parseInt(parts2[1]) - 1;
							int lastDay = Integer.parseInt(parts2[2]);
							
							Date lastDate = new Date(lastYear,lastMonth,lastDay);
							userToShow.setRegisterDate(registerDate);
							userToShow.setLastAccessDate(lastDate);							
							userToShow.setNumSongLists(Integer.parseInt(t.getModel().getValueAt(row,3).toString()));
							userToShow.setTotalSongs(Integer.parseInt(t.getModel().getValueAt(row,4).toString()));
							userToShow.setNumFollowers(Integer.parseInt(t.getModel().getValueAt(row,5).toString()));
							userToShow.setNumFollowing(Integer.parseInt(t.getModel().getValueAt(row,6).toString()));
							System.out.println(userToShow);
						}else{
							return;
						}
						view.showPopupMenu(ev.getX(), ev.getY(), 2);						
					}
				}				
			};
			
			onFollowers = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int modifiers = e.getModifiers();
					if((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
						view.viewFollowers(userToShow, ctrl);
						followers = view.getFollowers();
						showFollowersTable();
					}
				}
			};
			
			onUserDelete = new MouseAdapter() {
				
			@Override
				public void mousePressed(MouseEvent e) {
					int modifiers = e.getModifiers();
					if((modifiers & InputEvent.BUTTON1_MASK) == 
							InputEvent.BUTTON1_MASK) {
						if(deleteUserFromDataBase(userToShow)) {
							
							for(int i = 0; i < users.size(); i++) {
								if(users.get(i).getUserName().equals(userToShow.getUserName())) {
									users.remove(i);
								}
							}
							showUsersTable();
						}
					}
			}
			};
			
			onDelete = new MouseAdapter() {				
				
			@Override
				public void mousePressed(MouseEvent e) {					
					int modifiers = e.getModifiers();
					if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
						if(songToDelete != null) {
							if(db.deleteSong(songToDelete, dbCon)) {	
								for (int i = 0; i < songs.size(); i++) {
									if((songs.get(i).getSongName().equals(songToDelete.getSongName()) && 
										songs.get(i).getArtistName().equals(songToDelete.getArtistName()))) {
										songs.remove(i);
									}
								}
								showMusicTable();
								if(!songs.isEmpty()) {
									server.sendMessageAllListSongs();
								}
							}
						}
					}
				}
			};
			
			stop = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reproductor.pause();
					view.setStop();
				}
			};
			
			play = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reproductor.play();
					view.setPlay();
				}
			};
			
			// Inicialitza vista i connecta a base de dades
			serverConf = ConfigParser.getServerConfig();			
			dbCon = new ConectorDB(serverConf.getUserName(), serverConf.getPassword(), serverConf.getdB_Name(), serverConf.getdB_Port());
			dbCon.connect();	
			
			view = new ViewServer(this);
			reproductor.setView(view);
			view.reproductor();
			initTables(dbCon,db);			
			view.update();
			showMusicTable();
			showUsersTable();	
		}catch (Exception e){			
			e.printStackTrace();			
		}	
		server = new Server(ctrl);
		server.startServer();
		db = new DataBaseServer();
		db.setServer(server);
	}	
	
	public MouseAdapter getOnMouserUserFollowers() {
		return onMouserUserFollowers;
	}

	public void setOnMouserUserFollowers(MouseAdapter onMouserUserFollowers) {
		this.onMouserUserFollowers = onMouserUserFollowers;
	}

	public ActionListener getPlay() {
		return play;
	}

	public void setPlay(ActionListener play) {
		this.play = play;
	}

	public void stopReproductor(){
		reproductor.stop();
	}
	
	public ActionListener getStop() {
		return stop;
	}


	public void setStop(ActionListener stop) {
		this.stop = stop;
	}

	public List<Song> getSongsFromDataBase() {
		List<Song> songs = new ArrayList <Song> ();
		try {
			songs = db.selectSongs(dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return songs;
	}
	
	public Song getSongAdded() {
		return songAdded;
	}

	public void setSongAdded(Song songAdded) {
		this.songAdded = songAdded;
	}

	public ConectorDB getDbCon() {
		return dbCon;
	}

	public void setDbCon(ConectorDB dbCon) {
		this.dbCon = dbCon;
	}

	public String[] getColumnNames1() {
		return columnNames1;
	}

	public void setColumnNames1(String[] columnNames1) {
		this.columnNames1 = columnNames1;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public ActionListener getOnAdd() {			
		return onAdd;				
	}
	
	public ActionListener getOnExaminate() {	
		return onExaminate;
	}
	
	public MouseListener getOnMouse(){	
		return onMouse;	
	}
	
	public MouseListener getOnDelete(){	
		return onDelete;	
	}
	
	public MouseListener getOnFollowers() {
		return onFollowers;
	}
	
	public MouseListener getOnUserDelete() {
		return onUserDelete;
	}
	
	public void initTables(ConectorDB dbCon, DataBaseServer db){
		try{
			songs = db.selectSongs(dbCon);
			users = db.selectUsers(dbCon);
			users = updateUsers(users, dbCon);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	public List<User> updateUsers(List<User> users, ConectorDB dbCon) {
		try {
			users = db.updateUsers(users, dbCon);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return users;
	}
	
	public void showMusicTable() {	
		view.updateSongs(showSongs(songs));
		view.clearFields();
	}
	
	public void showUsersTable() {	
		view.updateUsers(showUsers(users));	
	}
	public void showFollowersTable() {	
		view.updateFollowers(showUsers(followers));
	}
	
	public boolean getInit(){
		return this.init;
	}
	
	public boolean deleteUserFromDataBase(User u) {
		return db.deleteUser(u, dbCon);
	}
	
	public String [][] showSongs(List<Song> songs){				
		int i = 0;
		data = new String [songs.size()][columnNames1.length];
		for(Song s:songs){
			data[i][0] = s.getSongName();
			data[i][1] = s.getGenreName();
			data[i][2] = s.getAlbumName();
			data[i][3] = s.getArtistName();
			data[i][4] = s.getRuta();
			i++;				
		}
		return data;		
	}
	public List<User> getFollowers(String username) {
		
		List<User> followers = new ArrayList<User> ();
		try {
			followers = db.selectFollowers(username, dbCon);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return followers;
	}
	
	public String [][] showUsers(List<User> users){		
		int i = 0;
		data = new String [users.size()][columnNames2.length];
		
		for (User u : users) {			
			data[i][0] = u.getUserName();
			data[i][1] = u.getRegisterDate().toString();
			data[i][2] = u.getLastAccessDate().toString();
			data[i][3] = Integer.toString(u.getNumSongLists());
			data[i][4] = Integer.toString(u.getTotalSongs());
			data[i][5] = Integer.toString(u.getNumFollowers());
			data[i][6] = Integer.toString(u.getNumFollowing());
			i++;		
		}
		return data;
	}
	 
	public String [][] initData (){
		try {		
			songs = db.selectSongs(dbCon);
			users = db.selectUsers(dbCon);			
		} catch(SQLException ex) {			
			ex.printStackTrace();			
		}
		data =  showSongs(songs);
		return data;	
	}
	
	public String[] songToVector(Song songAdded){	
		String [] vector = new String[5];	
		vector[0] = songAdded.getSongName();
		vector[1] = songAdded.getGenreName();
		vector[2] = songAdded.getAlbumName();
		vector[3] = songAdded.getArtistName();
		vector[4] = songAdded.getRuta();	
		return vector;
	}
	
	public String [] getColumnNames (){	
		return this.columnNames1;	
	}	
	
	public boolean checkLogin(String userName, String password) {
		List<User> users = new ArrayList<User>();
		boolean error = true;
		try {
		users = db.selectUsers(dbCon);
		for(User user : users){
			if(user.getPassword().equals(password) && user.getUserName().equals(userName)){
				error = false;
				db.updateUserAccess(user, dbCon);
			}
		}
		users = db.selectUsers(dbCon);
		this.users = users;
		showUsersTable();
		}catch(Exception ex) {
			ex.printStackTrace();
			error = true;
		}
		return error;	
	}
	
	public List<Song> getListSongs() {	
		try {
			return db.selectSongs(dbCon);	
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}	
	}

	public List<User> getListUsers() {	
		try {
			return db.selectUsers(dbCon);	
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}	
	}

	public List<String> getListUsersFollowing(String username) {	
		try {
			return db.selectFollowing(username, dbCon);
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}	
	}
	
	public List<String> getUserPlayLists(String name) {
		List <String> playLists = new ArrayList <String> ();
		try{
			int id = db.selectUserId(name, dbCon);
			playLists = db.selectPlayLists(id, dbCon);
			return playLists;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean createPlayList(String username, String playListName) {
		try {
			return db.createPlayList(username, playListName, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePlayList(String username, String playListName) {
		try {
			return db.deletePlayList(username, playListName, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<Song> readPlayList(String username, String playListName) {
		try {
			return db.readPlayList(username, playListName, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean addSongPlayList(String username, String playListName, String songName, String artist) {
		try {
			return db.addSongPlayList(username, playListName, songName, artist, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteSongPlayList(String username, String playListName, String songName, String artist) {
		try {
			return db.deleteSongPlayList(username, playListName, songName, artist, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	public boolean followUser(String username, String followUsername) {
		try {
			return db.followUser(username, followUsername, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean unfollowUser(String username, String followUsername) {
		try {
			return db.unfollowUser(username, followUsername, dbCon);
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public int updatePuntuation(Song song) {
		List<Song>	songList = new ArrayList<Song>();
		int error = 1;
		try {
			
			songList = db.selectSongs(dbCon);
			for(Song s : songList) {
				if(s.getSongName().equals(song.getSongName()) && s.getArtistName().equals(song.getArtistName())) {
					db.updatePuntuation(song, dbCon);
					error = 0;			
				}
			}
		}catch(Exception e){
			error = 1;
		}
		return error;
		
		
	}
	
	public int updateReproductions(Song song) {
		List<Song>	songList = new ArrayList<Song>();
		int error = 1;
		try {
			
			songList = db.selectSongs(dbCon);
			for(Song s : songList) {
				if(s.getSongName().equals(song.getSongName())&& s.getArtistName().equals(song.getArtistName())) {
					db.updateReproductions(song, dbCon);
					error = 0;			
				}
			}
		}catch(Exception e){
			error = 1;
		}
		return error;
		
	}
	
	
	
	
	public int setCancion(Song song) {
		List<Song>	songList = new ArrayList<Song>();
		int error = 1;
		try {
		songList = db.selectSongs(dbCon);
		for(Song s : songList){
			if(s.getSongName().equals(song.getSongName())){
				db.deleteSong(s, dbCon);
				db.addSong(song, dbCon);
				error = 0;
			}
		}
		}catch(Exception e){
			error = 1;
		}
		return error;
	}

	public int checkRegister(String userName, String password, String mail) {
		List<User> users = new ArrayList<User>();
		int error = 0;
		try {
		users = db.selectUsers(dbCon);
		for(User user : users){
			if(user.getMail().equals(mail) || user.getUserName().equals(userName)){
				if(user.getMail().equals(mail)){
					error = 1;
				}else if(user.getUserName().equals(userName)){
					error = 2;
				}else{
					error = 3;
				}
			}
		}
		if(error == 0){
			try {
				User u = new User();
				u.setUserName(userName);
				u.setPassword(password);
				u.setMail(mail);
				db.addUser(u, dbCon);
				users = db.selectUsers(dbCon);
				this.users = users;
				showUsersTable();
			}catch(Exception ex) {
				error = 3;
				ex.printStackTrace();
			}	
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			error = 3;
		}
		return error;
	}
	
	public void render() {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				view.setVisible(true);
			}
		});	
	}
	
	public MouseListener getOnMouseUser(){	
		return onMouseUser;	
	}
}
