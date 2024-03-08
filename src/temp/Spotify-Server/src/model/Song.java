package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("serial")
public class Song implements Serializable{
	private String name;
	private String genere;
	private String album;
	private String artist;
	private String path;
	private int numStars;
	private int numReproducciones;

	public Song() {
		artist = null;
		genere = null;
		album = null;
		name = null;
		numStars = 0;
		setNumReproducciones(0);
	}
	
	public Song(String name, String genere, String album, String artist, String path, int numStars) {
		this.name = name;
		this.genere = genere;
		this.album = album;
		this.artist = artist;
		this.path = path;		
		this.numStars = numStars;
		setNumReproducciones(0);
	}
	
	public void addReproduccion(){
		setNumReproducciones(getNumReproducciones() + 1);
	}

	public int getPuntuacion() {
		return numStars;
	}

	public void setPuntuacion(int puntuacion) {
		this.numStars = puntuacion;
	}

	public String getRuta() {
		return path;
	}

	public void setRuta(String ruta) {
		this.path = ruta;
	}

	@SuppressWarnings("unused")
	public String cargarInformacionCancion(String song){
		path = song;
		try{
			InputStream input = new FileInputStream(new File(song));
			ContentHandler handler = new DefaultHandler();
			Metadata metadata = new Metadata();
			Parser parser = new Mp3Parser();
			ParseContext parseCtx = new ParseContext();
			parser.parse(input, handler, metadata, parseCtx);
			input.close();
			String[] metadataNames = metadata.names();

			name = metadata.get("title");
			if(name == null || name.length() < 2){
				name = song;
			}
			artist = metadata.get("Author");
			genere = metadata.get("xmpDM:genre");
			album = metadata.get("Content-Type");
		}catch(Exception e){}		
		return name;
	}

	public String getArtistName() {
		return artist;
	}

	public void setArtistName(String artistName) {
		this.artist = artistName;
	}

	public String getGenreName() {
		return genere;
	}

	public void setGenreName(String genreName) {
		this.genere = genreName;
	}

	public String getAlbumName() {
		return album;
	}

	public void setAlbumName(String albumName) {
		this.album = albumName;
	}

	public String getSongName() {
		return name;
	}

	public void setSongName(String songName) {
		this.name = songName;
	}

	public int getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
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