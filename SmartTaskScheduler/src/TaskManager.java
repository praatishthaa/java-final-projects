import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class TaskManager {
    private PriorityQueue<Task> tasks;

    public TaskManager() {
        tasks = new PriorityQueue<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getTasksForToday() {
        LocalDate today = LocalDate.now();
        List<Task> todayTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDeadline().equals(today)) {
                todayTasks.add(task);
            }
        }
        return todayTasks;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(new ArrayList<>(tasks));
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<Task> loaded = (List<Task>) in.readObject();
            tasks.clear();
            tasks.addAll(loaded);
        }
    }
}
