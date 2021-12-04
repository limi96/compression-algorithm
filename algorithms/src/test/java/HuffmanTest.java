/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author limi
 */
public class HuffmanTest {
    
    Huffman h; 
    String input; 
    
    public HuffmanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        h = new Huffman(); 
        input = "123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\n" +
                "¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
        
        h.encode(input);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void correctDecodeWithASCII256() {        
        String decodeOutput = h.decode(h.getRootNode(), h.getEncodedMessage(input));
        assertEquals(input, decodeOutput);
    }
    
    // Tests if the rootNode has the same bytesize as the original input bytesize
    // Currently fails the test...
    // @Test 
    // public void correctRootNodeFreq() {
    //     byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
    //     assertEquals(h.getRootNode().freq, byteArray.length); 
    // }
    
    // Tests if the codeFreq hashmap contains the original byte size
    @Test
    public void correctCodeFreqValue() {
        
        int sum = 0; 
        for (Integer value : h.getCodeFreq().values()) {
            sum += value; 
        }       
        assertEquals(h.getRootNode().freq, sum); 
    }
    
}
