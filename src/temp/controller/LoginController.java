package temp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import view.AuthenticationView;
import view.MainView;
import view.RegisterView;

public class LoginController implements ActionListener {
	private AuthenticationView loginView;
	private MainView mainView;
	private ServerComunication sComunication;
	private RegisterView registerView;
	RegisterController registerController;

	public LoginController (AuthenticationView loginView){
		mainView = new MainView();	
		this.loginView = loginView;
		//Part comunicacio inicialitzem socket de comunicacio
		sComunication = new ServerComunication();
		//Inicialitzem registre d'usuari
		registerView = new RegisterView();
		registerController = new RegisterController(registerView, sComunication);
		registerView.registreControladorRegistrat(registerController);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("REGISTRAR")){
			//Afegim el boto que volguem 
			registerView.setVisible(true);
		}else if (e.getActionCommand().equals("LOGIN")){
			userLogin();
		}
	}
	
	public void userLogin(){
		String dataOutput;

		dataOutput = ("LOGIN:"+loginView.getUsername()+"/"
			+loginView.GetContraenyaEntra());
	
		if(loginView.getUsername().length() < 1 || loginView.GetContraenyaEntra().length() < 1){
			loginView.setInformacioLogin("Error a l'entrar, nom d'usuari o contrasenya incorrectes!");	
		}else{
			sComunication.sendSelection(dataOutput);
			try {
				if(sComunication.reciveMessage().equals("OK")){
					loginView.setVisible(false);
					mainView.setVisible(true);
					mainView.setUsername(loginView.getUsername());
					MainController mainViewController = new MainController(mainView, sComunication, loginView.getUsername());	
					mainViewController.setUsername(loginView.getUsername());
					initMainViewControllers(mainViewController);
				}else{
					loginView.setInformacioLogin("Error a l'entrar, nom d'usuari o contrasenya incorrectes!");	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initMainViewControllers(MainController mainViewController){
		mainViewController.start();
		initControllersButtons(mainViewController);
		initControllersMenu(mainViewController);
	}
	
	public void initControllersButtons(MainController mainViewController){
		mainView.registreControladorPlay(mainViewController);
		mainView.registreControladorStop(mainViewController);
		mainView.regitreCintroladorDavant(mainViewController);
		mainView.regitreCintroladorEnrera(mainViewController);
		mainView.registreControladorActividad(mainViewController);
		mainView.registreControladorAlbumes(mainViewController);
		mainView.registreControladorCanciones(mainViewController);
		mainView.registreControladorEmisoras(mainViewController);
		mainView.registreControladorExplorar(mainViewController);
		mainView.registreControladorRadio(mainViewController);
		mainView.registreControladorArchivosLocales(mainViewController);
		mainView.registreControladorReproduir(mainViewController);
		mainView.registreControladorPauseArchivos(mainViewController);
		mainView.registreControladorReproduirArchivos(mainViewController);
		mainView.registreControladorPause(mainViewController);
		mainView.registreControladorAbrirCarpeta(mainViewController);
		mainView.registreControladorBuscar(mainViewController);
		mainView.registreTableArchivos(mainViewController);
		mainView.registreTableCanciones(mainViewController);
		mainView.registreControladorNovaLLista(mainViewController);
		mainView.registreNovaLListaMenu(mainViewController);
		mainView.registreWindow(mainViewController);
		mainView.registreTableUsers(mainViewController);
		mainView.registreBuscarUsuari(mainViewController);
		mainView.registrePopupMenuPlaylist(mainViewController);
		mainView.registreFollowing(mainViewController);
		mainView.phregistreBuscar(mainViewController);
		mainView.phregistreBuscarUsuari(mainViewController);
	}
	
	public void initControllersMenu(MainController mainViewController){
		mainView.registerMenuPause(mainViewController);
		mainView.registerMenuSalir(mainViewController);
		mainView.registreMenuBuscar(mainViewController);
		mainView.registreMenuAcercar(mainViewController);
		mainView.registreMenuAlejar(mainViewController);
		mainView.registreMenuZoom(mainViewController);
	}
}
