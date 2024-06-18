package Zadanie2;

public class Zadanie2 {
    public static void main(String[] args) {
        // Uzyskanie instancji loggera
        Logger logger = Logger.getInstance();

        // Logowanie wiadomości na różnych poziomach
        logger.info("This is an informational message.");
        logger.warning("This is a warning message.");
        logger.error("This is an error message.");
    }
}
