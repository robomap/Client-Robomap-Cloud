package temp.model;

import javax.swing.SwingUtilities;

import controller.LoginController;
import view.AuthenticationView;

public class Principal {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AuthenticationView loginView = new AuthenticationView();		
		SwingUtilities.invokeLater(new Runnable() {	
			public void run() {		
				try{
					//Entrem a l'interficie de Login
					LoginController loginController = new LoginController(loginView);
					//Controlador del boto Registra per quan l'usuari premi registrar
					loginView.registreControladorRegistra(loginController);
					// Controlador per quan l'usuari premi login 
					loginView.registreControladorLogin(loginController);
				}catch(Exception e){}
			}
		});
	}
}