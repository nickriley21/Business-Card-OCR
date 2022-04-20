# Business Card Parser
This is my solution to the Asymmetrik Programming Challenge, "Business Card OCR".<br />
https://asymmetrik.com/programming-challenges/<br />
Business Card Parser takes text from a file that was produced by an imaginary business 
card scanner and produces the contact information of the person on the card.<br />
Uses regular expression to process phone numbers and emails and [Apache OpenNLP](https://opennlp.apache.org/)
to recognize names.<br /> Used Maven 3.6.3 and Java JDK 8.
## Running the Code
Start with running `mvn clean install`, this creates a .jar file that you can from the command line inside the directory `BusinessCardOCR`.<br />
The .jar file takes in a .txt file of one singular business card. You can run it like the following:
```bash
java -jar target/java-0.0.1-SNAPSHOT-jar-with-dependencies.jar <yourfile.txt>
```
This will subsequently print out the wanted information like the following:
```bash
user@host:~/BusinessCardOCR$ java -jar target/java-0.0.1-SNAPSHOT-jar-with-dependencies.jar src/test/LisaHaung.txt 
Name: Lisa Haung
Phone: 4105551234
Email: lisa.haung@foobartech.com
```
