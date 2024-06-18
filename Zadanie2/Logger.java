package Zadanie2;

// Logger.java
public class Logger {
    // Jedyna instancja klasy Logger (Singleton)
    private static Logger instance;

    // Prywatny konstruktor, aby uniemożliwić tworzenie obiektów z zewnątrz
    private Logger() {
    }

    // Metoda do uzyskania jedynej instancji klasy Logger
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Metoda logowania na poziomie info
    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    // Metoda logowania na poziomie warning
    public void warning(String message) {
        System.out.println("WARNING: " + message);
    }

    // Metoda logowania na poziomie error
    public void error(String message) {
        System.out.println("ERROR: " + message);
    }
}
