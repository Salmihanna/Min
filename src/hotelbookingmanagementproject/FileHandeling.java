
package hotelbookingmanagementproject;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandeling implements Serializable{
    static public Customers customer;
    static public List<Customers> customerList = Arrays.asList();
    
    public static void saveToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("customers");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(customerList);
            os.close();
            System.out.println("Filen är sparade");
        } catch (FileNotFoundException ex) {
            System.err.println("Filen finns inte");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public static List<Customers> importFromFile() {
        try {
            FileInputStream fis = new FileInputStream("customers");
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            customerList = (List<Customers>) inputStream.readObject();
            inputStream.close();
        } catch (IOException ex) {
            System.out.println("Filen finns inte");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return customerList;
    }
    
    //Lambda
    public static void searchCustomerFile(String lastName){
        //List<Customers> list = 
        customerList.stream().filter((s) -> s.getLastName()
                .equalsIgnoreCase(lastName)).forEach(s -> System.out.println(s.getFirstName() + " " + s.getLastName()));
    }
    
    //method reference
    public static void printOutCustomer(){
        customerList.stream().forEach(Customers::printOut);
    }
    
    public static <T> void createCustomerFile(T t1, T t2){
        String firstName = (String) t1;
        String lastName = (String) t2;
        Customers customer = new Customers(firstName, lastName);
        customerList.add(customer);
        saveToFile();
    }
    
    public static void loadCustomer(){
        importFromFile();
        for (Customers customers : customerList) {
            System.out.println(customers.getFirstName() + " " + customers.getLastName());
        }
    }
}
