package model;

import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import network.ServerComunication;
import view.MainView;
import controller.Reproductor;

public class SongsManager {
	private Reproductor reproductor;
	private MainView mainView;
	private boolean selectArchivosLocales;
	private boolean selectPantallaArchivosLocales;
	private LinkedList<Song> cancionesArchivosList;
	private LinkedList<Song> cancionesCancionesList;
	private int cancionActualArchivosLocales;
	private int cancionActualCanciones;
	ServerComunication sComunication;
	
	public SongsManager(MainView  mainView, ServerComunication sComunication, String username) {
		this.mainView = mainView;
		this.sComunication = sComunication; 
		cancionActualCanciones = -1;
		reproductor = new  Reproductor(mainView,this, username);
		selectArchivosLocales = true;
		selectPantallaArchivosLocales = true;
		cancionesCancionesList = new LinkedList<Song>();
		cancionesArchivosList = new LinkedList<Song>();
	}
	
	public void setPuntuacioArchivos(int rowArchivos, int rowCanciones, int puntuacion, boolean selectArchivosLocales){
		try{
			if(selectArchivosLocales){
				cancionesArchivosList.get(rowArchivos).setPuntuacion(puntuacion);		
			}else{
				cancionesCancionesList.get(rowCanciones).setPuntuacion(puntuacion);
				sComunication.sendSelection("SET_PUNTUACION:"+cancionesCancionesList.get(rowCanciones).getSongName()+"/"+cancionesCancionesList.get(rowCanciones).getPuntuacion());
			}
		}catch(IndexOutOfBoundsException e){}
	}
	
	public void buscar(){
		if(selectPantallaArchivosLocales){
			LinkedList<Song> cancionesArchivosListAux = new LinkedList<Song>();
			if(cancionesArchivosList != null){
				for(int i = 0; i < cancionesArchivosList.size(); i++){
					if(cancionesArchivosList.get(i).getSongName().toLowerCase().contains(mainView.getTextBuscar().toLowerCase())){
						cancionesArchivosListAux.add(cancionesArchivosList.get(i));
					}
				}
			}
			mainView.actualizarTableArchivos(cancionesArchivosListAux);
		}else{
			LinkedList<Song> cancionesCancionesAux = new LinkedList<Song>();
			if(cancionesCancionesList != null){
				for(int i = 0; i < cancionesCancionesList.size(); i++){
					if(cancionesCancionesList.get(i).getSongName().toLowerCase().contains(mainView.getTextBuscar().toLowerCase())){
						cancionesCancionesAux.add(cancionesCancionesList.get(i));
					}
				}
			}
			mainView.actualizarTableCanciones(cancionesCancionesAux);
		}
	}
	
	public boolean isSelectPantallaArchivosLocales() {
		return selectPantallaArchivosLocales;
	}

	public void setSelectPantallaArchivosLocales(
			boolean selectPantallaArchivosLocales) {
		this.selectPantallaArchivosLocales = selectPantallaArchivosLocales;
	}

	public void actualizarTableArchivos(){
		mainView.actualizarTableArchivos(cancionesArchivosList);
	}

	public void actualizarTableCanciones(){
		mainView.actualizarTableCanciones(cancionesCancionesList);
	}
	
	public void setBotoPlay(){
		if(!reproductor.play()){
			mainView.setBotoPlay(getSelectArchivosLocales());
			
			if(selectArchivosLocales){
				mainView.setBotoPlayArchivos();
				mainView.SetBotoStopCanciones();
			}else{
				mainView.SetBotoPlayCanciones();
				mainView.setBotoStopArchivos();
			}
		}
	}
	
	public void setBotoStop(){
		reproductor.pause();
		mainView.setBotoStop();
		mainView.setBotoStopArchivos();
		mainView.SetBotoStopCanciones();
	}
	
	public void setBotoPlayArchivos(){
		if(selectArchivosLocales){
			setBotoPlay();
		}else{
			mainView.setBotoStop();
			mainView.SetBotoStopCanciones();
			setBotoStop();
			if(cancionesArchivosList != null){
				selectArchivosLocales = true;
				cargarCancion();	
			}
		}
	}
	
