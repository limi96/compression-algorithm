
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays; 
import java.nio.ByteBuffer;

import algorithms.Huffman; 
import algorithms.utils.Node; 
import algorithms.utils.HuffmanUtils; 

public class huffmanUtilsTest {
    
    Huffman h; 
    Random rnd;
    int numberOfRepetitions; 
    int lengthOfInput; 
    int numberOfWrongResults;

    Node inputRootNode; 
    
    public huffmanUtilsTest() {
    
    }

    @Before
    public void setUp() {
        h = new Huffman(); 
        rnd = new Random();   
        numberOfWrongResults = 0;
        numberOfRepetitions = 100;  
        lengthOfInput = 100;  

    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSerializeAndDeserializeBFS() {
        
        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }
            
            h.encode(inputBuffer.toString());
            Node inputRootNode = h.getRootNode();
            ArrayList<String> input = HuffmanUtils.serializeBFS(inputRootNode);
            
            Node outputRootnode = HuffmanUtils.deserializeBFS(input); 
            ArrayList<String> reverseInput = HuffmanUtils.serializeBFS(outputRootnode);
            
            boolean testResult = input.toString().equals(reverseInput.toString()); 

            if (!testResult) {
                numberOfWrongResults++;
            }
        }

        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void testSeralizeAndDeserializeToBytes() throws java.io.IOException {
        for (int i = 0; i < numberOfRepetitions; i++) { 
            StringBuffer inputBuffer = new StringBuffer(); 

            for (int j = 0; j < lengthOfInput; j++) {
                inputBuffer.append((char)rnd.nextInt(256)); 
            }

            h.encode(inputBuffer.toString());
            inputRootNode = h.getRootNode();

            byte[] input = HuffmanUtils.serializeToBytes(inputRootNode);
            byte[] treeLengthData = Arrays.copyOfRange(input, 0, 4);

            ByteBuffer bufferForTreeLengthData = ByteBuffer.wrap(treeLengthData);
            int treeLength = bufferForTreeLengthData.getInt();
            byte[] treeData = Arrays.copyOfRange(input, 4, 4+treeLength);

            Node outputRootnode = HuffmanUtils.deserializeFromBytes(treeData); 
            byte[] reverseInput = HuffmanUtils.serializeToBytes(outputRootnode);
            boolean testResult = Arrays.equals(input, reverseInput); 

            if (!testResult) {  
                numberOfWrongResults++;
            }
        }
        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void testCombineTreeBytesWithLength() {

        for (int i = 0; i < numberOfRepetitions; i++) { 

            byte[] bytes = new byte[rnd.nextInt(lengthOfInput)]; 

            rnd.nextBytes(bytes); 
    
            int input = bytes.length; 
    
            byte[] outputBytes = HuffmanUtils.combineTreeBytesWithLength(bytes, input);
            
            byte[] treeLengthData = Arrays.copyOfRange(outputBytes, 0, 4);
            ByteBuffer bufferForTreeLengthData = ByteBuffer.wrap(treeLengthData);
            int reverseInput = bufferForTreeLengthData.getInt();
    
            boolean testResult = input == reverseInput; 
    
            if (!testResult) {  
                numberOfWrongResults++;
            }
         
        }

        assertEquals(0, numberOfWrongResults);
    }

    @Test
    public void testCombineTreeDataWithMessage() {
        for (int i = 0; i < numberOfRepetitions; i++) { 
            byte[] firstBytes = new byte[rnd.nextInt(lengthOfInput)]; 
            byte[] secondBytes = new byte[rnd.nextInt(lengthOfInput)]; 
            rnd.nextBytes(firstBytes); 
            rnd.nextBytes(secondBytes); 

            int firstLength = firstBytes.length; 

            byte[] outputBytes = HuffmanUtils.combineTreeDataWithMessage(firstBytes, secondBytes);
            byte[] firstOutputBytes = Arrays.copyOfRange(outputBytes, 0, firstLength); 
            byte[] secondOutputBytes = Arrays.copyOfRange(outputBytes, firstLength, outputBytes.length); 

            boolean testResult = Arrays.equals(firstBytes, firstOutputBytes) && Arrays.equals(secondBytes, secondOutputBytes); 

            if (!testResult) {  
                numberOfWrongResults++;
            }
        }
        assertEquals(0, numberOfWrongResults);
    }
}
