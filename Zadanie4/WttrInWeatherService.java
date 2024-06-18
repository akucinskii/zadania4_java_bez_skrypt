package Zadanie4;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

class WttrInWeatherService implements WeatherService {
    public String getWeatherData(String location) {
        HttpURLConnection connection = null;
        try {
            String apiUrl = "https://wttr.in/" + location.replace(" ", "%20") + "?format=%C+%t";
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    response.append((char) c);
                }
                reader.close();

                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}
