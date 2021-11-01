package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileInputStream; 
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span; 
   

public class ContactInfo {
    
    private String fileName, name, email, phone;

    public ContactInfo(String fileName) {
        this.fileName = fileName;
        this.name = findName();
        this.email = findEmail();
        this.phone = findPhoneNumber();
        /**
         * Below are possible errors.
         * Name: either no name given or name could not be found. 
         * Names can be anythin, so it is possible Open NLP's NER misses some.
         * 
         * Email: given no email or an invalid email according to the RFC 5322.
         * 
         * Phone: given no phone number or invalid phone number
         */
        if (name.equals(""))
            this.name = "Unrecognized Name";
        if (email == null)
            this.email = "Unrecognized Email Address";
        if (phone == null)
            this.phone = "Unrecognized Phone Number";
    }

    /**
     * returns the full name of the individual (eg. John Smith, Susan Malick).
     */
    public String getName() {
        return this.name;
    }

    /**
     * returns the phone number formatted as a sequence of digits
     */
    public String getPhoneNumber() {
        return this.phone;
    }

    /**
     * returns the email address.
     */
    public String getEmailAddress() {
        return this.email;
    }

    /**
     * Uses regular expressions to find an email. Matches to addresses
     * allowed by the RFC 5322.
     */
    private String findEmail() {
        try {
            BufferedReader bufferedReader = 
                new BufferedReader(new FileReader(this.fileName));
            String line;
            String email = null;

            while ((line = bufferedReader.readLine()) != null) {
                /** 
                 * This pattern matches the addresses that RFC 5322 permits
                 * Group 1 is the username
                 * */
                Pattern p = Pattern.compile("([a-zA-Z0-9_!#$%&’*+\\/=?`{|}~^.-]+)@[a-zA-Z0-9.-]+");
                Matcher m = p.matcher(line);
                if (m.find()) {
                    email = m.group(0);
                    return email;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    /**
     * uses regular expressions to match to US and non US phone numbers
     * in many different formats.
     */
    private String findPhoneNumber() {
        try {
            BufferedReader bufferedReader = 
                new BufferedReader(new FileReader(this.fileName));
            String line;
            String pnum = null;

            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * This pattern matches potential phone numbers
                 * Group 1 is Country Code
                 * gruop 2 is Area Code
                 * Group 3 is Exchange
                 * Group 4 is Subscriber Number
                 */
                Pattern p1 = Pattern.compile("\\+?(\\d{1,3})?\\s?\\(?(\\d{3})\\)?[\\s.-]?(\\d{3})[\\s.-]?(\\d{4})");
                Matcher m1 = p1.matcher(line);
                
                /**
                 * Pattern p1 also matches to fax numbers, so assuming that the
                 * card labels the fax number with "fax" or "Fax", you can ignore
                 * those.
                 */
                Pattern p2 = Pattern.compile("Fax|fax");
                Matcher m2 = p2.matcher(line);
                
                if (m1.find() && !m2.find()) {
                    String countryCode = m1.group(1);
                    if (countryCode == null) {
                        countryCode = "";
                    }
                    pnum = countryCode + m1.group(2) + 
                        m1.group(3) + m1.group(4);
                    return pnum;
                }
            }
            bufferedReader.close();
            return null;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Uses OpenNLP's Name Entity Recognition to find the string with the
     * highest probability of being the name.
     */
    private String findName() {
        try {
            // Use the Open NLP en-ner-peron model
            InputStream inputStreamNameFinder = 
                new FileInputStream("src/main/OpenNLPModels/en-ner-person.bin");       
            TokenNameFinderModel model = 
                new TokenNameFinderModel(inputStreamNameFinder);
            NameFinderME finder = new NameFinderME(model);
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            /**
             * Create hash map to keep track of potential name 
             * and its probability
             */
            HashMap<String,Double> probMap = new HashMap<String,Double>();
            String line;
            BufferedReader bufferedReader 
                = new BufferedReader(new FileReader(this.fileName));
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * Looks for potential names in the line using the NameFinder
                 * according to the model from Open NLP. 
                 */
                String[] tokens = tokenizer.tokenize(line);
		        Span[] nameSpans = finder.find(tokens);
                 
                /** 
                 * Records probability
                 * of each potential name and puts it into the HashMap.
                 */
                double[] probs = finder.probs(nameSpans);
                String[] spans = Span.spansToStrings(nameSpans, tokens);
                for (int i = 0; i < spans.length; i++)
                    probMap.put(spans[i],probs[i]);
            }
            bufferedReader.close();
            // Find the String mapping to the max probability.
            String max = "";
            for (HashMap.Entry<String,Double> e : probMap.entrySet()) {
                if (max.equals(""))
                    max = e.getKey();
                if (e.getValue() > probMap.get(max))
                    max = e.getKey();
            }
            // Return the max
            return max;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String toString() {
        return "Name: "  + getName()
            + "\nPhone: " + getPhoneNumber()
            + "\nEmail: " + getEmailAddress();
    }
}