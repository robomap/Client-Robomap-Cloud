package Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import View.ViewServer;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class Reproductor implements BasicPlayerListener {
	@SuppressWarnings("unused")
	private InputStream fis;
	private BasicPlayer player;
	@SuppressWarnings("unused")
	private BasicController control = (BasicController) player;
	private AudioFileFormat baseFileFormat;
	public double long_bytes = 0;
	private String estado = "error";
	private long segPasados = 0;
	public float progressUpdate;
	public double progressNow = 0;
	private long duracion = 0;
	@SuppressWarnings({ "unused", "rawtypes" })
	private Map properties;
	private String song;
	private ViewServer view;

	public void setView(ViewServer view){
		this.view = view;
	}
	
	public Reproductor() {
		player = new BasicPlayer();
		
	}
	
	public boolean loadSong(String song) { 
		boolean error = false;
		estado = "parado";
		player.addBasicPlayerListener(this);
		try {
			this.song = song;
			player.open(new File(song));
			player.play();
			view.setPlay();
			estado = "reproduciendo";
		} catch (BasicPlayerException | NullPointerException e) {
			error = true;
			view.setjProgressBar(0, 0, 0);
			estado = "error";
		}
		return error;
	}
	
	public long getTotalTime(){
		long i = 0;
		if(duracion <= 0){
			i  = 0;
		}else{
			i = duracion;
		}
		return i;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void opened(Object arg0, Map map) {
		// TODO Auto-generated method stub
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(new File(song));
		} catch (UnsupportedAudioFileException | IOException e) {}


		properties = baseFileFormat.properties();
		if(map.containsKey("audio.length.bytes")){
			long_bytes = (Double.parseDouble((map).get("audio.length.bytes").toString())/1024);
			view.setMaximumProgressBar(((int)(long_bytes))*1000);
		}
		
		duracion = getSeconds(Double.parseDouble(map.get("duration").toString()));
	}
	
	public void volumen(){
		try {
			player.setGain(10);
		} catch (BasicPlayerException e) {}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void progress(int bytesread,long microsecond,byte[] pcmdata, Map properties){
		float progressUpdate = (float)(bytesread * 1.0f / long_bytes * 1.0f);
		progressNow = (double)((long_bytes * progressUpdate)/1024)*1000;
		view.setjProgressBar(progressNow, getTotalTime(), getTimeRead());
	}
	
	public long getTimeRead(){
		long time = (long)((duracion*progressNow/1000)/long_bytes);
		return time;
	}
	
	private int getSeconds(double d){
		int aux = (int)(d/1000000);
		return aux;
	}
	
	public void pause(){
		try {
			if(estado.equals("reproduciendo")){
				player.pause();
				estado = "pausado";
			}			
		} catch (BasicPlayerException e) {}
	}
	
	public void resume(){
		try{
			if(estado.equals("pausado")){
				player.resume();
				estado = "reproduciendo";
			}
		}catch(BasicPlayerException e){}
	}
	
	public void retroceder(long position){
		try{
			float pAux = progressUpdate;
			int a = (int)(long_bytes * (pAux-0.003));
			player.seek((long)(a));
			play();
			
		}catch(BasicPlayerException e){}
	}

	public void adelantar(long position){
		try{
			float pAux = progressUpdate;
			int a = (int)(long_bytes * (pAux+0.001));
			player.seek((long)(a));
			play();
			
		}catch(BasicPlayerException e){}
	}
	
	public String getState(){
		return estado;
	}
	
	public void setState(String c){
		if(c.equals("parado")){
			estado = "parado";
		}else if(c.equals("reproduciendo")){
			estado = "reproduciendo";
		}else if(c.equals("pausado")){ 
			estado = "pausado";
		}
	}
	
	public long getSegundosPasados(){
		return segPasados;
	}
	
	public void stop(){
		try {
			if(!estado.equals("parado")){
				player.stop();
				estado = "parado";
			}
			
		} catch (BasicPlayerException e) {}
	}
	
	public String getName(){
		return null;	
	}
	
	public boolean play(){
		boolean error = true;
		try {
			if(estado.equals("parado")){
				player.play();
				estado = "reproduciendo";
				error = false;
			}else if(estado.equals("pausado")){
				player.resume();
				player.play();
				estado = "reproduciendo";
				error = false;
			}else if (estado.equals("error")){
				error = true;
			}		
		} catch (BasicPlayerException e) {}

		return error;
	}

	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}