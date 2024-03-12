package controller;

import java.io.IOException;

import service.ApiService;

public class ApiController {
    private ApiService ApiService;

    public ApiController() {
        this.ApiService = new ApiService();
    }

    public void handleLoginRequest(String username, String password) {
        try {
            String response = ApiService.login(username, password);
            System.out.println("API Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
