package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import model.Song;
import model.User;
import Controller.ControllerServer;

public class DedicatedServer extends Thread{
	private boolean isOn;
	private Socket sClient;
	private DataInputStream dataInput;
	private DataOutputStream dataOut;
	private ObjectOutputStream objectOut;
	private LinkedList<DedicatedServer> clients;
	private Server server;
	private String estado = "inicio";
	private ControllerServer controller;
	private String username;
	
	public DedicatedServer(ControllerServer controller, Socket sClient, LinkedList<DedicatedServer> clients, Server server) {
		this.isOn = false;
		this.sClient = sClient;
		this.clients = clients;
		this.server = server;
		this.controller = controller;
	}

	public void startDedicatedServer() {
		//Iniciem el servidor dedicat
		isOn = true;
		this.start();
	}

	public void stopDedicatedServer() {
		//Aturem el servidor dedicat
		this.isOn = false;
		this.interrupt();
	}
	
	public void sendListSongs(List<Song> listSongs) {
		try {
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			objectOut.writeObject(listSongs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//vipe
	public void sendUserPlaylists(String diStream){
		String[] info = diStream.split(":");
		//Conte el nom d'usuari :   info[1]
		try {
			User user = new User();
			user.setPlayLists(controller.getUserPlayLists(info[1]));
			sendSelection("USER_PLAYLIST");
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			objectOut.writeObject(user);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPlaylists(String username){

		//Conte el nom d'usuari :   info[1]
		try {
			User user = new User();
			user.setPlayLists(controller.getUserPlayLists(username));
			sendSelection("PLAYLISTS");
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			objectOut.writeObject(user);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendListUsers(List<User> listUsers) {
		try {
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			objectOut.writeObject(listUsers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendListUsersFollowing(List<String> listUsers) {
		try {
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			objectOut.writeObject(listUsers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendSong(File song) {
		try {
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			byte[] content = Files.readAllBytes(song.toPath());
			objectOut.writeObject(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendListSongs(){
		List<Song> songList = controller.getListSongs();
		sendSelection("LIST_SONGS");
		sendListSongs(songList);
	}

	public void sendListUsers(){
		List<User> userList = controller.getListUsers();
		sendSelection("LIST_USERS");
		sendListUsers(userList);
	}
	
	//Victor esta funcion tienen que ir los usuarios que sigues
	public void sendListUsersFollowing(String username){
		
		List<String> userList = null;
		if(username != null){
			this.username = username;
			userList = controller.getListUsersFollowing(username);
		}
		
		sendSelection("LIST_FOLLOWING");
		sendListUsersFollowing(userList);
	}
	
	public void playSong(String diStream){
		String[] info = diStream.split(":");
		List<Song> songList = controller.getListSongs();
		Song songSelected = new Song();
		for(Song song : songList){
			if(info[1].equals(song.getSongName())){
				songSelected = song;
			}
		}
		File songFile;
		songFile = (new File(songSelected.getRuta()));
		if(songFile.canRead()){
			sendSelection("PLAY_SONG:" + songSelected.getSongName());
			songSelected.addReproduccion();
			controller.updateReproductions(songSelected);
//			controller.setCancion(songSelected);
			sendSong(songFile);
		}else{
			sendSelection("Error al reproduir la canço: "+songSelected.getSongName());
		}
	}
	
	public void setPuntuacion(String diStream){
		String[] info = diStream.split(":");
		String[] aux = info[1].split("/");
		List<Song> songList = controller.getListSongs();
		Song songSelected = new Song();
		for(Song song : songList){
			if(aux[0].equals(song.getSongName())){
				songSelected = song;
			}
		}

		songSelected.setPuntuacion(Integer.parseInt(aux[1]));
		controller.updatePuntuation(songSelected);
//		controller.setCancion(songSelected);
		sendListSongs();
	}
	
	public void programa() throws IOException {
		
		String diStream = dataInput.readUTF();
		String[] parts = diStream.split(":");
		System.out.println("SERVER:" + diStream);
				
		switch(parts[0].toUpperCase()) {
		
			case "LIST_SONGS":
				sendListSongs();
				break;
				
			case "PLAY_SONG":
				playSong(diStream);
				break;
				
			case "SET_PUNTUACION":
				setPuntuacion(diStream);
				break;
				
			case "LIST_USERS":
				sendListUsers();
				break;

			case "LIST_FOLLOWING":
				sendListUsersFollowing(parts[1]);
				break;
				
			case "USER_PLAYLIST":
				//Aqui s'ha d'enviar les llistes de reproduccio dels usuaris
				sendUserPlaylists(diStream);
				break;
				
			case "PLAYLISTS":
				String[] info = diStream.split(":");
				sendPlaylists(info[1]);
				break;
//				//Enviar la llista seleccionada del panell left
//				sendListSongs();
//				break;
				
			case "CREATE_PLAYLIST":
				//parts[1] = user.name, parts[2] = playlist.name
				controller.createPlayList(parts[1], parts[2]);
				sendPlaylists(parts[1]);
				break;
				
			case "DELETE_PLAYLIST":
				//parts[1] = user.name, parts[2] = playlist.name
				controller.deletePlayList(parts[1], parts[2]);
				sendPlaylists(parts[1]);
				break;
				
			case "READ_PLAYLIST":
				//parts[1] = user.name, parts[2] = playlist.name
				//sendSelection("READ_PLAYLIST:" + parts[1] + ":" + parts[2]);
				sendSelection("LIST_SONGS");
				sendListSongs(controller.readPlayList(parts[1], parts[2]));
				break;
			
			case "DELETE_SONG_PLAYLIST":
				//parts[1] = user.name, parts[2] = playlist.name
				//parts[3] = song.name, parts[4] = song.artist
				
				try{
					controller.deleteSongPlayList(parts[1], parts[2], parts[3], parts[4]);
				}catch(ArrayIndexOutOfBoundsException e){}
				
				sendSelection("LIST_SONGS");
				//Falta------------------------------------------------------//Eliminar cancion de a base de datos
				//-----------------------------------------------------------
				//-----------------------------------------------------------
				sendListSongs(controller.readPlayList(parts[1], parts[2]));
				break;
			
			case "ADD_SONG_PLAYLIST":
				//parts[1] = user.name, parts[2] = playlist.name, 
				//parts[3] = song.name, parts[4] = song.artist
				controller.addSongPlayList(parts[1], parts[2], parts[3], parts[4]);
				
			case "FOLLOW":
				//parts[1] = user.name (me), parts[2] = user.name (follow)
				List<String> userList = null;
				if(username != null){
					userList = controller.getListUsersFollowing(username);
				}
				boolean error = false;
				for(String user : userList){
					if(user.equals(parts[2])){
						error = true;
					}
				}
				if(!error){
					controller.followUser(parts[1], parts[2]);	
				}			
				break;
				
			case "UNFOLLOW":
				//parts[1] = user.name (me), parts[2] = user.name (follow)
				controller.unfollowUser(parts[1], parts[2]);
				break;
				
		}

	}
	
	public void inicio() throws IOException{
			String diStream;
			diStream = dataInput.readUTF();
			System.out.println(diStream);
			if(diStream.contains("REGISTER")){
				//Username, password i mail
				String[] info = diStream.split(":");
				String[] aux = info[1].split("/");
				int error = controller.checkRegister(aux[0], aux[1], aux[2]);
				if(error == 1){
					sendSelection("KO:MailError");
				}else if(error == 2){
					sendSelection("KO:UsernameError");
				}else if(error == 3){
					sendSelection("KO:ErrorTotal");
				}
			}else if(diStream.contains("LOGIN")){
				String[] info = diStream.split(":");
				String[] aux = info[1].split("/");
				//Username i password
				boolean error = controller.checkLogin(aux[0], aux[1]);
				if(!error) {
					sendSelection("OK");
					estado = "programa";
				}else if(error){
					sendSelection("KO");
				}
			}
	}
	
	public void run(){
		try{
			while(isOn){
				dataInput = new DataInputStream(sClient.getInputStream());
				dataOut = new DataOutputStream(sClient.getOutputStream());
				if(estado.equals("inicio")){
					inicio();
				}else{
					programa();
				}
			};
		}catch(IOException e){
			// en cas derror aturem el servidor dedicat
			stopDedicatedServer();
			// eliminem el servidor dedicat del conjunt de servidors dedicats
			clients.remove(this);
			//Invoquem el metode del servidor que mostra els servidors dedicats actuals
			server.showClients();
		}
	}

	public void sendSelection(String selection) {
		try {
			dataOut.writeUTF(selection);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
