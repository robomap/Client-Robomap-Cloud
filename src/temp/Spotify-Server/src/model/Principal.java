package model;

import javax.swing.SwingUtilities;

import Controller.ControllerServer;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {		
				try{
					ControllerServer ctrl = new ControllerServer();
					ctrl.render();  
				}catch(Exception e){}
			}
		});
	}
}