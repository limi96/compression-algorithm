
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random; 
import algorithms.utils.*;
import java.util.Arrays;

public class FileUtilsTest {
    
    Random rnd = new Random();   
    int numberOfRepetitions; 
    int lengthOfInput; 
    int numberOfWrongResults;
    
    
    @Before
    public void setUp() {
        numberOfWrongResults = 0;
        numberOfRepetitions = 100;  
        lengthOfInput = 100;  
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testBitsToBytesToBitsConversion() {
        String input = ""; 

        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < 10 + rnd.nextInt(lengthOfInput); j++) {
                inputBuffer.append(rnd.nextInt(2) % 2 == 0 ? "0" : "1"); 
            }

            input = new String(inputBuffer); 

            String reverseInput = FileUtils.bytesToBits(FileUtils.bitsToByte(input));
            boolean testResult = input.equals(reverseInput);
            if (!testResult) {
                numberOfWrongResults++;
            }
        }
        assertEquals(0, numberOfWrongResults);
    }
    
    @Test
    public void testBitsToIntsToBitsConversion() {
        int input = 0; 

        for (int i = 8; i <= 32; i++) { 

            for (int j = 0; j < numberOfRepetitions; j++) {
                input = rnd.nextInt((int)Math.pow(2,i));
                int reverseInput = FileUtils.bitsToInt(FileUtils.intToBits(input,i));
                boolean testResult = input == reverseInput;

                if (!testResult) {
                    numberOfWrongResults++;
                }   
            }
        }

        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void testWriteAndReadFiles() throws java.io.IOException {
        String[] fileExtensions = new String[]{".hf",".lzw"};

        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }
            
            byte[] input = inputBuffer.toString().getBytes(); 

            for (String extension : fileExtensions) {
                FileUtils.writeFile("FileUtilsTest"+extension, input); 
                byte[] reverseInput = FileUtils.readFile("FileUtilsTest"+extension);
                boolean testResult = Arrays.equals(input, reverseInput); 
    
                if (!testResult) {
                    numberOfWrongResults++;
                }
            }
        }
        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void testWriteAndReadTextFiles() throws java.io.IOException {

        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }
            String input = inputBuffer.toString(); 

            FileUtils.writeTextFile("FileUtilsText", input); 
            String reverseInput = FileUtils.readTextFile("FileUtilsText.txt"); 
            boolean testResult = input.equals(reverseInput); 
        
            if (!testResult) {
                numberOfWrongResults++;
            }
        }
        assertEquals(0, numberOfWrongResults);
    }

}
