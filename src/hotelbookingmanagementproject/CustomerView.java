package hotelbookingmanagementproject;

import java.util.Scanner;

public class CustomerView {

    public Scanner input = new Scanner(System.in);

    //1. Display rooms details
    //      1. Luxury Double Room
    //          Number of double beds : 1
    //          AC : Yes
    //          Free breakfast : Yes
    //          Charge per day:4000
    //          //Continue : (y/n)
    //      2.Deluxe Double Room
    //      3. Luxury Single Room
    //      4.Deluxe Single Room
    //2. Display rooms availability
    //      Number of rooms available : 20
    //      Continue : (y/n)
    //3. Book
    //      Choose room type :
    //      1. Luxury Double Room
    //      2.Deluxe Double Room
    //      3. Luxury Single Room
    //      4.Deluxe Single Room
    //      Choose room number from :
    //      1,2,3,4,5,6,7,8,9,10,
    //      Enter room number: 9
    //      Enter customer name: Ghulam Murtaza
    //      Enter contact number: 120 411 8730
    //4. Order food
    // Nått i samma stil 
    //5. Checkout
    //  Få totala summan 
    //6. Exit
    // Stäng av programmet/hoppa tillbaka
    public int customerMenu() {
//        1. Display room details
//        2. Display room availability 
//        3. Book
//        4. Order food
//        5. Checkout
//        6. Exit
        int choice = 0;
        boolean run = true;
        while (run) {
            System.out.println("\n~~~~~~Gör ditt val~~~~~~"
                    + "\n[1] Visa rum detaljer"
                    + "\n[2] Visa rum som är tillgängliga"
                    + "\n[3] Boka"
                    + "\n[4] Beställ mat"
                    + "\n[5] Checka ut"
                    + "\n[6] Avsluta");
            System.out.print("Välj: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                run = false;
            }
        }
        return choice;
    }

    public boolean customerSwitch(int choice) {
        ConnectToDatabase database = new ConnectToDatabase();
        boolean run = true;
        switch (choice) {
            case 1:
                //visa rum
                database.printOutRooms();
                database.showRoom();
                break;
            case 2:
                //visa tillgängliga rum 
                
                break;
            case 3:
                //Boka
                database.booking();
                break;
            case 4:
                //beställ mat
                database.orderFood();
                break;
            case 5:
                //Checka ut
                database.checkOut();
                break;
            case 6:
                //Avsluta
                run = false;
                break;
        }
        return run;
    }
}
