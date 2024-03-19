package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import service.ApiClient;
import view.DevicesView;

public class AuthenticationController implements ActionListener {
    private DevicesView authenticationView;
    private ApiClient apiClient;

    public AuthenticationController(DevicesView authenticationView) {
        this.authenticationView = authenticationView;
        this.apiClient = new ApiClient();
        this.authenticationView.registreControladorLogin(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("LOGIN".equals(e.getActionCommand())) {
            // Get username and password from the view
            String username = authenticationView.getUsername();
            String password = authenticationView.GetContraenyaEntra();
            System.out.println("Credentials-----------------" + username + password);
            // Call the login method in ApiClient
            try {
                String response = apiClient.login(username, password);

                // Handle the response (e.g., display a message in the view)
                authenticationView.setInformacioLogin("API Response: " + response);
            } catch (IOException ex) {
                ex.printStackTrace();
                // Handle the exception (e.g., display an error message in the view)
                authenticationView.setInformacioLogin("Error occurred during login.");
            }
        }
    }
}
