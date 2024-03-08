package temp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import temp.model.network.ServerComunication;
import view.RegisterView;

public class RegisterController implements ActionListener {
	private RegisterView registerView;
	private ServerComunication sComunication;
	
	public  RegisterController(RegisterView registerView, ServerComunication sComunication){
		this.registerView = registerView;
		this.sComunication = sComunication;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("REGISTRAR-SE")){
			boolean letter = false;
			boolean digit = false;
			for(int i = 0; i < registerView.GetContrasenyaRegistre().length(); i++){
				if(Character.isLetter(registerView.GetContrasenyaRegistre().charAt(i))){
					letter = true;
				}
				if(Character.isDigit(registerView.GetContrasenyaRegistre().charAt(i))){
					digit = true;
				}
			}
			if(registerView.GetContrasenyaRegistre().length() < 6 || !digit || !letter){
				registerView.SetOmplirRespostaRegistre("Error al Registrar-se");	
			}else if (registerView.GetCorreuRegistre().length() < 1){
				registerView.SetOmplirRespostaRegistre("Error al Registrar-se");
			}else if (ComprobarCorreu()){
				registerView.SetOmplirRespostaRegistre("Error al Correu");
			}else if (registerView.GetNickRegistre().equals("")){
				registerView.SetOmplirRespostaRegistre("Error al Registrar-se");			
			}else{
				String dataOutput = ("REGISTER:"+registerView.GetNickRegistre()+"/"
						+registerView.GetContrasenyaRegistre()+"/"
						+registerView.GetCorreuRegistre());
				sComunication.sendSelection(dataOutput);
				try {
					String answer = sComunication.reciveMessage();
					if(answer.equals("LIST_USERS")){
						sComunication.recieveListUsers();
						registerView.exitRegister();
						//List  following
						sComunication.reciveMessage();
						sComunication.recieveListUsersFollowing();
					}else if(answer.contains("UsernameError")){
						registerView.SetOmplirRespostaRegistre("Error el nombre de usuario ya existe!");
					}else if(answer.contains("MailError")){
						registerView.SetOmplirRespostaRegistre("Error el mail ya existe!");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public boolean ComprobarCorreu(){
		boolean error = true;
		if(registerView.GetCorreuRegistre().contains("@") && 
		    registerView.GetCorreuRegistre().contains(".") &&
		    registerView.GetCorreuRegistre().length() > 4){
			error = false;	
		}
		return error;
	}
}
