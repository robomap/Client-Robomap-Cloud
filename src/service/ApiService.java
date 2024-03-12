package service;

import java.io.IOException;
import java.util.Map;

public class ApiService {

    private ApiClient apiClient;

    public ApiService() {
        this.apiClient = new ApiClient();
    }

    public String login(String username, String password) throws IOException {
        String apiUrl = "http://localhost:8000/auth/login";

        Map<String, String> loginData = Map.of(
                "email", "test.user@robomap.ai",
                "password", "Robomap"
        );

        return apiClient.sendPostRequest(apiUrl, loginData);
    }
}
