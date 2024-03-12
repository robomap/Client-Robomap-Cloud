package service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    public ApiClient() {
    }

    public String sendPostRequest(String apiUrl, Map<String, String> postData) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            StringBuilder postDataStringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : postData.entrySet()) {
                if (postDataStringBuilder.length() != 0) {
                    postDataStringBuilder.append('&');
                }
                postDataStringBuilder.append(entry.getKey());
                postDataStringBuilder.append('=');
                postDataStringBuilder.append(entry.getValue());
            }
            byte[] postDataBytes = postDataStringBuilder.toString().getBytes("UTF-8");

            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(postDataBytes);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }
                return responseStringBuilder.toString();
            }
        } finally {
            connection.disconnect();
        }
    }

    public String login(String email, String password) throws IOException {
        String apiUrl = "http://localhost:8000/auth/login";

        // Create a map with the login data
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        // Send the login request using ApiClient
        return sendPostRequest(apiUrl, loginData);
    }
    
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();

        try {
            String apiUrl = "http://localhost:8000/auth/login";
            Map<String, String> postData = new HashMap<>();
            postData.put("email", "test.user@robomap.ai");
            //Map<String, String> postData = Map.of("test.user@robomap.ai", "Robomap");
            postData.put("password", "Robomap");
            String response = apiClient.sendPostRequest(apiUrl, postData);
            System.out.println("API Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
