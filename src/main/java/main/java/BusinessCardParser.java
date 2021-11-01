package main.java;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BusinessCardParser {
    private ContactInfo info;
    
    // used to run code where first arg is a file
    public static void main(String[] args) {
        if (args.length == 1 && Files.exists(Paths.get(args[0]))) {
            BusinessCardParser bcr = new BusinessCardParser();
            System.out.println(bcr.getContactInfo(args[0]));
        }
        else System.out.println("Error: Invalid Number of Arguments");
    }

    /**
     * gets and sets the contact info of a person from their business card read
     * from a text file.
     */
    public ContactInfo getContactInfo(String fileName) {
        if (Files.exists(Paths.get(fileName))) {
            this.info = new ContactInfo(fileName);
            return this.info;
        }
        // invalid fileName
        return new ContactInfo(null);
    }

    public String getName() {
        return info.getName();
    }
    public String getPhoneNumber() {
        return info.getPhoneNumber();
    }
    public String getEmailAddress() {
        return info.getEmailAddress();
    }

    public String toString() {
        return info.toString();
    }
}