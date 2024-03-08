package temp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class User implements Serializable {
	private String userName;
	private String password;
	private Date registerDate;
	private Date lastAccessDate;
	private int numSongLists;
	private int totalSongs;
	private int numFollowers;
	private int numFollowing;
	private String mail;
	private List<String> playLists = new ArrayList<String> ();
	
	
	public User() {
		registerDate = new Date(0);
		lastAccessDate = new Date(0);
		numSongLists = 0;
		totalSongs = 0;
		numFollowers = 0;
		numFollowing = 0;
	}

	public User(String userName, Date registerDate, Date lastAccessDate,
			int numSongLists, int totalSongs, int numFollowers, int numFollowing, List<String> playLists) {
		super();
		this.userName = userName;
		this.registerDate = registerDate;
		this.lastAccessDate = lastAccessDate;
		this.numSongLists = numSongLists;
		this.totalSongs = totalSongs;
		this.numFollowers = numFollowers;
		this.numFollowing = numFollowing;
		this.playLists = playLists;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public int getNumSongLists() {
		return numSongLists;
	}

	public void setNumSongLists(int numSongLists) {
		this.numSongLists = numSongLists;
	}

	public int getTotalSongs() {
		return totalSongs;
	}

	public void setTotalSongs(int totalSongs) {
		this.totalSongs = totalSongs;
	}

	public int getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(int numFollowers) {
		this.numFollowers = numFollowers;
	}

	public int getNumFollowing() {
		return numFollowing;
	}

	public void setNumFollowing(int numFollowing) {
		this.numFollowing = numFollowing;
	}
	
	
	public List<String> getPlayLists() {
		return playLists;
	}
	
	public void setPlayLists(List<String> playLists) {
		this.playLists = playLists;
		this.numSongLists = playLists.size();
	}
	
	@Override
	public String toString() {
		return "User [userName=" + userName + ", registerDate=" + registerDate
				+ ", lastAccessDate=" + lastAccessDate + ", numSongLists="
				+ numSongLists + ", totalSongs=" + totalSongs
				+ ", numFollowers=" + numFollowers + ", numFollowing="
				+ numFollowing + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
