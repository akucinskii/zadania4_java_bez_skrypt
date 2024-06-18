package Zadanie3;

// Interfejs operacji matematycznych

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

interface Operation {
    double calculate(double a, double b);
}

class Addition implements Operation {
    public double calculate(double a, double b) {
        return a + b;
    }
}

class Subtraction implements Operation {
    public double calculate(double a, double b) {
        return a - b;
    }
}

class Multiplication implements Operation {
    public double calculate(double a, double b) {
        return a * b;
    }
}

class Division implements Operation {
    public double calculate(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Dzielenie przez zero");
        }
        return a / b;
    }
}

class Modulo implements Operation {
    public double calculate(double a, double b) {
        return a % b;
    }
}

class Exponentiation implements Operation {
    public double calculate(double a, double b) {
        return Math.pow(a, b);
    }
}

// Fabryka operacji matematycznych
class OperationFactory {
    public Operation createOperation(String operator) {
        switch (operator) {
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            case "%":
                return new Modulo();
            case "^":
                return new Exponentiation();
            default:
                return null;
        }
    }
}

public class Calculator {
    public static void main(String[] args) {
        OperationFactory operationFactory = new OperationFactory();
        if (args.length == 0) {
            System.out.println("Podaj nazwę pliku do wczytania danych");
            System.exit(0);
        }
        String fileName = args[0]; // Nazwa pliku, w którym znajdują się operacje i liczby

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(" ")); // Rozdzielamy wg spacji

                if (parts.length != 3) {
                    System.out.println("Błąd w danych wejściowych: " + line);
                    continue;
                }

                double operand1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double operand2 = Double.parseDouble(parts[2]);

                Operation operation = operationFactory.createOperation(operator);
                if (operation != null) {
                    try {
                        double result = operation.calculate(operand1, operand2);
                        System.out.println(operand1 + " " + operator + " " + operand2 + " = " + result);
                    } catch (ArithmeticException e) {
                        System.out.println("Błąd obliczeń: " + e.getMessage());
                    }
                } else {
                    System.out.println("Nieznany operator: " + operator);
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }
}
