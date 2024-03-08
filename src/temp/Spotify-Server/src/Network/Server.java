package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import Controller.ControllerServer;

public class Server extends Thread{
	private boolean isOn;
	private ServerSocket sSocket;
	//Relacio amb els servidors dedicats
	private LinkedList<DedicatedServer> dServers;
	private ControllerServer controller;

	public Server(ControllerServer  controller) {
		this.controller = controller;
		try {
			this.isOn = false;
			//Obrim un socket de tipus servidor
			this.sSocket = new ServerSocket(NetworkConfiguration.SERVER_PORT);
			this.dServers = new LinkedList<DedicatedServer>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		//Iniciem el thread del servidor
		isOn = true;
		this.start();
	}

	public void stopServer() {
		//Aturem el thread del servidor
		isOn = false;
		this.interrupt();
	}

	public void showClients() {
		System.out.println("***** SERVER ***** (" + dServers.size() +" clients / dedicated servers running)");
	}
	
	public void sendMessageAllListSongs(){
		for(DedicatedServer sClient : dServers){
			sClient.sendListSongs();
		}
	}

	public void sendAllListUsers(){
		for(DedicatedServer sClient : dServers){
			sClient.sendListUsers();
			sClient.sendListUsersFollowing(sClient.getUsername());
		}
	}
	
	public void run()  {
		while (isOn) {
			try {
				// acceptem peticions de connexio dels clients
				// BLOQUEJA EXECUCIO DEL THREAD
				Socket sClient = sSocket.accept();
				
				//Creem un nou servidor dedicat per atendre les peticions del client
				DedicatedServer dsClient = new DedicatedServer(controller,  sClient, dServers, this);
				dServers.add(dsClient);
				dsClient.startDedicatedServer();
				//Engegem el servidor dedicat
				//dsClient.startDedicatedServer();
				showClients();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Aturem tots els servidors dedicats creats quan ja no atenem mes peticions de clients
		for (DedicatedServer dServer : dServers) {
			dServer.startDedicatedServer();
		}
	}
}
