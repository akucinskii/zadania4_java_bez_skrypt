package Zadanie6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task {
    private String description;
    private boolean isComplete;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}

class TaskView {
    public void displayTasks(List<Task> tasks) {
        System.out.println("Lista zadań:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isComplete() ? "Zakończone" : "Niezakończone";
            System.out.println((i + 1) + ". " + task.getDescription() + " [" + status + "]");
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}

class TaskController {
    private List<Task> tasks;
    private TaskView view;

    public TaskController(TaskView view) {
        this.tasks = new ArrayList<>();
        this.view = view;
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        view.displayMessage("Nowe zadanie zostało dodane: " + description);
    }

    public void displayTasks() {
        view.displayTasks(tasks);
    }

    public void editTask(int index, String newDescription) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setDescription(newDescription);
            view.displayMessage("Zadanie zostało zaktualizowane.");
        } else {
            view.displayMessage("Nieprawidłowy indeks zadania.");
        }
    }

    public void changeTaskStatus(int index, boolean isComplete) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setComplete(isComplete);
            view.displayMessage("Status zadania został zaktualizowany.");
        } else {
            view.displayMessage("Nieprawidłowy indeks zadania.");
        }
    }
}

class TaskApp {
    public static void main(String[] args) {
        TaskView view = new TaskView();
        TaskController controller = new TaskController(view);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Co chciałbyś zrobić?");
            System.out.println("1. Wyświetl zadania");
            System.out.println("2. Dodaj nowe zadanie");
            System.out.println("3. Edytuj zadanie");
            System.out.println("4. Zmień status zadania");
            System.out.println("5. Wyjdź z aplikacji");
            System.out.print("Wybierz opcję: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                controller.displayTasks();
            } else if (choice == 2) {
                scanner.nextLine();
                System.out.print("Podaj opis nowego zadania: ");
                String description = scanner.nextLine();
                controller.addTask(description);
            } else if (choice == 3) {
                System.out.print("Podaj numer zadania do edycji: ");
                int taskNumber = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Podaj nowy opis zadania: ");
                String newDescription = scanner.nextLine();
                controller.editTask(taskNumber - 1, newDescription);
            } else if (choice == 4) {
                System.out.print("Podaj numer zadania do zmiany statusu: ");
                int taskNumber = scanner.nextInt();
                System.out.print("Podaj nowy status zadania (true = zakończone, false = niezakończone): ");
                boolean isComplete = scanner.nextBoolean();
                controller.changeTaskStatus(taskNumber - 1, isComplete);
            } else if (choice == 5) {
                System.out.println("Dziękujemy za skorzystanie z aplikacji. Do widzenia!");
                break;
            } else {
                System.out.println("Nieprawidłowa opcja. Wybierz 1, 2, 3, 4 lub 5.");
            }
        }

        scanner.close();
    }
}
