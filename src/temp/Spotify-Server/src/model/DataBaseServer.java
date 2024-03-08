package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Network.Server;

public class DataBaseServer {
	
	@SuppressWarnings("unused")
	private ConectorDB dbCon;
	@SuppressWarnings("unused")
	private ServerConfiguration serverConf;
	private Server server;
	
	public DataBaseServer(){
	
	}
	
	public void setServer(Server server){
		this.server = server;
	}
	
	public boolean addSong(Song s, ConectorDB dbCon)throws SQLException{
//		serverConf = ConfigParser.getServerConfig();
//		dbCon = new ConectorDB(serverConf.getUserName(), serverConf.getPassword(), serverConf.getdB_Name(), serverConf.getdB_Port());
//		dbCon.connect();
		ResultSet rs = dbCon.selectQuery("SELECT * FROM song");
		List <Song> songs = new ArrayList<>();
		
		while(rs.next()){			
			Song s2 = new Song();
			s2.setSongName(rs.getString("name"));
			s2.setGenreName(rs.getString("gender"));
			s2.setAlbumName(rs.getString("album"));
			s2.setArtistName(rs.getString("artist"));
			s2.setRuta(rs.getString("path"));
			s2.setPuntuacion(0);
			s2.setNumReproducciones(rs.getInt("numReproductions"));
			songs.add(s2);
		}
		
		boolean equals = false;
		
		for(Song song:songs){
			if(song.getSongName().equals(s.getSongName()) && song.getArtistName().equals(s.getArtistName())){
				equals = true;
			}
		}

		if(!equals){	
			return dbCon.insertQuery("INSERT INTO song(name, gender, album, artist, path, numStars, numReproductions) VALUES ('" + 
					s.getSongName() + "', '" + s.getGenreName() + "', '"+ s.getAlbumName() + "', '" + s.getArtistName() + "', '" + s.getRuta() +
					"', '" + s.getPuntuacion() + "', '" + s.getNumReproducciones() + "')");
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Error, la cançó ja existeix en el sistema!");
			System.out.println("Error, la cançó ja existeix en el sistema!");
			return false;
		}
	}
	
