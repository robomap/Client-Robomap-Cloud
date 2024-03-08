package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class songAux implements Serializable{
	private String name;
	private String genere;
	private String album;
	private String artist;
	private String path;
	private int numStars;
	
	public songAux() {
		
	}
	
	public songAux(String name, String genere, String album, String artist, String path, int numStars) {	
		this.name = name;
		this.genere = genere;
		this.album = album;
		this.artist = artist;
		this.path = path;
		this.numStars = numStars;
	}
	
	public int getNumStars() {
		return numStars;
	}

	public void setNumStars(int numStars) {
		this.numStars = numStars;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		songAux other = (songAux) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (genere == null) {
			if (other.genere != null)
				return false;
		} else if (!genere.equals(other.genere))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numStars != other.numStars)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Song [name=" + name + ", genere=" + genere + ", album=" + album
				+ ", artist=" + artist + ", path=" + path + ", numStars="
				+ numStars + "]";
	}
}