import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.ArrayList; 
import algorithms.LZW;
import algorithms.utils.FileUtils;


public class lzwTest {
    
    LZW lzw; 
    Random rnd; 
    int numberOfRepetitions; 
    int lengthOfInput; 
    int numberOfWrongResults;

    @Before
    public void setUp() {
        lzw = new LZW(); 
        rnd = new Random();
        numberOfWrongResults = 0;
        numberOfRepetitions = 100;  
        lengthOfInput = 100;  
    }
    
    @Test
    public void varyingBitLengthsGiveSameASCIIOutput() {
        String input = FileUtils.readTextFile("test_files/ASCII_256.txt");

        for (int i = 8; i <= 32; i++) { 
            lzw.setBitLength(i);
            lzw.compress(input);
            ArrayList<Integer> encodedList = lzw.getEncoded(); 
            String reverseInput = lzw.uncompress(encodedList);
            boolean testResult = input.equals(reverseInput); 
    
            if (!testResult) {
                numberOfWrongResults++;
            }

        }    

        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void correctEncodeDecodeTestFiles() throws java.io.IOException {
        String[] inputFileNames = new String[]{"100_KB_lorem.txt", "100_KB_repeating_lorem_ipsum.txt", "100_KB_cScSc.txt", "ASCII_256.txt"};

        for (String fileName : inputFileNames) {
            fileName = "test_files/" + fileName; 
            String input = FileUtils.readTextFile(fileName);
            
            lzw.compress(input);
            lzw.writeLZWFile("lzwTest");
            lzw.readLZWFile("lzwTest.lzw");
            ArrayList<Integer> encodedList = lzw.getEncoded(); 
            String reverseInput = lzw.uncompress(encodedList);
            
            boolean testResult = input.equals(reverseInput); 

            if (!testResult) {
                numberOfWrongResults++;
            }
            assertEquals(0, numberOfWrongResults);
        }        
    }

    @Test
    public void correctEncodeDecodeRandomStrings() {

        for (int i = 0; i < numberOfRepetitions; i++) { 

            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }
            
            String input = inputBuffer.toString(); 
            lzw.compress(input);
            ArrayList<Integer> encodedList = lzw.getEncoded(); 
            String reverseInput = lzw.uncompress(encodedList);
                        
            boolean testResult = input.equals(reverseInput); 
            
            if (!testResult) {
                numberOfWrongResults++;
            }

        }
        assertEquals(0, numberOfWrongResults);
    }
}
