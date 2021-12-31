
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import algorithms.Huffman;
import algorithms.utils.FileUtils;


public class huffmanTest {
    Random rnd; 
    Huffman h; 
    String input; 
    int numberOfRepetitions; 
    int lengthOfInput; 
    int numberOfWrongResults;
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        h = new Huffman(); 
        rnd = new Random();
        numberOfWrongResults = 0;
        numberOfRepetitions = 1000;  
        lengthOfInput = 10000;  
    }

    @After
    public void tearDown() {
    }

    @Test
    public void correctEncodeDecodeTestFiles() throws java.io.IOException {
        String[] inputFileNames = new String[]{"100_KB_lorem.txt", "artofwar.txt", "warandpeace.txt", "100_KB_cScSc.txt", "ASCII_256.txt"};

        for (String fileName : inputFileNames) {
            fileName = "test_files/" + fileName; 
            String input = FileUtils.readTextFile(fileName);
            
            h.encode(input);
            h.writeEncodedFile("huffmanTest"); 
            h.readEncodedInput("huffmanTest.hf", true);

            String reverseInput = h.decode(h.getInputRootNode(), h.getEncodedMessage()); 
            boolean testResult = input.equals(reverseInput); 

            if (!testResult) {
                numberOfWrongResults++;
            }
            assertEquals(0, numberOfWrongResults);
        }        
    }

    @Test
    public void correctEncodeDecodeRandomStrings() throws java.io.IOException {

        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }
            String input = inputBuffer.toString(); 
            h.encode(input);
            h.writeEncodedFile("huffmanRandomStrings");
            h.readEncodedInput(h.getFullEncodedMessage(), false);
            
            String reverseInput = h.decode(h.getInputRootNode(), h.getEncodedMessage()); 
            boolean testResult = input.equals(reverseInput); 


            if (!testResult) {
                numberOfWrongResults++;
            }
        }

        assertEquals(0, numberOfWrongResults);
    }    
}
