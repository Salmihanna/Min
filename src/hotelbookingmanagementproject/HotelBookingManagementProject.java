package hotelbookingmanagementproject;

import com.sun.xml.internal.ws.api.message.Packet;
import java.awt.font.TextAttribute;
import java.util.Scanner;

public class HotelBookingManagementProject {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        FileHandeling.importFromFile();
        run();
    }

    public static void run() {
        boolean running = true;
        while (running) {
            running = firstSwitch(firstMenu());
        }
    }

    public static int firstMenu() {
        int choice = 0;
        boolean running = true;
        while (running) {
            System.out.println("\n~~~~~~Logga in~~~~~~"
                    + "\n[1] Kund"
                    + "\n[2] Admin"
                    + "\n[3] Avsluta");
            System.out.print("Välj: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                running = false;
            } else {
                input.next();
            }
        }
        return choice;
    }

    public static boolean firstSwitch(int choice) {
        boolean run = true;
        boolean minRun;
        switch (choice) {
            case 1:
                //Skickas till custumer view
                int choiceCustomer;
                minRun = true;
                while (minRun) {
                    CustomerView newView = new CustomerView();
                    choiceCustomer = newView.customerMenu();
                    //Lägg till boolean
                    minRun = newView.customerSwitch(choiceCustomer);
                }
                break;
            case 2:
                //Skickas till admin view
                int choiceAdmin;
                minRun = true;
                while (minRun) {
                    ReceptionistView newAdminView = new ReceptionistView();
                    choiceAdmin = newAdminView.adminView();
                    minRun = newAdminView.adminSwitch(choiceAdmin);
                }
                break;
            case 3:
                //Avsluta
                run = false;
                break;
        }
        return run;
    }
}
