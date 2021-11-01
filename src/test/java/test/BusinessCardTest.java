package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.java.*;

public class BusinessCardTest {
    @Test
    void testMikeSmith() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/MikeSmith.txt");
        assertEquals("Mike Smith",bcr.getName());
        assertEquals("4105551234",bcr.getPhoneNumber());
        assertEquals("msmith@asymmetrik.com",bcr.getEmailAddress());
    }

    @Test
    void testArthurWilson() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/ArthurWilson.txt");
        assertEquals("Arthur Wilson",bcr.getName());
        assertEquals("17035551259",bcr.getPhoneNumber());
        assertEquals("awilson@abctech.com",bcr.getEmailAddress());
    }

    @Test
    void testLisaHaung() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/LisaHaung.txt");
        assertEquals("Lisa Haung",bcr.getName());
        assertEquals("lisa.haung@foobartech.com",bcr.getEmailAddress());
        assertEquals("4105551234",bcr.getPhoneNumber());
    }

    @Test
    void testNameInCompany() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/NameInCompany.txt");
        assertEquals("Bob Dylan",bcr.getName());
        assertEquals("bdyl@abctech.com",bcr.getEmailAddress());
        assertEquals("6195431240",bcr.getPhoneNumber());
    }

    @Test
    void testDifferentNames() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/NicholasVRiley.txt");
        assertEquals("Nicholas V Riley",bcr.getName());
        bcr.getContactInfo("src/test/resources/BlaineSmith.txt");
        assertEquals("Blaine Smith",bcr.getName());
    }

    @Test
    void testFaxFirst() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/FaxFirst.txt");
        assertEquals("4105551234",bcr.getPhoneNumber());
        assertEquals("Sammy Samson",bcr.getName());
    }

    @Test
    void testNoName() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/MissingInfo.txt");
        assertEquals("Unrecognized Name",bcr.getName());
    }

    @Test
    void testNoEmailAddress() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/MissingInfo.txt");
        assertEquals("Unrecognized Email Address",bcr.getEmailAddress());
    }

    @Test
    void testNoPhoneNumber() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/MissingInfo.txt");
        assertEquals("Unrecognized Phone Number",bcr.getPhoneNumber());
    }

    @Test
    void testInternationalNum() {
        BusinessCardParser bcr = new BusinessCardParser();
        bcr.getContactInfo("src/test/resources/InternationalNum.txt");
        assertEquals("674567897758",bcr.getPhoneNumber());
    }
}
