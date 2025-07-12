import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;
import java.io.*;
import java.util.Timer;

public class TaskUI extends JFrame {
    private TaskManager taskManager;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JComboBox<String> filterBox;
    private static final String FILE_PATH = "data/tasks.dat";

    public TaskUI() {
        taskManager = new TaskManager();
        taskListModel = new DefaultListModel<>();

        setTitle("Smart Task Scheduler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Task list display
        taskList = new JList<>(taskListModel);
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Buttons
        JButton addBtn = new JButton("Add Task");
        JButton delBtn = new JButton("Delete Task");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        addBtn.addActionListener(e -> showAddDialog());
        delBtn.addActionListener(e -> deleteSelectedTask());
        saveBtn.addActionListener(e -> saveTasks());
        loadBtn.addActionListener(e -> loadTasks());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
                // Filter dropdown
        String[] filters = {"All Tasks", "Today’s Tasks", "High Priority"};
        filterBox = new JComboBox<>(filters);
        filterBox.addActionListener(e -> refreshList()); // update view when changed

        panel.add(filterBox, BorderLayout.NORTH);

        setVisible(true);
    }

private void scheduleReminder(Task task) {
    LocalDate today = LocalDate.now();
    if (!task.getDeadline().equals(today)) return;

    Timer timer = new Timer();
    // Reminder 10 seconds later (for demo); change to real time in prod
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(TaskUI.this,
                        "Reminder: Task \"" + task.getTitle() + "\" is due today!",
                        "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }, 10000); // 10 seconds = 10_000 ms
}




    private void showAddDialog() {
        JTextField titleField = new JTextField(15);
        String[] priorities = {"1 (High)", "2 (Medium)", "3 (Low)"};
        JComboBox<String> priorityBox = new JComboBox<>(priorities);
        JTextField deadlineField = new JTextField(LocalDate.now().toString());

        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityBox);
        inputPanel.add(new JLabel("Deadline (yyyy-MM-dd):"));
        inputPanel.add(deadlineField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add New Task",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText();
                int priority = priorityBox.getSelectedIndex() + 1;
                LocalDate deadline = LocalDate.parse(deadlineField.getText());

                Task newTask = new Task(title, priority, deadline);
                taskManager.addTask(newTask);
                refreshList();
                scheduleReminder(newTask);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void deleteSelectedTask() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            taskManager.deleteTask(selected);
            refreshList();
        }
    }

        private void refreshList() {
        taskListModel.clear();

        String selected = (String) filterBox.getSelectedItem();
        List<Task> filtered;

        if ("Today’s Tasks".equals(selected)) {
            filtered = taskManager.getTasksForToday();
        } else if ("High Priority".equals(selected)) {
            filtered = taskManager.getAllTasks().stream()
                    .filter(t -> t.getPriority() == 1)
                    .toList();
        } else {
            filtered = taskManager.getAllTasks();
        }

        for (Task t : filtered) {
            taskListModel.addElement(t);
        }
    }


    private void saveTasks() {
        try {
            taskManager.saveToFile(FILE_PATH);
            JOptionPane.showMessageDialog(this, "Tasks saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() {
        try {
            taskManager.loadFromFile(FILE_PATH);
            refreshList();
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskUI::new);
    }
}
