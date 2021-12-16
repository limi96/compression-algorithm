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

import algorithms.utils.Node;

import static org.junit.Assert.*;
/**
 *
 * @author limi
 */
public class NodeTest {
    
    
    Node test1;
    Node test2; 
    
    public NodeTest() {
    }
    

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        test1 = new Node("a", 12);        
        test2 = new Node("b", 10, test1, null);    
    }

    @After
    public void tearDown() {
    }
    @Test 
    public void correctConstruction() {
        assertEquals("a", test1.letter);
        assertEquals("b", test2.letter);
        assertEquals(12, test1.freq);
        assertEquals(10, test2.freq);
        assertEquals(test1, test2.left);
        assertEquals(null, test2.right);
    }
    
}
