package Zadanie4;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

interface WeatherObserver {
    void update(String location, String weather);
}

class WeatherStation {
    private Map<String, List<WeatherObserver>> subscriptions = new HashMap<>();
    private WeatherService weatherService;

    public void addObserver(WeatherObserver observer, String location) {
        subscriptions.computeIfAbsent(location, k -> new ArrayList<>()).add(observer);
    }

    public void removeObserver(WeatherObserver observer, String location) {
        subscriptions.computeIfPresent(location, (k, v) -> {
            v.remove(observer);
            return v.isEmpty() ? null : v;
        });
    }

    public void setWeatherService(WeatherService service) {
        this.weatherService = service;
    }

    public void updateWeather(String location) {
        if (weatherService != null) {
            String weatherData = weatherService.getWeatherData(location);
            if (weatherData != null) {
                notifyObservers(location, weatherData);
            } else {
                System.out.println("Failed to fetch weather data for " + location);
            }
        } else {
            System.out.println("No weather service selected.");
        }
    }

    public void startWeatherUpdates(String location, int interval) {
        Timer timer = new Timer();
        class WeatherUpdateTask extends TimerTask {
            private String location;

            public WeatherUpdateTask(String location) {
                this.location = location;
            }

            @Override
            public void run() {
                updateWeather(location);
            }
        }

        timer.scheduleAtFixedRate(new WeatherUpdateTask(location), 0L, interval * 1000L);
    }

    private void notifyObservers(String location, String weatherData) {
        List<WeatherObserver> observers = subscriptions.get(location);
        if (observers != null) {
            for (WeatherObserver observer : observers) {
                observer.update(location, weatherData);
            }
        }
    }
}

class WeatherUser implements WeatherObserver {
    protected String name;

    public WeatherUser(String name) {
        this.name = name;
    }

    public void update(String location, String weatherData) {
        System.out.println(name + " received weather update for " + location + ": " + weatherData);
    }

    public void subscribeToWeather(WeatherStation station, String location, int updateInterval) {
        station.addObserver(this, location);
        station.startWeatherUpdates(location, updateInterval);
        System.out.println(name + " subscribed to weather updates for " + location + " with a " + updateInterval
                + " second interval.");
    }

    public void unsubscribeFromWeather(WeatherStation station, String location) {
        station.removeObserver(this, location);
        System.out.println(name + " unsubscribed from weather updates for " + location);
    }
}

// Wcale nie musimy mieć tylko jednej klasy z możliwością obserwowania
class PolishWeatherUser extends WeatherUser {

    public PolishWeatherUser(String name) {
        super(name);
    }

    public void update(String location, String weatherData) {
        System.out.println(name + " otrzymano aktualizację pogody " + location + ": " + weatherData);
    }

    public void subscribeToWeather(WeatherStation station, String location) {
        station.addObserver(this, location);
        station.startWeatherUpdates(location, 60); // Aktualizacja co 60 sekund
        System.out.println(name + " zasubskrybował pogodę dla " + location);
    }

    public void unsubscribeFromWeather(WeatherStation station, String location) {
        station.removeObserver(this, location);
        System.out.println(name + " odsubskrybował pogodę");
    }
}

interface WeatherService {
    String getWeatherData(String location);
}

public class WeatherApp {
    public static void main(String[] args) throws InterruptedException {
        WeatherStation weatherStation = new WeatherStation();

        // Wczytaj konfigurację z pliku properties
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("./Zadanie4/config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String weatherServiceClassName = "Zadanie4." + properties.getProperty("weatherService");
        WeatherService weatherService = createWeatherService(weatherServiceClassName);
        if (weatherService != null) {
            weatherStation.setWeatherService(weatherService);
        } else {
            System.out.println("Failed to create weather service.");
        }

        WeatherUser user1 = new WeatherUser("User 1");
        WeatherUser user2 = new WeatherUser("User 2");
        WeatherUser user3 = new PolishWeatherUser("Kowalski");

        user1.subscribeToWeather(weatherStation, "Paris", 60);
        user2.subscribeToWeather(weatherStation, "New York", 100);
        user3.subscribeToWeather(weatherStation, "Warsaw", 90);

        Thread.sleep(10000l);
        // Wyrejestrowanie użytkownika
        user1.unsubscribeFromWeather(weatherStation, "Paris");
        user1.subscribeToWeather(weatherStation, "London", 150);
        user2.subscribeToWeather(weatherStation, "Warsaw", 120);
    }

    private static WeatherService createWeatherService(String className) {
        try {
            Class<?> weatherServiceClass = Class.forName(className);
            return (WeatherService) weatherServiceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
