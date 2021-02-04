package hotelbookingmanagementproject;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectToDatabase {
    
    public Scanner input = new Scanner(System.in);
    
    public Connection connect() {
        final String url = "jdbc:mysql://localhost:3306/hotel?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String user = "root";
        final String password = "Etthundra2";
        Connection connection = null;
        
        try {
            // 1. Anslut till databasen
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public void createCustomer() {
        String firstName;
        String lastName;
        
        System.out.print("\nFörnamn: ");
        firstName = input.nextLine();
        System.out.print("Efternamn: ");
        lastName = input.nextLine();
        Connection connection = connect();
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("USE hotel");
            stmt.executeLargeUpdate("INSERT INTO customer(firstName, lastName) VALUES('" + firstName + "', '" + lastName + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileHandeling.createCustomerFile(firstName, lastName);
        System.out.println("\n******Kunden är skapad!******\n");
    }
    
    public void searchCustomer() {
        String lastName;
        ResultSet result;
        
        System.out.println("**********************");
        System.out.print("Efternamn: ");
        lastName = input.nextLine();
        Connection connection = connect();
        try {
            //söka på kund och hitta rumnr.
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("USE hotel");
            result = stmt.executeQuery("SELECT * FROM customer "
                    + "\nLEFT JOIN bookRoom ON customer.customerId = bookRoom.customerId "
                    + "\nLEFT JOIN rooms ON bookRoom.roomId = rooms.roomId "
                    + "\nWHERE lastName LIKE '%" + lastName + "%';");
            while (result.next()) {
                //System.out.println("\nFörnamn: " + result.getString("firstName"));
                //System.out.println("Efternamn: " + result.getString("lastName"));
                System.out.println();
                FileHandeling.searchCustomerFile(lastName);
                System.out.println("Rumsnummer: " + result.getInt("rooms.roomId"));
                System.out.println("Typ av rum: " + result.getString("typeOfRoom"));
            }
            System.out.println("**********************");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateCustomer() {
        String firstName;
        String lastName;
        String newFirstName;
        String newLastname;
        int result;
        int result2;
        
        System.out.println("**********************");
        System.out.print("Förnamn: ");
        firstName = input.nextLine();
        System.out.print("Efternamn: ");
        lastName = input.nextLine();
        System.out.print("Ange nytt förnamn: ");
        newFirstName = input.nextLine();
        System.out.print("Ange nytt efternamn: ");
        newLastname = input.nextLine();
        Connection connection = connect();
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("USE hotel");
            result = stmt.executeUpdate("UPDATE customer"
                    + "\nSET firstName = '" + newFirstName + "'"
                    + "\nWHERE firstName LIKE '" + firstName + "'"
                    + "\nAND lastName LIKE '" + lastName + "';");
            result2 = stmt.executeUpdate("UPDATE customer "
                    + "\nSET lastName = '" + newLastname + "'"
                    + "\nWHERE firstName LIKE '" + newFirstName + "'"
                    + "\nAND lastName LIKE '" + lastName + "';");
            if (result == 1 && result2 == 1) {
                System.out.println("\n******Kunder är uppdaterad******");
                FileHandeling.createCustomerFile(newFirstName, newLastname);
            } else {
                System.out.println("\n******Uppdateringen misslyckad, dubbelkolla namnet******");
            }
        } catch (Exception e) {
        }
    }
    
    public <T> void delete(T tableName) {
        String firstName;
        String lastName;
        int result;
        Connection connection = connect();
        
        System.out.println("**********************");
        System.out.print("Förnamn: ");
        firstName = input.nextLine();
        System.out.print("Efternamn: ");
        lastName = input.nextLine();
        try {
            Statement stmt = connection.createStatement();
            
            stmt.executeUpdate("USE LIBRARY");
            result = stmt.executeUpdate("DELETE FROM " + tableName
                    + "\nWHERE firstName LIKE '" + firstName + "'"
                    + "\nAND lastname LIKE '" + lastName + "';");
            if (result == 1) {
                System.out.println("\n******Borttagningen lyckades******");
            } else {
                System.out.println("\n******Borttagning misslyckad, dubbelkolla namnet******");
            }
        } catch (Exception e) {
        }
    }
    
    public void booking() {
        String firstName;
        String lastName;
        int roomId = 0;
        int customerId;
        int days = 0;
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        boolean breakfast = false;
        boolean running = true;
        boolean ifInt = true;
        while (running) {
            System.out.println("**********************");
            System.out.print("Förnamn: ");
            firstName = input.nextLine();
            System.out.print("Efternamn: ");
            lastName = input.nextLine();
            try {
                stmt = connection.createStatement();
                result = stmt.executeQuery("SELECT * FROM customer"
                        + "\nWHERE firstName LIKE '" + firstName + "'"
                        + "\nAND lastName LIKE '" + lastName + "';");
                if (result.next()) {
                    customerId = result.getInt("customerId");
                } else {
                    System.err.println("*******Misslyckades att hitta*******");
                    continue;
                }
                result = stmt.executeQuery("SELECT * FROM rooms"
                        + "\nWHERE available IS TRUE");
                while (result.next()) {
                    System.out.println("\nRumsnummer: " + result.getInt("roomId"));
                    System.out.println("Typ av rum: " + result.getString("typeOfRoom"));
                    System.out.println("Pris per natt: " + result.getInt("price"));
                }
                while (ifInt) {
                    System.out.print("\nVälj rumsnummer som ska bokas: ");
                    if (input.hasNextInt()) {
                        roomId = input.nextInt();
                        ifInt = false;
                    } else {
                        System.err.println("******Måste vara ett heltal******");
                    }
                    
                }
                ifInt = true;
                while (ifInt) {
                    System.out.print("Hur många nätter: ");
                    if (input.hasNextInt()) {
                        days = input.nextInt();
                        ifInt = false;
                    } else {
                        System.err.println("******Måste vara ett heltal******");
                    }
                }
                System.out.println("\nFrukost: ja/nej");
                if (input.next().equalsIgnoreCase("ja")) {
                    breakfast = true;
                }
                stmt.executeLargeUpdate("INSERT INTO bookRoom(customerId, roomId, days, breakfast)"
                        + "\nVALUES(" + customerId + ", " + roomId + ", " + days + ", " + breakfast + ")");
                stmt.executeUpdate("UPDATE rooms"
                        + "\nSET available = FALSE"
                        + "\nWHERE roomId = " + roomId);
                System.out.println("*********************"
                        + "\n********BOKAT********"
                        + "\n*********************");
                running = false;
                
            } catch (Exception e) {
            }
        }
    }
    
    public void uppgrade() {
        int roomIdNow = 0;
        int roomIdNew = 0;
        boolean ifInt = true;
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        while (ifInt) {
            System.out.print("*********************"
                    + "\nVilket rum ska uppgraderas: ");
            if (input.hasNextInt()) {
                roomIdNow = input.nextInt();
                ifInt = false;
            } else {
                System.err.println("******Måste vara ett heltal******");
            }
        }
        ifInt = true;
        while (ifInt) {
            System.out.print("Till vilket rum: ");
            if (input.hasNextInt()) {
                roomIdNew = input.nextInt();
                ifInt = false;
            } else {
                System.err.println("******Måste vara ett heltal******");
            }
        }
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE bookRoom"
                    + "\nSET roomId = " + roomIdNew
                    + "\nWHERE roomId = " + roomIdNow);
            System.out.println("***********************"
                    + "\n******UPPGRADERAT******"
                    + "\n***********************");
        } catch (Exception e) {
        }
    }
    
    public void orderFood() {
        int roomId = 0;
        int foodId = 0;
        boolean ifInt = true;
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        while (ifInt) {
            System.out.print("*********************"
                    + "\nAnge rumsnummer: ");
            if (input.hasNextInt()) {
                roomId = input.nextInt();
                ifInt = false;
            } else {
                System.err.println("******Måste vara ett heltal******");
            }
        }
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT * FROM food");
            while (result.next()) {
                if (result.isFirst()) {
                    System.out.println("\nMeny:\n");
                }
                System.out.println(result.getInt("foodId") + " .) " + result.getString("foodName")
                        + " " + result.getInt("price") + ":-");
            }
            ifInt = true;
            while (ifInt) {
                System.out.print("*********************"
                        + "\nVilken rätt: ");
                if (input.hasNextInt()) {
                    foodId = input.nextInt();
                    ifInt = false;
                } else {
                    System.err.println("******Måste vara ett heltal******");
                }
            }
            stmt.executeUpdate("INSERT INTO orderFood(roomId, foodId) VALUES(" + roomId + ", " + foodId + ")");
            System.out.println("********************"
                    + "\n******BESTÄLLT******"
                    + "\n********************");
        } catch (Exception e) {
        }
    }

    //Lambda
    CountInterface count = (a, b, c) -> {
        return a * b + c;
    };
    
    public void checkOut() {
        int roomId = 0;
        int days = 0;
        int roomPrice = 0;
        int foodPrice = 0;
        int sum;
        String yesOrNo;
        boolean ifInt = true;
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        List<String> list = new ArrayList<>();
        
        while (ifInt) {            
            System.out.print("Ange rumsnummer: ");
            if (input.hasNextInt()) {
                roomId = input.nextInt();
                ifInt = false;                
            } else {
                input.next();
                System.err.println("******Måste vara ett heltal******");
            }
        }
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT * FROM customer\n"
                    + "JOIN bookRoom ON customer.customerId = bookRoom.customerId\n"
                    + "JOIN rooms ON bookRoom.roomId = rooms.roomId\n"
                    + "JOIN orderFood ON rooms.roomId = orderFood.roomId\n"
                    + "JOIN food ON orderFood.foodId = food.foodId\n"
                    + "WHERE rooms.roomId = " + roomId + ";");
            //plocka ut summorna och addare dom += och skriva ut allt
            while (result.next()) {
                int food;
                food = result.getInt("food.price");
                foodPrice += food;
                roomPrice = result.getInt("rooms.price");
                days = result.getInt("days");
                if (result.isFirst()) {
                    System.out.println("Typ av rum: " + result.getString("typeOfRoom")
                            + "\nAntal dagar: " + days + "\nPris per natt: " + roomPrice + ":-");
                }
                ExceptionsHandeling pad = new ExceptionsHandeling();
                System.out.println(result.getString("foodName") + "\nPris: " + food + ":-");
            }
            sum = count.intCount(roomPrice, days, foodPrice);
            System.out.println("\nTotalt: " + sum + ":-");
            System.out.println("Checka ut: ja/nej ");
            yesOrNo = input.next();
            if (yesOrNo.equalsIgnoreCase("ja")) {
                stmt.executeUpdate("DELETE FROM orderFood "
                        + "\nWHERE roomId = " + roomId);
                stmt.executeUpdate("UPDATE rooms"
                        + "\nSET available = TRUE"
                        + "\nWHERE roomId = " + roomId);
            }
        } catch (Exception e) {
        }
    }
    
    public void printOutRooms() {
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        ExceptionsHandeling pad = new ExceptionsHandeling();
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT DISTINCT * FROM rooms\n"
                    + "GROUP BY typeOfRoom;");
            int columnCount = result.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = result.getMetaData().getColumnName(i + 1);
            }
            for (String columnName : columnNames) {
                System.out.print(pad.PadRight(columnName));
            }
            while (result.next()) {                
                System.out.println();
                for (String columnName : columnNames) {
                    String value = result.getString(columnName);
                    if (value == "1") {
                        value = "Tillgänglig";
                    }
                    System.out.print(pad.PadRight(value));
                }
            }
            System.out.println();
        } catch (Exception e) {
        }
    }
    
    public void showRoom(){
        boolean run = true;
        int roomId = 0;
        int numberOfbeds;
        ResultSet result;
        Connection connection = connect();
        Statement stmt;
        List<String> list = new ArrayList<>();
        
        while (run) {            
            System.out.print("\nAnge rumsnummer: ");
            if (input.hasNextInt()) {
                roomId = input.nextInt();
                run = false;
            } else {
                System.err.println("\nSkriv ett heltal");
            }
        }
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT * FROM rooms WHERE roomId = " + roomId);
            if (result.getString("typeOfRoom").equalsIgnoreCase("Singel rum")) {
                numberOfbeds = 1;
            } else {
                numberOfbeds = 2;
            }
            System.out.println("Antal sängar: " //+ numberOfbeds 
                    + "\nAC: YES"
                    + "\nPris per natt: " + result.getInt("price"));
        } catch (Exception e) {
        }
    }
}