	public void stop(){
		reproductor.stop();
	}
	
	public void setBotoPlayCanciones(){
		if(cancionActualCanciones == -1){
			cancionActualCanciones = 0;
			sComunication.sendSelection("PLAY_SONG:"+cancionesCancionesList.get(0).getSongName());
		}
		if(selectArchivosLocales){
			mainView.setBotoStop();
			mainView.setBotoStopArchivos();
			setBotoStop();
			
			if(cancionesCancionesList != null){
				selectArchivosLocales = false;
				cargarCancion();
			}
		}else{
			selectArchivosLocales = false;
			setBotoPlay();
		}
	}
	
	public void setBotoStopArchivos(){
		setBotoStop();
	}
	
	public void setBotoStopCanciones(){
		setBotoStop();
		mainView.SetBotoStopCanciones();
	}
	
	public void archivosLocales() throws UnsupportedAudioFileException, IOException{
		FileManager fileManager = new FileManager();
		selectArchivosLocales = true;
   	 	String[] songs = fileManager.openFolder();
		if(songs != null && songs.length != 0){
			cancionesArchivosList = new LinkedList<Song>();
			cancionActualArchivosLocales = 0;
			Song cancion;
			for(int i = 0; i < songs.length; i++){
				if(fileManager.isMP3(songs[i].toString())){
					cancion = new Song();
					cancion.cargarInformacionCancion(songs[i].toString());
					cancionesArchivosList.add(cancion);
				}	
			}
			if(cancionesArchivosList != null && cancionesArchivosList.size() != 0){
				reproductor.stop();			
				setBotoStopCanciones();
				cargarCancion();
			}
		}
	}
	
	public LinkedList<Song> getCancionesCancionesList() {
		return cancionesCancionesList;
	}

	public void setCancionesCancionesList(LinkedList<Song> cancionesCancionesList) {
		this.cancionesCancionesList = cancionesCancionesList;
	}

	public void avanzar(){
		if(selectArchivosLocales){
			try{
				if((cancionesArchivosList.size()-1) > cancionActualArchivosLocales){
					cancionActualArchivosLocales++;
				}else{
					cancionActualArchivosLocales = 0;
				}
				cargarCancion();
			}catch(NullPointerException e){}
		}else if(cancionesCancionesList.size()-1 != cancionActualCanciones){
			try{
				if((cancionesCancionesList.size())-1 > cancionActualCanciones){
					cancionActualCanciones++;
				}else{
					cancionActualCanciones = 0;
				}
				sComunication.sendSelection("PLAY_SONG:"+cancionesCancionesList.get(cancionActualCanciones).getSongName());
			}catch(NullPointerException e){}
		}
	}

	public void setCancionActualCancionesPorNombre(String name){
		int i = 0;
		for(Song s  : cancionesCancionesList){
			if(name.equals(s.getSongName())){
				cancionActualCanciones = i;
			}
			i++;
		}
	}
	
