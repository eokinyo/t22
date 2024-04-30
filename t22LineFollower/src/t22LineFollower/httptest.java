package t22LineFollower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httptest {

    // Method to read data from a given URL
    public String readDataFromUrl(String urlString) {
        URL url;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();
        
        try {
            // Create a URL object using the provided URL string
            url = new URL("128 database file adress");
            
            // Open a connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // Use GET method
            conn.setRequestProperty("Accept", "application/json"); // Expecting JSON response
            
            // Check for non-200 response codes
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP request failed with response code " + responseCode);
            }
            try (InputStream is = conn.getInputStream()) {
                InputStreamReader isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading from URL: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.err.println("Error closing buffered reader: " + e.getMessage());
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return response.toString();
    }
}
