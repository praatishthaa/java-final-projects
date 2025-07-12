# Final Projects: Java Mini Applications

This repository contains two fully functional Java-based desktop applications built as part of the internship project phase:

1.  [Employee PDF Report Generator](#-employee-pdf-report-generator)
2.  [Smart Task Scheduler with Priority Queues](#-smart-task-scheduler-with-priority-queues)

## Employee PDF Report Generator

### **Objective**
Generate stylized, individual PDF reports for employees using data from a CSV file.

### **Tech Stack**
- Java
- Apache PDFBox
- CSVReader (custom)
- Fonts: `.ttf` custom font

### **Features**
- Reads employee data from a CSV file.
- Generates one PDF per employee, with:
  - Styled headers and rows
  - Colored backgrounds
  - Table borders
  - Timestamps
- Saves all PDFs in a date-named folder (`reports/YYYY-MM-DD/`).

### **Deliverables**
- Source code (`EmployeePDF/src`)
- Sample CSV (`employees.csv`)
- Generated PDFs (`reports/`)
- [employee_report.pdf](./EmployeePDF/reports/John_Doe.pdf) example


## ✅ Smart Task Scheduler with Priority Queues

### **Objective**
A desktop task manager that organizes and prioritizes tasks based on urgency and deadlines.

### **Tech Stack**
- Java
- Java Swing
- Java Timer
- File I/O (serialization)

### **Features**
- Add, delete, and view tasks.
- Auto-sorting based on priority and deadline.
- Real-time reminders for today's tasks (via `JOptionPane`).
- Save/load tasks from disk.
- Visual desktop interface using Java Swing.
- Filters for "Today's Tasks".

### **Deliverables**
- Source code (`SmartTaskScheduler/src`)
- Data folder for saved tasks (`data/tasks.dat`)
- Executable `.jar` file (`SmartTaskScheduler.jar`)


## 🚀 How to Run

### **EmployeePDF**
1. Open in IntelliJ or any Java IDE.
2. Ensure `employees.csv` and `fonts/times.ttf` exist.
3. Run `Main.java`.

### **Smart Task Scheduler**
1. Run `TaskUI.java` from `src`.
2. Or double-click `SmartTaskScheduler.jar`.


