
package t22LineFollower;
 
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import lejos.hardware.Button;

import lejos.hardware.Sound;
 
public class httptest {
 
    public static void main(String[] args) {

        System.out.println("Read some text from URL");

        System.out.println("Press any key to start");

        Button.waitForAnyPress();
 
        // The URL to connect to the server's REST API

        String urlString = "http://172.30.160.1:8080/rest/t22RestfulProject/addrobotbyget";
 
        // Try to open the connection and read from the URL

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 
            // Set request method and timeouts

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000); // Connection timeout (milliseconds)

            connection.setReadTimeout(5000); // Read timeout (milliseconds)
 
            // Check response code

            int responseCode = connection.getResponseCode();

            System.out.println("Response Code: " + responseCode);
 
            // Check if the response code is OK (200)

            if (responseCode == HttpURLConnection.HTTP_OK) {

                try (InputStream inputStream = connection.getInputStream();

                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        System.out.println(line);

                    }

                }

            } else {

                System.out.println("Failed to retrieve data from URL. Response code: " + responseCode);

            }
 
            // Close the connection

            connection.disconnect();
 
        } catch (MalformedURLException e) {

            System.out.println("Invalid URL format: " + urlString);

            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("Error occurred while trying to connect to the URL: " + urlString);

            e.printStackTrace();

        }
 
        System.out.println("Press any key to finish.");

        Button.waitForAnyPress();

    }

}
