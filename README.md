# AttendanceRecord
This program manages employee attendance records. It allows users to add new attendance records, display all attendance records, and save/load records to/from a file named updated_attendance.csv.

Changes
From reading and saving to/from .txt file
To reading and saving to/from .csv file // use Attendance Record provided by MotorPH
https://drive.google.com/file/d/1s1jYpuJGiPLDFHfPXAGtuX9zDz3AdgcO/view?usp=sharing 

Inputs
Old - taking inputs of employeeId, date, timeIn, timeOut, isClockIn

New - taking inputs of employeeId, firstName, lastName, date, timeIn, timeOut, isClockIn
To match the Attendance Record provided by MotorPH

Classes
AttendanceRecord
The AttendanceRecord class contains the main method and methods for managing attendance records.
Fields
static Scanner scan: A Scanner object for reading user input.
static ArrayList<EmployeeAttendance> logs: An ArrayList to store EmployeeAttendance objects.
static final String AttendanceLogs: The name of the file where attendance records are saved.
Methods
main(String[] args)
The main method that runs the program. It displays a menu for the user to add attendance, display all attendance, or save and exit the program.
addAttendance()
Prompts the user to enter employee attendance details and adds a new EmployeeAttendance object to the logs list.
displayAttendance()
Displays all attendance records stored in the logs list.
loadRecordsFromFile()
Loads attendance records from the Attendance.txt file and adds them to the logs list.
saveRecordsToFile()
Saves all attendance records from the logs list to the Attendance.txt file.

EmployeeAttendance
The EmployeeAttendance class represents an employee's attendance record.
Fields
private String employeeId: The employee's ID.
private LocalDate date: The date of the attendance record.
private LocalTime timeIn: The time the employee clocked in.
private LocalTime timeOut: The time the employee clocked out.
private boolean isClockIn: Indicates whether the employee clocked in.
private LocalDateTime timestamp: The timestamp of the attendance record.
private double hoursWorked: The total hours worked by the employee.
public static DateTimeFormatter DATE: A DateTimeFormatter for formatting dates.
public static DateTimeFormatter TIME: A DateTimeFormatter for formatting times.
public static DateTimeFormatter TIMESTAMP: A DateTimeFormatter for formatting timestamps.
Constructor
EmployeeAttendance(String employeeId, String date, String timeIn, String timeOut, boolean isClockIn)
Creates a new EmployeeAttendance object with the specified employee ID, date, time in, time out, and clock-in status. It also calculates the hours worked.
Methods
toFileString()
Returns a string representation of the EmployeeAttendance object for saving to a file.
displayLog()
Displays the attendance record details.
calculateHoursWorked()
Calculates and returns the total hours worked by the employee.
fromFileString(String line)
Creates and returns an EmployeeAttendance object from a string representation of an attendance record.

Usage
Run the Program: Execute the AttendanceRecord class to start the program.
Add Attendance: Select option 1 to add a new attendance record. Enter the employee ID, date, time in, and time out.
Display All Attendance: Select option 2 to display all attendance records.
Save and Exit: Select option 3 to save the records to the Attendance.txt file and exit the program.
Notes
The program uses LocalDate, LocalTime, and LocalDateTime from the java.time package for date and time handling.
The attendance records are saved to and loaded from a file named Attendance.txt.
The calculateHoursWorked method calculates the total hours worked based on the time in and time out.
This documentation provides an overview of the AttendanceRecord program, its classes, methods, and usage instructions.
