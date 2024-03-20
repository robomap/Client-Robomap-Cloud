package temp.model.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Song;
import model.SongsManager;
import model.User;
import temp.controller.MainController;

public class ServerComunication extends Thread{
	private boolean isOn;
	private Socket socketToServer;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private ObjectInputStream objectIn;
	private MainController mainController;
	private SongsManager songsManager;
	
	public SongsManager getSongsManager() {
		return songsManager;
	}

	public void setSongsManager(SongsManager songsManager) {
		this.songsManager = songsManager;
	}

	public ServerComunication(){
		try{
			this.isOn = false;
			// connectem amb el servidor i obrim els canals de comunicacio
			this.socketToServer = new Socket(NetworkConfiguration.SERVER_IP, NetworkConfiguration.SERVER_PORT);
			
		}catch(IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error al conectar-se amb el servidor,  provi de reiniciar el programa!");
			System.exit(0);
		}
	}
	
	public void receiveSong() throws IOException {		
			try {
				objectIn = new ObjectInputStream(socketToServer.getInputStream());
				FileOutputStream fos = new FileOutputStream("./music/"+mainController.getUsername()+"ActualSong.mp3");
				byte[] content;
				content = (byte[])objectIn.readObject();
				fos.write(content);
				fos.flush();
				fos.close();
			} catch (ClassNotFoundException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String reciveMessage() throws IOException{
		String message = "";

		dataIn = new DataInputStream(socketToServer.getInputStream());
		message = dataIn.readUTF();
		System.out.println("CLIENT:" + message);
		return message;
	}
	
	public void startServerComunication(){
		// iniciem la comunicacio amb el servidor
		isOn = true;
		this.start();
	}

	public void stopServerComunication(){
		//Aturem la comunicacio amb el servidor
		this.isOn = false;
		this.interrupt();
	}
	
	public LinkedList<Song> recieveListSongs(){
		LinkedList<Song> listaCanciones = new LinkedList<Song>();
		try {
			objectIn = new ObjectInputStream(socketToServer.getInputStream());
			@SuppressWarnings({ "unchecked" })
			List<Song> songList = (List<Song>) objectIn.readObject();
			
			for(int i = 0; i < songList.size(); i++){
				listaCanciones.add(songList.get(i));
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaCanciones;
	}

	public LinkedList<User> recieveListUsers(){
		LinkedList<User> listaUsuarios = new LinkedList<User>();
		try {
			objectIn = new ObjectInputStream(socketToServer.getInputStream());
			@SuppressWarnings({ "unchecked" })
			List<User> usuarios = (List<User>)objectIn.readObject();
			
			for(int i = 0; i < usuarios.size(); i++){
				listaUsuarios.add(usuarios.get(i));
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarios;
	}
	
	public LinkedList<String> recieveListUsersFollowing(){
		LinkedList<String> listaUsuarios = new LinkedList<String>();
		try {
			objectIn = new ObjectInputStream(socketToServer.getInputStream());
			@SuppressWarnings({ "unchecked" })
			List<String> usuarios = (List<String>)objectIn.readObject();
			System.out.println(usuarios);
			if(usuarios != null){
				for(int i = 0; i < usuarios.size(); i++){
					listaUsuarios.add(usuarios.get(i));
				}
			}else{
					listaUsuarios.add("No sigues a nadie!");
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarios;
	}
	
	public LinkedList<User> recieveListFollowing (){
		LinkedList<User> listaFollowing = new LinkedList<User>();
		try {
			objectIn = new ObjectInputStream(socketToServer.getInputStream());
			@SuppressWarnings({ "unchecked" })
			List<User> Following = (List<User>)objectIn.readObject();
			
			for(int i = 0; i < Following.size(); i++){
				listaFollowing.add(Following.get(i));
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaFollowing;
	}
	
	public User reciveUserPlaylist(){
		User u = new User();
		try {
			objectIn = new ObjectInputStream(socketToServer.getInputStream());
			User user = (User)objectIn.readObject();
			u = user;
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
	
	public void run(){
		try {
			while(isOn){
				String message = reciveMessage();
				System.out.println(message);
					if(message.equals("LIST_SONGS")){
						mainController.setListSongs(recieveListSongs());
					}else if(message.contains("PLAY_SONG")){
						receiveSong();
						String[] info = message.split(":");
						mainController.cargarCancion(info[1]);
					}else if(message.contains("Error")){
						JOptionPane.showMessageDialog(new JFrame(), message);
						mainController.avanzar();
					}else if(message.equals("LIST_USERS")){
						mainController.actualizarListUsers(recieveListUsers());
					}else if(message.equals("LIST_FOLLOWING")){
						mainController.actualizarListFollowing(recieveListUsersFollowing());
					}else if(message.equals("USER_PLAYLIST")){
						mainController.actualizarUserPlaylist(reciveUserPlaylist());
					}else if(message.equals("PLAYLISTS")){
						//Printar playlists usuario al left panel falta funcion
						mainController.actualizarPlaylists(reciveUserPlaylist().getPlayLists());
					}
			}
		}catch(IOException e) {
			e.printStackTrace();
			stopServerComunication();
			// si hi ha algun problema satura la comunicacio amb el servidor
			//stopServerComunication();
			System.out.println("*** ERROR DE CONNNEXIO AMB EL SERVIDOR ***");
		}	
	}
	
	public void sendSelection(String selection) {
		try {
			// envia el panell sobre el cual sha fet clic al servidor.
			// Aixo provocara que el servidor actualitzi el model i que
			// a continuacio envii lestat del model a tots els clients,
			// inclos aquest mateix.
			dataOut = new DataOutputStream(socketToServer.getOutputStream());
			dataOut.writeUTF(selection);
		} catch (IOException e) {
			e.printStackTrace();
			// si hi ha algun problema satura la comunicacio amb el servidor
			stopServerComunication();
			System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
		}
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
