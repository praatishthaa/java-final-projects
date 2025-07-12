import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Comparable<Task>, Serializable {
    private String title;
    private int priority; // 1 (High), 2 (Medium), 3 (Low)
    private LocalDate deadline;

    public Task(String title, int priority, LocalDate deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getTitle() { return title; }
    public int getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }

    public void setTitle(String title) { this.title = title; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    @Override
    public int compareTo(Task other) {
        // Sort by priority first, then deadline
        if (this.priority != other.priority) return Integer.compare(this.priority, other.priority);
        return this.deadline.compareTo(other.deadline);
    }

    @Override
    public String toString() {
        return "[" + priorityToString(priority) + "] " + title + " - " + deadline;
    }

    private String priorityToString(int p) {
        return switch (p) {
            case 1 -> "High";
            case 2 -> "Medium";
            case 3 -> "Low";
            default -> "Unknown";
        };
    }
}