	public void retroceder(){
		if(selectArchivosLocales){
			try{
				if(cancionActualArchivosLocales != 0){
					cancionActualArchivosLocales--;
				}
				cargarCancion();
			}catch(NullPointerException e){}
		}else{
			try{
				if(cancionActualCanciones != 0){
					cancionActualCanciones--;
				}
				sComunication.sendSelection("PLAY_SONG:"+cancionesCancionesList.get(cancionActualCanciones).getSongName());
			}catch(NullPointerException ex){}	
		}
	}
	
	
	public void cargarCancion(){
		boolean error = false;
		mainView.setBotoStop();
		reproductor.stop();
		
		if(selectArchivosLocales){
			error = reproductor.loadSong(cancionesArchivosList.get(cancionActualArchivosLocales).getRuta());
			mainView.setNombreCancion(cancionesArchivosList.get(cancionActualArchivosLocales).getSongName());
		}else{
			error = reproductor.loadSong("selectCanciones");			
			mainView.setNombreCancion(cancionesCancionesList.get(cancionActualCanciones).getSongName());
		}
		
		if(error){
			if(selectArchivosLocales){
				JOptionPane.showMessageDialog(mainView, "Error al intentar cargar la cancion: " + cancionesArchivosList.get(cancionActualArchivosLocales).getRuta());
				if(cancionesArchivosList.size()-1 <= cancionActualArchivosLocales){
					avanzar();
				}
			}else{
				JOptionPane.showMessageDialog(mainView, "Error al intentar cargar la cancion: " + cancionesCancionesList.get(cancionActualCanciones).getRuta());
				if(cancionesCancionesList.size()-1 <= cancionActualCanciones){
					avanzar();
				}
			}
		}else{
			if(selectArchivosLocales){
				cancionesArchivosList.get(cancionActualArchivosLocales).addReproduccion();
			}else{
				cancionesCancionesList.get(cancionActualCanciones).addReproduccion();
				sComunication.sendSelection("SET_REPRODUCCIONES:" +cancionesCancionesList.get(cancionActualCanciones).getSongName());
			}
			mainView.actualizarTableCanciones(getCancionesCancionesList());
			mainView.actualizarTableArchivos(getCancionesArchivosList());
			mainView.setNombreCancion(getActualSong());
			mainView.setBotoPlay(getSelectArchivosLocales());
			reproductor.play();
		}
	}
	
	public void cargarCancionArchivosPorNombre(String name){
		setBotoStopCanciones();
		if(cancionesArchivosList != null){
			for(int i = 0; i < cancionesArchivosList.size(); i++){
				if(cancionesArchivosList.get(i).getSongName().equals(name)){
					selectArchivosLocales = true;
					cancionActualArchivosLocales = i;
					cargarCancion();
					i = cancionesArchivosList.size();
				}
			}
		}
	}

	public void cargarCancionCancionesPorNombre(String name){
		setBotoStopArchivos();
		if(cancionesCancionesList != null){
			for(int i = 0; i < cancionesCancionesList.size(); i++){
				if(cancionesCancionesList.get(i).getSongName().equals(name)){
					selectArchivosLocales = false;
					cancionActualCanciones = i;
					cargarCancion();
					i = cancionesCancionesList.size();
				}
			}
		}
	}
	
	public Reproductor getReproductor() {
		return reproductor;
	}

	public void setReproductor(Reproductor reproductor) {
		this.reproductor = reproductor;
	}

	public MainView getMainView() {
		return mainView;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public boolean isSelectArchivosLocales() {
		return selectArchivosLocales;
	}

	public void setSelectArchivosLocales(boolean selectArchivosLocales) {
		this.selectArchivosLocales = selectArchivosLocales;
	}

	public boolean getSelectArchivosLocales() {
		return selectArchivosLocales;
	}
	
	public LinkedList<Song> getCancionesArchivosList() {
		return cancionesArchivosList;
	}

	public void setCancionesArchivosList(LinkedList<Song> cancionesArchivosList) {
		this.cancionesArchivosList = cancionesArchivosList;
	}

	public int getCancionActualArchivosLocales() {
		return cancionActualArchivosLocales;
	}

	public int getCancionActualCanciones() {
		return cancionActualCanciones;
	}

	public void setCancionActualCanciones(int cancionActualCanciones) {
		this.cancionActualCanciones = cancionActualCanciones;
	}

	public void setCancionActualArchivosLocales(int cancionActualArchivosLocales) {
		this.cancionActualArchivosLocales = cancionActualArchivosLocales;
	}

	public String getActualSong() {
		String songName = "";
		if(selectArchivosLocales){
			songName = cancionesArchivosList.get(cancionActualArchivosLocales).getSongName();
		}else{
			songName = cancionesCancionesList.get(cancionActualCanciones).getSongName();
		}
		return songName;
	}

	public void setActualSong(String actualSong) {
		if(selectArchivosLocales){
			cancionesArchivosList.get(cancionActualArchivosLocales).setSongName(actualSong);
		}else{
			cancionesCancionesList.get(cancionActualCanciones).setSongName(actualSong);
		}
	}


}
