import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceRecord {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<EmployeeAttendance> logs = new ArrayList<>();
    static final String AttendanceLogs = "updated_attendance.csv";

    public static void main(String[] args) {
        loadRecordsFromFile(); // Loads existing records on startup

        while (true) {
            System.out.println("\n--- Employee Attendance ---");
            System.out.println("1. Add Attendance");
            System.out.println("2. Display All Attendance");
            System.out.println("3. Save and Exit");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    addAttendance(); // Adding
                    break;

                case 2: // Display Attendance Record
                    displayAttendance();
                    break;

                case 3: // Exit
                    saveRecordsToFile(); // Save Inputs before exiting program
                    System.out.println("Exiting Program...");
                    return;

                default: // if choice is not found then try again.
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    public static void addAttendance() { // Method to add attendance
        System.out.println("\n--- Enter Employee Attendance ---");
        System.out.print("Enter Employee ID: ");
        String employeeId = scan.nextLine();

        System.out.print("Enter First Name: ");
        String firstName = scan.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scan.nextLine();

        System.out.print("Date (MM/DD/YYYY): ");
        String date = scan.nextLine();

        System.out.print("Time In (HH:MM AM/PM): ");
        String timeIn = scan.nextLine();

        System.out.print("Time Out (HH:MM AM/PM): ");
        String timeOut = scan.nextLine();

        System.out.println("\nAdding Done.");

        boolean isClockIn = !timeIn.isEmpty() && !timeOut.isEmpty(); // True if timeIn is not empty

        logs.add(new EmployeeAttendance(employeeId, firstName, lastName, date, timeIn, timeOut, isClockIn)); // Storing
                                                                                                             // inputs
                                                                                                             // in
                                                                                                             // ArrayList
    }

    public static void displayAttendance() { // Method to Display Records
        System.out.println("\n--- Attendance Logs ---");

        for (EmployeeAttendance log : logs) { // to display all records stored
            log.displayLog();
        }
    }

    public static void loadRecordsFromFile() { // Method to load records from Attendance.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(AttendanceLogs))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(EmployeeAttendance.fromFileString(line));
            }
            System.out.println("\nLogs loaded successfully.");
        }

        catch (FileNotFoundException e) {
            System.out.println("\nNo existing file found. Exit program and refresh.");
        }

        catch (IOException e) {
            System.out.println("Error loading logs: " + e.getMessage());
        }
    }

    public static void saveRecordsToFile() { // Method to save records to Attendance.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AttendanceLogs))) {
            for (EmployeeAttendance log : logs) {
                writer.write(log.toFileString());
                writer.newLine();
            }
            System.out.println("\nRecords saved successfully.");
        }

        catch (IOException e) {
            System.out.println("Error saving logs: " + e.getMessage());
        }
    }
}

// Class for employee attendance details
class EmployeeAttendance {
    private String employeeId; // declaring variables for employee attendance
    private String firstName;
    private String lastName;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    private boolean isClockIn;
    private LocalDateTime timestamp;
    private double hoursWorked;

    public static DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static DateTimeFormatter TIME = DateTimeFormatter.ofPattern("hh:mm a");
    public static DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    // Constructor
    public EmployeeAttendance(String employeeId, String firstName, String lastName, String date, String timeIn,
            String timeOut, boolean isClockIn) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = LocalDate.parse(date, DATE);
        this.timeIn = timeIn.isEmpty() ? null : LocalTime.parse(timeIn, TIME);
        this.timeOut = timeOut.isEmpty() ? null : LocalTime.parse(timeOut, TIME);
        this.isClockIn = isClockIn;
        this.timestamp = this.timeIn != null ? LocalDateTime.of(this.date, this.timeIn) : null;
        this.hoursWorked = calculateHoursWorked();
    }

    // Method that converts every objects to String for saving
    // (Use for saveRecordsToFile method)
    public String toFileString() {
        return employeeId + "," + firstName + "," + lastName + "," + date.format(DATE) + ","
                + (timeIn != null ? timeIn.format(TIME) : "N/A") + ","
                + (timeOut != null ? timeOut.format(TIME) : "N/A") + "," + isClockIn + ","
                + (timestamp != null ? timestamp.format(TIMESTAMP) : "N/A") + "," + String.format("%.2f", hoursWorked);
    }

    // method to display logs
    public void displayLog() {
        System.out.println(
                "Employee ID: " + employeeId + " | First Name: " + firstName + " | Last Name: " + lastName + " | Date: "
                        + date + " | Time In: " + (timeIn != null ? timeIn.format(TIME) : "N/A") + " | Time Out: "
                        + (timeOut != null ? timeOut.format(TIME) : "N/A") + " | Clock In: " + isClockIn
                        + " | Timestamp: "
                        + (timestamp != null ? timestamp.format(TIMESTAMP) : "N/A") +
                        " | Hours Worked: " + String.format("%.2f", hoursWorked));
    }

    public double calculateHoursWorked() {
        if (timeIn != null && timeOut != null) {
            LocalDateTime start = LocalDateTime.of(date, timeIn);
            LocalDateTime end = LocalDateTime.of(date, timeOut);
            Duration duration = Duration.between(start, end);
            return duration.toMinutes() / 60.0; // Convert minutes to hours (double)
        }
        return 0.0; // Return 0.0 if timeIn or timeOut is null
    }

    // Method that converts the text from Attendance.txt to objects
    // (Use for loadRecordsFromFile method)
    public static EmployeeAttendance fromFileString(String line) {
        String[] parts = line.split(",");
        String employeeId = parts[0];
        String firstName = parts[1];
        String lastName = parts[2];
        String date = parts[3];
        String timeIn = parts[4];
        String timeOut = parts[5];
        boolean isClockIn = Boolean.parseBoolean(parts[6]);
        LocalDateTime timestamp = parts[7].equals("N/A") ? null : LocalDateTime.parse(parts[7], TIMESTAMP);
        double hoursWorked = Double.parseDouble(parts[8]);

        EmployeeAttendance attendance = new EmployeeAttendance(employeeId, firstName, lastName, date, timeIn, timeOut,
                isClockIn);
        attendance.timestamp = timestamp;
        attendance.hoursWorked = hoursWorked;
        return attendance;
    }
}