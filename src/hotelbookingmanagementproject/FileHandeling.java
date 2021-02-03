
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandeling implements Serializable{
    //public String fileName = "customers.ser";
    public Customers customer;
    public ArrayList<Customers> customerList = new ArrayList<>();
    
    public void saveToFile() { // Jag behöver få in en arrayList eller ett object i metoden.
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
    
    public ArrayList<Customers> importFromFile() {
        ArrayList<Customers> customerArray = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("customers");
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            customerArray = (ArrayList<Customers>) inputStream.readObject();
            inputStream.close();
        } catch (IOException ex) {
            System.out.println("Filen finns inte");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return customerArray;
    }
    
    public void createCustomerFile(String firstName, String lastName){
        Customers customer = new Customers(firstName, lastName);
        customerList.add(customer);
        saveToFile();
    }
    
    public void loadCustomer(){
        ArrayList<Customers> customer = importFromFile();
        for (Customers customers : customer) {
            System.out.println(customers.getFirstName() + " " + customers.getLastName());
        }
        
    }
}
