/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void correctDecodeWithAllAlphabets() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";

        h.encode(input);        
        String output = h.decode(h.getRootNode(), h.getEncodedMessage(input));
        assertEquals(input, output);
    }
    
    
}
