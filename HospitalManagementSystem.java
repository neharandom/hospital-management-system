import java.util.*;

// Main class
public class HospitalManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital("City HealthCare");

        System.out.println("Welcome to the Hospital Management System");
        boolean running = true;

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Patient Registration");
            System.out.println("2. Appointment Scheduling");
            System.out.println("3. Electronic Health Records (EHR)");
            System.out.println("4. Billing and Invoicing");
            System.out.println("5. Inventory Management");
            System.out.println("6. Staff Management");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hospital.patientRegistration(scanner);
                    break;
                case 2:
                    hospital.scheduleAppointment(scanner);
                    break;
                case 3:
                    hospital.manageEHR(scanner);
                    break;
                case 4:
                    hospital.billingAndInvoicing(scanner);
                    break;
                case 5:
                    hospital.manageInventory(scanner);
                    break;
                case 6:
                    hospital.staffManagement(scanner);
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the Hospital Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}

class Hospital {
    private String name;
    private List<Patient> patients;
    private List<Staff> staff;
    private List<Appointment> appointments;
    private Inventory inventory;

    public Hospital(String name) {
        this.name = name;
        this.patients = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.inventory = new Inventory();
    }

    public void patientRegistration(Scanner scanner) {
        System.out.println("Enter patient name:");
        String name = scanner.nextLine();
        System.out.println("Enter patient age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter patient address:");
        String address = scanner.nextLine();

        Patient newPatient = new Patient(name, age, address);
        patients.add(newPatient);
        System.out.println("Patient registered successfully.");
    }

    public void scheduleAppointment(Scanner scanner) {
        System.out.println("Enter patient name:");
        String patientName = scanner.nextLine();
        System.out.println("Enter doctor name:");
        String doctorName = scanner.nextLine();
        System.out.println("Enter appointment date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        Appointment appointment = new Appointment(patientName, doctorName, date);
        appointments.add(appointment);
        System.out.println("Appointment scheduled successfully.");
    }

    public void manageEHR(Scanner scanner) {
        System.out.println("Enter patient name to view or update EHR:");
        String patientName = scanner.nextLine();

        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(patientName)) {
                System.out.println("EHR for " + patientName + ":");
                patient.displayEHR(scanner);
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    public void billingAndInvoicing(Scanner scanner) {
        System.out.println("Enter patient name for billing:");
        String patientName = scanner.nextLine();
        System.out.println("Enter amount to bill:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Invoice generated for " + patientName + ": $" + amount);
    }

    public void manageInventory(Scanner scanner) {
        inventory.displayInventory();
        System.out.println("1. Add Item\n2. Remove Item");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Enter item name:");
            String itemName = scanner.nextLine();
            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            inventory.addItem(itemName, quantity);
        } else if (choice == 2) {
            System.out.println("Enter item name:");
            String itemName = scanner.nextLine();
            inventory.removeItem(itemName);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void staffManagement(Scanner scanner) {
        System.out.println("1. Add Staff\n2. View Staff");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Enter staff name:");
            String name = scanner.nextLine();
            System.out.println("Enter position:");
            String position = scanner.nextLine();
            Staff newStaff = new Staff(name, position);
            staff.add(newStaff);
            System.out.println("Staff added successfully.");
        } else if (choice == 2) {
            System.out.println("Staff List:");
            for (Staff s : staff) {
                System.out.println(s);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}

class Patient {
    private String name;
    private int age;
    private String address;
    private String ehr;

    public Patient(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.ehr = "No records available.";
    }

    public String getName() {
        return name;
    }

    public void displayEHR(Scanner scanner) {
        System.out.println(ehr);
        System.out.println("Do you want to update the EHR? (yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter new EHR details:");
            ehr = scanner.nextLine();
            System.out.println("EHR updated successfully.");
        }
    }
}

class Staff {
    private String name;
    private String position;

    public Staff(String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return name + " - " + position;
    }
}

class Appointment {
    private String patientName;
    private String doctorName;
    private String date;

    public Appointment(String patientName, String doctorName, String date) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment: " + patientName + " with Dr. " + doctorName + " on " + date;
    }
}

class Inventory {
    private Map<String, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void addItem(String name, int quantity) {
        items.put(name, items.getOrDefault(name, 0) + quantity);
        System.out.println("Item added successfully.");
    }

    public void removeItem(String name) {
        if (items.containsKey(name)) {
            items.remove(name);
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }
}

