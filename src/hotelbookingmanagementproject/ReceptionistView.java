package hotelbookingmanagementproject;

import java.util.Scanner;

public class ReceptionistView {

    public Scanner input = new Scanner(System.in);
//  1.Storing Customer Details KLAR
//  2.Searching Customer Details KLAR
//  3.Upgrade and delete details KLAR
//  4.Booking or upgrading room 
//  5.Ordering Food for Particular Room
//  6.Check out for customer and showing bill

    public int adminView() {
        int choice = 0;
        boolean run = true;
        while (run) {
            System.out.println("\n~~~~~~Gör ditt val~~~~~~"
                    + "\n[1] Lägg till en kund"
                    + "\n[2] Söka på kunder"
                    + "\n[3] Visa alla kunder"
                    + "\n[4] Uppdatera kund"
                    + "\n[5] Radera kund"
                    + "\n[6] Boka eller uppdatera rum"
                    + "\n[7] Beställ mat till ett rum"
                    + "\n[8] Checka ut kund"
                    + "\n[9] Gå tillbaka");
            System.out.print("Välj: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                run = false;
            } else {
                input.next();
            }
        }
        return choice;
    }

    public boolean adminSwitch(int choice) {
        ConnectToDatabase database = new ConnectToDatabase();
        boolean run = true;
        switch (choice) {
            case 1:
                //Lägg till kund
                database.createCustomer();
                break;
            case 2:
                //Sök på kund
                database.searchCustomer();
                break;
            case 3:
                // Visa alla kunder
                FileHandeling.printOutCustomer();
                break;
            case 4:
                //Uppdatera kund 
                database.updateCustomer();
                break;
            case 5:
                //radera kund
                database.delete("customer");
                break;
            case 6:
                //Boka och uppdatera rum
                bookOrUpgradeSwitch(bookOrUpgradeView());
                break;
            case 7:
                //Beställ mat till ett rum
                database.orderFood();
                break;
            case 8:
                //Checka ut kund
                database.checkOut();
                break;
            case 9:
                //Avsluta
                run = false;
                break;
        }
        return run;
    }

    public int bookOrUpgradeView() {
        int choice = 0;
        boolean run = true;
        while (run) {
            System.out.println("~~~~~~Boka eller uppgradera~~~~~~"
                    + "\n[1] Boka"
                    + "\n[2] Uppgradera");
            System.out.print("Välj: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                run = false;
            }
        }
        return choice;
    }

    public void bookOrUpgradeSwitch(int choice) {
        ConnectToDatabase database = new ConnectToDatabase();
        switch (choice) {
            case 1:
                database.booking();
                break;
            case 2:
                database.printOutRooms("available");
                database.uppgrade();
                break;
        }
    }

}
