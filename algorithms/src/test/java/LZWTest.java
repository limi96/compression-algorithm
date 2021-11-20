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

import java.util.ArrayList; 

/**
 *
 * @author limi
 */
public class LZWTest {
    
    
    LZW lzw; 
    
    public LZWTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lzw = new LZW(); 
    }

    @After
    public void tearDown() {
    }

    @Test
    public void correctDecodeWithASCII256() {
        String input = "123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\n" +
"¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
        
        lzw.compress(input);           
        ArrayList<Integer> encodedList = lzw.getEncodedList(); 
        String decodedOutput = lzw.uncompress(encodedList);
        
        assertEquals(input, decodedOutput);
    }
    

}