	public void addUser(User u, ConectorDB dbCon)throws SQLException {	
		dbCon.insertQuery("INSERT INTO user(nickname, password, registerDate, lastAccesDate, numSongLists, totalSongs, numFollowers, numFollowing, mail) VALUES ('" + 
					u.getUserName() + "', '" + u.getPassword() + "', '" +  (new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())) +"', '"+ u.getLastAccessDate()+"', '"+ u.getNumSongLists()+"', '"+ u.getTotalSongs()+"', '"+ u.getNumFollowers()+"', '"+ u.getNumFollowing()+"', '"+ u.getMail()+"')" );
		server.sendAllListUsers();
	}
	
	public int updatePuntuation(Song s, ConectorDB dbCon) throws SQLException {
		
		int songId = selectSongId(s.getSongName(), s.getArtistName(), dbCon);
		return(dbCon.updateQuery("UPDATE song SET numStars = '" + s.getPuntuacion() + "' WHERE id =" + songId ));
		
		
	}
	
	public int updateReproductions(Song s, ConectorDB dbCon) throws SQLException {
		
		int songId = selectSongId(s.getSongName(), s.getArtistName(), dbCon);
		return(dbCon.updateQuery("UPDATE song SET numReproductions = '" + s.getNumReproducciones() + "' WHERE id =" + songId ));
		
		
	}
	
	public void updateUserAccess(User u, ConectorDB dbCon)throws SQLException {
		int userId = selectUserId(u.getUserName(), dbCon);
		System.out.println(userId);
		String d = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		System.out.println(d);
		dbCon.updateQuery("UPDATE user SET lastAccesDate = '" + d + "' WHERE id ="  + userId );
		
	}
	public List<User> updateUsers(List<User> users, ConectorDB dbCon)throws SQLException {
		
		// Actualizar los atributos de: numSongLists, totalSongs, numFollowers, numFollowing
		
		int totalSongsList;
		
		for (User u : users) {
		totalSongsList = 0;	
		ArrayList<Integer> listIds = new ArrayList<Integer> ();
				int countPlayLists = countSongLists(u, dbCon);
				
				u.setNumSongLists(countPlayLists);
				
				int userId = selectUserId(u.getUserName(), dbCon);
				
				
					
					ResultSet rs = dbCon.selectQuery("SELECT id FROM list WHERE user =" + userId);
					while(rs.next()) {
						
						int idPlayList =  rs.getInt("id");
						listIds.add(idPlayList);
						
					}
					System.out.println(listIds);
					
					for(int i = 0; i < listIds.size(); i++) {
						
						int countSongsList = 0;
						int listId = listIds.get(i);
						rs = dbCon.selectQuery("SELECT COUNT(id) FROM list_song WHERE list =" + listId);
						rs.next();
						countSongsList = rs.getInt(1);
						totalSongsList = totalSongsList + countSongsList;
						
					}
					System.out.println(totalSongsList);
					u.setTotalSongs(totalSongsList);	
					int numFollowers = countFollowers(u, dbCon);
					int numFollowing = countFollowing(u, dbCon);
					
					u.setNumFollowers(numFollowers);
					u.setNumFollowing(numFollowing);
					dbCon.updateQuery("UPDATE user SET numSongLists ='" + countPlayLists + "' WHERE id =" + userId);
					dbCon.updateQuery("UPDATE user SET totalSongs ='" + totalSongsList + "' WHERE id =" + userId);
					dbCon.updateQuery("UPDATE user SET numFollowers ='" + numFollowers + "' WHERE id =" + userId);
					dbCon.updateQuery("UPDATE user SET numFollowing ='" + numFollowing + "' WHERE id =" + userId);
		}
		return users;
		
		
	
	
	
	}
	public int countFollowing(User u, ConectorDB dbCon) throws SQLException {
		
		int userId = selectUserId(u.getUserName(), dbCon);
		ResultSet rs = dbCon.selectQuery("SELECT COUNT(id) FROM follow WHERE following =" + userId);
		rs.next();
		int numFollowing = rs.getInt(1);
		
		return numFollowing;
	}
	
	public int countFollowers(User u, ConectorDB dbCon) throws SQLException {
		
		int userId = selectUserId(u.getUserName(), dbCon);
		
		ResultSet rs = dbCon.selectQuery("SELECT COUNT(id) FROM follow WHERE follower =" + userId);
		rs.next();
		int numFollowers = rs.getInt(1);
		
		return numFollowers;
	}
	
	public int countSongLists (User u, ConectorDB dbCon) throws SQLException {
		
		
		int userId = selectUserId(u.getUserName(), dbCon);
		
		ResultSet rs = dbCon.selectQuery("SELECT COUNT(id) FROM list WHERE user =" + userId); 
		rs.next();
		int count = rs.getInt(1);
		return count;
		
	}
	
	public List<Song> selectSongs(ConectorDB dbCon) throws SQLException {		

		List<Song> songs = new ArrayList<>();
		ResultSet rs = dbCon.selectQuery("SELECT * FROM song");
			
		while(rs.next()){
			Song s = new Song();
			s.setSongName(rs.getString("name"));
			s.setGenreName(rs.getString("gender"));
			s.setAlbumName(rs.getString("album"));
			s.setArtistName(rs.getString("artist"));
			s.setRuta(rs.getString("path"));
			s.setPuntuacion(rs.getInt("numStars"));
			s.setNumReproducciones(rs.getInt("numReproductions"));
			songs.add(s);
		}
		return songs;
	}
	
	public List<User> selectUsers(ConectorDB dbCon) throws SQLException {
//		serverConf = ConfigParser.getServerConfig();
//		dbCon = new ConectorDB(serverConf.getUserName(), serverConf.getPassword(), serverConf.getdB_Name(), serverConf.getdB_Port());
//		dbCon.connect();
//		dbCon.up
		List<User> users = new ArrayList<>();
		ResultSet rs = dbCon.selectQuery("SELECT * FROM user");
			
		while(rs.next()){		
			User u = new User();
			u.setUserName(rs.getString("nickname"));
			u.setPassword(rs.getString("password"));
			u.setRegisterDate(rs.getDate("registerDate"));
			u.setLastAccessDate(rs.getDate("lastAccesDate"));
			u.setNumSongLists(rs.getInt("numSongLists"));
			u.setTotalSongs(rs.getInt("totalSongs"));
			u.setNumFollowers(rs.getInt("numFollowers"));
			u.setNumFollowing(rs.getInt("numFollowing"));
			u.setMail(rs.getString("mail"));
			users.add(u);		
		}
		return users;
	}
	
	public int selectUserId(String name, ConectorDB dbCon) throws SQLException {
		
		ResultSet rs = dbCon.selectQuery("SELECT id FROM user WHERE nickname = '" + name + "'");
		rs.next();
		int id = rs.getInt("id");
				
		return id;
		
	}
	
	public int selectPlayListId(int userId, String name, ConectorDB dbCon) throws SQLException {
		
		ResultSet rs = dbCon.selectQuery("SELECT id FROM list WHERE name = '" + name + "'"
				+ " AND user = " + userId);
		
		rs.next();
		int id = rs.getInt("id");
		
		return id;
	}
	
	public int selectSongId(String songName, String artist, ConectorDB dbCon) throws SQLException {
		
		ResultSet rs = dbCon.selectQuery("SELECT id FROM song WHERE name = '" + 
					songName + "' AND artist = '" + artist + "'");
		
		rs.next();
		int id = rs.getInt("id");
		
		return id;
	}
	
	public List<String> selectPlayLists(int id, ConectorDB dbCon) throws SQLException {
		
		List<String> playLists = new ArrayList<String> ();
		ResultSet rs = dbCon.selectQuery("SELECT id, name FROM list WHERE user = " + id);
		while(rs.next()) {
			String s = rs.getString("name");
			playLists.add(s);
		}
		return playLists;
	}
	
	public boolean createPlayList(String username, String playListName, ConectorDB dbCon) throws SQLException {
		int userId = selectUserId(username, dbCon);
		
		ResultSet rs = dbCon.selectQuery("SELECT COUNT(id) FROM list"
				+ " WHERE name = '" + playListName + "' AND user = " + userId);
		
		rs.next();
		int c = rs.getInt(1);
		if(c != 0) {
			return false;
		}
		
		return dbCon.insertQuery("INSERT INTO list(name, user) VALUES('" + playListName 
				+ "', " + userId + ")");
	}
	
	public boolean deletePlayList(String username, String playListName, ConectorDB dbCon) throws SQLException {
		int userId = selectUserId(username, dbCon);
		int playListId = selectPlayListId(userId, playListName, dbCon);
		dbCon.deleteQuery("DELETE FROM list_song WHERE list = " + playListId);
		return dbCon.deleteQuery("DELETE FROM list WHERE id = " + playListId);
	}
	
	public List<Song> readPlayList(String username, String playListName, ConectorDB dbCon) throws SQLException {
		
		int userId = selectUserId(username, dbCon);
		int playListId = selectPlayListId(userId, playListName, dbCon);
		
		ResultSet rs = dbCon.selectQuery("SELECT s.name, s.gender, s.album, "
				+ "s.artist, s.path, s.numStars, s.numReproductions FROM song s, "
				+ "list_song ls WHERE s.id = ls.song AND ls.list = " + playListId);
		
		List<Song> songs = new ArrayList<>();
		while(rs.next()) {
			Song s = new Song();
			s.setSongName(rs.getString("name"));
			s.setGenreName(rs.getString("gender"));
			s.setAlbumName(rs.getString("album"));
			s.setArtistName(rs.getString("artist"));
			s.setRuta(rs.getString("path"));
			s.setPuntuacion(rs.getInt("numStars"));
			s.setNumReproducciones(rs.getInt("numReproductions"));
			songs.add(s);
		}
		return songs;
	}
	
	public boolean addSongPlayList(String username, String playListName, String songName, String artist, ConectorDB dbCon) throws SQLException {
		int userId = selectUserId(username, dbCon);
		int playListId = selectPlayListId(userId, playListName, dbCon);
		int songId = selectSongId(songName, artist, dbCon);
		return dbCon.insertQuery("INSERT INTO list_song(list, song) VALUES(" + playListId 
				+ ", " + songId + ")");
	}
	
	public boolean deleteSongPlayList(String username, String playListName, String songName, String artist, ConectorDB dbCon) throws SQLException {		
		int userId = selectUserId(username, dbCon);
		int playListId = selectPlayListId(userId, playListName, dbCon);
		int songId = selectSongId(songName, artist, dbCon);
		
		return dbCon.deleteQuery("DELETE FROM list_song WHERE song = " + songId + " AND list = " + playListId);
	}
	
	public boolean followUser(String username, String followUsername, ConectorDB dbCon) throws SQLException {	
		int userId = selectUserId(username, dbCon);
		int followUserId = selectUserId(followUsername, dbCon);
		return dbCon.insertQuery("INSERT INTO follow(follower, following) VALUES(" + userId + ", " + followUserId + ")");
	}
	
	public boolean unfollowUser(String username, String followUsername, ConectorDB dbCon) throws SQLException {	
		int userId = selectUserId(username, dbCon);
		int followUserId = selectUserId(followUsername, dbCon);
		return dbCon.deleteQuery("DELETE FROM follow WHERE follower = " + userId + " AND following = " + followUserId);
	}
	
	public List<String> selectFollowing(String username, ConectorDB dbCon) throws SQLException {	
		int userId = selectUserId(username, dbCon);
		ResultSet rs = dbCon.selectQuery("SELECT u.nickname FROM user u, follow f WHERE f.following = u.id AND f.follower = " + userId );
		List<String> following = new ArrayList<String> ();
		while(rs.next()) {
			String s = rs.getString("nickname");
			following.add(s);
		}
		return following;
	}
	
	public List<User> selectFollowers(String username, ConectorDB dbCon) throws SQLException {
		int userId = selectUserId(username, dbCon);
		ResultSet rs = dbCon.selectQuery("SELECT u.nickname, u.registerDate, u.lastAccesDate,"
				+ "u.numSongLists, u.totalSongs, u.numFollowers, u.numFollowing FROM user u, "
				+ "follow f WHERE f.follower = u.id AND f.following = " + userId);
		List<User> followers = new ArrayList<User> ();
		while(rs.next()) {
			User u = new User();
			u.setUserName(rs.getString("nickname"));
			u.setRegisterDate(rs.getDate("registerDate"));
			u.setLastAccessDate(rs.getDate("lastAccesDate"));
			u.setNumSongLists(rs.getInt("numSongLists"));
			u.setTotalSongs(rs.getInt("totalSongs"));
			u.setNumFollowers(rs.getInt("numFollowers"));
			u.setNumFollowing(rs.getInt("numFollowing"));
			followers.add(u);
		}
		return followers;
	}
	
	public boolean deleteSong(Song s, ConectorDB dbCon) {
		try {
			int songId = selectSongId(s.getSongName(), s.getArtistName(), dbCon);
						
			boolean success = dbCon.deleteQuery("DELETE FROM list_song WHERE song = " + songId);
								
			if(success) {
				return dbCon.deleteQuery("DELETE FROM song WHERE name = '" + s.getSongName() 
						+ "' AND artist = '" + s.getArtistName() + "'");
			}
			return false;
		} catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(User u, ConectorDB dbCon) {
		try {
			
			int userId = selectUserId(u.getUserName(), dbCon);
			System.out.println(userId);	
			
			dbCon.deleteQuery("DELETE FROM follow WHERE following = " + userId +"OR follower = " + userId);
			try {
			ResultSet rs = dbCon.selectQuery("SELECT id FROM list WHERE user = "+ userId );
			
			while(rs.next()) {
				
				int playListId = rs.getInt("id");
				
					dbCon.deleteQuery("DELETE FROM list_song WHERE list = " + playListId);
	
				
			}
				
			
			} catch(SQLException e) {
				e.printStackTrace();
			}
			dbCon.deleteQuery("DELETE FROM list WHERE user = " + userId );
			
			
			
			return dbCon.deleteQuery("DELETE FROM user WHERE id = " + userId);
			
			
		} catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
		
	}
}